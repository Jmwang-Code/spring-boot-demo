package com.cn.jmw.递归算法.动态规划;

import java.util.Arrays;

public class 最长的斐波那契子序列的长度873 {

    /**
     * 分析此题：
     *
     * 给了一个数组，要求找出最长的斐波那契子序列的长度
     * 此时要达成 arr[i] + arr[j] = arr[nextIndex]  这个条件
     * 正常情况下需要 int i,int j,int nextIndex 三个指针
     * 但是最少需要两个指针，所以我们可以固定一个指针，然后用另一个指针去找
     * 达成arr[i] + arr[j]然后在arr中寻找他们相加的值，从而确定index，然后传递index即可
     */
    public int lenLongestFibSubseq(int[] arr) {
        int maxLength = 0;
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                maxLength = Math.max(maxLength, 2 + dfs(arr, i, j));
            }
        }
        return maxLength > 2 ? maxLength : 0;
    }

    private int dfs(int[] arr, int i, int j) {
        //arr[i] + arr[j] = arr[nextIndex]
        int target = arr[i] + arr[j];
        //二分查找 target的位置
        int nextIndex = Arrays.binarySearch(arr, target);
        if (nextIndex < 0) {
            return 0;
        }
        int maxLength = 1 + dfs(arr, j, nextIndex);
        return maxLength;
    }

    /**
     * 缓存法
     */
    public int lenLongestFibSubseq2(int[] arr) {
        int[][] dp = new int[arr.length][arr.length];
        for (int i = 0; i < arr.length; i++) {
            Arrays.fill(dp[i], -1);
        }

        int maxLength = 0;
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                maxLength = Math.max(maxLength, 2 + dfs(arr, i, j,dp));
            }
        }
        return maxLength > 2 ? maxLength : 0;
    }

    private int dfs(int[] arr, int i, int j,int[][] dp) {
        if (dp[i][j]!=-1){
            return dp[i][j];
        }

        //arr[i] + arr[j] = arr[nextIndex]
        int target = arr[i] + arr[j];
        //二分查找 target的位置
        int nextIndex = Arrays.binarySearch(arr, target);
        if (nextIndex < 0) {
            return 0;
        }
        int maxLength = 1 + dfs(arr, j, nextIndex,dp);
        dp[i][j] = maxLength;
        return maxLength;
    }

    /**
     * 动态规划 二维
     */
    public int lenLongestFibSubseq3(int[] arr) {
        int[][] dp = new int[arr.length][arr.length];

        int maxLength = 0;
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                int target = arr[i] + arr[j];
                //二分查找 target的位置
                int nextIndex = Arrays.binarySearch(arr, target);
                if (nextIndex < 0) {
                    continue;
                }
                dp[j][nextIndex] = Math.max(dp[j][nextIndex], 1 + dp[i][j]);
                maxLength = Math.max(maxLength, 2 + dp[j][nextIndex]);
            }
        }
        return maxLength > 2 ? maxLength : 0;
    }


    public static void main(String[] args) {
        //测试
        最长的斐波那契子序列的长度873 最长的斐波那契子序列的长度873 = new 最长的斐波那契子序列的长度873();
//        System.out.println(最长的斐波那契子序列的长度873.lenLongestFibSubseq(new int[]{1, 2, 3, 4, 5, 6, 7, 8}));
//
//        System.out.println(最长的斐波那契子序列的长度873.lenLongestFibSubseq(new int[]{1, 3, 7, 11, 12, 14, 18}));
//
//        System.out.println(最长的斐波那契子序列的长度873.lenLongestFibSubseq(new int[]{2, 4, 7, 8, 9, 10, 14, 15, 18, 23, 32, 50}));

        System.out.println(最长的斐波那契子序列的长度873.lenLongestFibSubseq2(new int[]{1, 2, 3, 4, 5, 6, 7, 8}));

        System.out.println(最长的斐波那契子序列的长度873.lenLongestFibSubseq2(new int[]{1, 3, 7, 11, 12, 14, 18}));

        System.out.println(最长的斐波那契子序列的长度873.lenLongestFibSubseq2(new int[]{2, 4, 7, 8, 9, 10, 14, 15, 18, 23, 32, 50}));

        System.out.println(最长的斐波那契子序列的长度873.lenLongestFibSubseq3(new int[]{1, 2, 3, 4, 5, 6, 7, 8}));

        System.out.println(最长的斐波那契子序列的长度873.lenLongestFibSubseq3(new int[]{1, 3, 7, 11, 12, 14, 18}));

        System.out.println(最长的斐波那契子序列的长度873.lenLongestFibSubseq3(new int[]{2, 4, 7, 8, 9, 10, 14, 15, 18, 23, 32, 50}));

    }
}
