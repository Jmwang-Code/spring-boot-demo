package com.cn.jmw.递归算法.动态规划;

public class 骑士在棋盘上的概率688 {

    static double outQP = 0;

    /**
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
            outQP++;
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

    public static void main(String[] args) {
        //测试
        int n = 3, k = 2, row = 0, column = 0;
        骑士在棋盘上的概率688 骑士在棋盘上的概率688 = new 骑士在棋盘上的概率688();
        System.out.println(骑士在棋盘上的概率688.knightProbability(n, k, row, column));
    }
}
