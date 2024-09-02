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
    /*定义构造三叉搜索树规则如下：
    每个节点都存有一个数，当插入一个新的数时，从根节点向下寻找，直到找到一个合适的空节点插入。
    查找的规则是：
            1. 如果数小于节点的数减去500，则将数插入节点的左子树
        2. 如果数大于节点的数加上500，则将数插入节点的右子树
        3. 否则，将数插入节点的中子树
    给你一系列数，请按以上规则，按顺序将数插入树中，构建出一棵三叉搜索树，最后输出树的高度。
*/
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

