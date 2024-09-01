package org.example;

import java.util.*;

/*给定一个二维数组M行N列，二维数组里的数字代表图片的像素，为了简化问题，仅包含像素1和5两种像素，每种像素代表一个物体，2个物体相邻的格子为边界，求像素1代表的物体的边界个数。

像素1代表的物体的边界指与像素5相邻的像素1的格子，边界相邻的属于同一个边界，相邻需要考虑8个方向（上，下，左，右，左上，左下，右上，右下）。

其他约束：

地图规格约束为：

        0<M<100

        0<N<100


        1）如下图，与像素5的格子相邻的像素1的格子（0,0）、（0,1）、（0,2）、（1,0）、（1,2）、（2,0）、（2,1）、（2,2）、（4,4）、（4,5）、（5,4）为边界，另（0,0）、（0,1）、（0,2）、（1,0）、（1,2）、（2,0）、（2,1）、（2,2）相邻，为1个边界，（4,4）、（4,5）、（5,4）相邻，为1个边界，所以下图边界个数为2。


        2）如下图，与像素5的格子相邻的像素1的格子（0,0）、（0,1）、（0,2）、（1,0）、（1,2）、（2,0）、（2,1）、（2,2）、（3,3）、（3,4）、（3,5）、（4,3）、（4,5）、（5,3）、（5,4）、（5,5）为边界，另这些边界相邻，所以下图边界个数为1。注：（2,2）、（3,3）相邻。
*/
public class BoundaryCounter {
    private static final int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1}; // Directions for 8 possible moves
    private static final int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1}; // Directions for 8 possible moves

    public static int countBoundaries(int[][] grid) {
        int M = grid.length;
        int N = grid[0].length;
        boolean[][] visited = new boolean[M][N];
        int boundaryCount = 0;

        // Iterate through each cell in the grid
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                if (grid[i][j] == 1 && !visited[i][j] && isBoundary(i, j, grid)) {
                    dfs(i, j, grid, visited);
                    boundaryCount++;
                }
            }
        }

        return boundaryCount;
    }

    private static boolean isBoundary(int x, int y, int[][] grid) {
        // Check if the current cell is adjacent to any cell with value 5
        for (int i = 0; i < 8; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];
            if (nx >= 0 && nx < grid.length && ny >= 0 && ny < grid[0].length && grid[nx][ny] == 5) {
                return true;
            }
        }
        return false;
    }

    private static void dfs(int x, int y, int[][] grid, boolean[][] visited) {
        Stack<int[]> stack = new Stack<>();
        stack.push(new int[]{x, y});

        while (!stack.isEmpty()) {
            int[] current = stack.pop();
            int cx = current[0];
            int cy = current[1];

            if (visited[cx][cy]) {
                continue;
            }

            visited[cx][cy] = true;

            for (int i = 0; i < 8; i++) {
                int nx = cx + dx[i];
                int ny = cy + dy[i];
                if (nx >= 0 && nx < grid.length && ny >= 0 && ny < grid[0].length && grid[nx][ny] == 1 && !visited[nx][ny]) {
                    stack.push(new int[]{nx, ny});
                }
            }
        }
    }

    public static void main(String[] args) {
        int[][] grid = {
                {1, 1, 1, 1, 1, 1},
                {1, 5, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 5}
        };
        System.out.println("Number of boundaries: " + countBoundaries(grid));
    }
}
