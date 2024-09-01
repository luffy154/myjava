package org.example;

import java.util.*;

/* 给定一个包含 0 和 1 的二维矩阵

         给定一个初始位置和速度

 一个物体从给定的初始位置触发, 在给定的速度下进行移动, 遇到矩阵的边缘则发生镜面反射

 无论物体经过 0 还是 1, 都不影响其速度

 请计算并给出经过 t 时间单位后, 物体经过 1 点的次数

 矩阵以左上角位置为[0, 0](列(x), 行(行)), 例如下面A点坐标为[2, 1] (第二列, 第一行)


         +--------------------------- 递增(x)
| 0 0 1 0 0 0 0 1 0 0 0 0
     | 0 0 1 0 0 0 0 1 0 0 0 0
     | 0 0 1 0 0 0 0 1 0 0 0 0
     | 0 0 1 0 0 0 0 1 0 0 0 0
     | 0 0 1 0 0 0 0 1 0 0 0 0
     | 0 0 1 0 0 0 0 1 0 0 0 0
     | 0 0 1 0 0 0 0 1 0 0 0 0
     |
 递增(y)

 注意:

 如果初始位置的点是 1, 也计算在内
 时间的最小单位为1, 不考虑小于 1 个时间单位内经过的点*/
public class BouncingBall {
    public static int countOnesVisited(int[][] matrix, int startX, int startY, int velocityX, int velocityY, int t) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        // Initial position
        int x = startX;
        int y = startY;

        // Velocity
        int vx = velocityX;
        int vy = velocityY;

        // Count of 1s visited
        int count = 0;

        // Use a set to avoid counting the same cell multiple times
        Set<String> visited = new HashSet<>();

        for (int time = 0; time <= t; time++) {
            // Check if the current position contains 1 and not yet visited
            if (matrix[y][x] == 1) {
                String key = x + "," + y;
                if (!visited.contains(key)) {
                    visited.add(key);
                    count++;
                }
            }

            // Move the ball
            x += vx;
            y += vy;

            // Handle the reflection
            if (x < 0) {
                x = -x;
                vx = -vx;
            } else if (x >= cols) {
                x = 2 * (cols - 1) - x;
                vx = -vx;
            }

            if (y < 0) {
                y = -y;
                vy = -vy;
            } else if (y >= rows) {
                y = 2 * (rows - 1) - y;
                vy = -vy;
            }
        }

        return count;
    }

    public static void main(String[] args) {
        int[][] matrix = {
                {0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0},
                {0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0},
                {0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0},
                {0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0},
                {0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0},
                {0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0},
                {0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0}
        };

        int startX = 0;
        int startY = 0;
        int velocityX = 1;
        int velocityY = 1;
        int t = 10;

        int result = countOnesVisited(matrix, startX, startY, velocityX, velocityY, t);
        System.out.println("Number of 1s visited: " + result);
    }
}
