package com.cn.jmw.递归算法.动态规划;

import org.junit.Test;

import java.util.Arrays;

public class LCR089打家劫舍or跳房子 {

    public int rob(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        if (nums.length == 1) {
            return nums[0];
        }

        return Math.max(process(nums, 0), process(nums, 1));
    }

    //nums 数组
    //index 变量——当前的位置
    public int process(int[] nums, int index) {
        //越界了
        if (index >= nums.length) return 0;

        //对于每个当前来说都是走2步和走3步。
        //走2步
        int rob2 = nums[index] + process(nums, index + 2);
        //走3步
        int rob3 = nums[index] + process(nums, index + 3);
        return Math.max(rob2, rob3);
    }

    public int rob2(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        if (nums.length == 1) {
            return nums[0];
        }

        //dp数组
        int[] dp = new int[nums.length + 1];
        Arrays.fill(dp, -1);

        return Math.max(process2(nums, 0, dp), process2(nums, 1, dp));
    }

    public int process2(int[] nums, int index, int[] dp) {
        //越界了
        if (index >= nums.length) return 0;

        if (dp[index]!=-1) return dp[index];

        //对于每个当前来说都是走2步和走3步。
        //走2步
        int ans = 0;
        int rob2 = nums[index] + process2(nums, index + 2, dp);
        //走3步
        ans = Math.max(rob2, nums[index] + process2(nums, index + 3, dp));
        dp[index] = ans;
        return ans;
    }

    /**
     * 动态规划
     */
    public int rob3(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        if (nums.length == 1) {
            return nums[0];
        }

        int[] dp = new int[nums.length + 1];
        dp[nums.length] = 0;
        dp[nums.length - 1] = nums[nums.length - 1];
        dp[nums.length - 2] = nums[nums.length - 2];
        for (int i = nums.length-3; i >= 0; i--) {
            dp[i] = nums[i] + Math.max(dp[i+2],dp[i+3]);
        }

        return Math.max(dp[0],dp[1]);
    }

    /**
     * 动态规划 空间复杂度O(1)
     */
    public int rob4(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        if (nums.length == 1) {
            return nums[0];
        }

        int s3 = 0;
        int s2 = nums[nums.length - 1];
        int s1 = nums[nums.length - 2];
        int s0 = 0;
        for (int i = nums.length-3; i >= 0; i--) {
            s0 = nums[i] + Math.max(s2,s3);
            s3 = s2;
            s2 = s1;
            s1 = s0;
        }

        return Math.max(s1,Math.max(s2,s0));
    }

    //帮我构建对应的Test 并且要多个测试案例
    @Test
    public void test() {
        //rob 不是静态的
        LCR089打家劫舍or跳房子 lcr089打家劫舍or跳房子 = new LCR089打家劫舍or跳房子();
        int[] nums = {2, 7, 9, 3, 1};
        System.out.println(lcr089打家劫舍or跳房子.rob4(nums));
        //继续案例
        int[] nums1 = {1, 2, 3, 1};
        System.out.println(lcr089打家劫舍or跳房子.rob4(nums1));
        //继续案例
        int[] nums2 = {1, 3, 1};
        System.out.println(lcr089打家劫舍or跳房子.rob4(nums2));

    }

}
