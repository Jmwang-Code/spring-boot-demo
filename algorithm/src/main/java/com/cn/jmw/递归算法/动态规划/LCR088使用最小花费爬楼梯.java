package com.cn.jmw.递归算法.动态规划;

import java.util.Arrays;

public class LCR088使用最小花费爬楼梯 {

    //我设定 从0和1开始寻找两个最小值，他们的位置也就是对应数组位置
    public int minCostClimbingStairs(int[] cost) {
        //从0或者1开始
        return Math.min(process(cost, 0), process(cost, 1));
    }

    public int process(int[] cost, int cur) {
        //当大于数组或者等于长度也就是走完了
        if (cur >= cost.length) {
            return 0;
        }

        //走1步
        int ans1 =  process(cost, cur + 1);
        //走2步
        int ans2 =  process(cost, cur + 2);

        return cost[cur] + Math.min(ans1, ans2);
    }

    /**
     * 缓存
     */
    public int minCostClimbingStairs2(int[] cost) {
        int[] dp = new int[cost.length];
        Arrays.fill(dp, -1);

        //从0或者1开始
        return Math.min(process2(cost, 0,dp), process2(cost, 1,dp));
    }

    public int process2(int[] cost, int cur,int[] dp) {
        //当大于数组或者等于长度也就是走完了
        if (cur >= cost.length) {
            return 0;
        }

        if (dp[cur]!=-1)return dp[cur];

        //走1步
        int ans1 =  process2(cost, cur + 1,dp);
        //走2步
        int ans2 =  process2(cost, cur + 2,dp);

        dp[cur] = cost[cur] + Math.min(ans1, ans2);
        return dp[cur];
    }

    /**
     * 动态规划
     */
    public int minCostClimbingStairs3(int[] cost) {
        int N = cost.length;
        int[] dp = new int[cost.length];

        dp[N-1] = cost[N-1];
        dp[N-2] = cost[N-2];
        for (int i = N-3; i >= 0; i--) {
            dp[i] = cost[i] + Math.min(dp[i+1], dp[i+2]);
        }

        //从0或者1开始
        return Math.min(dp[0],dp[1]);
    }


    public static void main(String[] args) {
        //测试
        LCR088使用最小花费爬楼梯 lcr088使用最小花费爬楼梯 = new LCR088使用最小花费爬楼梯();
        int[] cost = {10, 15, 20};
        System.out.println(lcr088使用最小花费爬楼梯.minCostClimbingStairs(cost));

        int[] cost2 = {1, 100, 1, 1, 1, 100, 1, 1, 100, 1};
        System.out.println(lcr088使用最小花费爬楼梯.minCostClimbingStairs(cost2));

        System.out.println(lcr088使用最小花费爬楼梯.minCostClimbingStairs2(cost));
        System.out.println(lcr088使用最小花费爬楼梯.minCostClimbingStairs2(cost2));

        System.out.println(lcr088使用最小花费爬楼梯.minCostClimbingStairs3(cost));
        System.out.println(lcr088使用最小花费爬楼梯.minCostClimbingStairs3(cost2));
    }
}
