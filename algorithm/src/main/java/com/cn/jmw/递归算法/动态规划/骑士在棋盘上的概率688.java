package com.cn.jmw.递归算法.动态规划;

import java.util.Arrays;

/**
 * 数据量爆炸的时候，递归算法的时间复杂度是指数级别的，所以需要使用缓存或者动态规划来解决
 *
 * 但是此题动态规划会走过所有的位置，而缓存法只会走过需要的位置。
 *
 * 因为可能性有8的k次方种，所以缓存法的时间复杂度是O(8^k)，指数级建议采用缓存法
 * count(动态规划) >> count(缓存法)
 */
public class 骑士在棋盘上的概率688 {

    /**
     * 常式
     *
     * 棋盘大小是 n x n
     * 一共移动k次
     * 开始位置(row,column)
     */
    public double knightProbability(int n, int k, int row, int column) {
        //常式
        if (k == 0) {
            return 1;
        }

        //需要8的k次方，这里的可能性
        return process(n, k, row, column)/Math.pow(8, k);
    }

    public double process(int n, int k, int row, int column) {
        //棋盘
        if (row < 0 || row >= n || column < 0 || column >= n) {
            return 0;
        }

        //走完了
        if (k == 0) {
            return 1;
        }

        double way = process(n, k - 1, row + 2, column + 1);
        way += process(n, k - 1, row + 2, column - 1);
        way += process(n, k - 1, row - 2, column + 1);
        way += process(n, k - 1, row - 2, column - 1);
        way += process(n, k - 1, row + 1, column + 2);
        way += process(n, k - 1, row + 1, column - 2);
        way += process(n, k - 1, row - 1, column + 2);
        way += process(n, k - 1, row - 1, column - 2);
        return way;
    }

    /**
     * 缓存   这个题就是缓存法速度远远快于递归法和动态规划法
     *
     * 棋盘大小是 n x n
     * 一共移动k次
     * 开始位置(row,column)
     */
    public double knightProbability2(int n, int k, int row, int column) {
        //常式
        if (k == 0) {
            return 1;
        }

        double[][][] dp = new double[n][n][k + 1];
        // 初始化缓存数组
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Arrays.fill(dp[i][j], -1);
            }
        }

        //需要8的k次方，这里的可能性
        return process2(n, k, row, column,dp)/Math.pow(8, k);
    }

    public double process2(int n, int k, int row, int column,double[][][] dp) {
        //棋盘
        if (row < 0 || row >= n || column < 0 || column >= n) {
            return 0;
        }

        //走完了
        if (k == 0) {
            return 1;
        }

        if (dp[row][column][k]!=-1){
            return dp[row][column][k];
        }

        double way = process2(n, k - 1, row + 2, column + 1,dp);
        way += process2(n, k - 1, row + 2, column - 1,dp);
        way += process2(n, k - 1, row - 2, column + 1,dp);
        way += process2(n, k - 1, row - 2, column - 1,dp);
        way += process2(n, k - 1, row + 1, column + 2,dp);
        way += process2(n, k - 1, row + 1, column - 2,dp);
        way += process2(n, k - 1, row - 1, column + 2,dp);
        way += process2(n, k - 1, row - 1, column - 2,dp);

        dp[row][column][k] = way;
        return way;
    }

    /**
     * 动态规划：三维数组
     *
     * 棋盘大小是 n x n
     * 一共移动k次
     * 开始位置(row,column)
     */
    public double knightProbability3(int n, int k, int row, int column) {
        // 创建一个三维数组来保存每个位置在经过 k 步后的可能性
        double[][][] dp = new double[n][n][k + 1];

        // 初始化 k = 0 的情况，即在第 0 步时，马在任意位置的可能性都是 1
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                dp[i][j][0] = 1;
            }
        }

        // 使用动态规划计算每个位置在经过 k 步后的可能性
        for (int step = 1; step <= k; step++) {
            for (int r = 0; r < n; r++) {
                for (int c = 0; c < n; c++) {
                    double way = 0;
                    if (isValidPosition(n, r + 2, c + 1)) way += dp[r + 2][c + 1][step - 1];
                    if (isValidPosition(n, r + 2, c - 1)) way += dp[r + 2][c - 1][step - 1];
                    if (isValidPosition(n, r - 2, c + 1)) way += dp[r - 2][c + 1][step - 1];
                    if (isValidPosition(n, r - 2, c - 1)) way += dp[r - 2][c - 1][step - 1];
                    if (isValidPosition(n, r + 1, c + 2)) way += dp[r + 1][c + 2][step - 1];
                    if (isValidPosition(n, r + 1, c - 2)) way += dp[r + 1][c - 2][step - 1];
                    if (isValidPosition(n, r - 1, c + 2)) way += dp[r - 1][c + 2][step - 1];
                    if (isValidPosition(n, r - 1, c - 2)) way += dp[r - 1][c - 2][step - 1];
                    dp[r][c][step] = way;
                }
            }
        }

        // 返回马在 (row, column) 位置经过 k 步后的可能性
        return dp[row][column][k] / Math.pow(8, k);
    }

    // 判断位置是否在棋盘内
    private boolean isValidPosition(int n, int r, int c) {
        return r >= 0 && r < n && c >= 0 && c < n;
    }

    /**
     * 动态规划：二维化
     *
     * 棋盘大小是 n x n
     * 一共移动k次
     * 开始位置(row,column)
     */
    public double knightProbability4(int n, int k, int row, int column) {
        // 创建一个三维数组来保存每个位置在经过 k 步后的可能性
        double[][] dp = new double[n][n];

        // 初始化 k = 0 的情况，即在第 0 步时，马在任意位置的可能性都是 1
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                dp[i][j] = 1;
            }
        }

        // 使用动态规划计算每个位置在经过 k 步后的可能性
        for (int step = 1; step <= k; step++) {
            double[][] next = new double[n][n];
            for (int r = 0; r < n; r++) {
                for (int c = 0; c < n; c++) {
                    double way = 0;
                    if (isValidPosition(n, r + 2, c + 1)) way += dp[r + 2][c + 1];
                    if (isValidPosition(n, r + 2, c - 1)) way += dp[r + 2][c - 1];
                    if (isValidPosition(n, r - 2, c + 1)) way += dp[r - 2][c + 1];
                    if (isValidPosition(n, r - 2, c - 1)) way += dp[r - 2][c - 1];
                    if (isValidPosition(n, r + 1, c + 2)) way += dp[r + 1][c + 2];
                    if (isValidPosition(n, r + 1, c - 2)) way += dp[r + 1][c - 2];
                    if (isValidPosition(n, r - 1, c + 2)) way += dp[r - 1][c + 2];
                    if (isValidPosition(n, r - 1, c - 2)) way += dp[r - 1][c - 2];
                    next[r][c] = way;
                }
            }
            dp = next;
        }

        // 返回马在 (row, column) 位置经过 k 步后的可能性
        return dp[row][column] / Math.pow(8, k);
    }

    public static void main(String[] args) {
        //测试
        int n = 3, k = 2, row = 0, column = 0;
        骑士在棋盘上的概率688 骑士在棋盘上的概率688 = new 骑士在棋盘上的概率688();
        System.out.println(骑士在棋盘上的概率688.knightProbability(n, k, row, column));
        System.out.println(骑士在棋盘上的概率688.knightProbability2(8, 30, 6, 4));
        System.out.println(骑士在棋盘上的概率688.knightProbability3(8, 30, 6, 4));
        System.out.println(骑士在棋盘上的概率688.knightProbability4(8, 30, 6, 4));
    }
}
