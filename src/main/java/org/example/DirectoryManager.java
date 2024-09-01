package org.example;

import java.util.*;

public class DirectoryManager {

    // 节点类，表示一个目录
    static class DirectoryNode {
        String name;
        Map<String, DirectoryNode> children;

        DirectoryNode(String name) {
            this.name = name;
            this.children = new HashMap<>();
        }
    }

    private DirectoryNode root;  // 根目录
    private DirectoryNode current;  // 当前目录

    public DirectoryManager() {
        root = new DirectoryNode("/");
        current = root;
    }

    // 处理 mkdir 命令
    public void mkdir(String name) {
        if (!current.children.containsKey(name)) {
            current.children.put(name, new DirectoryNode(name));
        }
    }

    // 处理 cd 命令
    public void cd(String name) {
        if (name.equals("..")) {
            if (current != root) {
                // 返回上级目录
                current = getParent(root, current);
            }
        } else if (current.children.containsKey(name)) {
            // 进入子目录
            current = current.children.get(name);
        }
    }

    // 查找当前目录的父节点
    private DirectoryNode getParent(DirectoryNode root, DirectoryNode target) {
        if (root.children.containsValue(target)) {
            return root;
        }
        for (DirectoryNode child : root.children.values()) {
            DirectoryNode parent = getParent(child, target);
            if (parent != null) {
                return parent;
            }
        }
        return null;
    }

    // 处理 pwd 命令
    public String pwd() {
        return buildPath(current);
    }

    // 构建当前路径字符串
    private String buildPath(DirectoryNode node) {
        if (node == root) {
            return "/";
        }
        String path = "";
        Stack<String> stack = new Stack<>();
        while (node != root) {
            stack.push(node.name);
            node = getParent(root, node);
        }
        while (!stack.isEmpty()) {
            path += "/" + stack.pop();
        }
        return path;
    }

    public static void main(String[] args) {
        DirectoryManager dm = new DirectoryManager();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();
            if (command.startsWith("mkdir ")) {
                dm.mkdir(command.substring(6));
            } else if (command.startsWith("cd ")) {
                dm.cd(command.substring(3));
            } else if (command.equals("pwd")) {
                System.out.println(dm.pwd());
            }
        }
    }
}
