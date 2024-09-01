package org.example;

import java.util.ArrayList;
import java.util.List;
//在一棵树中，每个节点代表一个家庭成员，节点的数字表示其个人的财富值，一个节点及其直接相连的子节点被定义为一个小家庭。
//现给你一棵树，请计算出最富裕的小家庭的财富和
class TreeNode4 {
    int wealth;
    List<TreeNode4> children;

    TreeNode4(int wealth) {
        this.wealth = wealth;
        this.children = new ArrayList<>();
    }
}
/*在一棵树中，每个节点代表一个家庭成员，节点的数字表示其个人的财富值，一个节点及其直接相连的子节点被定义为一个小家庭。
现给你一棵树，请计算出最富裕的小家庭的财富和。
*/
public class WealthiestFamily {
    public static void main(String[] args) {
        // 示例树的构建
        TreeNode4 root = new TreeNode4(10);
        TreeNode4 child1 = new TreeNode4(5);
        TreeNode4 child2 = new TreeNode4(3);
        TreeNode4 child3 = new TreeNode4(8);
        TreeNode4 child4 = new TreeNode4(2);

        root.children.add(child1);
        root.children.add(child2);
        child1.children.add(child3);
        child2.children.add(child4);

        System.out.println("最富裕的小家庭的财富和是: " + findWealthiestFamily(root));
    }

    public static int findWealthiestFamily(TreeNode4 root) {
        if (root == null) {
            return 0;
        }

        int[] maxWealth = {Integer.MIN_VALUE};
        calculateFamilyWealth(root, maxWealth);
        return maxWealth[0];
    }

    private static int calculateFamilyWealth(TreeNode4 node, int[] maxWealth) {
        if (node == null) {
            return 0;
        }

        int currentWealth = node.wealth;

        for (TreeNode4 child : node.children) {
            currentWealth += child.wealth;
        }

        maxWealth[0] = Math.max(maxWealth[0], currentWealth);

        // 继续递归检查子节点
        for (TreeNode4 child : node.children) {
            calculateFamilyWealth(child, maxWealth);
        }

        return currentWealth;
    }
}

