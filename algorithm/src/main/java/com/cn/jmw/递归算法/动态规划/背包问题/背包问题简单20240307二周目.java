package com.cn.jmw.递归算法.动态规划.背包问题;

/**
 * w[] 重量数组
 * v[] 价值数组
 * bag 背包称重多少
 *
 * 返回背包容量最大价值
 */
public class 背包问题简单20240307二周目 {

    //常式
    public static int maxValue1(int[] w, int[] v, int restBag) {
        if (w==null || v==null || w.length ==0 || v.length==0 || restBag<0)return 0;

        return process1(w,v,0,restBag);
    }

    public static int process1(int[] w, int[] v, int index, int restBag) {
        if (restBag<0) return -1;

        if (index==w.length)return 0;

        //不要当前货
        int ans1 = process1(w,v,index+1,restBag);
        int ans2 = process1(w,v,index+1,restBag-w[index]);
        //要当前货
        if (ans2!=-1){
            ans2 = v[index]+ans2;
        }
        return Math.max(ans1,ans2);
    }

    public static void main(String[] args) {
        //测试
        int[] w = {2, 1, 3, 4};
        int[] v = {5, 6, 3, 19};
        int bag = 8;
        System.out.println(maxValue1(w, v, bag));
    }
}
