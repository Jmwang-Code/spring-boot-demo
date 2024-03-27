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

    //递归常式
    public int minFlipsMonoIncr3(String s) {
        return minFlips(s.toCharArray(), 0, 0);
    }

    private int minFlips(char[] s, int index, int flips) {
        if (index == s.length) {
            return flips;
        }

        // 第一种情况：将当前位置的字符变为 '0'，并继续递归处理下一个位置
        int flipsWithZero = s[index] == '0' ? 0 : 1;
        int flips1 = minFlips(s, index + 1, flips + flipsWithZero);

        // 第二种情况：将当前位置的字符变为 '1'，并将后续所有的字符都变为 '0'，并统计变换次数
        int flipsWithOne = s[index] == '1' ? 0 : 1;
        for (int i = index + 1; i < s.length; i++) {
            if (s[i] == '1') {
                flipsWithOne++;
            }
        }
        int flips2 = minFlips(s, index + 1, flipsWithOne);

        // 返回两种情况下的较小值
        return Math.min(flips1, flips2);
    }


    @Test
    public void test() {
        System.out.println(minFlipsMonoIncr3("00110"));

        System.out.println(minFlipsMonoIncr3("010110"));

        System.out.println(minFlipsMonoIncr3("00011000"));
    }
}
