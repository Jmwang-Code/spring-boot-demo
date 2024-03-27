package com.cn.jmw.递归算法.动态规划.背包问题;

import org.junit.Test;

import java.util.Arrays;

//类似01背包问题
public class LCR092将字符串翻转到单调递增 {

    public int minFlipsMonoIncr(String s) {
        int n = s.length();
        int dp0 = 0, dp1 = 0;
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            int dp0New = dp0, dp1New = Math.min(dp0, dp1);
            if (c == '1') {
                dp0New++;
            } else {
                dp1New++;
            }
            dp0 = dp0New;
            dp1 = dp1New;
        }
        return Math.min(dp0, dp1);
    }

    //O(n)空间复杂度
    public int minFlipsMonoIncr2(String s) {
        int n = s.length();
        int[] dp0 = new int[n + 1];
        int[] dp1 = new int[n + 1];
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            dp0[i + 1] = dp0[i];
            dp1[i + 1] = Math.min(dp0[i], dp1[i]);
            if (c == '1') {
                dp0[i + 1]++;
            } else {
                dp1[i + 1]++;
            }
        }
        return Math.min(dp0[n], dp1[n]);
    }

    public int minFlipsMonoIncr3(String s) {
        int n = s.length();
        int[] dp0 = new int[n + 1]; // dp0[i]表示将前i个字符全部变成0所需的最小翻转次数
        int[] dp1 = new int[n + 1]; // dp1[i]表示将前i个字符全部变成1所需的最小翻转次数

        for (int i = 1; i <= n; i++) {
            dp0[i] = dp0[i - 1] + (s.charAt(i - 1) == '1' ? 1 : 0); // 如果当前字符是'1'，则需要进行翻转
            dp1[i] = Math.min(dp0[i - 1], dp1[i - 1]) + (s.charAt(i - 1) == '0' ? 1 : 0); // 如果当前字符是'0'，则需要进行翻转
        }

        return Math.min(dp0[n], dp1[n]);
    }


    @Test
    public void test() {
        System.out.println(minFlipsMonoIncr3("00110"));

        System.out.println(minFlipsMonoIncr3("010110"));

        System.out.println(minFlipsMonoIncr3("00011000"));
    }

}
