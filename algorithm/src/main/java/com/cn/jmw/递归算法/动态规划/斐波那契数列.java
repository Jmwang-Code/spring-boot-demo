package com.cn.jmw.递归算法.动态规划;

import java.util.ArrayList;
import java.util.List;

public class 斐波那契数列 {

    // leetcode 093.最长的斐波那契子序列的长度
    public int lenLongestFibSubseq(int[] arr) {
        int n = arr.length;
        int[][] dp = new int[n][n];
        int max = 0;
        for(int i = 2; i < n; i++){
            int l = 0, r = i - 1;
            while(l < r){
                int sum = arr[l] + arr[r];
                if(sum < arr[i]){
                    l++;
                }else if(sum > arr[i]){
                    r--;
                }else{
                    dp[r][i] = dp[l][r] + 1;
                    max = Math.max(max, dp[r][i]);
                    l++;
                    r--;
                }
            }
        }
        return max == 0 ? 0 : max + 2;
    }

    // 1414. 和为 K 的最少斐波那契数字数目
    public int findMinFibonacciNumbers(int k) {
        List<Integer> f = new ArrayList<Integer>();
        f.add(1);
        int a = 1, b = 1;
        while (a + b <= k) {
            int c = a + b;
            f.add(c);
            a = b;
            b = c;
        }
        int ans = 0,sum = k;
        for (int i = f.size()-1; i >= 0 && sum > 0; i--) {
            Integer integer = f.get(i);
            if (integer<=sum){
                ans++;
                sum-=integer;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        //测试
        斐波那契数列 斐波那契数列 = new 斐波那契数列();
        int[] arr = {1,2,3,4,5,6,7,8};
        int i = 斐波那契数列.lenLongestFibSubseq(arr);
        System.out.println(i);

        int minFibonacciNumbers = 斐波那契数列.findMinFibonacciNumbers(7);
    }
}
