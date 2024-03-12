package com.cn.jmw.递归算法.动态规划;

import java.util.Arrays;

public class 贴纸拼词 {

    public static int minStickers(String[] stickers, String target){
        int ans = process(stickers, target);
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    public static int process(String[] stickers, String target){
        if (target.length()==0)return 0;

        int min = Integer.MAX_VALUE;
        for (String sticker:stickers) {
            String rest = getStr(target,sticker);
            if (target.length()!=rest.length()){
                min = Math.min(min,process(stickers,rest));
            }
        }
        return min + (min == Integer.MAX_VALUE?0:1);
    }

    public static String getStr(String target,String st){
        char[] stArray = st.toCharArray();
        char[] targetArray = target.toCharArray();
        int[] charArray = new int[26];
        for (char c:stArray) charArray[c-'a']--;
        for (char c: targetArray) charArray[c-'a']++;
        StringBuilder stringBuilder = new StringBuilder();
        //确定单词位置
        for (int i = 0; i < 26; i++) {
            if (charArray[i]>0){
                //确定append次数
                for (int j = 0; j < charArray[i]; j++) {
                    stringBuilder.append((char) (i+'a'));
                }
            }
        }
        return stringBuilder.toString();
    }





    //来个测试案例
    public static void main(String[] args) {
        String[] stickers = {"with","example","science"};
        String target = "thehat";
        System.out.println(minStickers(stickers, target));

        String[] stickers2 = {"notice","possible"};
        String target2 = "basicbasic";
        System.out.println(minStickers(stickers2, target2));


    }


}
