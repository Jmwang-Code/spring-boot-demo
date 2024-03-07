package com.cn.jmw.递归算法.动态规划.背包问题;

import java.util.Arrays;

/**
 * 给你一个 只包含正整数 的 非空 数组 nums 。请你判断是否可以将这个数组分割成两个子集，使得两个子集的元素和相等。
 */
public class 分割等和子集416 {

    //常式
    public boolean canPartition(int[] nums) {
        //特例
        if (nums == null || nums.length == 0) return false;

        int sum = 0;
        for (int num : nums) {
            sum += num;
        }

        //奇数不可能
        if (sum % 2 != 0) return false;

        //转化为背包问题
        int target = sum / 2;
        return process1(nums, 0, target);
    }

    public boolean process1(int[] nums, int index, int rest) {
        if (rest < 0) return false;
        if (index == nums.length) return rest == 0;

        //不要当前货
        boolean ans1 = process1(nums, index + 1, rest);
        //要当前货
        boolean ans2 = process1(nums, index + 1, rest - nums[index]);

        return ans1 || ans2;
    }

    /**
     * 动态规划DP方程 O(N)
     */
    public boolean canPartition2(int[] nums) {
        //特例
        if (nums == null || nums.length == 0) return false;

        int sum = 0;
        for (int num : nums) {
            sum += num;
        }

        //奇数不可能
        if (sum % 2 != 0) return false;

        //转化为背包问题
        int target = sum / 2;

        //DP方程
        int[][] dp = new int[nums.length+1][target+1];

        for (int index = dp.length-2; index >= 0 ; index--) {
            for (int rest = 0; rest < dp[index].length; rest++) {
                int p1 = dp[index+1][rest];
                int p2 = 0;
                if (rest-nums[index]>=0){
                    p2 = nums[index] + dp[index+1][rest-nums[index]];
                }
                dp[index][rest] = Math.max(p1,p2);
            }
        }

        return dp[0][target]==target;
    }

    /**
     * 动态规划DP方程 O(1)即可
     */
    public boolean canPartition3(int[] nums) {
        //特例
        if (nums == null || nums.length == 0) return false;

        int sum = 0;
        for (int num : nums) {
            sum += num;
        }

        //奇数不可能
        if (sum % 2 != 0) return false;

        //转化为背包问题
        int target = sum / 2;

        //DP方程
        int[] dp = new int[target+1];

        for (int index = nums.length-1; index >= 0 ; index--) {
            int[] next = new int[target+1];
            for (int rest = 0; rest < dp.length; rest++) {
                int p1 = dp[rest];
                int p2 = 0;
                if (rest-nums[index]>=0){
                    p2 = nums[index] + dp[rest-nums[index]];
                }
                next[rest] = Math.max(p1,p2);
            }
            dp = next;
        }

        return dp[target]==target;
    }

    public static void main(String[] args) {
        int[] nums = {1, 5, 11, 5};
        分割等和子集416 test = new 分割等和子集416();
        System.out.println(test.canPartition(nums));

        System.out.println(test.canPartition2(nums));

        System.out.println(test.canPartition3(nums));
    }
}
