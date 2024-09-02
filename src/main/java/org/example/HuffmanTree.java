package org.example;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.ArrayList;
import java.util.List;
/*给定长度为n的无序的数字数组，每个数字代表二叉树的叶子节点的权值，数字数组的值均大于等于1。
    请完成一个函数，根据输入的数字数组，生成哈夫曼树，并将哈夫曼树按照中序遍历输出。
    为了保证输出的二叉树中序遍历结果统一，增加以下限制：二叉树节点中，左节点权值小于等于右节点权值，
    根节点权值为左右节点权值之和。当左右节点权值相同时，左子树高度高度小于等于右子树。
    注意：所有用例保证有效，并能生成哈夫曼树。
    提醒：哈夫曼树又称最优二叉树，是一种带权路径长度最短的二叉树。所谓树的带权路径长度，
    就是树中所有的叶结点的权值乘上其到根结点的路径长度（若根结点为0层，叶结点到根结点的路径长度为叶结点的层数）。
    例如：
    由叶子节点5 15 40 30 10生成的最优二叉树如下图所示，该树的最短带权路径长度为40*1+30*2+15*3+5*4+10*4=205。*/
// 定义树节点类
class TreeNode5 implements Comparable<TreeNode5> {
    int weight;
    TreeNode5 left, right;

    public TreeNode5(int weight, TreeNode5 left, TreeNode5 right) {
        this.weight = weight;
        this.left = left;
        this.right = right;
    }

    // 用于优先队列中的比较
    @Override
    public int compareTo(TreeNode5 other) {
        if (this.weight == other.weight) {
            // 当权值相等时，确保左子树优先
            return (this.left == null ? 0 : 1) - (other.left == null ? 0 : 1);
        }
        return this.weight - other.weight;
    }
}

public class HuffmanTree {

    // 构建哈夫曼树
    public static TreeNode5 buildHuffmanTree(int[] weights) {
        // 使用优先队列（最小堆）
        PriorityQueue<TreeNode5> heap = new PriorityQueue<>();

        // 创建叶子节点并加入到堆中
        for (int weight : weights) {
            heap.add(new TreeNode5(weight, null, null));
        }

        // 合并节点直到堆中只剩一个节点
        while (heap.size() > 1) {
            TreeNode5 left = heap.poll();
            TreeNode5 right = heap.poll();
            TreeNode5 newNode = new TreeNode5(left.weight + right.weight, left, right);
            heap.add(newNode);
        }

        return heap.poll();
    }

    // 中序遍历哈夫曼树
    public static List<Integer> inorderTraversal(TreeNode5 node) {
        List<Integer> result = new ArrayList<>();
        if (node != null) {
            result.addAll(inorderTraversal(node.left));
            result.add(node.weight);
            result.addAll(inorderTraversal(node.right));
        }
        return result;
    }

    // 主函数
    public static void main(String[] args) {
        int[] weights = {5, 15, 40, 30, 10};
        TreeNode5 root = buildHuffmanTree(weights);
        List<Integer> inorderResult = inorderTraversal(root);
        System.out.println("Inorder Traversal of Huffman Tree: " + inorderResult);
    }
}

