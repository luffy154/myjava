package org.example;

import java.util.*;

    /*评估一个网络的信号质量，其中一个做法是将网络划分为栅格，
    然后对每个栅格的信号质量计算。路测的时候，希望选择一条信号最好的路线（彼此相连的栅格集合）进行演示。
    现给出R行C列的整数数组Cov，每个单元格的数值S即为该栅格的信号质量（已归一化，无单位，值越大信号越好）。

    要求从[0, 0]到[R-1, C-1]设计一条最优路测路线。返回该路线得分。
    规则：

            1.     路测路线可以上下左右四个方向，不能对角。
            2.     路线的评分是以路线上信号最差的栅格为准的，例如路径8→4→5→9的值为 4，该线路评分为4。线路最优表示该条线路的评分最高。*/

public class OptimalPath {

    public static int findBestSignalPath(int[][] Cov) {
        int R = Cov.length;
        int C = Cov[0].length;
        int low = 0, high = Integer.MAX_VALUE;

        // Determine the range for binary search
        for (int[] row : Cov) {
            for (int val : row) {
                high = Math.min(high, val);
            }
        }

        int bestSignal = 0;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (canReachEnd(Cov, R, C, mid)) {
                bestSignal = mid;
                low = mid + 1; // Try for a higher minimum value
            } else {
                high = mid - 1; // Try for a lower minimum value
            }
        }

        return bestSignal;
    }

    private static boolean canReachEnd(int[][] Cov, int R, int C, int minSignal) {
        boolean[][] visited = new boolean[R][C];
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{0, 0});
        visited[0][0] = true;

        int[] directions = {-1, 0, 1, 0, 0, -1, 0, 1}; // Up, Down, Left, Right

        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            int x = cell[0], y = cell[1];

            if (x == R - 1 && y == C - 1) {
                return true;
            }

            for (int i = 0; i < 4; i++) {
                int nx = x + directions[2 * i];
                int ny = y + directions[2 * i + 1];

                if (nx >= 0 && nx < R && ny >= 0 && ny < C && !visited[nx][ny] && Cov[nx][ny] >= minSignal) {
                    visited[nx][ny] = true;
                    queue.add(new int[]{nx, ny});
                }
            }
        }

        return false;
    }

    public static void main(String[] args) {
        int[][] Cov = {
                {5, 2, 1, 3},
                {6, 3, 4, 5},
                {7, 8, 4, 6},
                {8, 6, 5, 7}
        };
        System.out.println("Maximum possible minimum signal: " + findBestSignalPath(Cov));
    }
}
