package org.example;

import java.util.Stack;

//给定一个表达式，求其分数计算结果
//
//        表达式的限制如下：
//
//        1. 所有的输入数字皆为正整数(包括0)
//        2. 仅支持四则运算(+-*/)和括号
//        3. 结果为整数或分数, 分数必须化为最简格式(比如6, 3/4, 7/8, 90/7)
//        4. 除数可能为0，如果遇到这种情况，直接输出"ERROR"
//        5. 输入和最终计算结果中的数字都不会超出整型范围
//
//        用例的输入一定合法, 不会出现括号不匹配的情况
public class FractionCalculator {

    // 定义一个表示分数的类
    static class Fraction {
        int numerator;  // 分子
        int denominator;  // 分母

        public Fraction(int numerator, int denominator) {
            if (denominator == 0) {
                throw new ArithmeticException("ERROR");
            }
            if (denominator < 0) {  // 保证分母为正
                numerator = -numerator;
                denominator = -denominator;
            }
            int gcd = gcd(Math.abs(numerator), Math.abs(denominator));
            this.numerator = numerator / gcd;
            this.denominator = denominator / gcd;
        }

        // 加法
        public Fraction add(Fraction other) {
            return new Fraction(
                    this.numerator * other.denominator + other.numerator * this.denominator,
                    this.denominator * other.denominator
            );
        }

        // 减法
        public Fraction subtract(Fraction other) {
            return new Fraction(
                    this.numerator * other.denominator - other.numerator * this.denominator,
                    this.denominator * other.denominator
            );
        }

        // 乘法
        public Fraction multiply(Fraction other) {
            return new Fraction(
                    this.numerator * other.numerator,
                    this.denominator * other.denominator
            );
        }

        // 除法
        public Fraction divide(Fraction other) {
            return new Fraction(
                    this.numerator * other.denominator,
                    this.denominator * other.numerator
            );
        }

        // 求最大公约数
        private int gcd(int a, int b) {
            while (b != 0) {
                int temp = b;
                b = a % b;
                a = temp;
            }
            return a;
        }

        @Override
        public String toString() {
            if (denominator == 1) {
                return String.valueOf(numerator);
            }
            return numerator + "/" + denominator;
        }
    }

    public static String evaluateExpression(String expression) {
        try {
            return evaluate(expression).toString();
        } catch (ArithmeticException e) {
            return "ERROR";
        }
    }

    private static Fraction evaluate(String expression) {
        Stack<Fraction> fractions = new Stack<>();
        Stack<Character> operators = new Stack<>();
        int i = 0;

        while (i < expression.length()) {
            char c = expression.charAt(i);

            if (c == ' ') {
                i++;
                continue;
            }

            if (c == '(') {
                int j = findClosingParenthesis(expression, i);
                fractions.push(evaluate(expression.substring(i + 1, j)));
                i = j + 1;
            } else if (Character.isDigit(c)) {
                int j = i;
                while (j < expression.length() && Character.isDigit(expression.charAt(j))) {
                    j++;
                }
                fractions.push(new Fraction(Integer.parseInt(expression.substring(i, j)), 1));
                i = j;
            } else if (c == '+' || c == '-' || c == '*' || c == '/') {
                while (!operators.isEmpty() && precedence(operators.peek()) >= precedence(c)) {
                    char operator = operators.pop();
                    Fraction b = fractions.pop();
                    Fraction a = fractions.pop();
                    fractions.push(applyOperator(a, b, operator));
                }
                operators.push(c);
                i++;
            }
        }

        while (!operators.isEmpty()) {
            char operator = operators.pop();
            Fraction b = fractions.pop();
            Fraction a = fractions.pop();
            fractions.push(applyOperator(a, b, operator));
        }

        return fractions.pop();
    }

    private static Fraction applyOperator(Fraction a, Fraction b, char operator) {
        switch (operator) {
            case '+':
                return a.add(b);
            case '-':
                return a.subtract(b);
            case '*':
                return a.multiply(b);
            case '/':
                return a.divide(b);
        }
        throw new UnsupportedOperationException("Invalid operator");
    }

    private static int precedence(char operator) {
        if (operator == '+' || operator == '-') {
            return 1;
        } else if (operator == '*' || operator == '/') {
            return 2;
        }
        return 0;
    }

    private static int findClosingParenthesis(String expression, int startIndex) {
        int count = 0;
        for (int i = startIndex; i < expression.length(); i++) {
            if (expression.charAt(i) == '(') count++;
            if (expression.charAt(i) == ')') count--;
            if (count == 0) return i;
        }
        throw new IllegalArgumentException("Mismatched parentheses");
    }

    public static void main(String[] args) {
        String expression1 = "1/2 + 3/4 * (1 + 1/2)";
        String expression2 = "1/0 + 3/4"; // Should return ERROR
        System.out.println(evaluateExpression(expression1)); // Output: 5/4
        System.out.println(evaluateExpression(expression2)); // Output: ERROR
    }
}
