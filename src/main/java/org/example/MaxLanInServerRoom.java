package org.example;

/*在一个机房中，服务器的位置标识在 n*m 的整数矩阵网格中，
1 表示单元格上有服务器，
0 表示没有。如果两台服务器位于同一行或者同一列中紧邻的位置，则认为它们之间可以组成一个局域网。
请你统计机房中最大的局域网包含的服务器个数。*/
public class MaxLanInServerRoom {

    // 用于表示四个方向（上下左右）
    private static final int[] dx = {-1, 1, 0, 0};
    private static final int[] dy = {0, 0, -1, 1};

    public static int maxLan(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        boolean[][] visited = new boolean[n][m];
        int maxLanSize = 0;

        // 遍历整个网格
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                // 如果当前单元格有服务器且未被访问过
                if (grid[i][j] == 1 && !visited[i][j]) {
                    // 计算该局域网的服务器数量
                    int lanSize = dfs(grid, visited, i, j, n, m);
                    // 更新最大局域网大小
                    maxLanSize = Math.max(maxLanSize, lanSize);
                }
            }
        }

        return maxLanSize;
    }

    // 深度优先搜索（DFS）函数
    private static int dfs(int[][] grid, boolean[][] visited, int x, int y, int n, int m) {
        // 标记当前节点为已访问
        visited[x][y] = true;
        int lanSize = 1; // 计算局域网大小，包含当前服务器

        // 遍历四个方向
        for (int dir = 0; dir < 4; dir++) {
            int nx = x + dx[dir];
            int ny = y + dy[dir];

            // 判断新的位置是否在边界内，且未被访问过，并且是一个服务器
            if (nx >= 0 && nx < n && ny >= 0 && ny < m && grid[nx][ny] == 1 && !visited[nx][ny]) {
                lanSize += dfs(grid, visited, nx, ny, n, m);
            }
        }

        return lanSize;
    }

    public static void main(String[] args) {
        int[][] grid = {
                {1, 0, 0, 1, 0},
                {1, 1, 0, 0, 0},
                {0, 1, 0, 0, 1},
                {0, 0, 0, 1, 1}
        };
        System.out.println(maxLan(grid));  // 示例输出：4
    }
}
