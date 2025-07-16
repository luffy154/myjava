package org.example;

import java.util.LinkedList;
import java.util.Queue;

public class RedBlackTree<K extends Comparable<K>, V> {

    // 定义节点颜色常量
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    // 树节点类
    private class Node {
        K key;
        V value;
        Node left, right;
        boolean color; // 节点颜色
        int size;     // 子树节点数

        public Node(K key, V value, boolean color, int size) {
            this.key = key;
            this.value = value;
            this.color = color;
            this.size = size;
        }
    }

    private Node root; // 根节点

    public RedBlackTree() {
        // 初始化空树
    }

    // 判断节点颜色（空节点为黑色）
    private boolean isRed(Node node) {
        return node != null && node.color == RED;
    }

    // 获取子树节点数
    private int size(Node node) {
        return node == null ? 0 : node.size;
    }

    public int size() {
        return size(root);
    }

    public boolean isEmpty() {
        return root == null;
    }

    // 查找键对应的值
    public V get(K key) {
        if (key == null) throw new IllegalArgumentException("Key cannot be null");
        return get(root, key);
    }

    private V get(Node node, K key) {
        while (node != null) {
            int cmp = key.compareTo(node.key);
            if (cmp < 0) node = node.left;
            else if (cmp > 0) node = node.right;
            else return node.value;
        }
        return null;
    }

    // 检查键是否存在
    public boolean contains(K key) {
        return get(key) != null;
    }

    // 插入键值对
    public void put(K key, V value) {
        if (key == null) throw new IllegalArgumentException("Key cannot be null");
        if (value == null) {
            delete(key);
            return;
        }

        root = put(root, key, value);
        root.color = BLACK; // 根节点始终为黑色
    }

    private Node put(Node node, K key, V value) {
        if (node == null) return new Node(key, value, RED, 1); // 新节点为红色

        int cmp = key.compareTo(node.key);
        if (cmp < 0) node.left = put(node.left, key, value);
        else if (cmp > 0) node.right = put(node.right, key, value);
        else node.value = value; // 更新已有键的值

        // 修复红黑树性质
        if (isRed(node.right) && !isRed(node.left)) node = rotateLeft(node);
        if (isRed(node.left) && isRed(node.left.left)) node = rotateRight(node);
        if (isRed(node.left) && isRed(node.right)) flipColors(node);

        node.size = 1 + size(node.left) + size(node.right);
        return node;
    }

    // 删除最小键
    public void deleteMin() {
        if (isEmpty()) throw new IllegalStateException("Tree is empty");

        // 如果根节点的两个子节点都是黑色，将根节点设为红色
        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;

        root = deleteMin(root);
        if (!isEmpty()) root.color = BLACK;
    }

    private Node deleteMin(Node node) {
        if (node.left == null) return null;

        if (!isRed(node.left) && !isRed(node.left.left))
            node = moveRedLeft(node);

        node.left = deleteMin(node.left);
        return balance(node);
    }

    // 删除键
    public void delete(K key) {
        if (key == null) throw new IllegalArgumentException("Key cannot be null");
        if (!contains(key)) return;

        // 如果根节点的两个子节点都是黑色，将根节点设为红色
        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;

        root = delete(root, key);
        if (!isEmpty()) root.color = BLACK;
    }

    private Node delete(Node node, K key) {
        if (key.compareTo(node.key) < 0) {
            if (!isRed(node.left) && node.left != null && !isRed(node.left.left))
                node = moveRedLeft(node);
            node.left = delete(node.left, key);
        } else {
            if (isRed(node.left))
                node = rotateRight(node);
            if (key.compareTo(node.key) == 0 && (node.right == null))
                return null;
            if (!isRed(node.right) && node.right != null && !isRed(node.right.left))
                node = moveRedRight(node);
            if (key.compareTo(node.key) == 0) {
                Node x = min(node.right);
                node.key = x.key;
                node.value = x.value;
                node.right = deleteMin(node.right);
            } else {
                node.right = delete(node.right, key);
            }
        }
        return balance(node);
    }

    // 获取最小键
    public K min() {
        if (isEmpty()) throw new IllegalStateException("Tree is empty");
        return min(root).key;
    }

    private Node min(Node node) {
        while (node.left != null)
            node = node.left;
        return node;
    }

    // 左旋转
    private Node rotateLeft(Node h) {
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = h.color;
        h.color = RED;
        x.size = h.size;
        h.size = 1 + size(h.left) + size(h.right);
        return x;
    }

    // 右旋转
    private Node rotateRight(Node h) {
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = h.color;
        h.color = RED;
        x.size = h.size;
        h.size = 1 + size(h.left) + size(h.right);
        return x;
    }

    // 颜色翻转
    private void flipColors(Node h) {
        h.color = !h.color;
        h.left.color = !h.left.color;
        h.right.color = !h.right.color;
    }

    // 将红色节点向左移动
    private Node moveRedLeft(Node h) {
        flipColors(h);
        if (h.right != null && isRed(h.right.left)) {
            h.right = rotateRight(h.right);
            h = rotateLeft(h);
            flipColors(h);
        }
        return h;
    }

    // 将红色节点向右移动
    private Node moveRedRight(Node h) {
        flipColors(h);
        if (h.left != null && isRed(h.left.left)) {
            h = rotateRight(h);
            flipColors(h);
        }
        return h;
    }

    // 恢复红黑树平衡
    private Node balance(Node h) {
        if (isRed(h.right)) h = rotateLeft(h);
        if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
        if (isRed(h.left) && isRed(h.right)) flipColors(h);

        h.size = 1 + size(h.left) + size(h.right);
        return h;
    }

    // 中序遍历（升序）
    public Iterable<K> keys() {
        Queue<K> queue = new LinkedList<>();
        inorder(root, queue);
        return queue;
    }

    private void inorder(Node node, Queue<K> queue) {
        if (node == null) return;
        inorder(node.left, queue);
        queue.add(node.key);
        inorder(node.right, queue);
    }

    // 打印树结构（用于调试）
    public void printTree() {
        printTree(root, 0);
    }

    private void printTree(Node node, int level) {
        if (node == null) return;

        printTree(node.right, level + 1);
        for (int i = 0; i < level; i++)
            System.out.print("    ");

        System.out.print(node.key + (node.color ? "(R)" : "(B)") + "\n");
        printTree(node.left, level + 1);
    }

    // 测试用例
    public static void main(String[] args) {
        RedBlackTree<Integer, String> tree = new RedBlackTree<>();

        // 插入操作
        tree.put(5, "Apple");
        tree.put(3, "Banana");
        tree.put(7, "Cherry");
        tree.put(2, "Date");
        tree.put(4, "Fig");
        tree.put(6, "Grape");
        tree.put(8, "Kiwi");

        System.out.println("Tree size: " + tree.size());
        System.out.println("Keys in order: " + tree.keys());
        System.out.println("Value for key 4: " + tree.get(4));

        // 删除操作
        tree.delete(3);
        System.out.println("After deleting key 3, size: " + tree.size());
        System.out.println("Keys after deletion: " + tree.keys());

        System.out.println("\nTree structure:");
        tree.printTree();
    }
}
