package org.example;

import java.util.LinkedList;
import java.util.Queue;

/*马是象棋(包括中国象棋和国际象棋)中的棋子，走法是每步直一格再斜一格，即先横着或直着走一格，
然后再斜着走一个对角线，可进可退，可越过河界，俗称“马走‘日’字。

        给顶m行n列的棋盘（网格图），棋盘上只有有棋子象棋中的棋子“马”，
        并且每个棋子有等级之分，等级为k的马可以跳1~k步（走的方式与象棋中“马”的规则一样，不可以超出棋盘位置），
        问是否能将所有马跳到同一位置，如果存在，输出最少需要的总步数（每匹马的步数相加），不存在则输出-1。

注：允许不同的马在跳的过程中跳到同一位置，
坐标为(x,y)的马跳一次可以跳到到坐标为(x+1, y+2), (x+1, y-2), (x+2, y+1), (x+2, y-1), (x-1, y+2), (x-1, y-2), (x-2, y+1), (x-2, y-1),的格点上，但是不可以超出棋盘范围。*/
public class ChessHorse {
    // 定义方向数组，表示马的跳跃方向
    private static final int[][] DIRECTIONS = {
            {1, 2}, {1, -2}, {2, 1}, {2, -1},
            {-1, 2}, {-1, -2}, {-2, 1}, {-2, -1}
    };

    // 广度优先搜索计算最短步数
    private static int bfs(int startX, int startY, int endX, int endY, int m, int n, int maxStep) {
        boolean[][] visited = new boolean[m][n];
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{startX, startY, 0});
        visited[startX][startY] = true;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int x = current[0], y = current[1], steps = current[2];

            if (x == endX && y == endY) {
                return steps;
            }

            for (int i = 1; i <= maxStep; i++) {
                for (int[] direction : DIRECTIONS) {
                    int newX = x + i * direction[0];
                    int newY = y + i * direction[1];

                    if (newX >= 0 && newX < m && newY >= 0 && newY < n && !visited[newX][newY]) {
                        visited[newX][newY] = true;
                        queue.offer(new int[]{newX, newY, steps + 1});
                    }
                }
            }
        }

        return -1;  // 如果找不到路径，返回-1
    }

    public static int minTotalSteps(int[][] board, int[][] horses) {
        int m = board.length;
        int n = board[0].length;
        int minTotalSteps = Integer.MAX_VALUE;

        // 枚举所有可能的目标位置
        for (int targetX = 0; targetX < m; targetX++) {
            for (int targetY = 0; targetY < n; targetY++) {
                int totalSteps = 0;
                boolean canReach = true;

                for (int[] horse : horses) {
                    int startX = horse[0];
                    int startY = horse[1];
                    int level = horse[2];

                    int steps = bfs(startX, startY, targetX, targetY, m, n, level);
                    if (steps == -1) {
                        canReach = false;
                        break;
                    }
                    totalSteps += steps;
                }

                if (canReach) {
                    minTotalSteps = Math.min(minTotalSteps, totalSteps);
                }
            }
        }

        return minTotalSteps == Integer.MAX_VALUE ? -1 : minTotalSteps;
    }

    public static void main(String[] args) {
        int[][] board = new int[5][5];  // 5x5的棋盘
        int[][] horses = {
                {0, 0, 2},  // 第一个马在 (0,0) 位置，等级为 2
                {4, 4, 3},  // 第二个马在 (4,4) 位置，等级为 3
                {2, 2, 1}   // 第三个马在 (2,2) 位置，等级为 1
        };

        int result = minTotalSteps(board, horses);
        System.out.println("最少需要的总步数: " + result);
    }
}
