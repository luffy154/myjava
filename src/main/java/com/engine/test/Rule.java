package com.engine.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName:Rule
 * @author: qm
 * @Description:
 * @date:2025-02-21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rule {
    private String ruleName;
    private String condition; // 条件表达式
    private String action; // 满足规则时执行的动作
}
