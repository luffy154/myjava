package org.example;

/**
 * @ClassName:RedBlack
 * @author: qm
 * @Description:
 * @date:2025-07-16
 */
public class RedBlack {

    enum Color {
        RED, BLACK
    }

    static class Node {
        int data;
        Color color;
        Node left, right, parent;

        public Node(int data) {
            this.data = data;
            this.color = Color.RED; // 默认新节点为红色
            this.left = null;
            this.right = null;
            this.parent = null;
        }
    }

    static class RedBlackTree {
        private Node root;
        private final Node NIL; // 表示空节点

        public RedBlackTree() {
            NIL = new Node(-1);
            NIL.color = Color.BLACK;
            root = NIL;
        }

        // 插入方法
        public void insert(int data) {
            Node newNode = new Node(data);
            Node parent = null;
            Node current = root;

            while (current != NIL && current != null) {
                parent = current;
                if (newNode.data < current.data) {
                    current = current.left;
                } else {
                    current = current.right;
                }
            }

            newNode.parent = parent;
            if (parent == null) {
                root = newNode;
            } else if (newNode.data < parent.data) {
                parent.left = newNode;
            } else {
                parent.right = newNode;
            }

            fixInsert2(newNode);
        }

        // 修复插入后红黑树性质的方法
        private void fixInsert(Node node) {
            while (node != root && node.parent.color == Color.RED) {
                if (node.parent == node.parent.parent.left) {
                    Node uncle = node.parent.parent.right;
                    if (uncle.color == Color.RED) {
                        node.parent.color = Color.BLACK;
                        uncle.color = Color.BLACK;
                        node.parent.parent.color = Color.RED;
                        node = node.parent.parent;
                    } else {
                        if (node == node.parent.right) {
                            node = node.parent;
                            rotateLeft(node);
                        }
                        node.parent.color = Color.BLACK;
                        node.parent.parent.color = Color.RED;
                        rotateRight(node.parent.parent);
                    }
                } else {
                    Node uncle = node.parent.parent.left;
                    if (uncle.color == Color.RED) {
                        node.parent.color = Color.BLACK;
                        uncle.color = Color.BLACK;
                        node.parent.parent.color = Color.RED;
                        node = node.parent.parent;
                    } else {
                        if (node == node.parent.left) {
                            node = node.parent;
                            rotateRight(node);
                        }
                        node.parent.color = Color.BLACK;
                        node.parent.parent.color = Color.RED;
                        rotateLeft(node.parent.parent);
                    }
                }
            }
            root.color = Color.BLACK;
        }

        private void fixInsert2(Node node) {
            while (node != root && node.parent.color == Color.RED) {
                if (node.parent.left == node) {
                    rotateRight(node.parent.parent);
                } else {
                    rotateLeft(node.parent.parent);
                }
            }
            root.color = Color.BLACK;
        }

        // 左旋操作
        private void rotateLeft(Node node) {
            Node rightChild = node.right;
            node.right = rightChild.left;

            if (rightChild.left != NIL) {
                rightChild.left.parent = node;
            }

            rightChild.parent = node.parent;

            if (node.parent == null) {
                root = rightChild;
            } else if (node == node.parent.left) {
                node.parent.left = rightChild;
            } else {
                node.parent.right = rightChild;
            }

            rightChild.left = node;
            node.parent = rightChild;
        }

        // 右旋操作
        private void rotateRight(Node node) {
            Node leftChild = node.left;
            node.left = leftChild == null ? null : leftChild.right;

            if (leftChild != null && leftChild.right != NIL) {
                leftChild.right.parent = node;
            }

            leftChild.parent = node.parent;

            if (node.parent == null) {
                root = leftChild;
            } else if (node == node.parent.right) {
                node.parent.right = leftChild;
            } else {
                node.parent.left = leftChild;
            }

            leftChild.right = node;
            node.parent = leftChild;
        }

        // 打印红黑树（中序遍历）
        public void inorderTraversal(Node node) {
            if (node != NIL && node != null) {
                inorderTraversal(node.left);
                System.out.print(node.data + "(" + node.color + ") ");
                inorderTraversal(node.right);
            }
        }

        public static void main(String[] args) {
            RedBlackTree tree = new RedBlackTree();
            tree.insert(10);
            tree.insert(20);
            tree.insert(30);
            tree.insert(15);
            tree.inorderTraversal(tree.root);

            System.out.println("Inorder traversal of the tree:");
            tree.inorderTraversal(tree.root);
            System.out.println();
        }
    }

}
