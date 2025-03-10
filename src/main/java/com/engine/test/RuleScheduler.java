package com.engine.test;

import java.util.concurrent.BlockingQueue;

/**
 * @ClassName:RuleScheduler
 * @author: qm
 * @Description:
 * @date:2025-02-21
 */
public class RuleScheduler implements Runnable{
    private BlockingQueue<Object> queue;
    private RuleEngine ruleEngine;

    public RuleScheduler(BlockingQueue<Object> queue, RuleEngine ruleEngine) {
        this.queue = queue;
        this.ruleEngine = ruleEngine;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Object data = queue.take(); // 从队列中获取实时数据
                ruleEngine.execute(data); // 执行规则引擎
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
