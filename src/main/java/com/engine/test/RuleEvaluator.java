package com.engine.test;

/**
 * @ClassName:RuleEvaluator
 * @author: qm
 * @Description:
 * @date:2025-02-21
 */
public class RuleEvaluator {
    // 假设规则是基于条件字符串，实际情况中可以使用更复杂的表达式引擎（如 MVEL 或 SpEL）
    public boolean evaluateRule(Object data, Rule rule) {
        // 解析规则条件并评估，示例中直接返回 true
        // 这里你可以实现条件匹配逻辑，实际的业务可以依赖于更复杂的规则引擎
        return data.toString().contains(rule.getCondition()); // 仅作为示例
    }
}
