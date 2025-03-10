package com.jython.test;

import org.python.util.PythonInterpreter;

import java.util.Objects;

/**
 * @ClassName:JythonTest
 * @author: qm
 * @Description:
 * @date:2025-02-28
 */
public class JythonTest {
    public static void main(String[] args) {
        // 创建 Jython 解释器
        PythonInterpreter interpreter = new PythonInterpreter();

        // 在 Java 中设置变量，传递给 Jython 脚本
        interpreter.set("name", "World");

        // 执行 Jython 脚本
        interpreter.exec("print('Hello, ' + name + '!')");

        // 调用 Jython 函数
        interpreter.exec("def greet(name):\n" +
                "    return 'Greetings, ' + name + '!'\n");
        interpreter.exec("result = greet(name)");

        // 从 Jython 中获取结果
        String result = interpreter.get("result", String.class);
        System.out.println("Jython function result: " + result);

        String absolutePath = JythonTest.class.getClassLoader().getResource("mytest.py").getPath();
        interpreter.exec("import sys");
        interpreter.exec("sys.path.append('"+absolutePath+"')");
        interpreter.exec("import mytest");
        interpreter.exec("result1 = mytest.add(10, 20)");
        Integer a = interpreter.get("result", Integer.class);
        System.out.println("Jython function result: " + a);
        // 关闭解释器
        interpreter.close();
    }
}
