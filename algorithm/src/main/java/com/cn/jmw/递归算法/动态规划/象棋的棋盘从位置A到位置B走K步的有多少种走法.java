package com.cn.jmw.递归算法.动态规划;

import java.util.Arrays;

/**
 * 想象一个象棋的棋盘， 然后把整个棋盘放入第一象限，
 * 棋盘的最左下角是(0.0)位置那么整个棋盘就是横坐标上9条线、纵坐标上10条线的区域给你三个参数x, y. k 返回“马”从(O,d)位置出发，
 * 必须走k步最后落在(xy)上的方法数有多少种?
 * <p>
 * 注意象棋棋盘是10x9的
 */
public class 象棋的棋盘从位置A到位置B走K步的有多少种走法 {

    public int jump(int x, int y, int k) {
        return process(0, 0, k, x, y);
    }

    //当前位置(x,y)
    //还剩下rest步
    //目标(a,b)
    public int process(int x, int y, int rest, int a, int b) {
        //棋盘
        if (x < 0 || x > 9 || y < 0 || y > 8) {
            return 0;
        }

        //走完了
        if (rest == 0) {
            return x == a && y == b ? 1 : 0;
        }

        int way = process(x + 2, y + 1, rest - 1, a, b);
        way += process(x + 2, y - 1, rest - 1, a, b);
        way += process(x - 2, y + 1, rest - 1, a, b);
        way += process(x - 2, y - 1, rest - 1, a, b);
        way += process(x + 1, y + 2, rest - 1, a, b);
        way += process(x + 1, y - 2, rest - 1, a, b);
        way += process(x - 1, y + 2, rest - 1, a, b);
        way += process(x - 1, y - 2, rest - 1, a, b);
        return way;
    }

    /**
     * 缓存
     * <p>
     * 减少重复计算
     */
    public int jump2(int x, int y, int k) {
        // 使用一个三维数组来缓存结果
        int[][][] dp = new int[10][9][k + 1];
        // 初始化缓存数组
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 9; j++) {
                Arrays.fill(dp[i][j], -1);
            }
        }
        return process2(0, 0, k, x, y, dp);
    }

    public int process2(int x, int y, int rest, int a, int b, int[][][] dp) {
        //棋盘
        if (x < 0 || x > 9 || y < 0 || y > 8) {
            return 0;
        }

        //走完了
        if (rest == 0) {
            return x == a && y == b ? 1 : 0;
        }

        if (dp[x][y][rest] != -1) {
            return dp[x][y][rest];
        }

        int way = process2(x + 2, y + 1, rest - 1, a, b, dp);
        way += process2(x + 2, y - 1, rest - 1, a, b, dp);
        way += process2(x - 2, y + 1, rest - 1, a, b, dp);
        way += process2(x - 2, y - 1, rest - 1, a, b, dp);
        way += process2(x + 1, y + 2, rest - 1, a, b, dp);
        way += process2(x + 1, y - 2, rest - 1, a, b, dp);
        way += process2(x - 1, y + 2, rest - 1, a, b, dp);
        way += process2(x - 1, y - 2, rest - 1, a, b, dp);
        dp[x][y][rest] = way;
        return way;
    }

    /**
     * 动态规划
     * 三维立方体
     */
    public int jump3(int x, int y, int k) {
        // 使用一个三维数组来缓存结果
        int[][][] dp = new int[10][9][k + 1];
        dp[x][y][0] = 1;

        //三维的写法 l , x , y
        for (int l = 1; l < k + 1; l++) {
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 9; j++) {
                    int way = 0;
                    if (i + 2 <= 9 && j + 1 <= 8) way += dp[i + 2][j + 1][l - 1];
                    if (i + 2 <= 9 && j - 1 >= 0) way += dp[i + 2][j - 1][l - 1];
                    if (i - 2 >= 0 && j + 1 <= 8) way += dp[i - 2][j + 1][l - 1];
                    if (i - 2 >= 0 && j - 1 >= 0) way += dp[i - 2][j - 1][l - 1];
                    if (i + 1 <= 9 && j + 2 <= 8) way += dp[i + 1][j + 2][l - 1];
                    if (i + 1 <= 9 && j - 2 >= 0) way += dp[i + 1][j - 2][l - 1];
                    if (i - 1 >= 0 && j + 2 <= 8) way += dp[i - 1][j + 2][l - 1];
                    if (i - 1 >= 0 && j - 2 >= 0) way += dp[i - 1][j - 2][l - 1];
                    dp[i][j][l] = way;
                }
            }
        }

        return dp[0][0][k];
    }

    /**
     * 动态规划
     * 二维化
     */
    public int jump4(int x, int y, int k) {
        // 使用一个三维数组来缓存结果
        int[][] dp = new int[10][9];
        dp[x][y] = 1;

        //三维的写法 l , x , y
        for (int l = 1; l < k + 1; l++) {
            int[][] next = new int[10][9];
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 9; j++) {
                    int way = 0;
                    if (i + 2 <= 9 && j + 1 <= 8) way += dp[i + 2][j + 1];
                    if (i + 2 <= 9 && j - 1 >= 0) way += dp[i + 2][j - 1];
                    if (i - 2 >= 0 && j + 1 <= 8) way += dp[i - 2][j + 1];
                    if (i - 2 >= 0 && j - 1 >= 0) way += dp[i - 2][j - 1];
                    if (i + 1 <= 9 && j + 2 <= 8) way += dp[i + 1][j + 2];
                    if (i + 1 <= 9 && j - 2 >= 0) way += dp[i + 1][j - 2];
                    if (i - 1 >= 0 && j + 2 <= 8) way += dp[i - 1][j + 2];
                    if (i - 1 >= 0 && j - 2 >= 0) way += dp[i - 1][j - 2];
                    next[i][j] = way;
                }
            }
            dp = next;
        }

        return dp[0][0];
    }

    /**
     * 动态规划
     * 二维化 + 抽离判断
     */
    public int jump5(int x, int y, int k) {
        // 使用一个三维数组来缓存结果
        int[][] dp = new int[10][9];
        dp[x][y] = 1;

        //三维的写法 l , x , y
        for (int l = 1; l < k + 1; l++) {
            int[][] next = new int[10][9];
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 9; j++) {
                    int way = 0;
                    way += pick(dp,i + 2,j + 1);
                    way += pick(dp,i - 2,j - 1);
                    way += pick(dp,i + 2,j - 1);
                    way += pick(dp,i - 2,j + 1);
                    way += pick(dp,i + 1,j + 2);
                    way += pick(dp,i - 1,j - 2);
                    way += pick(dp,i + 1,j - 2);
                    way += pick(dp,i - 1,j + 2);
                    next[i][j] = way;
                }
            }
            dp = next;
        }

        return dp[0][0];
    }

    public int pick(int[][] dp,int x,int y){
        if (x < 0 || x > 9 || y < 0 || y > 8) {
            return 0;
        }
        return dp[x][y];
    }

    public static void main(String[] args) {
        //测试
        象棋的棋盘从位置A到位置B走K步的有多少种走法 象棋的棋盘从位置A到位置B走K步的有多少种走法 = new 象棋的棋盘从位置A到位置B走K步的有多少种走法();
        //计时
        long l = System.currentTimeMillis();
        System.out.println(象棋的棋盘从位置A到位置B走K步的有多少种走法.jump(7, 7, 10));
        System.out.println(System.currentTimeMillis() - l + "ms");

        long l1 = System.currentTimeMillis();
        System.out.println(象棋的棋盘从位置A到位置B走K步的有多少种走法.jump2(7, 7, 10));
        System.out.println(System.currentTimeMillis() - l1 + "ms");

        long l2 = System.currentTimeMillis();
        System.out.println(象棋的棋盘从位置A到位置B走K步的有多少种走法.jump3(7, 7, 10));
        System.out.println(System.currentTimeMillis() - l2 + "ms");

        long l3 = System.currentTimeMillis();
        System.out.println(象棋的棋盘从位置A到位置B走K步的有多少种走法.jump4(7, 7, 10));
        System.out.println(System.currentTimeMillis() - l3 + "ms");

        long l4 = System.currentTimeMillis();
        System.out.println(象棋的棋盘从位置A到位置B走K步的有多少种走法.jump5(7, 7, 10));
        System.out.println(System.currentTimeMillis() - l4 + "ms");
    }


}
