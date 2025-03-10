package com.engine.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @ClassName:Main
 * @author: qm
 * @Description:
 * @date:2025-02-21
 */
public class Main {
    public static void main(String[] args) {
        // 1. 初始化规则
        List<Rule> rules = new ArrayList<>();
        rules.add(new Rule("Rule1", "Sample", "Action1"));
        RuleEngine ruleEngine = new RuleEngine(rules);

        // 2. 初始化队列
        BlockingQueue<Object> queue = new LinkedBlockingQueue<>();

        // 3. 启动数据获取线程
        DataFetcher dataFetcher = new DataFetcher(queue);
        new Thread(dataFetcher).start();

        // 4. 启动规则调度线程
        RuleScheduler ruleScheduler = new RuleScheduler(queue, ruleEngine);
        new Thread(ruleScheduler).start();
    }
}
