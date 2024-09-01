package org.example;

import java.util.*;

/*一张地图上有n个城市，城市和城市之间有且只有一条道路相连：要么直接相连，要么通过其它城市中转相连（可中转一次或多次）。
城市与城市之间的道路都不会成环。
当切断通往某个城市 i 的所有道路后，地图上将分为多个连通的城市群，设该城市 i 的聚集度为
DPi（Degree of Polymerization）,  DPi = max(城市群1的城市个数， 城市群2的城市个数, ... 城市群m的城市个数)。

请找出地图上 DP 值最小的城市（即找到城市 j，使得 DPj = min(DP1, DP2 ... DPn) )

提示：如果有多个城市都满足条件，这些城市都要找出来（可能存在多个解）
提示：DPi 的计算，可以理解为已知一棵树，删除某个节点后，生成的多个子树，求解多个子树节点数的问题。*/
public class CityMap {

    // 用邻接表表示树
    private static Map<Integer, List<Integer>> graph = new HashMap<>();
    private static Map<Integer, Integer> subtreeSize = new HashMap<>();

    // DFS计算每个子树的大小
    private static int dfs(int node, int parent) {
        int size = 1;
        for (int neighbor : graph.get(node)) {
            if (neighbor != parent) {
                size += dfs(neighbor, node);
            }
        }
        subtreeSize.put(node, size);
        return size;
    }

    // 计算每个城市的聚集度
    private static Map<Integer, Integer> calculateDP(int n) {
        Map<Integer, Integer> dp = new HashMap<>();
        for (int i = 1; i <= n; i++) {
            int maxSubtreeSize = 0;
            for (int neighbor : graph.get(i)) {
                if (subtreeSize.get(neighbor) > subtreeSize.get(i)) {
                    maxSubtreeSize = Math.max(maxSubtreeSize, subtreeSize.get(neighbor));
                }
            }
            dp.put(i, maxSubtreeSize);
        }
        return dp;
    }

    public static void main(String[] args) {
        // 示例输入
        int n = 7; // 城市数量
        int[][] edges = {
                {1, 2},
                {1, 3},
                {2, 4},
                {2, 5},
                {3, 6},
                {3, 7}
        };

        // 初始化邻接表
        for (int i = 1; i <= n; i++) {
            graph.put(i, new ArrayList<>());
        }

        // 构建树
        for (int[] edge : edges) {
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
        }

        // 计算每个子树的大小
        dfs(1, -1);

        // 计算每个城市的聚集度
        Map<Integer, Integer> dp = calculateDP(n);

        // 查找最小聚集度
        int minDP = Collections.min(dp.values());
        List<Integer> result = new ArrayList<>();

        for (Map.Entry<Integer, Integer> entry : dp.entrySet()) {
            if (entry.getValue() == minDP) {
                result.add(entry.getKey());
            }
        }

        // 输出结果
        System.out.println("Cities with minimum DP value: " + result);
    }
}
