package com.cn.jmw.递归算法.动态规划.背包问题;

public class 背包问题1简单 {

    public static int maxValue1(int[] w, int[] v, int restBag) {
        if (w.length == 0 || v.length == 0 || v.length != w.length) return 0;

        return process1(w, v,  0, restBag);
    }

    public static int process1(int[] w, int[] v, int index, int restBag) {
        if (restBag < 0) return -1;
        if (index == w.length) return 0;

        //不要当前的货
        int value1 = process1(w, v, index+1,restBag);

        //极端情况下 会出现process是无效解，但是还是继续归可能性。
        int value2 = 0;
        if (process1(w, v, index+1, restBag-w[index])!=-1){
            //要当前的货
            value2 = v[index] + process1(w, v, index+1, restBag-w[index]);
        }

        int max = Math.max(value1, value2);
        return max;
    }

    public static int maxValue2(int[] w, int[] v, int bag) {
        int N = w.length;
        //index 0 - N
        // bag 0 - bag
        int[][] dp = new int[N+1][bag+1];
        //n行所有位置都是0

        for (int index = N-1; index >= 0; index--) {
            for (int bagRest = 0; bagRest <= bag; bagRest++) {
                int p1 = dp[index+1][bagRest];
                int p2 = -1;
                if (bagRest - w[index] >= 0){
                    p2 = v[index]+dp[index+1][bagRest-w[index]];
                }
                dp[index][bagRest] = Math.max(p1,p2);
            }
        }
        
        return dp[0][bag];
    }


    //测试
    public static void main(String[] args) {
        int[] w = {2, 1, 3, 4};
        int[] v = {5, 6, 3, 19};
        int bag = 8;
//        long l = System.currentTimeMillis();
//        for (int i = 0; i < 100000; i++) {
        System.out.println(maxValue1(w, v, bag));
//        }
//        System.out.println((System.currentTimeMillis()-l)/1000.0);
//
//        long l1 = System.currentTimeMillis();
//        for (int i = 0; i < 100000; i++) {
//            maxValue2(w, v, bag);
//        }
//        System.out.println((System.currentTimeMillis()-l1)/1000.0);

    }
}
