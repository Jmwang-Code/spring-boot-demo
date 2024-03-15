package com.cn.jmw.华为;

public class 合并两个有序数组88 {

    public void merge2(int[] nums1, int m, int[] nums2, int n) {
        int p1 = 0, p2 = 0;
        int[] sorted = new int[m + n];
        int cur;
        while (p1 < m || p2 < n) {
            if (p1 == m) {
                cur = nums2[p2++];
            } else if (p2 == n) {
                cur = nums1[p1++];
            } else if (nums1[p1] < nums2[p2]) {
                cur = nums1[p1++];
            } else {
                cur = nums2[p2++];
            }
            sorted[p1 + p2 - 1] = cur;
        }
        for (int i = 0; i != m + n; ++i) {
            nums1[i] = sorted[i];
        }
    }

    /**
     * 原地排序
     */
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int i1 = m-1,i2 = n-1;
        int tail = m + n - 1;
        int cur;
        while (i1 >= 0 || i2 >= 0){
            //处理越界是关键
            if (i1 == -1){
                cur = nums2[i2--];
            }else if (i2 == -1){
                cur = nums1[i1--];
            }else if (nums1[i1] > nums2[i2]){
                cur = nums1[i1--];
            }else {
                cur = nums2[i2--];
            }
            nums1[tail--] = cur;
        }
    }


    public static void main(String[] args) {
        //测试1
        int[] nums1 = {1, 2, 3, 0, 0, 0};
        int[] nums2 = {2, 5, 6};
        new 合并两个有序数组88().merge(nums1, 3, nums2, 3);
        for (int i : nums1) {
            System.out.print(i + " ");
        }

        //测试2
        int[] nums3 = {1};
        int[] nums4 = {};
        new 合并两个有序数组88().merge(nums3, 1, nums4, 0);
        for (int i : nums3) {
            System.out.print(i + " ");
        }

        //测试3
        int[] nums5 = {2, 0};
        int[] nums6 = {1};
        new 合并两个有序数组88().merge(nums5, 1, nums6, 1);
        for (int i : nums5) {
            System.out.print(i + " ");
        }

        int[] nums7 = {0};
        int[] nums8 = {1};
        new 合并两个有序数组88().merge(nums7, 0, nums8, 1);
        for (int i : nums5) {
            System.out.print(i + " ");
        }
    }
}
