package com.cn.jmw.递归算法.动态规划;

import org.junit.Test;

import java.util.Arrays;

public class LCR090打家劫舍II {

    public int rob(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        if (nums.length == 1) {
            return nums[0];
        }

        return Math.max(process(nums, 0, nums.length - 1), Math.max(process(nums, 1, nums.length), process(nums, 2, nums.length)));
    }

    //nums 数组
    //index 变量——当前的位置
    public int process(int[] nums, int index, int end) {
        //越界了
        if (index >= end) return 0;

        //对于每个当前来说都是走2步和走3步。
        //走2步
        int rob2 = nums[index] + process(nums, index + 2, end);
        //走3步
        int rob3 = nums[index] + process(nums, index + 3, end);
        return Math.max(rob2, rob3);
    }

    public int rob2(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        if (nums.length == 1) {
            return nums[0];
        }

        int[][] dp = new int[nums.length + 1][nums.length + 1];
        for (int i = 0; i < dp.length; i++) {
            Arrays.fill(dp[i], -1);
        }

        return Math.max(process2(nums, 0, nums.length - 1, dp),
                Math.max(process2(nums, 1, nums.length, dp), process2(nums, 2, nums.length, dp)));
    }

    //nums 数组
    //index 变量——当前的位置
    public int process2(int[] nums, int index, int end, int[][] dp) {
        //越界了
        if (index >= end) return 0;

        if (dp[index][end] != -1) return dp[index][end];

        //对于每个当前来说都是走2步和走3步。
        //走2步
        int rob2 = process2(nums, index + 2, end, dp);
        //走3步
        int rob3 = process2(nums, index + 3, end, dp);
        int ans = nums[index] + Math.max(rob2, rob3);
        dp[index][end] = ans;
        return ans;
    }

    /**
     * 动态规划 TODO 有问题
     */
    public int rob3(int[] nums) {
        if (nums.length == 1)
            return nums[0];
        if (nums.length == 2)
            return Math.max(nums[0], nums[1]);
        return Math.max(priority(Arrays.copyOfRange(nums, 0, nums.length - 1)),
                priority(Arrays.copyOfRange(nums, 1, nums.length)));
    }

    private int priority(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        dp[0] = nums[0];
        dp[1] = Math.max(nums[0], nums[1]);
        for (int i = 2; i < n; i++) {
            dp[i] = Math.max(dp[i - 2] + nums[i], dp[i - 1]);
        }
        return dp[n - 1];
    }


    @Test
    public void test() {
        System.out.println(rob3(new int[]{2, 3, 2}));
        System.out.println(rob3(new int[]{1, 2, 3, 1}));
        System.out.println(rob3(new int[]{0}));
        System.out.println(rob3(new int[]{226, 174, 214, 16, 218, 48, 153, 131, 128, 17, 157, 142, 88, 43, 37, 157, 43, 221, 191, 68, 206, 23, 225, 82, 54, 118, 111, 46, 80, 49, 245, 63, 25, 194, 72, 80, 143, 55, 209, 18, 55, 122, 65, 66, 177, 101, 63, 201, 172, 130, 103, 225, 142, 46, 86, 185, 62, 138, 212, 192, 125, 77, 223, 188, 99, 228, 90, 25, 193, 211, 84, 239, 119, 234, 85, 83, 123, 120, 131, 203, 219, 10, 82, 35, 120, 180, 249, 106, 37, 169, 225, 54, 103, 55, 166, 124}));
        System.out.println(rob3(new int[]{6, 6, 4, 8, 4, 3, 3, 10}));
        System.out.println(rob3(new int[]{2, 1, 1, 2}));
        System.out.println(rob3(new int[]{2, 2, 4, 3, 2, 5}));
    }
}
