package org.example;

import java.util.HashSet;
import java.util.Set;

/*某公司部门需要派遣员工去国外做项目。现在，代号为x的国家和代号为y的国家分别需要cntx名和cnty名员工。
部门每个员工有一个员工号(1,2,3……)，工号连续，从1开始。

部长派遣员工的规则：

规则1、从[1,k]中选择员工派遣出去
规则2、编号为x的倍数的员工不能去x国，编号为y的倍数的员工不能去y国
问题：
找到最小的k，使得可以将编号在[1,k]中的员工分配给x国和y国，且满足x国和y国的需求*/
public class EmployeeDispatch {

    // 计算最小公倍数
    private static int lcm(int a, int b) {
        return a * b / gcd(a, b);
    }

    // 计算最大公约数
    private static int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    // 计算最小的k值
    public static int findMinimumK(int x, int y, int cntx, int cnty) {
        int k = Math.max(cntx, cnty);
        Set<Integer> multiplesOfX = new HashSet<>();
        Set<Integer> multiplesOfY = new HashSet<>();
        Set<Integer> multiplesOfBoth = new HashSet<>();

        while (true) {
            multiplesOfX.clear();
            multiplesOfY.clear();
            multiplesOfBoth.clear();

            // 计算符合条件的员工
            for (int i = 1; i <= k; i++) {
                if (i % x == 0) multiplesOfX.add(i);
                if (i % y == 0) multiplesOfY.add(i);
                if (i % lcm(x, y) == 0) multiplesOfBoth.add(i);
            }

            // 确定总数量
            int totalX = k - multiplesOfY.size() - multiplesOfBoth.size();
            int totalY = k - multiplesOfX.size() - multiplesOfBoth.size();

            if (totalX >= cntx && totalY >= cnty) {
                return k;
            }

            k++;
        }
    }

    public static void main(String[] args) {
        int x = 3;
        int y = 4;
        int cntx = 5;
        int cnty = 4;

        int k = findMinimumK(x, y, cntx, cnty);
        System.out.println("The minimum k is: " + k);
    }
}
