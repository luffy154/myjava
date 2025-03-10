package com.groovy.test;

import com.alibaba.fastjson2.JSON;
import groovy.lang.GroovyClassLoader;
import jdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;
import org.codehaus.groovy.ast.CodeVisitorSupport;
import org.codehaus.groovy.ast.expr.ClosureExpression;
import org.codehaus.groovy.ast.expr.DeclarationExpression;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.SourceUnit;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName:GroovyVrialbleTest
 * @author: qm
 * @Description:
 * @date:2025-03-10
 */
public class GroovyVrialbleTest {
    public static Set<String> extractVariables(String groovyExpression) {
        // 配置编译器
        CompilerConfiguration config = new CompilerConfiguration();
        config.setTargetDirectory(new File("target")); // 临时目录（可忽略）

        // 创建编译单元
        GroovyClassLoader classLoader = new GroovyClassLoader();
        SourceUnit sourceUnit = new SourceUnit("script", groovyExpression, config, classLoader, null);

        // 解析表达式生成 AST
        try {
            sourceUnit.parse();
            sourceUnit.completePhase();
            sourceUnit.convert();
        } catch (SyntaxException e) {
            throw new RuntimeException("表达式语法错误: " + e.getMessage());
        }

        // 遍历 AST 提取变量名
        // 收集所有变量声明和引用
        Set<String> declaredVars = new HashSet<>();
        Set<String> referencedVars = new HashSet<>();
        sourceUnit.getAST().getStatementBlock().visit(new CodeVisitorSupport() {
            @Override
            public void visitDeclarationExpression(DeclarationExpression expr) {
                expr.getVariableExpression().visit(this);
                declaredVars.add(expr.getVariableExpression().getName());
                super.visitDeclarationExpression(expr);
            }

            // 捕获变量引用（如 x + y）
            @Override
            public void visitVariableExpression(VariableExpression expr) {
                referencedVars.add(expr.getName());
                super.visitVariableExpression(expr);
            }

            // 捕获闭包参数（如 { a -> ... } 中的 a）
            @Override
            public void visitClosureExpression(ClosureExpression expr) {
                Arrays.stream(expr.getParameters()).forEach(param -> declaredVars.add(param.getName()));
                super.visitClosureExpression(expr);
            }
        });
        System.out.println(JSON.toJSONString(declaredVars));
        // 过滤：引用变量 - 声明变量 = 外部变量
        referencedVars.removeAll(declaredVars);
        return referencedVars;
    }

    public static void main(String[] args) {
        String expression = "def a=10,b=3;a + b * c - d"; // 示例表达式
        Set<String> variables = extractVariables(expression);
        System.out.println("变量名: " + variables); // 输出: [a, b, c, d]
    }
}
