package com.engine.test;

import java.util.concurrent.BlockingQueue;

/**
 * @ClassName:DataFetcher
 * @author: qm
 * @Description:
 * @date:2025-02-21
 */
public class DataFetcher implements Runnable{
    private BlockingQueue<Object> queue; // 假设我们使用一个阻塞队列来存储获取到的数据

    public DataFetcher(BlockingQueue<Object> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        // 在实际应用中，这里可以连接到 Kafka、数据库等数据源
        while (true) {
            try {
                // 模拟实时数据获取
                Object data = fetchData();
                queue.put(data); // 将数据放入队列中供后续处理
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private Object fetchData() {
        // 模拟获取实时数据
        return "Sample Data: " + System.currentTimeMillis();
    }
}
