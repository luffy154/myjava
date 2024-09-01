package org.example;

import java.util.*;

/*现需要在某城市进行5G网络建设，已经选取N个地点设置5G基站，
编号固定为1到N，接下来需要各个基站之间使用光纤进行连接以确保基站能互联互通，
不同基站之间架设光纤的成本各不相同，且有些节点之间已经存在光纤相连，请你设计算法，计算出能联通这些基站的最小成本是多少。
注意：基站的联通具有传递性，入基站A与基站B架设了光纤，基站B与基站C也架设了光纤，则基站A与基站C视为可以互相联通*/
class Edge implements Comparable<Edge> {
    int u, v, cost;

    public Edge(int u, int v, int cost) {
        this.u = u;
        this.v = v;
        this.cost = cost;
    }

    public int compareTo(Edge other) {
        return this.cost - other.cost;
    }
}

class UnionFind {
    int[] parent;
    int[] rank;

    public UnionFind(int n) {
        parent = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }

    public int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]);
        }
        return parent[x];
    }

    public boolean union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);

        if (rootX != rootY) {
            if (rank[rootX] > rank[rootY]) {
                parent[rootY] = rootX;
            } else if (rank[rootX] < rank[rootY]) {
                parent[rootX] = rootY;
            } else {
                parent[rootY] = rootX;
                rank[rootX]++;
            }
            return true;
        }
        return false;
    }
}

public class MinimumCost {
    public static int minimumCost(int N, List<Edge> edges) {
        Collections.sort(edges);
        UnionFind uf = new UnionFind(N);

        int totalCost = 0;
        int edgesUsed = 0;

        for (Edge edge : edges) {
            if (uf.union(edge.u - 1, edge.v - 1)) {
                totalCost += edge.cost;
                edgesUsed++;
                if (edgesUsed == N - 1) break;
            }
        }

        return edgesUsed == N - 1 ? totalCost : -1;
    }

    public static void main(String[] args) {
        int N = 5;
        List<Edge> edges = new ArrayList<>();
        edges.add(new Edge(1, 2, 10));
        edges.add(new Edge(2, 3, 5));
        edges.add(new Edge(3, 4, 4));
        edges.add(new Edge(4, 5, 1));
        edges.add(new Edge(1, 5, 7));
        edges.add(new Edge(1, 3, 9));

        int result = minimumCost(N, edges);
        if (result != -1) {
            System.out.println("The minimum cost to connect all bases is: " + result);
        } else {
            System.out.println("It is not possible to connect all bases.");
        }
    }
}
