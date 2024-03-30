package com.cn.jmw.递归算法.动态规划;

import org.junit.Test;

/**
 * 根据题意：
 * 我们需要获取到最少偷窃K次，的最小赃款额度、
 * <p>
 * 但是他的行走规律还是 走2步 或者 走3步
 */
public class LCR090打家劫舍IV {

    public int minCapability(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int n = nums.length;
        int low = Integer.MAX_VALUE;
        int high = Integer.MIN_VALUE;
        for (int x : nums) {
            low = Math.min(low, x);
            high = Math.max(high, x);
        }
        while (low < high) {
            int mid = low + (high - low) / 2;
            int cnt = 0;
            for (int i = 0; i < n; i++) {
                if (nums[i] <= mid) {
                    cnt++;
                    i++;
                    if (cnt >= k) {
                        break;
                    }
                }
            }
            if (cnt >= k) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        return low;
    }


    @Test
    public void test() {
        int[] nums = {2, 3, 5, 9};
        int k = 2;
        System.out.println(minCapability(nums, k));
    }

}
