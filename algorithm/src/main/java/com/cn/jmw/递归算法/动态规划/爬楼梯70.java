package com.cn.jmw.递归算法.动态规划;

import java.util.Arrays;

public class 爬楼梯70 {

    public int climbStairs(int n) {
        return process(n-1);
    }

    public int process(int n){
        if (n<=0){
            return 1;
        }

        return process(n-1)+process(n-2);
    }

    public int climbStairs2(int n) {
        if (n <= 2) {
            return n;
        }

        int[] dp = new int[n];
        return process2(n-1,dp);
    }

    public int process2(int n,int[] dp){
        if (n<=0){
            return 1;
        }

        if (dp[n]!=0)return dp[n];

        int ans = process2(n - 1,dp) + process2(n - 2,dp);
        dp[n] = ans;
        return ans;
    }

    public int climbStairs3(int n) {

        int pre = 1;
        int cur = 1;
        for (int i = 2; i <= n; i++) {
            int next = cur + pre;
            pre = cur;
            cur = next;
        }
        return cur;
    }


    public static void main(String[] args) {
        爬楼梯70 爬楼梯70 = new 爬楼梯70();
        //测试
        System.out.println(爬楼梯70.climbStairs(3));

        System.out.println(爬楼梯70.climbStairs(4));

        System.out.println(爬楼梯70.climbStairs3(44));


    }
}
