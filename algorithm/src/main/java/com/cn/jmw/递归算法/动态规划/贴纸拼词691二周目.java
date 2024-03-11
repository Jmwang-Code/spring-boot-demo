package com.cn.jmw.递归算法.动态规划;

/**
 * 我们有 n 种不同的贴纸。每个贴纸上都有一个小写的英文单词。
 *
 * 您想要拼写出给定的字符串 target ，方法是从收集的贴纸中切割单个字母并重新排列它们。如果你愿意，你可以多次使用每个贴纸，每个贴纸的数量是无限的。
 *
 * 返回你需要拼出 target 的最小贴纸数量。如果任务不可能，则返回 -1 。
 *
 * 注意：在所有的测试用例中，所有的单词都是从 1000 个最常见的美国英语单词中随机选择的，并且 target 被选择为两个随机单词的连接。
 */
public class 贴纸拼词691二周目 {

    /**
     * 递归解法
     */
    public int minStickers(String[] stickers, String target) {
        if (stickers == null || stickers.length==0){
            return 0;
        }
        return process(stickers,target);
    }

    public int process(String[] stickers, String target){
        if (target.length() == 0) {
            return 0;
        }

        int min = Integer.MAX_VALUE;
        for (int i = 0; i < stickers.length; i++) {
            String rest = minus(target,stickers[i]);
            //如果长度不一样，说明有字符被消除了
            if (target.length()!=rest.length()){
                min = Math.min(min,process(stickers,rest));
            }
        }

        return min + (min == Integer.MAX_VALUE?0:1);
    }

    //获取当前贴纸剩下的String
    public static String minus(String target,String st){
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

    /**
     * 动态规划：可以吧贴纸看成多个物品，并且每个物品有无限个，然后target看成背包，每次从背包中取出一个字符，然后看看剩下的字符是否能够被贴纸拼出来
     */
    //stickers 0-stickers.length
    //target 0-target.length
    public int minStickers2(String[] stickers, String target) {
        if (stickers == null || stickers.length==0){
            return 0;
        }
        return process2(stickers,target);
    }

    public int process2(String[] stickers, String target){
        return 0;
    }

    public static void main(String[] args) {
        //测试
        String[] stickers = {"with", "example", "science"};
        String target = "thehat";
        贴纸拼词691二周目 贴纸拼词691二周目 = new 贴纸拼词691二周目();
        System.out.println(贴纸拼词691二周目.minStickers(stickers, target));
    }
}
