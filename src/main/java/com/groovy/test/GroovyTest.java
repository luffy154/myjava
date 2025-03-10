package com.groovy.test;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName:GroovyTest
 * @author: qm
 * @Description:
 * @date:2025-03-10
 */
public class GroovyTest {
    public static void main(String[] args) {
        GroovyShell shell = new GroovyShell();
        String script = "def greet(name) { return 'Hello, ' + name }; return greet('World')";
        Object result = shell.evaluate(script);
        System.out.println(result);

       script = "return 'Hello, Groovy ' + name"; // 直接执行表达式

        shell = new GroovyShell();
        Script groovyScript = shell.parse(script);

        // 绑定变量并执行
        groovyScript.setProperty("name", "Java");
        result = groovyScript.run();

        System.out.println(result);

        testBinding();
    }

    public static void testBinding() {
        // 定义外部Map对象
        Map<String, Object> variables = new HashMap<>();
        variables.put("x", 10);
        variables.put("y", 20);
        variables.put("z", 30);

        // 定义Groovy表达式
        String expression = "x=10;x + y * z";

        // 执行表达式并绑定Map
        Object result = evaluateExpression(expression, variables);

        // 输出结果
        System.out.println("Result of expression: " + result);
    }

    public static Object evaluateExpression(String expression, Map<String, Object> variables) {
        // 创建Binding对象并绑定Map中的变量
        Binding binding = new Binding();
        for (Map.Entry<String, Object> entry : variables.entrySet()) {
            binding.setVariable(entry.getKey(), entry.getValue());
        }

        // 创建GroovyShell并绑定Binding
        GroovyShell shell = new GroovyShell(binding);

        // 执行表达式并返回结果
        return shell.evaluate(expression);
    }
}
