package com.cn.jmw.递归算法.动态规划.背包问题;

/**
 * 有一堆石头，用整数数组 stones 表示。其中 stones[i] 表示第 i 块石头的重量。
 * <p>
 * 每一回合，从中选出任意两块石头，然后将它们一起粉碎。假设石头的重量分别为 x 和 y，且 x <= y。那么粉碎的可能结果如下：
 * <p>
 * 如果 x == y，那么两块石头都会被完全粉碎；
 * 如果 x != y，那么重量为 x 的石头将会完全粉碎，而重量为 y 的石头新重量为 y-x。
 * 最后，最多只会剩下一块 石头。返回此石头 最小的可能重量 。如果没有石头剩下，就返回 0。
 */
public class 最后一块石头的重量1049 {

    //常式
    public int lastStoneWeightII(int[] stones) {
        //计算总重量
        int totalSum = 0;
        for (int stone : stones) {
            totalSum += stone;
        }

        return process(stones, totalSum, 0, totalSum);
    }

    //石头数组 stones
    //总重量 totalSum
    //当前数组位置 index 0-(stones.length-1)
    //当前重量 sum - totalSum 其实因为一直有减法所以最多到（totalSum/2）
    private int process(int[] stones, int totalSum, int index, int sum) {
        if (index == stones.length) {
            return Math.abs(sum - (totalSum - sum));
        }

        //01背包 0
        int res = process(stones, totalSum, index + 1, sum);
        //01背包 1
        int res1 = process(stones, totalSum, index + 1, sum - stones[index]);

        return Math.min(res1, res);
    }

    /**
     * DP缓存版本
     */
    public int lastStoneWeightII2(int[] stones) {
        //计算总重量
        int totalSum = 0;
        for (int stone : stones) {
            totalSum += stone;
        }

        //dp方程
        int[][] dp = new int[stones.length+1][totalSum + 1];

        //赋值
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[i].length; j++) {
                dp[i][j] = -1;
            }
        }

        return process2(stones, totalSum, 0, totalSum , dp);
    }

    //index stones索引位置  0 - stones.length + 1
    //sun 相当于0-1背包中的 目标值，也就是 totalSum/2
    private int process2(int[] stones, int totalSum, int index, int sum, int[][] dp) {
        if (dp[index][totalSum]!=-1){
            return dp[index][totalSum];
        }

        if (index == stones.length) {
            return Math.abs(sum - (totalSum - sum));
        }

        //01背包 0
        int res = process2(stones, totalSum, index + 1, sum,dp);
        //01背包 1
        int res1 = process2(stones, totalSum, index + 1, sum - stones[index],dp);

        return Math.min(res1, res);
    }

    /**
     * 动态规划 O(n*sum)
     */
    public int lastStoneWeightII3(int[] stones) {
        //计算总重量
        int totalSum = 0;
        for (int stone : stones) {
            totalSum += stone;
        }

        //dp方程
        int[][] dp = new int[stones.length+1][totalSum + 1];

        //可有可无,因为默认值就是0。目的是给dp[i][0]赋值
        for (int i = 0; i < dp.length; i++) {
            dp[i][0] = 0;
        }

        //给dp[stones.length][i]赋值，其实本行都是一样的
        for (int i = 0; i < dp[0].length; i++) {
            dp[stones.length][i] = Math.abs(i - (totalSum - i));
        }

        for (int i = stones.length - 1; i >= 0; i--) {
            for (int j = 1; j < dp[i].length; j++) {
                if (j - stones[i] >= 0) {
                    dp[i][j] = Math.min(dp[i + 1][j], dp[i + 1][j - stones[i]]);
                } else {
                    dp[i][j] = dp[i + 1][j];
                }
            }
        }

        return dp[0][totalSum];
    }

    /**
     * 动态规划 O(sum)
     */
    public int lastStoneWeightII4(int[] stones) {
        //计算总重量
        int totalSum = 0;
        for (int stone : stones) {
            totalSum += stone;
        }

        //dp方程
        int[] dp = new int[totalSum + 1];

        //给dp[stones.length][i]赋值，其实本行都是一样的
        for (int i = 0; i < dp.length; i++) {
            dp[i] = Math.abs(i - (totalSum - i));
        }

        for (int i = stones.length - 1; i >= 0; i--) {
            int[] next = new int[totalSum + 1];
            for (int j = 1; j < dp.length; j++) {
                if (j - stones[i] >= 0) {
                    next[j] = Math.min(dp[j], dp[j - stones[i]]);
                } else {
                    next[j] = dp[j];
                }
            }
            dp = next;
        }

        return dp[totalSum];
    }


    public static void main(String[] args) {
        最后一块石头的重量1049 s = new 最后一块石头的重量1049();
        int[] stones = {2, 7, 4, 1, 8, 1};
//        System.out.println(s.lastStoneWeightII(stones));


        int[] stones2 = {89, 23, 100, 93, 82, 98, 91, 85, 33, 95, 72, 98, 63, 46, 17, 91, 92, 72, 77, 79, 99, 96, 55, 72, 24, 98, 79, 93, 88, 92};
//        long start3 = System.currentTimeMillis();
//        System.out.println(s.lastStoneWeightII(stones2));
//        long end3 = System.currentTimeMillis();
//        System.out.println("耗时：" + (end3 - start3)  + "ms");

//        long start4 = System.currentTimeMillis();
//        System.out.println(s.lastStoneWeightII2(stones));
//        System.out.println(s.lastStoneWeightII2(stones2));
//        long end4 = System.currentTimeMillis();
//        System.out.println("耗时：" + (end4 - start4)  + "ms");

        long start5 = System.currentTimeMillis();
        System.out.println(s.lastStoneWeightII3(stones));
        System.out.println(s.lastStoneWeightII3(stones2));
        long end5 = System.currentTimeMillis();
        System.out.println("耗时：" + (end5 - start5)  + "ms");

        long start6 = System.currentTimeMillis();
        System.out.println(s.lastStoneWeightII4(stones));
        System.out.println(s.lastStoneWeightII4(stones2));
        long end6 = System.currentTimeMillis();
        System.out.println("耗时：" + (end6 - start6)  + "ms");
    }
}
