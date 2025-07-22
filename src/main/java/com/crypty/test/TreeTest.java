package com.crypty.test;

import com.alibaba.fastjson2.JSON;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName:TreeTest
 * @author: qm
 * @Description:
 * @date:2025-07-19
 */
public class TreeTest {
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }
    @Data
    static class DeepValue{
        int deep;
        int strath;
        int value;

        public DeepValue(int deep, int strath, int value) {
            this.deep = deep;
            this.strath = strath;
            this.value = value;
        }
    }

    public static void main(String[] args) {
        List<DeepValue> list = new ArrayList<>();
        TreeNode root = new TreeNode(1);
        TreeNode node1 = new TreeNode(2);
        TreeNode node2 = new TreeNode(3);
        root.left = node1;
        root.right = node2;
        TreeNode node3 = new TreeNode(4);
        TreeNode node4 = new TreeNode(5);
        node1.left = node3;
        node1.right = node4;
        TreeNode node5 = new TreeNode(6);
        node4.left = node5;
        printTree(root, 0,0,list);

        Map<Integer, List<DeepValue>> map = list.stream().collect(Collectors.groupingBy(t->t.deep));
        System.out.println(JSON.toJSONString( map));
    }

    private static void printTree(TreeNode root, int strath, int deep, List<DeepValue> list) {
        if (root.left != null) {
            printTree(root.left, strath-1,deep + 1,list);
        }
        int i = deep;
        while (i-- > 0) {
            System.out.print("\t");
        }
        list.add(new DeepValue(deep,strath,root.val));
        System.out.println(root.val);
        if (root.right != null) {
            printTree(root.right, strath+1,deep + 1,list);
        }
    }
}
