package com.cn.jmw.递归算法.动态规划;

import java.util.Arrays;

/**
 * 分析此题：
 * <p>
 *     指数值太大了，不能让他单纯的走8个方向，我们知道他的走法是有规律的，所以我们可以用动态规划来解决这个问题
 */
public class 骑士拨号器935 {

    /**
     * 分析此题：
     * <p>
     * 长度为n
     * 在3 x 4上
     * 位置(3,0)和(3,2)是的位置是无法拨号的
     * 并且开始位置可以在0-9的任意位置
     */
    public int knightDialer(int n) {
        // 定义每个数字键盘上可以跳到的下一个数字键盘的位置
        int[][] nextPositions = {
                {4, 6}, {6, 8}, {7, 9}, {4, 8},
                {0, 3, 9}, {}, {0, 1, 7}, {2, 6},
                {1, 3}, {2, 4}
        };

        // 计算以每个数字键盘开始的号码数量
        int count = 0;
        for (int i = 0; i < 10; i++) {
            count += process(n, i, nextPositions);
            count %= (int) (1e9 + 7); // 防止溢出
        }

        return count;
    }

    private int process(int n, int start, int[][] nextPositions) {
        if (n == 1) {
            return 1;
        }

        int total = 0;
        for (int nextPos : nextPositions[start]) {
            total += process(n - 1, nextPos, nextPositions);
            total %= (int) (1e9 + 7); // 防止溢出
        }

        return total;
    }

    public int knightDialer2(int n) {
        // 定义每个数字键盘上可以跳到的下一个数字键盘的位置
        int[][] nextPositions = {
                {4, 6}, {6, 8}, {7, 9}, {4, 8},
                {0, 3, 9}, {}, {0, 1, 7}, {2, 6},
                {1, 3}, {2, 4}
        };

        // 初始化缓存数组
        int[][] dp = new int[n][10];
        for (int i = 0; i < n; i++) {
            Arrays.fill(dp[i], -1);
        }

        // 计算以每个数字键盘开始的号码数量
        int count = 0;
        for (int i = 0; i < 10; i++) {
            count += process(n, i, nextPositions, dp);
            count %= (int) (1e9 + 7); // 防止溢出
        }

        return count;
    }

    private int process(int n, int start, int[][] nextPositions, int[][] dp) {
        if (n == 1) {
            return 1;
        }

        // 如果已经计算过当前位置的数量，则直接返回缓存的值
        if (dp[n - 1][start] != -1) {
            return dp[n - 1][start];
        }

        int total = 0;
        for (int nextPos : nextPositions[start]) {
            total += process(n - 1, nextPos, nextPositions, dp);
            total %= (int) (1e9 + 7); // 防止溢出
        }

        // 将计算结果存入缓存
        dp[n - 1][start] = total;
        return total;
    }

    /**
     * 动态规划 二维
     */
    public int knightDialer3(int n) {
        // 定义每个数字键盘上可以跳到的下一个数字键盘的位置
        int[][] nextPositions = {
                {4, 6}, {6, 8}, {7, 9}, {4, 8},
                {0, 3, 9}, {}, {0, 1, 7}, {2, 6},
                {1, 3}, {2, 4}
        };

        // 初始化缓存数组
        int[][] dp = new int[n][10];

        //n==1
        for (int i = 0; i < 10; i++) {
            dp[0][i] = 1;
        }

        //TODO i是步,10个号码
        // 计算以每个数字键盘开始的号码数量
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < 10; j++) {
                for (int nextPos : nextPositions[j]) {
                    dp[i][j] += dp[i - 1][nextPos];
                    dp[i][j] %= (int) (1e9 + 7); // 防止溢出
                }
            }
        }

        int count = 0;
        for (int i = 0; i < 10; i++) {
            count += dp[n - 1][i];
            count %= (int) (1e9 + 7); // 防止溢出
        }

        return count;
    }


    public static void main(String[] args) {
        // 测试
        System.out.println(new 骑士拨号器935().knightDialer(1)); // 10
        // 测试
        System.out.println(new 骑士拨号器935().knightDialer2(3131)); // 10

        System.out.println(new 骑士拨号器935().knightDialer3(3131)); // 10
    }
}
