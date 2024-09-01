package org.example;

// 定义树节点的数据结构
class ThreeTreeNode {
    int value;
    ThreeTreeNode left, middle, right;

    public ThreeTreeNode(int value) {
        this.value = value;
        this.left = null;
        this.middle = null;
        this.right = null;
    }
}

// 定义三叉搜索树的操作
class TriSearchTree {
    private ThreeTreeNode root;

    public TriSearchTree() {
        this.root = null;
    }

    // 插入一个新数
    public void insert(int value) {
        root = insertRec(root, value);
    }

    // 插入的递归实现
    private ThreeTreeNode insertRec(ThreeTreeNode node, int value) {
        if (node == null) {
            return new ThreeTreeNode(value);  // 找到插入位置
        }

        if (value < node.value - 500) {
            node.left = insertRec(node.left, value); // 插入左子树
        } else if (value > node.value + 500) {
            node.right = insertRec(node.right, value); // 插入右子树
        } else {
            node.middle = insertRec(node.middle, value); // 插入中子树
        }
        return node;
    }

    // 计算树的高度
    public int getHeight() {
        return getHeightRec(root);
    }

    // 计算高度的递归实现
    private int getHeightRec(ThreeTreeNode node) {
        if (node == null) {
            return 0;
        }

        int leftHeight = getHeightRec(node.left);
        int middleHeight = getHeightRec(node.middle);
        int rightHeight = getHeightRec(node.right);

        return 1 + Math.max(leftHeight, Math.max(middleHeight, rightHeight));
    }
}

// 测试主类
public class ThreeTree {
    public static void main(String[] args) {
        TriSearchTree tree = new TriSearchTree();
        int[] values = {1000, 1500, 2000, 1200, 800, 1800, 2500};

        for (int value : values) {
            tree.insert(value);
        }

        int height = tree.getHeight();
        System.out.println("树的高度为: " + height);
    }
}

