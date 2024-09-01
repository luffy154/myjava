package org.example;

import java.util.*;
import java.time.Instant;

    /*请设计一个文件缓存系统，该文件缓存系统可以指定缓存的最大值（单位为字节）。

    文件缓存系统有两种操作：存储文件(put)和读取文件(get)

    操作命令为put fileName fileSize或者get fileName

    存储文件是把文件放入文件缓存系统中；读取文件是从文件缓存系统中访问已存在的文件，如果文件不存在，则不作任何操作。

    当缓存空间不足以存放新的文件时，根据规则删除文件，直到剩余空间满足新的文件大小为止，再存放新文件。
    具体的删除规则为：
    文件访问过后，会更新文件的最近访问时间和总的访问次数，当缓存不够时，
    按照第一优先顺序为访问次数从少到多，第二顺序为时间从老到新的方式来删除文件。*/

public class FileCacheSystem {

    private final int maxSize; // 最大缓存空间（字节）
    private int currentSize; // 当前缓存占用空间
    private final LinkedHashMap<String, FileInfo> cache;

    public FileCacheSystem(int maxSize) {
        this.maxSize = maxSize;
        this.currentSize = 0;
        this.cache = new LinkedHashMap<String, FileInfo>(16, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, FileInfo> eldest) {
                return currentSize > maxSize;
            }
        };
    }

    public void put(String fileName, int fileSize) {
        if (fileSize > maxSize) {
            System.out.println("Error: File size exceeds maximum cache size.");
            return;
        }

        // Check if file is already in cache
        if (cache.containsKey(fileName)) {
            FileInfo file = cache.get(fileName);
            currentSize -= file.size; // Remove old size
            file.size = fileSize; // Update file size
            currentSize += fileSize; // Add new size
            file.updateAccessInfo(); // Update access info
        } else {
            // Remove files to make space if needed
            while (currentSize + fileSize > maxSize) {
                removeLeastRecentlyUsedFile();
            }
            // Add new file
            cache.put(fileName, new FileInfo(fileSize));
            currentSize += fileSize;
        }
    }

    public void get(String fileName) {
        if (cache.containsKey(fileName)) {
            FileInfo file = cache.get(fileName);
            file.updateAccessInfo();
        }
    }

    private void removeLeastRecentlyUsedFile() {
        // Find the file with least access count, and if tie, oldest access time
        String fileToRemove = null;
        FileInfo minFile = null;

        for (Map.Entry<String, FileInfo> entry : cache.entrySet()) {
            FileInfo file = entry.getValue();
            if (minFile == null || file.accessCount < minFile.accessCount ||
                    (file.accessCount == minFile.accessCount && file.lastAccessTime.isBefore(minFile.lastAccessTime))) {
                minFile = file;
                fileToRemove = entry.getKey();
            }
        }

        if (fileToRemove != null) {
            FileInfo removedFile = cache.remove(fileToRemove);
            currentSize -= removedFile.size;
        }
    }

    private static class FileInfo {
        int size; // 文件大小
        int accessCount; // 访问次数
        Instant lastAccessTime; // 最近访问时间

        FileInfo(int size) {
            this.size = size;
            this.accessCount = 1;
            this.lastAccessTime = Instant.now();
        }

        void updateAccessInfo() {
            this.accessCount++;
            this.lastAccessTime = Instant.now();
        }
    }

    public static void main(String[] args) {
        FileCacheSystem cache = new FileCacheSystem(10);

        cache.put("file1", 3);
        cache.put("file2", 4);
        cache.get("file1");
        cache.put("file3", 2); // Should remove file2 to make space
        cache.get("file2"); // file2 should not be found
        cache.put("file4", 1); // Should be able to add file4

        // Checking results
        System.out.println("Current Cache: " + cache.cache);
    }
}
