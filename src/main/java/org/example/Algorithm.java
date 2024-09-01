package org.example;

import java.util.*;
import java.util.regex.Pattern;

class Point {
    int x, y, candy, step;

    Point(int x, int y, int candy, int step) {
        this.x = x;
        this.y = y;
        this.candy = candy;
        this.step = step;
    }
}

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
        val = x;
    }
}

class MemoryBlock {
    int start;  // 起始地址（偏移）
    int size;   // 块大小
    boolean isAllocated; // 是否已分配

    public MemoryBlock(int start, int size, boolean isAllocated) {
        this.start = start;
        this.size = size;
        this.isAllocated = isAllocated;
    }
}

class MemoryAllocator {
    private List<MemoryBlock> memoryBlocks;
    private final int totalSize = 100;

    public MemoryAllocator() {
        // 初始化整个堆为一个100字节的空闲块
        memoryBlocks = new ArrayList<>();
        memoryBlocks.add(new MemoryBlock(0, totalSize, false));
    }

    public MemoryBlock allocate(int size) {
        MemoryBlock bestFit = null;
        MemoryBlock previousAllocatedBlock = null;

        // 查找最近已分配块
        for (MemoryBlock block : memoryBlocks) {
            if (block.isAllocated) {
                previousAllocatedBlock = block;
            }
        }

        // 查找最佳适配的空闲块
        for (MemoryBlock block : memoryBlocks) {
            if (!block.isAllocated) {
                if (previousAllocatedBlock != null && block.start == previousAllocatedBlock.start + previousAllocatedBlock.size) {
                    // 检查是否紧邻前一已分配块
                    if (block.size >= size && (bestFit == null || block.size < bestFit.size)) {
                        bestFit = block;
                    }
                } else if (bestFit == null || block.size < bestFit.size) {
                    // 如果没有紧邻块，找最接近的适合的块
                    if (block.size >= size) {
                        bestFit = block;
                    }
                }
            }
        }

        // 如果找到了合适的块
        if (bestFit != null) {
            if (bestFit.size > size) {
                // 分裂内存块
                MemoryBlock newBlock = new MemoryBlock(bestFit.start + size, bestFit.size - size, false);
                memoryBlocks.add(memoryBlocks.indexOf(bestFit) + 1, newBlock);
            }
            bestFit.size = size;
            bestFit.isAllocated = true;
            return bestFit;
        }

        // 无法分配内存
        return null;
    }

    public void free(MemoryBlock block) {
        block.isAllocated = false;

        // 合并相邻的空闲块
        mergeFreeBlocks();
    }

    private void mergeFreeBlocks() {
        for (int i = 0; i < memoryBlocks.size() - 1; i++) {
            MemoryBlock current = memoryBlocks.get(i);
            MemoryBlock next = memoryBlocks.get(i + 1);
            if (!current.isAllocated && !next.isAllocated) {
                current.size += next.size;
                memoryBlocks.remove(i + 1);
                i--; // 重新检查当前块是否可以继续与下一个合并
            }
        }
    }

    public void printMemory() {
        System.out.println("Memory Blocks:");
        for (MemoryBlock block : memoryBlocks) {
            System.out.println("Start: " + block.start + ", Size: " + block.size + ", Allocated: " + block.isAllocated);
        }
    }
}

class Node implements Comparable<Node> {
    int row, col, time;

    Node(int row, int col, int time) {
        this.row = row;
        this.col = col;
        this.time = time;
    }

    @Override
    public int compareTo(Node other) {
        return this.time - other.time;
    }
}

public class Algorithm {
    static final int[][] drections = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}}; // 上下左右移动

    public static void MaxCandyTest() {
        int[][] grid = {
                {0, 2, 0, 0, 3},
                {0, -1, 1, 0, 0},
                {0, 20, 0, 1, 0},
                {1, 0, -1, 0, 4},
                {0, 0, 10, 0, 0}
        };
        Point mom = new Point(0, 0, 0, 0); // 妈妈的起始位置
        Point child = new Point(4, 4, 0, 0);   // 宝宝的位置
        int minStep = shortStep(grid, mom, child);
        System.out.println(minStep);
        System.out.println(maxCandies(grid, mom, child, minStep));
    }

    public static int shortStep(int[][] grid, Point mom, Point child) {
        int n = grid.length;
        boolean[][] visited = new boolean[n][n]; // 记录是否访问过
        //创建队列用于广域搜索
        Queue<Point> queue = new LinkedList<>();
        mom.candy = grid[mom.x][mom.y];
        mom.step = 0;
        queue.offer(mom);

        while (!queue.isEmpty()) {
            Point current = queue.poll();
            if (current.x == child.x && current.y == child.y) {
                return current.step;
            }

            for (int[] diretion : drections) {
                int newX = current.x + diretion[0];
                int newY = current.y + diretion[1];
                if (isValid(newX, newY, grid, visited)) {
                    visited[newX][newY] = true;
                    queue.offer(new Point(newX, newY, current.candy + grid[newX][newY], current.step + 1));
                }
            }
        }

        return -1;
    }

    public static int maxCandies(int[][] grid, Point mom, Point child, int minStep) {
        int n = grid.length;
        int maxCandies = 0; // 记录到达每个位置的最大糖果数量
        //创建队列用于广域搜索
        Queue<Point> queue = new LinkedList<>();
        mom.candy = grid[mom.x][mom.y];
        mom.step = 0;
        queue.offer(mom);

        while (!queue.isEmpty()) {
            Point current = queue.poll();
            if (current.x == child.x && current.y == child.y && current.step <= minStep) {
                maxCandies = Math.max(maxCandies, current.candy);
                continue;
            }

            if (current.step >= minStep) {
                continue;
            }

            for (int[] diretion : drections) {
                int newX = current.x + diretion[0];
                int newY = current.y + diretion[1];
                if (isValidNoVisit(newX, newY, grid)) {
                    queue.offer(new Point(newX, newY, current.candy + grid[newX][newY], current.step + 1));
                }
            }
        }

        return maxCandies;
    }

    private static boolean isValid(int x, int y, int[][] grid, boolean[][] visited) {
        int n = grid.length;
        return x >= 0 && x < n && y >= 0 && y < n && grid[x][y] != -1 && !visited[x][y];
    }

    private static boolean isValidNoVisit(int x, int y, int[][] grid) {
        int n = grid.length;
        return x >= 0 && x < n && y >= 0 && y < n && grid[x][y] != -1;
    }


    public static int sumOfMaxMinN(int[] nums, int N) {
        if (nums == null || nums.length < 2 * N) {
            return -1;  // 输入非法，数组长度不够
        }

        TreeSet set = new TreeSet();
        for (int num : nums) {
            set.add(num);
        }

        if (set.size() < 2 * N) {
            return -1;  // 输入非法，数组长度不够
        }
        System.out.println(set);
        Iterator<Integer> it = set.iterator();
        int i = 1;
        int minN = 0;
        int maxN = 0;
        int length = set.size();
        while (it.hasNext()) {
            if (i <= N) {
                minN += it.next();
            } else if (i > length - N) {
                maxN += it.next();
            } else {
                it.next();
            }
            i++;
        }

        return minN + maxN;
    }

    public static void Test() {
        int[] nums = {5, 4, 3, 6, 7, 4, 5, 6, 7, 8, 2, 5, 1, 6, 7, 8, 9};
        int N = 2;
        System.out.println(sumOfMaxMinN(nums, N));
    }


    public static String getSubLongStr(String s) {
        StringBuffer sb = new StringBuffer();
        boolean flag = false;
        String sub = "";
        Pattern p = Pattern.compile("[a-zA-Z]");
        for (int i = 0; i < s.length(); i++) {
            String c = s.substring(i, i + 1);
            //找到字母
            if (p.matcher(c).find()) {
                //向前找
                for (int k = i - 1; k >= 0; k--) {
                    String pre = s.substring(k, k + 1);
                    //到字母结束
                    if (p.matcher(pre).find()) {
                        break;
                    }
                    sb.append(pre);
                }
                //向前找如果存在多个字符，则需要翻转顺序
                if (sb.length() > 1) {
                    sb = sb.reverse();
                }
                sb.append(c);
                //向后找
                for (int k = i + 1; k < s.length(); k++) {
                    String pre = s.substring(k, k + 1);
                    //到字母结束
                    if (p.matcher(pre).find()) {
                        break;
                    }
                    sb.append(pre);
                }

                if (sub.length() < sb.length()) {
                    sub = sb.toString();
                }

                //
                sb = new StringBuffer();
            }
        }

        return sub;
    }

    public static void Test1() {
        String s = "dfsfd3243rwr333333333333334f33333333f3434f3f4fr3f3";
        System.out.println(getSubLongStr(s));
    }

    public static int maxMatch = 0; //数组1大于数组2最大个数
    public static int globalCount = 0; //最大计数

    public static int solution(int nums1[], int nums2[]) {
        int[] counts = new int[nums2.length];
        for (int i = 0; i < nums2.length; i++) {
            int count = 0;
            for (int j = 0; j < nums1.length; j++) {
                if (nums1[j] > nums2[i]) {
                    count++;
                }
            }
            counts[i] = count;
        }

        int allCount = 1;
        for (int i = counts.length - 1; i >= 0; i--) {
            int factor = counts[i] - (counts.length - i - 1);
            if (factor > 0) {
                allCount *= factor;
            }
        }
        System.out.println(Arrays.toString(counts));
        return allCount;
    }

    public static void Test2() {
        int[] a = {3, 5, 15, 17};
        int[] b = {2, 9, 11, 13};
        Arrays.sort(a);
        Arrays.sort(b);
        System.out.println("可以达到最优结果的a数组数量为: " + solution(a, b));
    }

    public static TreeNode transformTree(TreeNode root) {
        if (root == null) {
            return null;
        }

        // 递归计算左子树和右子树的和
        int leftSum = sumOfSubtree(root.left);
        int rightSum = sumOfSubtree(root.right);

        // 创建新节点，值为左子树和右子树的和
        TreeNode newRoot = new TreeNode(leftSum + rightSum);

        // 递归构建新树的左子树和右子树
        newRoot.left = transformTree(root.left);
        newRoot.right = transformTree(root.right);

        return newRoot;
    }

    private static TreeNode transformTree1(TreeNode node) {
        int val = 0;
        TreeNode newNode = new TreeNode(val);
        if (node.left != null) {
            TreeNode left = transformTree1(node.left);
            newNode.left = left;
            val += node.left.val + left.val;
        }
        if (node.right != null) {
            TreeNode right = transformTree1(node.right);
            newNode.right = right;
            val += node.right.val + right.val;
        }
        newNode.val = val;

        return newNode;
    }

    // 计算子树的和
    private static int sumOfSubtree(TreeNode node) {
        if (node == null) {
            return 0;
        }

        return node.val + sumOfSubtree(node.left) + sumOfSubtree(node.right);
    }

    // 打印树的前序遍历
    public static void printPreOrder(TreeNode node) {
        if (node == null) {
            return;
        }
        System.out.print(node.val + " ");
        printPreOrder(node.left);
        printPreOrder(node.right);
    }


    public static void Test3() {
        // 构建示例树
        TreeNode root = new TreeNode(6);
        root.left = new TreeNode(7);
        root.right = new TreeNode(9);
        root.left.right = new TreeNode(-2);
        root.right.left = new TreeNode(6);

        // 转换树
        TreeNode newRoot = transformTree1(root);

        // 打印转换后的树
        printPreOrder(newRoot);  // 预期输出: 20 -2 0 6 0
    }


    public static int longestEvenSubstring(String s) {
        int n = s.length();
        int maxLen = 0;
        int mask = 0;

        // 扩展字符串以处理环形问题
        String extendedS = s + s;

        // 记录每个状态第一次出现的索引
        Map<Integer, Integer> maskIndexMap = new HashMap<>();
        maskIndexMap.put(0, -1);  // 初始状态，表示所有字符出现次数为偶数

        for (int i = 0; i < 2 * n; i++) {
            char c = extendedS.charAt(i);

            // 更新当前字符的状态
            if (c == 'l') {
                mask ^= 1 << 0;
            } else if (c == 'o') {
                mask ^= 1 << 1;
            } else if (c == 'x') {
                mask ^= 1 << 2;
            }

            // 如果此状态已经出现过，计算最大长度
            if (maskIndexMap.containsKey(mask)) {
                int firstIndex = maskIndexMap.get(mask);
                if (i - firstIndex <= n) {  // 确保子字符串长度在原字符串范围内
                    maxLen = Math.max(maxLen, i - firstIndex);
                }
            } else {
                // 记录此状态第一次出现的索引
                maskIndexMap.put(mask, i);
            }
        }

        return maxLen;
    }

    private static void Test4() {
        String s = "xssosslsssxsxx";
        int result = longestEvenSubstring(s);
        System.out.println("最长符合条件的子字符串长度: " + result);
    }

    public static String getSmallestString(String s) {
        char[] chars = s.toCharArray();
        int n = chars.length;

        // 记录右侧最小字符及其索引
        int minIndex = n - 1;
        char minChar = chars[minIndex];

        // 记录需要交换的字符索引
        int swapIndex1 = -1;
        int swapIndex2 = -1;

        for (int i = n - 2; i >= 0; i--) {
            if (chars[i] > minChar) {
                // 找到一个较大字符并且有更小的字符在它之后
                swapIndex1 = i;
                swapIndex2 = minIndex;
            } else if (chars[i] < minChar) {
                // 更新最小字符及其索引
                minChar = chars[i];
                minIndex = i;
            }
        }

        // 如果找到可以交换的字符，执行交换
        if (swapIndex1 != -1) {
            char temp = chars[swapIndex1];
            chars[swapIndex1] = chars[swapIndex2];
            chars[swapIndex2] = temp;
        }

        return new String(chars);
    }

    private static void Test5() {
        String s = "abdc";
        String result = getSmallestString(s);
        System.out.println("最小字典序字符串: " + result);  // 输出应为 "acbd"
    }

    private static int transformInt(int n) {
        String bStr = Integer.toBinaryString(n);
        int count = 0;
        for (int i = 0; i < bStr.length(); i++) {
            if (bStr.substring(i, i + 1).equals("1")) {
                count++;
            }
        }

        int m = n + 1;
        while (true) {
            String mStr = Integer.toBinaryString(m);
            int mCount = 0;
            for (int i = 0; i < mStr.length(); i++) {
                if (mStr.substring(i, i + 1).equals("1")) {
                    mCount++;
                }
            }
            if (mCount == count) {
                break;
            }
            m++;
        }

        return m;
    }

    private static void Test6() {
        int n = 100;
        int m = transformInt(n);
        System.out.println(Integer.toBinaryString(n));
        System.out.println(Integer.toBinaryString(m));
    }


    //新运算符，1#2$4
    public static int evaluateMartianExpression(String expr) {
        // 首先处理 # 运算符，因为它的优先级高于 $
        expr = handleHashOperator(expr);
        System.out.println(expr);
        // 然后处理 $ 运算符
        return evaluateDollarOperator(expr);
    }

    // 处理 # 运算符
    private static String handleHashOperator(String expr) {
        // 使用栈来处理 # 运算符的优先级
        Stack<String> stack = new Stack<>();
        StringBuilder numberBuffer = new StringBuilder();
        for (char ch : expr.toCharArray()) {
            if (Character.isDigit(ch)) {
                numberBuffer.append(ch);
            } else {
                //栈里面有数据
                if (!stack.empty() && stack.size() > 1) {
                    String op = stack.pop();
                    //高优先级
                    if ("#".equals(op)) {
                        int x = Integer.parseInt(stack.pop());
                        int y = Integer.parseInt(numberBuffer.toString());
                        int result = 4 * x + 3 * y + 2;
                        stack.push(String.valueOf(result));
                    } else {
                        stack.push(op);
                        stack.push(numberBuffer.toString());
                    }
                    stack.push(String.valueOf(ch));
                    numberBuffer.setLength(0);
                } else {
                    if (numberBuffer.length() > 0) {
                        stack.push(numberBuffer.toString());
                        numberBuffer.setLength(0);
                    }
                    //运算符
                    stack.push(String.valueOf(ch));
                }

            }
        }
        if (numberBuffer.length() > 0) {
            String op = stack.pop();
            //高优先级
            if ("#".equals(op)) {
                int x = Integer.parseInt(stack.pop());
                int y = Integer.parseInt(numberBuffer.toString());
                int result = 4 * x + 3 * y + 2;
                stack.push(String.valueOf(result));
            } else {
                stack.push(op);
                stack.push(numberBuffer.toString());
            }
            numberBuffer.setLength(0);
        }

        return String.join("", stack);
    }

    // 处理 $ 运算符
    private static int evaluateDollarOperator(String expr) {
        String[] tokens = expr.split("\\$");
        int x = Integer.parseInt(tokens[0]);
        for (int i = 1; i < tokens.length; i++) {
            int y = Integer.parseInt(tokens[i]);
            x = 2 * x + y + 3;
        }
        return x;
    }

    private static void Test7() {
        String martianExpr = "1#2$3#4$5";
        System.out.println(evaluateMartianExpression(martianExpr));
    }

    //一个整数表示成一个连续自然数的和
    public static void findConsecutiveSums(int N) {
        int count = 0;

        // k 是连续自然数的个数，至少需要两个数
        for (int k = 2; k < (2 * N + 1); k++) {
            // 判断是否存在合适的 a
            int numerator = N - (k * (k - 1)) / 2;

            // 如果 numerator 能够被 k 整除并且 a 是正整数
            if (numerator > 0 && numerator % k == 0) {
                int a = numerator / k;
                count++;

                // 打印出该表达式
                System.out.print(N + " = ");
                for (int i = 0; i < k; i++) {
                    if (i > 0) {
                        System.out.print(" + ");
                    }
                    System.out.print((a + i));
                }
                System.out.println();
            }
        }

        // 打印总的表达式数目
        System.out.println("Total number of expressions: " + count);
    }

    private static void Test8() {
        MemoryAllocator allocator = new MemoryAllocator();

        allocator.printMemory();

        MemoryBlock block1 = allocator.allocate(20);
        allocator.printMemory();

        MemoryBlock block2 = allocator.allocate(30);
        allocator.printMemory();

        allocator.free(block1);
        allocator.printMemory();

        MemoryBlock block3 = allocator.allocate(25);
        allocator.printMemory();
    }

    /**
     * 假定街道是棋盘型的，每格距离相等，车辆通过每格街道需要时间均为 timePerRoad；街道的街口（交叉点）有交通灯，灯的周期T(=lights[row][col])各不相同；车辆可直行、左转和右转，其中直行和左转需要等相应T时间的交通灯才可通行，右转无需等待。
     * 现给出 n*m 个街口的交通灯周期，以及起止街口的坐标，计算车辆经过两个街口的最短时间。
     * 其中：
     * 1）起点和终点的交通灯不计入时间，且可以任意方向经过街口
     * 2）不可超出 n*m 个街口，不可跳跃，但边线也是道路（即 lights[0][0] -> lights[0][1] 是有效路径）
     * <p>
     * 入口函数定义:
     * <p>
     * lights : n*m 个街口每个交通灯的周期，值范围[0,120]，n和m的范围为[1,9]
     * timePerRoad : 相邻两个街口之间街道的通过时间,范围为[0,600]
     * rowStart : 起点的行号
     * colStart : 起点的列号
     * rowEnd : 终点的行号
     * colEnd : 终点的列号
     * return : lights[rowStart][colStart] 与 lights[rowEnd][colEnd] 两个街口之间的最短通行时间
     * <p>
     * int calcTime(int[][] lights,int timePerRoad,int rowStart,int colStart,int rowEnd,int colEnd)
     */
    public static int calcTime(int[][] lights, int timePerRoad, int rowStart, int colStart, int rowEnd, int colEnd) {
        int n = lights.length;
        int m = lights[0].length;

        boolean[][] visited = new boolean[n][m];
        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.offer(new Node(rowStart, colStart, 0));

        while (!pq.isEmpty()) {
            Node current = pq.poll();
            int row = current.row;
            int col = current.col;
            int currentTime = current.time;

            if (visited[row][col]) continue;
            visited[row][col] = true;

            // 到达终点
            if (row == rowEnd && col == colEnd) {
                return currentTime;
            }

            // 尝试移动到四个方向
            for (int i = 0; i < 4; i++) {
                int newRow = row + drections[i][0];
                int newCol = col + drections[i][1];

                if (newRow < 0 || newRow >= n || newCol < 0 || newCol >= m || visited[newRow][newCol]) {
                    continue;
                }

                int newTime = currentTime + timePerRoad;
                if (i == 0 || i == 1) { // 上下（直行）
                    newTime += waitTime(currentTime, lights[row][col]);
                } else { // 左右（左转或右转）
                    if (i == 2) { // 左转
                        newTime += waitTime(currentTime, lights[row][col]);
                    }
                    // 右转不需等待
                }

                pq.offer(new Node(newRow, newCol, newTime));
            }
        }

        return -1; // 无法到达终点
    }

    private static int waitTime(int currentTime, int lightCycle) {
        if (lightCycle == 0) return 0;
        return (lightCycle - (currentTime % lightCycle)) % lightCycle;
    }

    private static void Test9() {
        int[][] lights = {
                {10, 20, 30},
                {30, 0, 20},
                {40, 10, 0}
        };
        int timePerRoad = 5;
        int rowStart = 0, colStart = 0;
        int rowEnd = 2, colEnd = 2;

        int result = calcTime(lights, timePerRoad, rowStart, colStart, rowEnd, colEnd);
        System.out.println("最短通行时间: " + result);
    }

    //    给一个正整数列 nums，一个跳数 jump，及幸存数量 left。运算过程为：从索引为0的位置开始向后跳，中间跳过 J 个数字，命中索引为J+1的数字，该数被敲出，并从该点起跳，以此类推，直到幸存left个数为止。然后返回幸存数之和。
//    约束：
//            1）0是第一个起跳点。
//            2）起跳点和命中点之间间隔 jump 个数字，已被敲出的数字不计入在内。
//            3）跳到末尾时无缝从头开始（循环查找），并可以多次循环。
//            4）若起始时 left>len(nums) 则无需跳数处理过程。
//
//    /**
//     * nums: 正整数数列，长度范围 [1,10000]
//     * jump: 跳数，范围 [1,10000]
//     * left: 幸存数量，范围 [0,10000]
//     * return: 幸存数之和
//     */
//    int sumOfLeft(int[] nums,int jump,int left)
    public static int sumOfLeft(int[] nums, int jump, int left) {
        int n = nums.length;

        // 如果 left 大于等于数组长度，直接返回数组总和
        if (left >= n) {
            int sum = 0;
            for (int num : nums) {
                sum += num;
            }
            return sum;
        }

        boolean[] removed = new boolean[n];
        int remaining = n;
        int index = 0;

        // 模拟跳跃移除过程
        while (remaining > left) {
            int steps = 0;

            // 跳过 jump 个未被移除的元素
            while (steps < jump) {
                index = (index + 1) % n;
                if (!removed[index]) {
                    steps++;
                }
            }

            // 移除当前元素
            removed[index] = true;
            remaining--;

            // 移动到下一个未被移除的元素
            while (removed[index]) {
                index = (index + 1) % n;
            }
        }

        // 计算剩余元素的和
        int sum = 0;
        for (int i = 0; i < n; i++) {
            if (!removed[i]) {
                sum += nums[i];
            }
        }

        return sum;
    }

    private static void Test10() {
        int[] nums = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        int jump = 2;
        int left = 4;
        System.out.println(sumOfLeft(nums, jump, left));  // 示例输出
    }

    //    某部门计划通过结队编程来进行项目开发，已知该部门有N名员工，每个员工有独一无二的职级，每三个员工形成一个小组进行结队编程，结队分组规则如下：
//
//
//    从部门中选出序号分别为 i、j、k 的 3 名员工，他们的职级分别为 level[i]、level [j]、level [k]
//
//    结队小组需满足： level [i] < level [j] < level [k] 或者 level [i] > level [j] > level [k] ，其中  0 <= i < j < k < n
//
//    请你按上述条件计算可能组合的小组数量。同一员工可以参加多个小组。
    public static int countTeams(int[] level) {
        int n = level.length;
        int count = 0;

        // 遍历所有可能的中间元素 j
        for (int j = 1; j < n - 1; j++) {
            int leftLess = 0, leftGreater = 0;
            int rightLess = 0, rightGreater = 0;

            // 统计左侧小于和大于 level[j] 的元素个数
            for (int i = 0; i < j; i++) {
                if (level[i] < level[j]) leftLess++;
                if (level[i] > level[j]) leftGreater++;
            }

            // 统计右侧小于和大于 level[j] 的元素个数
            for (int k = j + 1; k < n; k++) {
                if (level[k] < level[j]) rightLess++;
                if (level[k] > level[j]) rightGreater++;
            }

            // 以 j 为中心，符合递增条件的三元组数量
            count += leftLess * rightGreater;
            // 以 j 为中心，符合递减条件的三元组数量
            count += leftGreater * rightLess;
        }

        return count;
    }

    private static void Test11() {
        int[] levels = {1, 2, 3, 4};
        System.out.println(countTeams(levels));  // 示例输出：4
    }

    //按自己的身高排序
    public static void sortHeights(Integer[] heights, int myHeight) {
        // 使用 Arrays.sort 进行排序
        Arrays.sort(heights, (a, b) -> {
            int diffA = Math.abs((int) a - myHeight);
            int diffB = Math.abs((int) b - myHeight);

            if (diffA == diffB) {
                return (int) a - (int) b;  // 若差值相同，则按身高升序排序
            } else {
                return diffA - diffB;  // 否则按差值升序排序
            }
        });
    }

    private static void Test12() {
        Integer[] heights = {120, 130, 125, 135, 110};
        int myHeight = 128;

        sortHeights(heights, myHeight);

        System.out.println("Sorted heights: " + Arrays.toString(heights));
    }

    //解密函数
    public static String decrypt(String s) {
        // 创建映射表
        Map<String, Character> mapping = new HashMap<>();
        for (int i = 1; i <= 9; i++) {
            mapping.put(String.valueOf(i), (char) ('a' + i - 1));
        }
        for (int i = 10; i <= 26; i++) {
            mapping.put(i + "*", (char) ('j' + i - 10));
        }

        StringBuilder decrypted = new StringBuilder();
        int i = 0;
        while (i < s.length()) {
            if (i + 2 < s.length() && s.charAt(i + 2) == '*') {
                // 处理 '10*' 到 '26*' 的映射
                String key = s.substring(i, i + 3);  // 包含 '*' 的三位字符串
                decrypted.append(mapping.get(key));
                i += 3;
            } else {
                // 处理 '1' 到 '9' 的映射
                String key = s.substring(i, i + 1);  // 单个字符的字符串
                decrypted.append(mapping.get(key));
                i++;
            }
        }

        return decrypted.toString();
    }

    //考勤模型
    public static boolean canGetAward(String[] attendance) {
        // 条件1：缺勤不超过一次
        int absentCount = 0;
        for (String record : attendance) {
            if (record.equals("absent")) {
                absentCount++;
                if (absentCount > 1) {
                    return false;
                }
            }
        }

        // 条件2：没有连续的迟到或早退
        for (int i = 1; i < attendance.length; i++) {
            if ((attendance[i].equals("late") && attendance[i - 1].equals("late")) ||
                    (attendance[i].equals("leaveearly") && attendance[i - 1].equals("leaveearly"))) {
                return false;
            }
        }

        // 条件3：任意连续7次考勤中，缺勤/迟到/早退不超过3次
        for (int i = 0; i <= attendance.length - 7; i++) {
            int count = 0;
            for (int j = i; j < i + 7; j++) {
                if (attendance[j].equals("absent") || attendance[j].equals("late") || attendance[j].equals("leaveearly")) {
                    count++;
                }
            }
            if (count > 3) {
                return false;
            }
        }

        return true;
    }

    private static void Test13() {
        String[] attendance1 = {"present", "late", "present", "leaveearly", "present", "absent", "present"};
        String[] attendance2 = {"present", "late", "leaveearly", "late", "present", "absent", "late"};

        System.out.println(canGetAward(attendance1));  // 输出 true
        System.out.println(canGetAward(attendance2));  // 输出 false
    }

    //会议室使用时间问题
    public static List<int[]> calculateUsage(int[][] meetings) {
        if (meetings == null || meetings.length == 0) {
            return new ArrayList<>();
        }

        // 1. 按开始时间对会议进行排序
        Arrays.sort(meetings, (a, b) -> a[0] - b[0]);

        List<int[]> usageTimes = new ArrayList<>();
        int[] currentInterval = meetings[0];

        for (int i = 1; i < meetings.length; i++) {
            // 2. 检查当前会议是否与之前的会议重叠
            if (meetings[i][0] <= currentInterval[1]) {
                // 如果重叠，合并时间段
                currentInterval[1] = Math.max(currentInterval[1], meetings[i][1]);
            } else {
                // 如果不重叠，添加当前时间段并更新
                usageTimes.add(currentInterval);
                currentInterval = meetings[i];
            }
        }
        // 添加最后一个时间段
        usageTimes.add(currentInterval);

        return usageTimes;
    }

    private static void Test14() {
        int[][] meetings = {
                {1, 3},
                {2, 4},
                {5, 6},
                {8, 10},
                {9, 12}
        };

        List<int[]> usage = calculateUsage(meetings);

        for (int[] interval : usage) {
            System.out.println("[" + interval[0] + ", " + interval[1] + "]");
        }
    }

    // 检查在给定的maxWorkforce下是否能够在M个月内完成所有需求
    private static boolean canFinish(int[] requirements, int M, int maxWorkforce) {
        int monthsNeeded = 0;
        int i = 0;

        while (i < requirements.length) {
            int currentWorkforce = 0;
            int tasksThisMonth = 0;

            // 尝试将两个需求放在同一个月中，且总工作量不超过maxWorkforce
            while (i < requirements.length && tasksThisMonth < 2 && currentWorkforce + requirements[i] <= maxWorkforce) {
                currentWorkforce += requirements[i];
                i++;
                tasksThisMonth++;
            }

            monthsNeeded++;
            if (monthsNeeded > M) {
                return false;  // 如果所需月份超过M，则返回false
            }
        }

        return true;  // 如果能在M个月内完成所有需求，返回true
    }

    public static int minWorkforce(int[] requirements, int M) {
        int left = 0;
        int right = 0;

        for (int req : requirements) {
            right += req;  // 最大人力上限为所有需求的总和
            left = Math.max(left, req);  // 最小人力下限为需求中的最大值
        }

        // 使用二分查找找到最小的每月人力值
        while (left < right) {
            int mid = (left + right) / 2;
            if (canFinish(requirements, M, mid)) {
                right = mid;  // 可以完成任务，尝试更小的人力值
            } else {
                left = mid + 1;  // 无法完成任务，增加人力值
            }
        }

        return left;  // 此时left等于right，即为最小人力值
    }

    private static void Test15() {
        int[] requirements = {10, 20, 30, 40};
        int M = 3;

        int result = minWorkforce(requirements, M);
        System.out.println("每月所需的最小人力: " + result);
    }


    //任务计算法
    private static int maxTasks(int[][] tasks) {
        // 按任务的结束时间升序排序
        Arrays.sort(tasks, (a, b) -> a[1] - b[1]);

        int maxTasks = 0;
        int currentDay = 0;

        for (int[] task : tasks) {
            int start = task[0];
            int end = task[1];

            // 如果当前时间在任务的可执行时间范围内
            if (currentDay <= end) {
                // 更新当前时间
                currentDay = Math.max(currentDay, start);
                // 完成任务
                maxTasks++;
                // 将当前时间推进到下一天
                currentDay++;
            }
        }

        return maxTasks;
    }

    private static void Test16() {
        int[][] tasks = {
                {1, 3},
                {2, 5},
                {3, 9},
                {2, 6},
                {8, 9}
        };
        int result = maxTasks(tasks);
        System.out.println("可以处理的最大任务数: " + result);
    }

    //进制计算
    public static String toBaseM(int k, int m) {
        StringBuilder sb = new StringBuilder();
        while (k > 0) {
            sb.append(k % m);
            k /= m;
        }
        return sb.reverse().toString(); // 因为是从低位到高位计算的，所以需要反转
    }

    // 计算幸运数字n在m进制表示中的出现次数
    public static int countLuckyNumber(int k, int m, int n) {
        String baseMNumber = toBaseM(k, m);
        char luckyDigit = Character.forDigit(n, m); // 将数字n转换为对应的字符
        int count = 0;

        // 统计幸运数字的出现次数
        for (char c : baseMNumber.toCharArray()) {
            if (c == luckyDigit) {
                count++;
            }
        }

        return count;
    }

    /*    Wonderland是小王居住地一家很受欢迎的游乐园。 Wonderland目前有4种售票方式，分别为一日票（1天）、三日票（3天）、周票（7天）和月票（30天）。

        每种售票方式的价格将由一个数组给出，每种票据在票面时限内可以无限制的进行游玩。例如，小王在第10日买了一张三日票，小王可以在第10日、第11日和第12日进行无限制的游玩。

        小王计划在接下来一年内多次游玩该游乐园。小王计划的游玩日期将由一个数组给出。 现在，请您根据给出的售票价格数组和小王计划游玩日期数组，返回完成游玩计划所需要的最低消费。
       */
    public static int minCostTickets(int[] days, int[] costs) {
        Set<Integer> daySet = new HashSet<>();
        for (int day : days) {
            daySet.add(day);
        }

        int lastDay = days[days.length - 1];
        int[] dp = new int[lastDay + 31]; // 额外的30天用于处理月票的边界情况

        for (int i = lastDay; i >= 0; i--) {
            if (!daySet.contains(i)) {
                dp[i] = dp[i + 1];
            } else {
                dp[i] = Math.min(costs[0] + dp[i + 1],
                        Math.min(costs[1] + dp[i + 3],
                                Math.min(costs[2] + dp[i + 7],
                                        costs[3] + dp[i + 30])));
            }
        }

        return dp[days[0]]; // 从第一个游玩日开始的最小花费
    }

    private static void Test17() {
        int[] days = {1, 4, 6, 7, 8, 20}; // 小王计划游玩日
        int[] costs = {2, 7, 15, 50};     // 一日票，三日票，周票，月票价格

        int result = minCostTickets(days, costs);
        System.out.println("最低消费: " + result);
    }

    /*
        从前有个村庄，村民们喜欢在各种田地上插上小旗子，
        旗子上标识了各种不同的数字。某天集体村民决定将覆盖相同数字的最小矩阵形的土地的分配给为村里做出巨大贡献的村民，
        请问，此次分配土地，做出贡献的村民中最大会分配多大面积？
        */
    public static int maxArea(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return 0;

        int maxArea = 0;
        Map<Integer, int[]> numberBoundaries = new HashMap<>();

        int rows = matrix.length;
        int cols = matrix[0].length;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int num = matrix[i][j];
                numberBoundaries.putIfAbsent(num, new int[]{i, j, i, j});

                int[] bounds = numberBoundaries.get(num);
                bounds[0] = Math.min(bounds[0], i); // Update top boundary
                bounds[1] = Math.min(bounds[1], j); // Update left boundary
                bounds[2] = Math.max(bounds[2], i); // Update bottom boundary
                bounds[3] = Math.max(bounds[3], j); // Update right boundary
            }
        }

        for (Map.Entry<Integer, int[]> entry : numberBoundaries.entrySet()) {
            int[] bounds = entry.getValue();
            int area = (bounds[2] - bounds[0] + 1) * (bounds[3] - bounds[1] + 1);
            maxArea = Math.max(maxArea, area);
        }

        return maxArea;
    }

    private static void Test18() {
        int[][] matrix = {
                {1, 2, 3, 4},
                {5, 2, 2, 2},
                {5, 5, 3, 4},
                {5, 5, 5, 4}
        };

        int result = maxArea(matrix);
        System.out.println("最大分配面积: " + result);
    }
/*

    小华和小为是很要好的朋友，
    他们约定周末一起吃饭。通过手机交流，他们在地图上选择了多个聚餐地点（
    由于自然地形等原因，部分聚餐地点不可达），求小华和小为都能到达的聚餐地点有多少个？
*/

    public static int countCommonPoints(int[][] grid, int[] startA, int[] startB) {
        Set<String> reachableA = bfs(grid, startA);
        Set<String> reachableB = bfs(grid, startB);

        reachableA.retainAll(reachableB); // 取交集

        return reachableA.size();
    }

    private static Set<String> bfs(int[][] grid, int[] start) {
        Set<String> reachable = new HashSet<>();
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(start);
        reachable.add(start[0] + "," + start[1]);

        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            for (int[] dir : drections) {
                int newRow = curr[0] + dir[0];
                int newCol = curr[1] + dir[1];
                if (isValid(grid, newRow, newCol) && !reachable.contains(newRow + "," + newCol)) {
                    queue.offer(new int[]{newRow, newCol});
                    reachable.add(newRow + "," + newCol);
                }
            }
        }
        return reachable;
    }

    private static boolean isValid(int[][] grid, int row, int col) {
        return row >= 0 && col >= 0 && row < grid.length && col < grid[0].length && grid[row][col] == 1;
    }

    private static void Test19() {
        int[][] grid = {
                {1, 1, 0, 0, 0},
                {0, 1, 0, 1, 1},
                {1, 0, 1, 1, 0}
        };
        int[] startA = {0, 0};
        int[] startB = {2, 2};

        int result = countCommonPoints(grid, startA, startB);
        System.out.println("共同可达的聚餐地点数量: " + result);
    }

    /*有N块二手市场收集的银饰，每块银饰的重量都是正整数，收集到的银饰会被熔化用于打造新的饰品。
    每一回合，从中选出三块 最重的 银饰，然后一起熔掉。假设银饰的重量分别为 x 、y和z，且 x <= y <= z。那么熔掉的可能结果如下：
    如果 x == y == z，那么三块银饰都会被完全熔掉；
    如果 x == y 且 y != z，会剩余重量为 z - y 的银块无法被熔掉；
    如果 x != y 且 y == z，会剩余重量为 y - x 的银块无法被熔掉；
    如果 x != y 且 y != z，会剩余重量为 z - y 与 y - x 差值 的银块无法被熔掉。
    最后，如果剩余两块，返回较大的重量（若两块重量相同，返回任意一块皆可）；如果只剩下一块，返回该块的重量；如果没有剩下，就返回 0。*/

    public static int meltSilver(int[] weights) {
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());

        // 将所有银饰重量加入最大堆中
        for (int weight : weights) {
            maxHeap.offer(weight);
        }

        while (maxHeap.size() >= 3) {
            int z = maxHeap.poll();
            int y = maxHeap.poll();
            int x = maxHeap.poll();

            if (x == y && y == z) {
                // 三块银饰完全熔化，剩余 0
                continue;
            } else if (x == y && y != z) {
                // 剩余重量为 z - y
                maxHeap.offer(z - y);
            } else if (x != y && y == z) {
                // 剩余重量为 y - x
                maxHeap.offer(y - x);
            } else {
                // 剩余重量为 z - y 与 y - x 差值
                maxHeap.offer(z - y);
                maxHeap.offer(y - x);
            }
        }

        // 最终剩下的银饰块数
        if (maxHeap.size() == 2) {
            int first = maxHeap.poll();
            int second = maxHeap.poll();
            return Math.max(first, second);
        } else if (maxHeap.size() == 1) {
            return maxHeap.poll();
        } else {
            return 0;
        }
    }

    private static void Test20() {
        int[] weights = {9, 7, 5, 3, 2};
        System.out.println("最终剩下的银饰重量: " + meltSilver(weights)); // 输出示例结果

    }

    /*

        特定大小的停车场，数组cars[]表示，其中1表示有车，0表示没车。
        车辆大小不一，小车占一个车位（长度1），货车占两个车位（长度2），卡车占三个车位（长度3），
        统计停车场最少可以停多少辆车，返回具体的数目。
    */
    public static int minCars(int[] cars) {
        int n = cars.length;
        int count = 0;
        int i = 0;

        while (i < n) {
            if (i + 2 < n && cars[i] == 0 && cars[i + 1] == 0 && cars[i + 2] == 0) {
                // 停放一辆卡车
                count++;
                i += 3;
            } else if (i + 1 < n && cars[i] == 0 && cars[i + 1] == 0) {
                // 停放一辆货车
                count++;
                i += 2;
            } else if (cars[i] == 0) {
                // 停放一辆小车
                count++;
                i += 1;
            } else {
                // 车位已经被占用，跳到下一个位置
                i += 1;
            }
        }

        return count;
    }

    private static void Test21() {
        int[] cars = {0, 0, 1, 0, 0, 0, 1, 0};
        System.out.println("最少可以停放的车辆数: " + minCars(cars)); // 输出示例结果

    }

    /*    输入字符串s，输出s中包含所有整数的最小和
                说明
    1. 字符串s，只包含 a-z A-Z +- ；
                2. 合法的整数包括
        1） 正整数 一个或者多个0-9组成，如 0 2 3 002 102
                2）负整数 负号 - 开头，数字部分由一个或者多个0-9组成，如 -0 -012 -23 -00023*/
    public static int minSumOfIntegers(String s) {
        List<Integer> integers = new ArrayList<>();
        int n = s.length();
        int i = 0;

        while (i < n) {
            // 跳过非数字和符号字符
            if (!Character.isDigit(s.charAt(i)) && s.charAt(i) != '+' && s.charAt(i) != '-') {
                i++;
                continue;
            }

            // 识别一个合法的整数
            int sign = 1;
            if (s.charAt(i) == '-') {
                sign = -1;
                i++;
            } else if (s.charAt(i) == '+') {
                i++;
            }

            // 处理数字部分
            int num = 0;
            boolean foundDigits = false;
            while (i < n && Character.isDigit(s.charAt(i))) {
                foundDigits = true;
                num = num * 10 + (s.charAt(i) - '0');
                i++;
            }

            if (foundDigits) {
                integers.add(sign * num);
            }
        }

        // 计算所有整数的和
        int sum = 0;
        for (int num : integers) {
            sum += num;
        }

        return sum;
    }

    private static void Test22() {
        String s = "ab-12bc+34ef-0003xyz002";
        System.out.println("字符串中所有整数的最小和为: " + minSumOfIntegers(s));
    }

    /*    提取字符串中的最长合法简单数学表达式，字符串长度最长的，并计算表达式的值。如果没有，则返回0
                简单数学表达式只能包含以下内容
    0-9数字，符号 +-*
        说明：
                1. 所有数字，计算结果都不超过long
    2. 如果有多个长度一样的，请返回第一个表达式的结果
    3. 数学表达式，必须是最长的，合法的
    4. 操作符不能连续出现，如 +--+1 是不合法的*/
    public static long calculateLongestExpression(String s) {
        int n = s.length();
        String longestExpression = "";
        StringBuilder currentExpression = new StringBuilder();

        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);

            if (Character.isDigit(c) || c == '+' || c == '-' || c == '*') {
                // Check for consecutive operators
                if (currentExpression.length() > 0 && !Character.isDigit(currentExpression.charAt(currentExpression.length() - 1)) && !Character.isDigit(c)) {
                    currentExpression = new StringBuilder();
                }
                currentExpression.append(c);
            } else {
                // Check and update the longest expression if valid
                if (currentExpression.length() > longestExpression.length() && isValidExpression(currentExpression.toString())) {
                    longestExpression = currentExpression.toString();
                }
                currentExpression = new StringBuilder();
            }
        }

        // Final check at the end of the loop
        if (currentExpression.length() > longestExpression.length() && isValidExpression(currentExpression.toString())) {
            longestExpression = currentExpression.toString();
        }

        return longestExpression.isEmpty() ? 0 : evaluateExpression(longestExpression);
    }

    // Function to check if the expression is valid (no consecutive operators)
    private static boolean isValidExpression(String expression) {
        for (int i = 1; i < expression.length(); i++) {
            if (!Character.isDigit(expression.charAt(i)) && !Character.isDigit(expression.charAt(i - 1))) {
                return false;
            }
        }
        return true;
    }

    // Function to evaluate a valid mathematical expression
    private static long evaluateExpression(String expression) {
        Stack<Long> nums = new Stack<>();
        Stack<Character> ops = new Stack<>();
        long num = 0;
        char prevOp = '+';

        for (int i = 0; i <= expression.length(); i++) {
            char c = i < expression.length() ? expression.charAt(i) : '#'; // '#' as a sentinel
            if (Character.isDigit(c)) {
                num = num * 10 + (c - '0');
            } else {
                if (prevOp == '+') {
                    nums.push(num);
                } else if (prevOp == '-') {
                    nums.push(-num);
                } else if (prevOp == '*') {
                    nums.push(nums.pop() * num);
                }
                prevOp = c;
                num = 0;
            }
        }

        long result = 0;
        while (!nums.isEmpty()) {
            result += nums.pop();
        }
        return result;
    }

    private static void Test23() {
        String s = "12+34-5*67abc123--56+78";
        System.out.println("最长合法表达式的值为: " + calculateLongestExpression(s));
    }

    /*有一种特殊的加密算法，明文为一段数字串，经过密码本查找转换，生成另一段密文数字串。规则如下：

            1.   明文为一段数字串由0~9组成

2.   密码本为数字0~9组成的二维数组

3.   需要按明文串的数字顺序在密码本里找到同样的数字串，密码本里的数字串是由相邻的单元格数字组成，上下和左右是相邻的，注意：对角线不相邻，同一个单元格的数字不能重复使用。

            4.   每一位明文对应密文即为密码本中找到的单元格所在的行和列序号（序号从0开始）组成的两个数字。如明文 第i位Data[i] 对应密码本单元格为Book[x][y]，则明文第i位对应的密文为X Y，X和Y之间用空格隔开。如果有多条密文，返回字符序最小的密文。如果密码本无法匹配，返回”error”。

    请你设计这个加密程序。*/

    private static final int[] DIRS = {0, 1, 0, -1, 1, 0, -1, 0}; // 方向数组：右，下，左，上

    public static String encrypt(int[][] book, String data) {
        int rows = book.length;
        int cols = book[0].length;
        List<String> validPaths = new ArrayList<>();

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (book[r][c] == data.charAt(0) - '0') {
                    boolean[][] visited = new boolean[rows][cols];
                    List<String> path = new ArrayList<>();
                    if (dfs(book, data, 0, r, c, visited, path)) {
                        validPaths.add(String.join(" ", path));
                    }
                }
            }
        }

        if (validPaths.isEmpty()) {
            return "error";
        }

        Collections.sort(validPaths); // 字典序排序
        return validPaths.get(0);
    }

    private static boolean dfs(int[][] book, String data, int index, int r, int c, boolean[][] visited, List<String> path) {
        if (index == data.length()) {
            return true; // 全部匹配完毕
        }

        if (r < 0 || r >= book.length || c < 0 || c >= book[0].length || visited[r][c] || book[r][c] != data.charAt(index) - '0') {
            return false;
        }

        visited[r][c] = true;
        path.add(r + " " + c);

        for (int i = 0; i < 4; i++) {
            int nr = r + DIRS[i * 2];
            int nc = c + DIRS[i * 2 + 1];
            if (dfs(book, data, index + 1, nr, nc, visited, path)) {
                return true;
            }
        }

        visited[r][c] = false;
        path.remove(path.size() - 1);
        return false;
    }
    private static void Test24() {
        int[][] book = {
                {0, 0, 2},
                {1, 3, 4},
                {6, 6, 4}
        };
        String data = "3";

        String result = encrypt(book, data);
        System.out.println("密文为: " + result);
    }

    /*有一个文件, 包含以一定规则写作的文本, 请统计文件中包含的文本数量


            规则如下

1. 文本以";"分隔，最后一条可以没有";"，但空文本不能算语句，比如"COMMAND A; ;"只能算一条语句.
            注意, 无字符/空白字符/制表符都算作"空"文本
2. 文本可以跨行, 比如下面, 是一条文本, 而不是三条
    COMMAND A
    AND
    COMMAND B;
3. 文本支持字符串, 字符串为成对的单引号(')或者成对的双引号("), 字符串可能出现用转义字符(\)处理的单双引号(比如"your input is: \"")和转义字符本身, 比如
                                        COMMAND A "Say \"hello\"";
4. 支持注释, 可以出现在字符串之外的任意位置, 注释以"--"开头, 到换行结束, 比如
    COMMAND A; -- this is comment
    COMMAND -- comment
    A AND COMMAND B;
    注意, 字符串内的"--", 不是注释*/

    public static int countValidStatements(String fileContent) {
        // Remove comments, but keep newlines
        String noComments = fileContent.replaceAll("--.*(?=\n)", "");

        boolean inSingleQuote = false;
        boolean inDoubleQuote = false;
        boolean escape = false;
        StringBuilder currentStatement = new StringBuilder();
        int statementCount = 0;

        for (int i = 0; i < noComments.length(); i++) {
            char c = noComments.charAt(i);

            if (escape) {
                currentStatement.append(c);
                escape = false;
            } else if (c == '\\') {
                currentStatement.append(c);
                escape = true;
            } else if (c == '\'' && !inDoubleQuote) {
                inSingleQuote = !inSingleQuote;
                currentStatement.append(c);
            } else if (c == '"' && !inSingleQuote) {
                inDoubleQuote = !inDoubleQuote;
                currentStatement.append(c);
            } else if (c == ';' && !inSingleQuote && !inDoubleQuote) {
                // End of a statement
                if (!currentStatement.toString().trim().isEmpty()) {
                    statementCount++;
                }
                currentStatement.setLength(0);
            } else {
                currentStatement.append(c);
            }
        }

        // Handle any remaining statement that might not be terminated by a semicolon
        if (!currentStatement.toString().trim().isEmpty()) {
            statementCount++;
        }

        return statementCount;
    }

    private static void Test25() {
        String fileContent = "\""
                + "COMMAND A \"Say \\\"hello\\\"\";"
                + "COMMAND B;"
                +"-- this is a comment\r\n"
                +"COMMAND C;"
                +"\"";
        System.out.println(fileContent);
        int count = countValidStatements(fileContent);
        System.out.println("Number of valid statements: " + count);
    }
/*

    <p>  RSA加密算法在网络安全世界中无处不在，
    它利用了极大整数因数分解的困难度，数据越大，安全系数越高，
    给定一个<span>32</span>位正整数，请对其进行因数分解，找出是哪两个素数的乘积。 </p>
*/

    // 获取所有小于等于 n 的素数列表
    private static List<Integer> generatePrimes(int n) {
        List<Integer> primes = new ArrayList<>();
        boolean[] isPrime = new boolean[n + 1];
        for (int i = 2; i <= n; i++) {
            isPrime[i] = true;
        }

        for (int p = 2; p * p <= n; p++) {
            if (isPrime[p]) {
                for (int i = p * p; i <= n; i += p) {
                    isPrime[i] = false;
                }
            }
        }

        for (int i = 2; i <= n; i++) {
            if (isPrime[i]) {
                primes.add(i);
            }
        }

        return primes;
    }

    // 将32位整数分解为两个素数的乘积
    public static int[] factorize(int n) {
        int limit = (int) Math.sqrt(n);
        List<Integer> primes = generatePrimes(limit);

        for (int prime : primes) {
            if (n % prime == 0) {
                int otherFactor = n / prime;
                if (isPrime(otherFactor)) {
                    return new int[]{prime, otherFactor};
                }
            }
        }

        return new int[]{-1, -1}; // 如果无法分解成两个素数的乘积
    }

    // 判断一个数是否为素数
    private static boolean isPrime(int n) {
        if (n <= 1) {
            return false;
        }
        if (n <= 3) {
            return true;
        }
        if (n % 2 == 0 || n % 3 == 0) {
            return false;
        }

        for (int i = 5; i * i <= n; i += 6) {
            if (n % i == 0 || n % (i + 2) == 0) {
                return false;
            }
        }

        return true;
    }
    private static void Test26() {
        int number = 217; // 输入一个32位正整数
        int[] factors = factorize(number);

        if (factors[0] != -1) {
            System.out.println("The number " + number + " can be factorized into primes: " +
                    factors[0] + " * " + factors[1]);
        } else {
            System.out.println("The number " + number + " cannot be factorized into two primes.");
        }
    }

    /*主管期望你来实现英文输入法单词联想功能。需求如下：

    依据用户输入的单词前缀，从已输入的英文语句中联想出用户想输入的单词，按字典序输出联想到的单词序列，如果联想不到，请输出用户输入的单词前缀。

    注意：

            1.  英文单词联想时，区分大小写

2.  缩略形式如”don't”，判定为两个单词，”don”和”t”

            3.  输出的单词序列，不能有重复单词，且只能是英文单词，不能有标点符号*/

    // 获取建议的单词列表
    public static List<String> getWordSuggestions(String inputText, String prefix) {
        // 使用 Set 来自动去重
        Set<String> wordSet = new TreeSet<>();

        // 分割字符串并清理标点符号
        String[] words = inputText.split("[^a-zA-Z']+");

        for (String word : words) {
            // 如果单词包含缩略形式 "don't"，将其拆分
            if (word.contains("'")) {
                String[] parts = word.split("'");
                for (String part : parts) {
                    if (!part.isEmpty() && part.startsWith(prefix)) {
                        wordSet.add(part);
                    }
                }
            } else if (word.startsWith(prefix)) {
                wordSet.add(word);
            }
        }

        return new ArrayList<>(wordSet);
    }

    private static void Test27() {
        // 假设已经输入的英文语句
        String inputText = "Hello, don't you think this is a great idea? Don't be late!";
        String prefix = "do"; // 用户输入的前缀

        List<String> suggestions = getWordSuggestions(inputText, prefix);

        if (suggestions.isEmpty()) {
            System.out.println(prefix);
        } else {
            for (String word : suggestions) {
                System.out.println(word);
            }
        }
    }

   /* 快递公司每日早晨，给每位快递员推送需要送到客户手中的快递以及路线信息，快递员自己又查找了一些客户与客户之间的路线距离信息，请你依据这些信息，给快递员设计一条最短路径，告诉他最短路径的距离。

    注意：

            1.     不限制快递包裹送到客户手中的顺序，但必须保证都送到客户手中

2.     用例保证一定存在投递站到每位客户之间的路线，但不保证客户与客户之间有路线，客户位置及投递站均允许多次经过

3.     所有快递送完后，快递员需回到投递站*/
    private static final int INF = Integer.MAX_VALUE / 2;
    // TSP 状态压缩 + 动态规划
    public static int tsp(int[][] dist, int n) {
        int[][] dp = new int[1 << n][n];
        for (int[] row : dp) {
            Arrays.fill(row, INF);
        }
        dp[1][0] = 0; // 初始化起点

        for (int mask = 1; mask < (1 << n); mask++) {
            for (int u = 0; u < n; u++) {
                if ((mask & (1 << u)) != 0) {
                    for (int v = 0; v < n; v++) {
                        if ((mask & (1 << v)) == 0) {
                            dp[mask | (1 << v)][v] = Math.min(dp[mask | (1 << v)][v], dp[mask][u] + dist[u][v]);
                        }
                    }
                }
            }
        }

        int res = INF;
        for (int i = 1; i < n; i++) {
            res = Math.min(res, dp[(1 << n) - 1][i] + dist[i][0]);
        }
        return res;
    }

    private static void Test28() {
        // 输入示例
        int n = 4; // 3个客户+1个投递站
        int[][] dist = {
                {0, 10, 15, 20}, // 投递站到客户1, 客户2, 客户3
                {10, 0, 35, 25}, // 客户1到 投递站, 客户2, 客户3
                {15, 35, 0, 30}, // 客户2到 投递站, 客户1, 客户3
                {20, 25, 30, 0}  // 客户3到 投递站, 客户1, 客户2
        };

        int minDistance = tsp(dist, n);
        System.out.println("最短路径的距离是: " + minDistance);
    }

    /*程序员小明打了一辆出租车去上班。出于职业敏感，他注意到这辆出租车的计费表有点问题，总是偏大。

    出租车司机解释说他不喜欢数字4，所以改装了计费表，任何数字位置遇到数字4就直接跳过，其余功能都正常。

    比如：

            1.     23再多一块钱就变为25；

            2.     39再多一块钱变为50；

            3.     399再多一块钱变为500；

    小明识破了司机的伎俩，准备利用自己的学识打败司机的阴谋。

    给出计费表的表面读数，返回实际产生的费用。*/

    public static int actualFare(int fare) {
        int result = 0;
        int base = 1;

        while (fare > 0) {
            int digit = fare % 10;
            fare /= 10;

            if (digit > 4) {
                digit--;  // 因为“缺4”系统
            }

            result += digit * base;
            base *= 9;  // 缺4系统的基数是9而不是10
        }

        return result;
    }

    /*开头和结尾都是元音字母（aeiouAEIOU）的字符串为 元音字符串 ，其中混杂的非元音字母数量为其 瑕疵度 。比如:

            ·         “a” 、 “aa”是元音字符串，其瑕疵度都为0

·         “aiur”不是元音字符串（结尾不是元音字符）

            ·         “abira”是元音字符串，其瑕疵度为2

    给定一个字符串，请找出指定瑕疵度的最长元音字符子串，并输出其长度，如果找不到满足条件的元音字符子串，输出0。

    子串：字符串中任意个连续的字符组成的子序列称为该字符串的子串。*/

    public static int findLongestVowelSubstring(String s, int maxDefects) {
        Set<Character> vowels = new HashSet<>();
        for (char c : "aeiouAEIOU".toCharArray()) {
            vowels.add(c);
        }

        int maxLength = 0;
        int defects = 0;
        int left = 0;

        for (int right = 0; right < s.length(); right++) {
            if (!vowels.contains(s.charAt(right))) {
                defects++;
            }

            // 确保当前子串是元音字符串并且瑕疵度不超过maxDefects
            while (defects > maxDefects || !vowels.contains(s.charAt(left)) || !vowels.contains(s.charAt(right))) {
                if (!vowels.contains(s.charAt(left))) {
                    defects--;
                }
                left++;
            }

            if (vowels.contains(s.charAt(left)) && vowels.contains(s.charAt(right)) && defects <= maxDefects) {
                maxLength = Math.max(maxLength, right - left + 1);
            }
        }

        return maxLength;
    }

    private static void Test29() {
        System.out.println(findLongestVowelSubstring("abira", 2));  // Output: 5
        System.out.println(findLongestVowelSubstring("abcdeiou", 1)); // Output: 4
        System.out.println(findLongestVowelSubstring("aeiouxyz", 3)); // Output: 7
        System.out.println(findLongestVowelSubstring("bcefg", 1)); // Output: 0
    }
    /*给定两个整数数组array1、array2，数组元素按升序排列。假设从array1、array2中分别取出一个元素可构成一对元素，现在需要取出k对元素，并对取出的所有元素求和，计算和的最小值
    注意：两对元素如果对应于array1、array2中的两个下标均相同，则视为同一对元素。*/

    public static int findKSmallestPairsSum(int[] array1, int[] array2, int k) {
        int n = array1.length;
        int m = array2.length;

        // 最小堆，元素为 {sum, i, j}，其中 sum 为 array1[i] + array2[j] 的和
        PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> a[0] - b[0]);

        // 初始化最小堆，先将 array1 中第一个元素与 array2 中的前 k 个元素配对
        for (int i = 0; i < Math.min(k, n); i++) {
            minHeap.offer(new int[]{array1[i] + array2[0], i, 0});
        }

        int resultSum = 0;

        // 从堆中取出 k 个最小的和
        while (k > 0 && !minHeap.isEmpty()) {
            int[] current = minHeap.poll();
            resultSum += current[0];
            int i = current[1];
            int j = current[2];

            // 如果 array2 中还有更多的元素，可以和 array1[i] 组合
            if (j + 1 < m) {
                minHeap.offer(new int[]{array1[i] + array2[j + 1], i, j + 1});
            }
            k--;
        }

        return resultSum;
    }
    private static void Test30() {
        int[] array1 = {1, 7, 11};
        int[] array2 = {2, 4, 6};
        int k = 3;

        System.out.println(findKSmallestPairsSum(array1, array2, k));
    }

    /*绘图机器的绘图笔初始位置在原点（0, 0），机器启动后其绘图笔按下面规则绘制直线：
            1）尝试沿着横向坐标轴正向绘制直线，直到给定的终点值E。
            2）期间可通过指令在纵坐标轴方向进行偏移，并同时绘制直线，偏移后按规则1 绘制直线；指令的格式为X offsetY，表示在横坐标X 沿纵坐标方向偏移，offsetY为正数表示正向偏移，为负数表示负向偏移。

    给定了横坐标终点值E、以及若干条绘制指令，请计算绘制的直线和横坐标轴、以及 X=E 的直线组成图形的面积。*/

    public static int calculateArea(int E, List<int[]> commands) {
        int area = 0;
        int currentX = 0;
        int currentY = 0;

        // 遍历指令
        for (int[] command : commands) {
            int nextX = command[0];
            int offsetY = command[1];

            // 计算当前X到下一指令X形成的矩形面积
            area += Math.abs(currentY) * (nextX - currentX);

            // 更新当前位置
            currentX = nextX;
            currentY += offsetY;
        }

        // 计算最后一段到E的面积
        area += Math.abs(currentY) * (E - currentX);

        return area;
    }
    private static void Test39() {
        // 终点E
        int E = 10;

        // 指令列表
        List<int[]> commands = new ArrayList<>();
        commands.add(new int[]{2, 3});  // 在X=2处偏移3
        commands.add(new int[]{5, -2}); // 在X=5处偏移-2
        commands.add(new int[]{8, 1});  // 在X=8处偏移1

        int area = calculateArea(E, commands);
        System.out.println("绘制的图形面积为: " + area);
    }

    /*服务之间交换的接口成功率作为服务调用关键质量特性，
    某个时间段内的接口失败率使用一个数组表示，
    数组中每个元素都是单位时间内失败率数值，
    数组中的数值为0~100的整数，
    给定一个数值(minAverageLost)表示某个时间段内平均失败率容忍值，
    即平均失败率小于等于minAverageLost，找出数组中最长时间段，如果未找到则直接返回NULL。*/

    public static String findLongestSubarray(int[] failedRates, int minAverageLost) {
        int n = failedRates.length;
        int start = 0, end = 0;
        int currentSum = 0;
        int maxLength = 0;
        int bestStart = -1, bestEnd = -1;

        while (end < n) {
            currentSum += failedRates[end];
            while (start <= end && currentSum > minAverageLost * (end - start + 1)) {
                currentSum -= failedRates[start];
                start++;
            }

            // Check if this is the longest valid subarray found
            if (end - start + 1 > maxLength) {
                maxLength = end - start + 1;
                bestStart = start;
                bestEnd = end;
            }
            end++;
        }

        if (maxLength == 0) {
            return "NULL";
        } else {
            return "Start: " + bestStart + ", End: " + bestEnd;
        }
    }
    private static void Test31() {
        int[] failedRates = {10, 20, 30, 40, 50, 60, 70};
        int minAverageLost = 30;
        System.out.println(findLongestSubarray(failedRates, minAverageLost));
    }

    /*给定M(0<M<=30)个字符（a-z），
    从中取出任意字符（每个字符只能用一次）拼接成长度为N(0<N<=5)的字符串，
    要求相同的字符不能相邻，计算出给定的字符列表能拼接出多少种满足条件的字符串，
    输入非法或者无法拼接出满足条件的字符串则返回0。*/

    public static int countValidStrings(String chars, int N) {
        if (chars == null || chars.length() == 0 || N <= 0 || N > 5 || chars.length() < N) {
            return 0;
        }

        Set<String> results = new HashSet<>();
        char[] charArray = chars.toCharArray();
        boolean[] used = new boolean[charArray.length];

        backtrack(results, new StringBuilder(), charArray, used, N);

        return results.size();
    }

    private static void backtrack(Set<String> results, StringBuilder current, char[] chars, boolean[] used, int N) {
        if (current.length() == N) {
            results.add(current.toString());
            return;
        }

        for (int i = 0; i < chars.length; i++) {
            if (used[i]) continue;
            if (current.length() > 0 && current.charAt(current.length() - 1) == chars[i]) continue;

            current.append(chars[i]);
            used[i] = true;
            backtrack(results, current, chars, used, N);
            used[i] = false;
            current.deleteCharAt(current.length() - 1);
        }
    }
    private static void Test32() {
        String chars = "abc";
        int N = 3;
        System.out.println(countValidStrings(chars, N)); // Example output: 6
    }

    /*给定两个字符串，分别为字符串A与字符串B。例如A字符串为ABCABBA，
    B字符串为CBABAC可以得到下图m*n的二维数组，定义原点为(0, 0)，终点为(m, n)，
    水平与垂直的每一条边距离为1,
    从原点(0, 0)到(0, A)为水平边，距离为1，从(0, A)到(A, C)为垂直边，距离为1；
    假设两个字符串同一位置的两个字符相同则可以作一个斜边，如(A, C)到(B, B)最短距离为斜边，距离同样为1。
    (0, 0)到(B, B)的距离为 1个水平边 + 1个垂直边 + 1个斜边 = 3。
    根据定义可知，原点到终点的最短距离路径最短距离为9：
    */
    public static int shortestDistance(String A, String B,int m,int n) {

        // Initialize DP table
        int[][] dp = new int[m + 1][n + 1];

        // Fill DP table
        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                if (i == 0 && j == 0) {
                    dp[i][j] = 0;
                } else {
                    int horizontal = (j > 0) ? dp[i][j - 1] + 1 : Integer.MAX_VALUE;
                    int vertical = (i > 0) ? dp[i - 1][j] + 1 : Integer.MAX_VALUE;
                    int diagonal = (i > 0 && j > 0 && A.charAt(i - 1) == B.charAt(j - 1)) ? dp[i - 1][j - 1] + 1 : Integer.MAX_VALUE;
                    dp[i][j] = Math.min(horizontal, Math.min(vertical, diagonal));
                }
            }
        }

        return dp[m][n];
    }
    private static void Test33() {
        String A = "ABCABBA";
        String B = "CBABAC";
        System.out.println("Shortest distance: " + shortestDistance(A, B,2,6));
    }

    /*1.众数是指一组数据中出现次数量多的那个数，众数可以是多个
2.中位数是指把一组数据从小到大排列，最中间的那个数，如果这组数据的个数是奇数，那最中间那个就是中位数，如果这组数据的个数为偶数，那就把中间的两个数之和除以2，所得的结果就是中位数
3.查找整型数组中元素的众数并组成一个新的数组，求新数组的中位数*/

    // 计算众数
    private static List<Integer> findModes(int[] nums) {
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        int maxFrequency = 0;

        // 统计每个元素的出现次数
        for (int num : nums) {
            int count = frequencyMap.getOrDefault(num, 0) + 1;
            frequencyMap.put(num, count);
            maxFrequency = Math.max(maxFrequency, count);
        }

        // 找出所有出现次数最多的元素
        List<Integer> modes = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
            if (entry.getValue() == maxFrequency) {
                modes.add(entry.getKey());
            }
        }

        return modes;
    }

    // 计算中位数
    private static double calculateMedian(List<Integer> list) {
        Collections.sort(list);

        int size = list.size();
        if (size % 2 == 1) {
            // 奇数长度，返回中间的元素
            return list.get(size / 2);
        } else {
            // 偶数长度，返回两个中间元素的平均值
            return (list.get(size / 2 - 1) + list.get(size / 2)) / 2.0;
        }
    }
    private static void Test34() {
        int[] array = {1, 2, 2, 3, 3, 4}; // 示例数组

        // 步骤 1: 查找众数
        List<Integer> modes = findModes(array);

        // 步骤 2: 计算新数组的中位数
        List<Integer> newArray = new ArrayList<>();
        for (int mode : modes) {
            newArray.add(mode);
        }
        double median = calculateMedian(newArray);

        System.out.println("众数: " + modes);
        System.out.println("新数组的中位数: " + median);
    }

    /*项目组共有N个开发人员，项目经理接到了M个独立的需求，
    每个需求的工作量不同，且每个需求只能由一个开发人员独立完成，
    不能多人合作。假定各个需求直接无任何先后依赖关系，
    请设计算法帮助项目经理进行工作安排，使整个项目能用最少的时间交付。*/

    public static int minimumCompletionTime(int N, Integer[] demands) {

        // Step 1: Sort demands in descending order
        Integer[] sortedDemands = Arrays.copyOf(demands,demands.length);
        Arrays.sort(sortedDemands, Collections.reverseOrder());

        // Step 2: Use a min-heap to track the current load of each developer
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(N);
        for (int i = 0; i < N; i++) {
            minHeap.add(0);  // Initialize all developers with load 0
        }

        // Step 3: Allocate demands to developers
        for (int demand : sortedDemands) {
            int currentLoad = minHeap.poll();  // Get the developer with the least load
            currentLoad += demand;  // Assign the demand to this developer
            minHeap.add(currentLoad);  // Update the developer's load in the heap
        }

        // Step 4: The maximum load in the heap is the minimum time needed
        return Collections.max(minHeap);
    }

    private static void Test35() {
        int N = 3;  // Number of developers
        Integer[] demands = {8, 5, 7, 6, 3,13,16};  // Workload for each demand

        int minCompletionTime = minimumCompletionTime(N, demands);
        System.out.println("Minimum Completion Time: " + minCompletionTime);
    }

    //用数组代表每个人的能力，一个比赛活动要求参赛团队的最低能力值为N，每个团队可以由1人或2人组成，且1个人只能参加1个团队，请计算出最多可以派出多少支符合要求的团队？
    public static int maxTeams(int[] abilities, int N) {
        Arrays.sort(abilities);  // Sort the abilities array
        int n = abilities.length;
        boolean[] used = new boolean[n];
        int teams = 0;

        // First, count individuals who can form teams alone
        for (int i = n - 1; i >= 0; i--) {
            if (abilities[i] >= N && !used[i]) {
                used[i] = true;
                teams++;
            }
        }

        // Then, try to form teams with pairs
        int left = 0;
        int right = n - 1;
        while (left < right) {
            if (used[left]) {
                left++;
                continue;
            }
            if (used[right]) {
                right--;
                continue;
            }
            if (abilities[left] + abilities[right] >= N) {
                // Can form a valid team
                used[left] = true;
                used[right] = true;
                teams++;
                left++;
                right--;
            } else {
                // Try with the next smallest element
                left++;
            }
        }

        return teams;
    }

    /*A、B两个人玩抢7游戏，游戏规则为A先报一个起始数字X(10<起始数字<10000)，
    B报下一个数字Y(X-Y<3),A再报一个数字Z(Y-Z<3)，
    以此类推，直到其中一个抢到7，抢到7即为胜者；在B赢得比赛的情况下，一共有多少种组合？*/
    private static final int WIN_NUMBER = 7;
    private static final int MAX_START = 10000;

    public static int countBWinCombinations() {
        int[][][] dp = new int[WIN_NUMBER + 1][2][WIN_NUMBER + 1];

        // Initialize the DP array for winning condition
        for (int i = WIN_NUMBER - 1; i >= 0; i--) {
            dp[i][1][i] = 1; // B wins if the game starts with number 7 and it's B's turn
        }

        // Fill DP table
        for (int a = WIN_NUMBER - 1; a >= 1; a--) {
            for (int turn = 1; turn >= 0; turn--) {
                for (int current = WIN_NUMBER - 1; current >= 1; current--) {
                    if (turn == 0) { // A's turn
                        dp[current][turn][current] = 0;
                        for (int next = current + 1; next <= WIN_NUMBER - 1 && next <= current + 2; next++) {
                            dp[current][turn][current] += dp[next][1][next];
                        }
                    } else { // B's turn
                        dp[current][turn][current] = 0;
                        for (int next = current + 1; next <= WIN_NUMBER - 1 && next <= current + 2; next++) {
                            dp[current][turn][current] += dp[next][0][next];
                        }
                    }
                }
            }
        }

        // Sum up all combinations where B wins
        int totalCombinations = 0;
        for (int start = 11; start < MAX_START; start++) {
            if (start < WIN_NUMBER) continue; // Start must be >= 7
            totalCombinations += dp[start][1][start];
        }

        return totalCombinations;
    }

    /*给定一个连续不包含空格字符串，该字符串仅包含英文小写字母及英文文标点符号(逗号、分号、句号)，同时给定词库，对该字符串进行精确分词。
    说明：
            1.精确分词： 字符串分词后，不会出现重叠。即“ilovechina” ，
            不同词库可分割为 “i，love，china” “ilove，china”，不能分割出现重叠的"i，ilove，china",i重叠出现
2.标点符号不成词，仅用于断句
3.词库：根据外部知识库统计出来的常用词汇例：dictionary=["i","love","china","lovechina","ilove"],
            4.分词原则：采用分词顺序优先且最长匹配原则
“ilovechina”，假设分词结果  [ i,ilove,lo,love,ch,china,lovechina ] 则输出 [ilove，china]
    错误输出：[i,lovechina],            原因："ilove ">优先于 "lovechina"成词
    错误输出：[i,love,china]            原因："ilove" >"i"  遵循最长匹配原则

*/

    public static List<String> segmentString(String s, List<String> dictionary) {
        List<String> result = new ArrayList<>();
        Set<String> dictSet = new HashSet<>(dictionary);

        int i = 0;
        while (i < s.length()) {
            int longestMatchLength = 0;
            String longestMatch = "";

            for (int j = i + 1; j <= s.length(); j++) {
                String subStr = s.substring(i, j);
                if (dictSet.contains(subStr) && subStr.length() > longestMatchLength) {
                    longestMatchLength = subStr.length();
                    longestMatch = subStr;
                }
            }

            if (longestMatchLength > 0) {
                result.add(longestMatch);
                i += longestMatchLength;
            } else {
                i++;
            }
        }

        return result;
    }
    private static void Test36() {
        String s = "ilovechina";
        List<String> dictionary = Arrays.asList("i", "love", "china", "lovechina", "ilove");

        List<String> segmented = segmentString(s, dictionary);
        System.out.println(segmented);  // Output: [ilove, china]
    }

    /*1到n的n个连续的数字组成一个数组，n为3的倍数
    每次按照顺序从数组中取出3个元素，去掉这3个元素中的一个最大值和一个最小值，并将剩下的元素累加为S，S初始值为0
    可以通过调整数组中元素的位置改变最终结果，每移动一个元素计为移动一次。请计算最少移动几次可以使得数组和S最大。*/
    public static int minMovesToMaxSum(int[] array) {
        int n = array.length;
        int[] sortedArray = array.clone();
        Arrays.sort(sortedArray);

        // Reverse sorted array to make it from largest to smallest
        for (int i = 0; i < n / 2; i++) {
            int temp = sortedArray[i];
            sortedArray[i] = sortedArray[n - i - 1];
            sortedArray[n - i - 1] = temp;
        }

        // Calculate the maximum sum S
        int S = 0;
        for (int i = 1; i < n; i += 3) {
            S += sortedArray[i];
        }

        // Calculate minimum moves
        Map<Integer, Integer> originalFrequency = new HashMap<>();
        for (int num : array) {
            originalFrequency.put(num, originalFrequency.getOrDefault(num, 0) + 1);
        }

        Map<Integer, Integer> sortedFrequency = new HashMap<>();
        for (int num : sortedArray) {
            sortedFrequency.put(num, sortedFrequency.getOrDefault(num, 0) + 1);
        }

        int moves = 0;
        for (int num : originalFrequency.keySet()) {
            int originalCount = originalFrequency.get(num);
            int sortedCount = sortedFrequency.getOrDefault(num, 0);
            moves += Math.abs(originalCount - sortedCount);
        }

        return moves / 2; // Each move is counted twice in the above loop
    }

    private static void Test37() {
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        System.out.println("Minimum moves to maximize S: " + minMovesToMaxSum(array));
    }

    /*给定一个矩阵，包含N*M个整数，和一个包含K个整数的数组。
    现在要求在这个矩阵中找一个宽度最小的子矩阵，要求子矩阵包含数组中所有的整数。*/
    private static boolean containsAll(int[][] matrix, int startRow, int endRow, int startCol, int endCol, Set<Integer> targetSet) {
        Set<Integer> foundSet = new HashSet<>();
        for (int i = startRow; i <= endRow; i++) {
            for (int j = startCol; j <= endCol; j++) {
                foundSet.add(matrix[i][j]);
                if (foundSet.size() == targetSet.size()) {
                    return true;
                }
            }
        }
        return false;
    }

    public static int findMinimumWidthSubmatrix(int[][] matrix, int[] targetArray) {
        int N = matrix.length;
        int M = matrix[0].length;
        Set<Integer> targetSet = new HashSet<>();
        for (int num : targetArray) {
            targetSet.add(num);
        }

        int minWidth = Integer.MAX_VALUE;

        // Traverse all possible submatrices
        for (int startRow = 0; startRow < N; startRow++) {
            for (int endRow = startRow; endRow < N; endRow++) {
                for (int startCol = 0; startCol < M; startCol++) {
                    for (int endCol = startCol; endCol < M; endCol++) {
                        if (containsAll(matrix, startRow, endRow, startCol, endCol, targetSet)) {
                            int width = endCol - startCol + 1;
                            minWidth = Math.min(minWidth, width);
                        }
                    }
                }
            }
        }

        return minWidth == Integer.MAX_VALUE ? -1 : minWidth; // Return -1 if no valid submatrix is found
    }
    private static void Test38() {
        int[][] matrix = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}
        };

        int[] targetArray = {5, 7, 11};

        int minWidth = findMinimumWidthSubmatrix(matrix, targetArray);
        System.out.println("Minimum width of the submatrix: " + minWidth);
    }

   /* 为了充分发挥GPU算力，需要尽可能多的将任务交给GPU执行，
   现在有一个任务数组，数组元素表示在这1秒内新增的任务个数且每秒都有新增任务，
   假设GPU最多一次执行n个任务，一次执行耗时1秒，在保证GPU不空闲情况下，最少需要多长时间执行完成*/
   public static int calculateMinimumTime(int[] taskArray, int n) {
       // Calculate the total number of tasks
       int totalTasks = 0;
       for (int tasks : taskArray) {
           totalTasks += tasks;
       }

       // Calculate the minimum time required
       // Use ceiling division: (totalTasks + n - 1) / n
       int minTime = (totalTasks + n - 1) / n;

       return minTime;
   }

    /*n个学生排成一排，学生编号分别是1到n，n为3的整倍数。老师随机抽签决定将所有学生分成m个3人的小组，n=3*m
    为了便于同组学生交流，老师决定将小组成员安排到一起，也就是同组成员彼此相连，同组任意两个成员之间无其它组的成员。
    因此老师决定调整队伍，老师每次可以调整任何一名学生到队伍的任意位置，计为调整了一次，
    请计算最少调整多少次可以达到目标。
    注意：对于小组之间没有顺序要求，同组学生之间没有顺序要求。*/
    public static int minAdjustments(int[] students, int n) {
        int m = n / 3;
        List<Integer>[] groups = new ArrayList[m];
        for (int i = 0; i < m; i++) {
            groups[i] = new ArrayList<>();
        }

        // 分配学生到相应的组
        for (int i = 0; i < n; i++) {
            int groupNumber = (students[i] - 1) / 3;
            groups[groupNumber].add(i);
        }

        // 计算最少移动次数
        int moves = 0;
        for (int i = 0; i < m; i++) {
            int targetPos = 3 * i;
            for (int j = 0; j < groups[i].size(); j++) {
                moves += Math.abs(groups[i].get(j) - (targetPos + j));
            }
        }

        return moves;
    }

    private static void Test40() {
        int[] students = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        int n = students.length;
        int result = minAdjustments(students, n);
        System.out.println("最少调整次数: " + result);
    }

    /*一个歌手准备从A城去B城参加演出。

            1)     按照合同，他必须在T天内赶到。

            2)     歌手途径N座城市。

            3)     歌手不能往回走。

            4)     每两座城市之间需要的天数都可以提前获知。

            5)     歌手在每座城市都可以在路边卖唱赚钱。经过调研，歌手提前获知了每座城市卖唱的收入预期：

    如果在一座城市第一天卖唱可以赚M，后续每天的收入会减少D（第二天赚的钱是M – D，第三天是M-2D…）。如果收入减到0就不会再少了。

            6)     歌手到达后的第二天才能开始卖唱。如果今天卖过唱，第二天才能出发。

    贪心的歌手最多可以赚多少钱？*/
    public static int maxProfit(int N, int T, int[][] paths, int[] M, int D) {
        // 初始化DP数组，所有值设置为负无穷，表示无法到达的状态
        int[][] dp = new int[N][T + 1];
        for (int[] row : dp) {
            Arrays.fill(row, Integer.MIN_VALUE);
        }
        dp[0][0] = 0;  // 起点城市的初始状态

        // 动态规划计算最大收益
        for (int i = 1; i < N; i++) {
            for (int t = 0; t <= T; t++) {
                for (int j = 0; j < i; j++) {
                    int daysRequired = paths[j][i];
                    if (daysRequired > 0 && t >= daysRequired) {
                        // 尝试在城市 i 卖唱 k 天
                        int profit = 0;
                        for (int k = 0; k + t + daysRequired <= T; k++) {
                            int income = Math.max(0, M[i] - D * k);
                            profit += income;
                            dp[i][t + daysRequired + k] = Math.max(dp[i][t + daysRequired + k], dp[j][t] + profit);
                        }
                    }
                }
            }
        }

        // 在所有能够在T天内到达终点的状态中，找到最大收益
        int maxProfit = Integer.MIN_VALUE;
        for (int t = 0; t <= T; t++) {
            maxProfit = Math.max(maxProfit, dp[N - 1][t]);
        }

        return maxProfit == Integer.MIN_VALUE ? 0 : maxProfit;
    }
    private static void Test41() {
        int N = 5;  // 城市数量
        int T = 10; // 必须在T天内赶到
        int[][] paths = {
                {0, 2, 5, 0, 0},
                {0, 0, 2, 4, 0},
                {0, 0, 0, 2, 3},
                {0, 0, 0, 0, 2},
                {0, 0, 0, 0, 0}
        };
        int[] M = {0, 10, 20, 30, 40}; // 各城市第一天卖唱的收入
        int D = 2;  // 收入每天递减的金额

        int result = maxProfit(N, T, paths, M, D);
        System.out.println("最大收益: " + result);
    }

    /*算法工程师小明面对着这样一个问题，需要将通信用的信道分配给尽量多的用户：


    信道的条件及分配规则如下：

            1)     所有信道都有属性：”阶”。阶为r的信道的容量为2^r比特；

            2)     所有用户需要传输的数据量都一样：D比特；

            3)     一个用户可以分配多个信道，但每个信道只能分配给一个用户；

            4)     只有当分配给一个用户的所有信道的容量和>=D，用户才能传输数据；


    给出一组信道资源，最多可以为多少用户传输数据？*/
    public static int maxUsers(int[] channels, int D) {
        // 将信道容量从大到小排序
        Integer[] sortedChannels = Arrays.stream(channels).boxed().toArray(Integer[]::new);
        Arrays.sort(sortedChannels, Collections.reverseOrder());

        int userCount = 0;
        int currentCapacity = 0;

        for (int capacity : sortedChannels) {
            currentCapacity += capacity;
            if (currentCapacity >= D) {
                userCount++;      // 成功为一个用户分配信道
                currentCapacity = 0; // 重置当前累积容量，继续为下一个用户分配
            }
        }

        return userCount;
    }

    private static void Test42() {
        // 信道阶，容量为2^r
        int[] channels = {8, 4, 4, 4, 2, 2, 1};  // 阶为3的信道有3个，阶为2的信道有2个，阶为1的信道有1个
        int D = 8;  // 每个用户需要传输的数据量

        int maxUsers = maxUsers(channels, D);
        System.out.println("最多可以为多少用户传输数据: " + maxUsers);
    }

    /*推荐多样性需要从多个列表中选择元素，一次性要返回N屏数据（窗口数量），每屏展示K个元素（窗口大小），选择策略：
            1. 各个列表元素需要做穿插处理，即先从第一个列表中为每屏选择一个元素，再从第二个列表中为每屏选择一个元素，依次类推
2. 每个列表的元素尽量均分为N份，如果不够N个，也要全部分配完，参考样例图：
            （1）从第一个列表中选择4条0 1 2 3，分别放到4个窗口中
（2）从第二个列表中选择4条10 11 12 13，分别放到4个窗口中
（3）从第三个列表中选择4条20 21 22 23，分别放到4个窗口中
（4）再从第一个列表中选择4条4 5 6 7，分别放到4个窗口中
...
        （5）再从第一个列表中选择，由于数量不足4条，取剩下的2条，放到窗口1和窗口2
（6）再从第二个列表中选择，由于数量不足4条并且总的元素数达到窗口要求，取18 19放到窗口3和窗口4

*/
    public static List<List<Integer>> selectElements(List<List<Integer>> lists, int N, int K) {
        // 初始化窗口数组，每个窗口是一个列表
        List<List<Integer>> windows = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            windows.add(new ArrayList<>());
        }

        // 循环处理每个列表中的元素
        boolean elementsRemaining = true;
        while (elementsRemaining) {
            elementsRemaining = false;
            for (List<Integer> list : lists) {
                int count = 0;
                while (!list.isEmpty() && count < N) {
                    windows.get(count).add(list.remove(0));
                    count++;
                }
                if (!list.isEmpty()) {
                    elementsRemaining = true;
                }
            }
        }

        // 检查每个窗口是否达到K个元素，如果不足，填充空值或根据需要补充逻辑
        for (List<Integer> window : windows) {
            while (window.size() < K) {
                // 根据具体业务逻辑，可以选择填充特殊值，或者根据其他策略处理
                window.add(null); // 这里使用null占位
            }
        }

        return windows;
    }
    private static void Test43() {
        // 示例输入
        List<List<Integer>> lists = new ArrayList<>();
        lists.add(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7));
        lists.add(Arrays.asList(10, 11, 12, 13, 18, 19));
        lists.add(Arrays.asList(20, 21, 22, 23));

        int N = 4; // 窗口数量
        int K = 2; // 每个窗口展示的元素数量

        List<List<Integer>> result = selectElements(lists, N, K);

        // 输出结果
        for (int i = 0; i < result.size(); i++) {
            System.out.println("Window " + (i + 1) + ": " + result.get(i));
        }
    }

    /*1.老李是货运公司承运人，老李的货车额定载货重量为wt
2.现有两种货物，货物A单件重量为wa，单件运费利润为pa，货物B单件重量为wb，单件运费利润为pb
3.老李每次发车时载货总重量刚好为货车额定载货重量wt，车上必须同时有货物A和货物B，货物A、B不可切割
4.老李单车次满载运输可获得的最高利润是多少*/

    public static int maxProfit(int wt, int wa, int pa, int wb, int pb) {
        int maxProfit = 0;

        // 枚举货物A的数量
        for (int x = 1; x * wa < wt; x++) {
            // 计算剩余重量可以容纳的货物B的数量
            int remainingWeight = wt - x * wa;
            if (remainingWeight > 0 && remainingWeight % wb == 0) {
                int y = remainingWeight / wb;
                // 计算利润
                int currentProfit = x * pa + y * pb;
                // 更新最大利润
                maxProfit = Math.max(maxProfit, currentProfit);
            }
        }

        return maxProfit;
    }
    private static void Test44() {
        int wt = 100; // 货车额定载重量
        int wa = 10;  // 货物A的重量
        int pa = 5;   // 货物A的利润
        int wb = 20;  // 货物B的重量
        int pb = 10;  // 货物B的利润

        int maxProfit = maxProfit(wt, wa, pa, wb, pb);
        System.out.println("单车次满载运输可获得的最高利润为: " + maxProfit);
    }

    /*一根X米长的树木，伐木工切割成不同长度的木材后进行交易，
    交易价格为每根木头长度的乘积。规定切割后的每根木头长度都为正整数；
    也可以不切割，直接拿整根树木进行交易。请问伐木工如何尽量少的切割，才能使收益最大化？*/
    public static int maxProfit(int X) {
        int[] dp = new int[X + 1];

        // 初始化
        for (int i = 1; i <= X; i++) {
            dp[i] = i; // 不切割的情况
        }

        // 动态规划求解
        for (int i = 2; i <= X; i++) {
            for (int j = 1; j <= i / 2; j++) {
                dp[i] = Math.max(dp[i], dp[j] * dp[i - j]);
            }
        }

        return dp[X];
    }
    private static void Test45() {
        int X = 10; // 树木长度
        int maxProfit = maxProfit(X);
        System.out.println("最大收益为: " + maxProfit);
    }

    /*现有两组服务器A和B，每组有多个算力不同的CPU，
    其中A[i]是A组第i个CPU的运算能力，B[i]是B组第i个CPU的运算能力。
    一组服务器的总算力是各CPU的算力之和。为了让两组服务器的算力相等，
    允许从每组各选出一个CPU进行一次交换，求两组服务器中，用于交换的CPU的算力，
    并且要求从A组服务器中选出的CPU，算力尽可能小。*/
    public static int[] findCPUsToSwap(int[] A, int[] B) {
        int sumA = 0, sumB = 0;
        for (int a : A) sumA += a;
        for (int b : B) sumB += b;

        int target = (sumA - sumB) / 2;

        Set<Integer> setB = new HashSet<>();
        for (int b : B) {
            setB.add(b);
        }

        for (int a : A) {
            int b = a - target;
            if (setB.contains(b)) {
                return new int[]{a, b};
            }
        }

        return new int[]{-1, -1}; // No valid swap found
    }
    private static void Test46() {
        int[] A = {1, 3, 5, 7};
        int[] B = {2, 4, 6, 8};

        int[] result = findCPUsToSwap(A, B);
        if (result[0] != -1) {
            System.out.println("Swap A: " + result[0] + " with B: " + result[1]);
        } else {
            System.out.println("No valid swap found.");
        }
    }

    /*M（1<=M <=20）辆车需要在一条不能超车的单行道到达终点，
    起点到终点的距离为N（1<=N<=400）。速度快的车追上前车后，只能以前车的速度继续行驶，求最后一车辆到达目的地花费的时间。
    注：每辆车固定间隔1小时出发，比如第一辆车0时出发，第二辆车1时出发，依次类推*/
    private static void Test47() {
        Scanner scanner = new Scanner(System.in);
        int M = scanner.nextInt(); // 车的数量
        int N = scanner.nextInt(); // 终点距离
        int[] speeds = new int[M]; // 每辆车的速度
        for (int i = 0; i < M; i++) {
            speeds[i] = scanner.nextInt();
        }

        // 到达终点所需时间的数组
        double[] arrivalTimes = new double[M];
        for (int i = 0; i < M; i++) {
            double time = N / (double) speeds[i];  // 计算每辆车如果没有阻碍，所需的时间
            arrivalTimes[i] = i + time;  // 出发时间 + 无阻碍行驶时间
            if (i > 0 && arrivalTimes[i] < arrivalTimes[i - 1]) {
                arrivalTimes[i] = arrivalTimes[i - 1];  // 如果追上了前车，就以前车的时间到达
            }
        }

        // 最后一辆车的到达时间即为答案
        System.out.printf("%.6f\n", arrivalTimes[M - 1]);
    }

    /*小明在玩一个游戏，游戏规则如下：
    在游戏开始前，小明站在坐标轴原点处（坐标值为0）。
    给定一组指令和一个幸运数，每个指令都是一个整数，小明按照指定的要求前进或者后退指定的步数。前进代表朝坐标轴的正方向走，后退代表朝坐标轴的负方向走。
    幸运数为一个整数，如果某个指令正好和幸运数相等，则小明行进步数加1。

    例如：
    幸运数为3，指令为[2,3,0,-5]
    指令为2，表示前进2步；
    指令为3，正好和幸运数相等，前进3+1=4步；
    指令为0，表示原地不动，既不前进，也不后退。
    指令为-5，表示后退5步；

    请你计算小明在整个游戏过程中，小明所处的最大坐标值。*/
    public static int getMaxPosition(int luckyNumber, int[] instructions) {
        int position = 0; // 当前坐标
        int maxPosition = 0; // 最大坐标

        for (int instruction : instructions) {
            int steps = instruction;

            // 如果指令等于幸运数，步数+1
            if (instruction == luckyNumber) {
                steps += 1;
            }

            // 更新当前位置
            position += steps;

            // 更新最大坐标值
            if (position > maxPosition) {
                maxPosition = position;
            }
        }

        return maxPosition;
    }

    private static void Test48() {
        int luckyNumber = 3; // 幸运数
        int[] instructions = {2, 3, 0, -5}; // 指令数组

        System.out.println(getMaxPosition(luckyNumber, instructions));
    }

    /*现有N个任务需要处理，同一时间只能处理一个任务，处理每个任务所需要的时间固定为1。
    每个任务都有最晚处理时间限制和积分值，在最晚处理时间点之前处理完成任务才可获得对应的积分奖励。
    可用于处理任务的时间有限，请问在有限的时间内，可获得的最多积分。*/
    static class Task {
        int deadline;
        int score;

        public Task(int deadline, int score) {
            this.deadline = deadline;
            this.score = score;
        }
    }
    public static int maxScore(int[] deadlines, int[] scores, int totalTime) {
        int n = deadlines.length;
        List<Task> tasks = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            tasks.add(new Task(deadlines[i], scores[i]));
        }

        // 按任务最晚处理时间排序
        tasks.sort((a, b) -> a.deadline - b.deadline);

        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);
        int currentTime = 0;
        int totalScore = 0;

        for (Task task : tasks) {
            if (currentTime < task.deadline && currentTime < totalTime) {
                maxHeap.offer(task.score);
                totalScore += task.score;
                currentTime++;
            } else if (!maxHeap.isEmpty() && task.score > maxHeap.peek()) {
                totalScore += task.score - maxHeap.poll();
                maxHeap.offer(task.score);
            }
        }

        return totalScore;
    }

    private static void Test49() {
        int[] deadlines = {2, 1, 2, 1, 3};
        int[] scores = {100, 50, 200, 150, 300};
        int totalTime = 3; // 可用的处理时间

        System.out.println("最大可获得积分: " + maxScore(deadlines, scores, totalTime));
    }

    /*定义构造三叉搜索树规则如下：
    每个节点都存有一个数，当插入一个新的数时，从根节点向下寻找，直到找到一个合适的空节点插入。
    查找的规则是：
            1. 如果数小于节点的数减去500，则将数插入节点的左子树
        2. 如果数大于节点的数加上500，则将数插入节点的右子树
        3. 否则，将数插入节点的中子树
    给你一系列数，请按以上规则，按顺序将数插入树中，构建出一棵三叉搜索树，最后输出树的高度。
*/

    private static void Test50() {
    }private static void Test51() {
    }private static void Test52() {
    }private static void Test53() {
    }private static void Test54() {
    }private static void Test55() {
    }

    public static void main(String[] args) {
        Test45();
    }
}
