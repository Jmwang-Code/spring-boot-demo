package com.cn.jmw.杂题;

public class 检查是否所有字符出现次数相同1941 {

    public boolean areOccurrencesEqual(String s) {
        //干扰因素
        if (s == null || s.length() == 0) {
            return true;
        }

        //26字母
        int[] arr = new int[26];

        for (int i = 0; i < s.length(); i++) {
            arr[s.charAt(i) - 'a']++;
        }

        int count = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 0) {
                continue;
            }

            if (count == 0) {
                count = arr[i];
                continue;
            }

            if (count != arr[i]) {
                return false;
            }
        }
        return true;
    }

    public boolean areOccurrencesEqual2(String s) {
        char[] chars = s.toCharArray();
        int[] array = new int[26];

        for (char aChar : chars) {
            array[aChar - 'a'] += 1;
        }

        int z = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == 0) {
                continue;
            }

            if (z == 0) {
                z = array[i];
                continue;
            }
            if (array[i] != z) {
                return false;
            }
        }

        return true;
    }

    //测试
    public static void main(String[] args) {
        String s = "abacbc";
        检查是否所有字符出现次数相同1941 test = new 检查是否所有字符出现次数相同1941();
        System.out.println(test.areOccurrencesEqual(s));

        //2
        s = "aaabb";
        System.out.println(test.areOccurrencesEqual(s));

        //来个复杂的测试案例
        s = "aabbccdd";
        System.out.println(test.areOccurrencesEqual(s));
    }
}
