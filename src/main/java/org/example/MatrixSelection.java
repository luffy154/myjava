package org.example;

import java.util.*;

/*
    从一个N*M（N<=M)的矩阵中选出N个数，任意两个数字不能在同一行或同一列，求选出来的N个数中第K大的数字的最小值是多少。
*/
public class MatrixSelection {

    // Helper method to check if we can select N numbers >= value
    private static boolean canSelect(int[][] matrix, int N, int value) {
        boolean[][] valid = new boolean[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (matrix[i][j] >= value) {
                    valid[i][j] = true;
                }
            }
        }
        return canFindMaximumMatching(valid, N);
    }

    // Helper method to find if there is a maximum matching in a bipartite graph
    private static boolean canFindMaximumMatching(boolean[][] valid, int N) {
        int[] match = new int[N];
        Arrays.fill(match, -1);
        boolean[] visited = new boolean[N];
        for (int u = 0; u < N; u++) {
            Arrays.fill(visited, false);
            if (!bipartiteMatch(valid, u, match, visited, N)) {
                return false;
            }
        }
        return true;
    }

    private static boolean bipartiteMatch(boolean[][] valid, int u, int[] match, boolean[] visited, int N) {
        for (int v = 0; v < N; v++) {
            if (valid[u][v] && !visited[v]) {
                visited[v] = true;
                if (match[v] == -1 || bipartiteMatch(valid, match[v], match, visited, N)) {
                    match[v] = u;
                    return true;
                }
            }
        }
        return false;
    }

    public static int findKthLargestMinValue(int[][] matrix, int N, int K) {
        int low = Integer.MIN_VALUE;
        int high = Integer.MAX_VALUE;
        int answer = Integer.MAX_VALUE;

        for (int[] row : matrix) {
            for (int num : row) {
                low = Math.max(low, num);
                high = Math.min(high, num);
            }
        }

        while (low <= high) {
            int mid = (low + high) / 2;
            if (canSelect(matrix, N, mid)) {
                answer = mid;
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return answer;
    }

    public static void main(String[] args) {
        int[][] matrix = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}
        };
        int N = 3;
        int K = 2;
        int result = findKthLargestMinValue(matrix, N, K);
        System.out.println("The minimum value for the " + K + "th largest number is: " + result);
    }
}
