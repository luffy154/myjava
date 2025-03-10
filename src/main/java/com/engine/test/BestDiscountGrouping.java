package com.engine.test;

/**
 * @ClassName:BestDiscountGrouping
 * @author: qm
 * @Description:
 * @date:2025-02-21
 */
import java.util.*;

public class BestDiscountGrouping {
    static class Product {
        double price;
        int type; // 1 = 只能8折, 2 = 只能满减, 3 = 可选最优

        public Product(double price, int type) {
            this.price = price;
            this.type = type;
        }
    }

    static double bestTotalPrice = Double.MAX_VALUE;

    // 计算 8 折价格
    private static double apply80Discount(double sum) {
        return sum >= 30 ? sum * 0.8 : sum;
    }

    // 计算 满 20 减 5 价格
    private static double applyFullReduction(double sum) {
        return sum >= 20 ? sum - 5 * Math.floor(sum / 20) : sum;
    }

    // 回溯 + 贪心寻找最优分组
    private static void findBestGrouping(List<Product> products, List<List<Product>> groups, int index) {
        if (index == products.size()) {
            // 计算当前分组的总价
            double totalPrice = 0.0;
            for (List<Product> group : groups) {
                double sum = group.stream().mapToDouble(p -> p.price).sum();
                boolean has80 = group.stream().anyMatch(p -> p.type == 1 || p.type == 3);
                boolean hasFullReduction = group.stream().anyMatch(p -> p.type == 2 || p.type == 3);

                if (has80 && hasFullReduction) {
                    // 该组可以选择 8 折 或 满减，选最优
                    totalPrice += Math.min(apply80Discount(sum), applyFullReduction(sum));
                } else if (has80) {
                    totalPrice += apply80Discount(sum);
                } else if (hasFullReduction) {
                    totalPrice += applyFullReduction(sum);
                } else {
                    totalPrice += sum; // 无法触发任何优惠
                }
            }
            bestTotalPrice = Math.min(bestTotalPrice, totalPrice);
            return;
        }

        // 遍历所有可能的分组方式
        Product current = products.get(index);
        for (List<Product> group : groups) {
            group.add(current);
            findBestGrouping(products, groups, index + 1);
            group.remove(group.size() - 1);
        }

        // 作为新的一组
        List<Product> newGroup = new ArrayList<>();
        newGroup.add(current);
        groups.add(newGroup);
        findBestGrouping(products, groups, index + 1);
        groups.remove(groups.size() - 1);
    }

    public static double getOptimalTotal(List<Product> products) {
        bestTotalPrice = Double.MAX_VALUE;
        findBestGrouping(products, new ArrayList<>(), 0);
        return bestTotalPrice;
    }

    public static void main(String[] args) {
        List<Product> products = Arrays.asList(
                new Product(20, 1),  // 只能 8 折
                new Product(40, 2),  // 只能满减
                new Product(100, 3), // 两者都可
                new Product(10, 3),  // 两者都可
                new Product(15, 2)   // 只能满减
        );

        System.out.println("最优总价: " + getOptimalTotal(products));
    }
}

