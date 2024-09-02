package org.example;

import java.util.*;
/*某个产品当前迭代周期内有N个特性（）需要进行覆盖测试，每个特性都被评估了对应的优先级，特性使用其ID作为下标进行标识。
    设计了M个测试用例（），每个用例对应了一个覆盖特性的集合，测试用例使用其ID作为下标进行标识，测试用例的优先级定义为其覆盖的特性的优先级之和。
    在开展测试之前，需要制定测试用例的执行顺序，规则为：优先级大的用例先执行，如果存在优先级相同的用例，用例ID小的先执行。*/
public class TestCasePriority {
    static class TestCase {
        int id;
        int priority;

        TestCase(int id, int priority) {
            this.id = id;
            this.priority = priority;
        }
    }

    /*5         // N = 5 个特性
            1 2 3 4 5 // 特性优先级：特性0优先级为1，特性1优先级为2，依次类推
            3         // M = 3 个测试用例
            2 0 1     // 测试用例0覆盖特性0和1
            2 2 3     // 测试用例1覆盖特性2和3
            2 3 4     // 测试用例2覆盖特性3和4

            2 1 0*/
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 输入特性数N
        int N = scanner.nextInt();
        int[] featurePriority = new int[N];

        // 输入特性优先级
        for (int i = 0; i < N; i++) {
            featurePriority[i] = scanner.nextInt();
        }

        // 输入测试用例数M
        int M = scanner.nextInt();
        List<TestCase> testCases = new ArrayList<>();

        // 输入每个测试用例覆盖的特性集合，并计算优先级
        for (int i = 0; i < M; i++) {
            int numFeatures = scanner.nextInt();
            int prioritySum = 0;

            for (int j = 0; j < numFeatures; j++) {
                int featureId = scanner.nextInt();
                prioritySum += featurePriority[featureId];
            }

            testCases.add(new TestCase(i, prioritySum));
        }

        // 按优先级降序排序，如果优先级相同则按ID升序
        testCases.sort((a, b) -> {
            if (a.priority != b.priority) {
                return b.priority - a.priority;
            } else {
                return a.id - b.id;
            }
        });

        // 输出排序后的测试用例ID
        for (TestCase testCase : testCases) {
            System.out.print(testCase.id + " ");
        }
    }
}

