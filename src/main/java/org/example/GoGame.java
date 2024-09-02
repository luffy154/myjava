package org.example;

import java.util.*;
/*围棋棋盘由纵横各19条线垂直相交组成，棋盘上一共19x19=361个交点，对弈双方一方执白棋，一方执黑棋，落子时只能将棋子置于交点上。
        “气”是围棋中很重要的一个概念，某个棋子有几口气，是指其上下左右方向四个相邻的交叉点中，有几个交叉点没有棋子，由此可知：
        1、在棋盘的边缘上的棋子最多有3口气（黑1），在棋盘角点的棋子最多有2口气（黑2），其它情况最多有4口气（白1）

        2、所有同色棋子的气之和叫作该色棋子的气，需要注意的是，同色棋子重合的气点，对于该颜色棋子来说，只能计算一次气，比如下图中，黑棋一共4口气，而不是5口气，因为黑1和黑2中间红色三角标出的气是两个黑棋共有的，对于黑棋整体来说只能算一个气。

        3、本题目只计算气，对于眼也按气计算，如果您不清楚“眼”的概念，可忽略，按照前面描述的规则计算即可。
现在，请根据输入的黑棋和白棋的坐标位置，计算黑棋和白起一共各有多少气？*/
public class GoGame {

    private static final int SIZE = 19; // 棋盘大小
    private static final int EMPTY = 0; // 表示空点
    private static final int BLACK = 1; // 表示黑棋
    private static final int WHITE = 2; // 表示白棋

    private static final int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}}; // 上下左右四个方向

    // 棋盘上的气计算函数
    private static int calculateLiberties(int[][] board, int x, int y, boolean[][] visited) {
        int color = board[x][y];
        int liberties = 0;
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{x, y});
        visited[x][y] = true;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int curX = current[0];
            int curY = current[1];

            for (int[] dir : directions) {
                int newX = curX + dir[0];
                int newY = curY + dir[1];

                if (newX >= 0 && newX < SIZE && newY >= 0 && newY < SIZE) {
                    if (board[newX][newY] == EMPTY) {
                        liberties++;
                    } else if (board[newX][newY] == color && !visited[newX][newY]) {
                        visited[newX][newY] = true;
                        queue.offer(new int[]{newX, newY});
                    }
                }
            }
        }

        return liberties;
    }

    public static void main(String[] args) {
        int[][] board = new int[SIZE][SIZE];
        // 根据输入初始化棋盘，比如：
        // board[x][y] = BLACK; // 黑棋放置在(x, y)位置
        // board[x][y] = WHITE; // 白棋放置在(x, y)位置

        // 输入棋盘状态（以下仅为示例，实际输入需要替换）
        board[1][1] = BLACK;
        board[1][2] = BLACK;
        board[18][18] = WHITE;

        boolean[][] visited = new boolean[SIZE][SIZE];
        int blackLiberties = 0;
        int whiteLiberties = 0;

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == BLACK && !visited[i][j]) {
                    blackLiberties += calculateLiberties(board, i, j, visited);
                } else if (board[i][j] == WHITE && !visited[i][j]) {
                    whiteLiberties += calculateLiberties(board, i, j, visited);
                }
            }
        }

        System.out.println("Black Liberties: " + blackLiberties);
        System.out.println("White Liberties: " + whiteLiberties);
    }
}

