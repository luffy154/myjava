package org.example;

import java.util.*;

/*有一棵二叉树，每个节点由一个大写字母标识(最多26个节点）。
现有两组字母，分别表示后序遍历（左孩子->右孩子->父节点）
和中序遍历（左孩子->父节点->右孩子）的结果，
请输出层次遍历的结果。*/
class TreeNode2 {
    char val;
    TreeNode2 left, right;

    TreeNode2(char x) {
        val = x;
        left = right = null;
    }
}

public class BinaryTree {
    private Map<Character, Integer> inorderMap; // 用于快速查找根节点在中序遍历中的位置

    // 根据后序和中序遍历结果构造二叉树
    public TreeNode2 buildTree(char[] inorder, char[] postorder) {
        int n = inorder.length;
        inorderMap = new HashMap<>();
        for (int i = 0; i < n; i++) {
            inorderMap.put(inorder[i], i);
        }
        return buildTreeHelper(postorder, 0, n - 1, 0, n - 1);
    }

    // 辅助函数，通过递归构建二叉树
    private TreeNode2 buildTreeHelper(char[] postorder, int postStart, int postEnd, int inStart, int inEnd) {
        if (postStart > postEnd || inStart > inEnd) {
            return null;
        }

        // 根节点是后序遍历的最后一个节点
        char rootVal = postorder[postEnd];
        TreeNode2 root = new TreeNode2(rootVal);

        // 在中序遍历中找到根节点的位置
        int rootIndex = inorderMap.get(rootVal);

        // 计算左子树的节点数
        int leftSize = rootIndex - inStart;

        // 递归构建左子树和右子树
        root.left = buildTreeHelper(postorder, postStart, postStart + leftSize - 1, inStart, rootIndex - 1);
        root.right = buildTreeHelper(postorder, postStart + leftSize, postEnd - 1, rootIndex + 1, inEnd);

        return root;
    }

    // 层次遍历二叉树
    public List<Character> levelOrder(TreeNode2 root) {
        List<Character> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Queue<TreeNode2> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            TreeNode2 node = queue.poll();
            result.add(node.val);

            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }

        return result;
    }

    public static void main(String[] args) {
        BinaryTree binaryTree = new BinaryTree();
        char[] inorder = {'D', 'B', 'E', 'A', 'F', 'C'};
        char[] postorder = {'D', 'E', 'B', 'F', 'C', 'A'};

        TreeNode2 root = binaryTree.buildTree(inorder, postorder);
        List<Character> levelOrderResult = binaryTree.levelOrder(root);

        for (char c : levelOrderResult) {
            System.out.print(c + " ");
        }
        // 示例输出：A B C D E F
    }
}
