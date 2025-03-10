package com.engine.test;

import java.util.List;

/**
 * @ClassName:RuleEngine
 * @author: qm
 * @Description:
 * @date:2025-02-21
 */
public class RuleEngine {
    private List<Rule> rules;
    private RuleEvaluator evaluator;

    public RuleEngine(List<Rule> rules) {
        this.rules = rules;
        this.evaluator = new RuleEvaluator();
    }

    // 执行规则引擎
    public void execute(Object data) {
        for (Rule rule : rules) {
            if (evaluator.evaluateRule(data, rule)) {
                // 触发规则的动作
                System.out.println("Rule triggered: " + rule.getRuleName());
                executeAction(rule.getAction(), data);
            }
        }
    }

    private void executeAction(String action, Object data) {
        // 执行相应的动作，例如：发送通知、更新数据库等
        System.out.println("Executing action: " + action + " for data: " + data);
    }
}
