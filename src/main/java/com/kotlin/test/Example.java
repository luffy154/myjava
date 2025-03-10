package com.kotlin.test;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName:Example
 * @author: qm
 * @Description:
 * @date:2025-02-19
 */
public class Example {
    public static void main(String[] args) {
        ScriptManager scriptManager = new ScriptManager();

        // 简单计算示例
        String script = "val x = 10; val y = 20; x + y";
        Object result = scriptManager.executeScript(script, new HashMap<>());
        System.out.println("Result: " + result);  // 输出: 30

        // 带参数的脚本
        String paramScript = "val name = bindings[\"name\"] as String\n" +
                "            val age = bindings[\"age\"] as Int\n" +
                "            \"Hello, $name! You are $age years old.\"";

        Map<String, Object> params = new HashMap<>();
        params.put("name", "John");
        params.put("age", 30);

        Object paramResult = scriptManager.executeScript(paramScript, params);
        System.out.println(paramResult);
    }
}
