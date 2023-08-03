package com.cn.jmw.数组原地替换;

import org.junit.Test;

public class RemoveDuplicatesFromOrderedArrays {

    //删除有序数组中的重复项：通解
    public static int removeDuplicates(int[] nums) {
        return process(nums, 2);
    }

    private static int process(int[] nums, int k) {
        //返回前u个数的数组长度
        int prefix = 0;

        for (int current: nums) {
            if (prefix<k || nums[prefix-k]!=current){
                nums[prefix++] = current;
            }
        }
        return prefix;
    }

    @Test
    public void test(){
        int[] nums = {1,1,1,1,1,2,2,3};
        int i = removeDuplicates(nums);
        System.out.println(i);
    }
}
