package com.cn.jmw.递归算法.动态规划.背包问题;

public class 背包问题1简单 {

    public static int maxValue1(int[] w, int[] v, int bag) {
        if (w.length==0 || v.length==0 || v.length!=w.length)return 0;

        return process1(w, v, 0, bag);
    }

    public static int process1(int[] w, int[] v, int cur, int bag) {

    }


    //测试
    public static void main(String[] args) {
        int[] w = { 3, 2, 4, 7 };
        int[] v = { 5, 6, 3, 19 };
        int bag = 11;
        System.out.println(maxValue1(w, v, bag));
    }
}
