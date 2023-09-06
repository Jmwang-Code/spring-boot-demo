package com.cn.jmw.递归算法.动态规划.背包问题;

public class 背包问题1简单 {

    public static int maxValue1(int[] w, int[] v, int bag) {
        if (w.length == 0 || v.length == 0 || v.length != w.length) return 0;

        return process1(w, v,  0, bag);
    }

    public static int process1(int[] w, int[] v, int index, int bag) {
        if (bag < 0) return -1;
        if (index == w.length) return 0;


        //不要当前的货
        int value1 = process1(w, v, index+1,bag);

        //极端情况下 会出现process是无效解，但是还是继续归可能性。
        int value2 = 0;
        if (process1(w, v, index+1, bag-w[index])!=-1){
            //要当前的货
            value2 = v[index] + process1(w, v, index+1, bag-w[index]);
        }

        int max = Math.max(value1, value2);
        return max;
    }


    //测试
    public static void main(String[] args) {
        int[] w = {3, 2, 4, 7};
        int[] v = {5, 6, 3, 19};
        int bag = 11;
        System.out.println(maxValue1(w, v, bag));
    }
}
