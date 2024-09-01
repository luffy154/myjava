package org.example;

import java.util.*;

/*给定一个有向图，图中可能包含有环，图使用二维矩阵表示，
每一行的第一列表示起始节点，第二列表示终止节点，如[0, 1]表示从0到1的路径。
每个节点用正整数表示。求这个数据的首节点与尾节点，题目给的用例会是一个首节点，
但可能存在多个尾节点。同时，图中可能含有环。如果图中含有环，返回[-1]。
说明：入度为0是首节点，出度为0是尾节点。*/
public class GraphNodes {

    public static List<Integer> findNodes(int[][] edges) {
        if (edges == null || edges.length == 0) {
            return Arrays.asList(0, 0);
        }

        // 构建图
        Map<Integer, Set<Integer>> graph = new HashMap<>();
        Map<Integer, Integer> inDegree = new HashMap<>();
        Map<Integer, Integer> outDegree = new HashMap<>();
        Set<Integer> allNodes = new HashSet<>();

        for (int[] edge : edges) {
            int start = edge[0];
            int end = edge[1];

            allNodes.add(start);
            allNodes.add(end);

            graph.putIfAbsent(start, new HashSet<>());
            graph.putIfAbsent(end, new HashSet<>());

            graph.get(start).add(end);

            inDegree.put(end, inDegree.getOrDefault(end, 0) + 1);
            outDegree.put(start, outDegree.getOrDefault(start, 0) + 1);

            if (!inDegree.containsKey(start)) {
                inDegree.put(start, 0);
            }
            if (!outDegree.containsKey(end)) {
                outDegree.put(end, 0);
            }
        }

        // 检查图中是否有环
        if (hasCycle(graph, allNodes)) {
            return Arrays.asList(-1);
        }

        // 找到入度为0的节点（首节点）和出度为0的节点（尾节点）
        List<Integer> startNodes = new ArrayList<>();
        List<Integer> endNodes = new ArrayList<>();

        for (int node : allNodes) {
            if (inDegree.get(node) == 0) {
                startNodes.add(node);
            }
            if (outDegree.get(node) == 0) {
                endNodes.add(node);
            }
        }

        // 返回结果
        List<Integer> result = new ArrayList<>();
        result.addAll(startNodes);
        result.addAll(endNodes);
        return result;
    }

    private static boolean hasCycle(Map<Integer, Set<Integer>> graph, Set<Integer> nodes) {
        Map<Integer, Integer> indegree = new HashMap<>();
        for (int node : nodes) {
            indegree.put(node, 0);
        }

        for (int u : graph.keySet()) {
            for (int v : graph.get(u)) {
                indegree.put(v, indegree.get(v) + 1);
            }
        }

        Queue<Integer> queue = new LinkedList<>();
        for (int node : nodes) {
            if (indegree.get(node) == 0) {
                queue.add(node);
            }
        }

        int visitedCount = 0;
        while (!queue.isEmpty()) {
            int u = queue.poll();
            visitedCount++;
            for (int v : graph.getOrDefault(u, Collections.emptySet())) {
                indegree.put(v, indegree.get(v) - 1);
                if (indegree.get(v) == 0) {
                    queue.add(v);
                }
            }
        }

        return visitedCount != nodes.size();
    }

    public static void main(String[] args) {
        int[][] edges = {
                {1, 2},
                {2, 3},
                {3, 4},
                {4, 5},
                {5, 2} // Adding a cycle here
        };
        System.out.println(findNodes(edges)); // Output: [-1] due to cycle

        int[][] edges2 = {
                {1, 2},
                {2, 3},
                {3, 4}
        };
        System.out.println(findNodes(edges2)); // Output: [1, 4]
    }
}
