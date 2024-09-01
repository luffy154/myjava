package org.example;

import java.util.*;

/*一个局域网内有很多台电脑，分别标注为0 - N-1的数字。相连接的电脑距离不一样，所以感染时间不一样，感染时间用t表示。
        其中网络内一个电脑被病毒感染，其感染网络内所有的电脑需要最少需要多长时间。如果最后有电脑不会感染，则返回-1
        给定一个数组times表示一个电脑把相邻电脑感染所用的时间。
        如图：path[i]= {i,j, t} 表示电脑i->j 电脑i上的病毒感染j，需要时间t。*/
public class NetworkInfection {
    public int minInfectionTime(int N, int[][] times) {
        List<int[]>[] graph = new List[N];
        for (int i = 0; i < N; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int[] time : times) {
            int u = time[0], v = time[1], t = time[2];
            graph[u].add(new int[]{v, t});
            graph[v].add(new int[]{u, t});  // 因为感染是双向的
        }

        int result = Integer.MAX_VALUE;
        for (int i = 0; i < N; i++) {
            int maxTime = dijkstra(graph, i, N);
            if (maxTime == -1) return -1;
            result = Math.min(result, maxTime);
        }

        return result;
    }

    private int dijkstra(List<int[]>[] graph, int start, int N) {
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        int[] dist = new int[N];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[start] = 0;
        pq.offer(new int[]{start, 0});

        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int u = current[0], time = current[1];

            if (time > dist[u]) continue;

            for (int[] neighbor : graph[u]) {
                int v = neighbor[0], t = neighbor[1];
                if (dist[u] + t < dist[v]) {
                    dist[v] = dist[u] + t;
                    pq.offer(new int[]{v, dist[v]});
                }
            }
        }

        int maxTime = 0;
        for (int i = 0; i < N; i++) {
            if (dist[i] == Integer.MAX_VALUE) return -1;
            maxTime = Math.max(maxTime, dist[i]);
        }

        return maxTime;
    }

    public static void main(String[] args) {
        NetworkInfection ni = new NetworkInfection();
        int[][] times = {{0, 1, 4}, {1, 2, 1}, {2, 3, 6}, {1, 3, 2}};
        int N = 4;
        System.out.println(ni.minInfectionTime(N, times));  // 输出 4
    }
}