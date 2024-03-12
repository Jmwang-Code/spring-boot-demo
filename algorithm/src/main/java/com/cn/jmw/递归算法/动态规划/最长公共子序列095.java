package com.cn.jmw.递归算法.动态规划;

public class 最长公共子序列095 {

    //最长公共子序列 0-Math.min(text1,text2)
    public int longestCommonSubsequence(String text1, String text2) {
        //边界考虑
        if (text1 == null || text2 == null || text1.length() == 0 || text2.length() == 0) {
            return 0;
        }

        char[] str1 = text1.toCharArray();
        char[] str2 = text2.toCharArray();

        return process(str1, str2, str1.length - 1, str2.length - 1);
    }

    public int process(char[] str1, char[] str2, int i, int j) {
        /**
         * 四中可能性
         * 1.字符串1和字符串2都到了最后一个字符
         * 2.字符串1到了最后一个字符，字符串2没有到最后一个字符
         * 3.字符串2到了最后一个字符，字符串1没有到最后一个字符
         * 4.字符串1和字符串2都没有到最后一个字符
         */
        if (i == 0 && j == 0) {
            return str1[i] == str2[j] ? 1 : 0;
        } else if (i == 0) {
            if (str1[i] == str2[j]) {
                return 1;
            } else {
                return process(str1, str2, i, j - 1);
            }
        } else if (j == 0) {
            if (str1[i] == str2[j]) {
                return 1;
            } else {
                return process(str1, str2, i - 1, j);
            }
        } else {
            /**
             * 1.i可能是公共字符，j不可能
             * 2.j可能是公共字符，i不可能
             * 3.i和j都可能
             */
            int ans1 = process(str1, str2, i, j - 1);
            int ans2 = process(str1, str2, i - 1, j);
            int ans3 = str1[i] == str2[j] ? (1 + process(str1, str2, i - 1, j - 1)) : 0;
            return Math.max(ans1, Math.max(ans2, ans3));
        }
    }

    public static void main(String[] args) {
        //测试
        最长公共子序列095 test = new 最长公共子序列095();
        String text1 = "abcde";
        String text2 = "ace";
        System.out.println(test.longestCommonSubsequence(text1, text2));
    }
}
