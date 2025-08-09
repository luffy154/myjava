package com.elevator.main;

/**
 * @ClassName:BitStorage
 * @author: qm
 * @Description:
 * @date:2025-08-06
 */
import java.util.Random;

public class BitStorage {
    public static void main(String[] args) {
        // 步骤 1：初始化字节数组
        byte[] bitArray = new byte[125000]; // 1000 位需要 125 个字节

        // 步骤 2：随机选择一个位置
        Random random = new Random();
        int randomBitIndex = random.nextInt(1000);  // 选择一个 0 到 999 的随机位置

        // 步骤 3：确定该位置所在的字节和在字节中的具体位置
        int byteIndex = randomBitIndex / 8;  // 找到字节的索引
        int bitIndex = randomBitIndex % 8;   // 找到字节内的位的索引

        // 步骤 4：通过按位或（|）操作将该位置设置为 1
        bitArray[byteIndex] |= (1 << (7 - bitIndex));  // 7 - bitIndex 是因为 byte 是按从左到右排列的，bitIndex 越小，对应字节位数越高

        // 打印结果：验证某个位置是否被设置为 1
        System.out.println("设置的位置是: " + randomBitIndex);
        System.out.println("字节数组中第 " + byteIndex + " 个字节是: " + Integer.toBinaryString(bitArray[byteIndex] & 0xFF));

        // 打印整个字节数组（每个字节用二进制表示）
        for (int i = 0; i < bitArray.length; i++) {
            System.out.print(String.format("%8s", Integer.toBinaryString(bitArray[i] & 0xFF)).replace(' ', '0') + " ");
        }

        System.out.println(bitArray[byteIndex] & ((1 << (7 - bitIndex))));
    }
}

