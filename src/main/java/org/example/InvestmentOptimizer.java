package org.example;

import java.util.*;
/*在一款虚拟游戏中生活，你必须进行投资以增强在虚拟游戏中的资产以免被淘汰出局。现有一家Bank，它提供有若干理财产品m，风险及投资回报不同，你有N（元）进行投资，能接受的总风险值为X。
        你要在可接受范围内选择最优的投资方式获得最大回报。
        说明：
        在虚拟游戏中，每项投资风险值相加为总风险值；
        在虚拟游戏中，最多只能投资2个理财产品；
        在虚拟游戏中，最小单位为整数，不能拆分为小数；
        投资额*回报率=投资回报*/
public class InvestmentOptimizer {

    static class Investment {
        int risk;
        double returnRate;
        int minAmount;

        Investment(int risk, double returnRate, int minAmount) {
            this.risk = risk;
            this.returnRate = returnRate;
            this.minAmount = minAmount;
        }
    }

    public static double maximizeReturn(int N, int X, List<Investment> investments) {
        int m = investments.size();
        double maxReturn = 0.0;

        // 单个投资的情况
        for (int i = 0; i < m; i++) {
            Investment inv1 = investments.get(i);
            if (inv1.minAmount <= N && inv1.risk <= X) {
                double return1 = inv1.minAmount * inv1.returnRate;
                maxReturn = Math.max(maxReturn, return1);
            }
        }

        // 两个投资的情况
        for (int i = 0; i < m; i++) {
            Investment inv1 = investments.get(i);
            for (int j = i + 1; j < m; j++) {
                Investment inv2 = investments.get(j);

                // 遍历所有可能的投资组合
                for (int amount1 = inv1.minAmount; amount1 <= N; amount1 += inv1.minAmount) {
                    for (int amount2 = inv2.minAmount; amount1 + amount2 <= N; amount2 += inv2.minAmount) {
                        if (amount1 >= inv1.minAmount && amount2 >= inv2.minAmount) {
                            int totalRisk = inv1.risk + inv2.risk;
                            if (totalRisk <= X) {
                                double totalReturn = amount1 * inv1.returnRate + amount2 * inv2.returnRate;
                                maxReturn = Math.max(maxReturn, totalReturn);
                            }
                        }
                    }
                }
            }
        }

        return maxReturn;
    }

    public static void main(String[] args) {
        int N = 100; // 总投资额
        int X = 15;  // 最大风险值

        List<Investment> investments = new ArrayList<>();
        investments.add(new Investment(5, 0.1, 20));
        investments.add(new Investment(10, 0.2, 30));
        investments.add(new Investment(8, 0.15, 25));

        double result = maximizeReturn(N, X, investments);
        System.out.println("Maximum possible return: " + result);
    }
}

