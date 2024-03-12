package com.cn.jmw.递归算法.动态规划;

import java.util.HashMap;
import java.util.Map;

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
        int ans = process(stickers, target);
        return ans == Integer.MAX_VALUE ? -1 : ans;
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
     * 剪枝：小优化
     */
    public int minStickers2(String[] stickers, String target) {
        //进行字符统计
        int N = stickers.length;
        int[][] stickerss = new int[N][26];
        for (int i = 0; i < N; i++) {
            for (char c:stickers[i].toCharArray()){
                stickerss[i][c-'a']++;
            }
        }

        int ans = process2(stickerss, target);

        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    public int process2(int[][] stickers, String target){
        //如果当前目标长度为0说明结束了
        if (target.length()==0){
            return 0;
        }

        //当前字符长度
        int N = target.length();
        //获取target char数组
        char[] targetCharArray = target.toCharArray();
        int[] targetArray = new int[26];
        //把targetCharArray也进行字符统计
        for (char c:targetCharArray){
            targetArray[c-'a']++;
        }

        int ans = Integer.MAX_VALUE;

        //第一层 选择某一张卡片
        for (int i = 0; i < stickers.length; i++) {
            int[] sticker = stickers[i];
            //减枝操作 - 按照顺序 每次只选择当前目标字符串中首字母存在的 贴纸。
            if (sticker[targetCharArray[0]-'a']>0){

                //拼接字符串
                StringBuffer sb = new StringBuffer();
                //第二层进行减少target字符串
                for (int j = 0; j < 26; j++) {
                    //这里的判断依据主要是，当前目标数字的字符是否存在，存在则需要贴纸
                    if (targetArray[j]>0){
                        int num = targetArray[j] - sticker[j];
                        for (int k = 0; k < num; k++) {
                            sb.append((char) (j+'a'));
                        }
                    }
                }
                // 更新 target 字符串，去除已经拼接的字符
                String rest = sb.toString();
                ans = Math.min(ans,process2(stickers,rest));
            }
        }

        return ans +(ans == Integer.MAX_VALUE?0:1);
    }

    /**
     * 动态规划：可以吧贴纸看成多个物品，并且每个物品有无限个，然后target看成背包，每次从背包中取出一个字符，然后看看剩下的字符是否能够被贴纸拼出来
     */
    public int minStickers3(String[] stickers, String target) {
        //进行字符统计
        int N = stickers.length;
        int[][] stickerss = new int[N][26];
        for (int i = 0; i < N; i++) {
            for (char c:stickers[i].toCharArray()){
                stickerss[i][c-'a']++;
            }
        }

        Map<String,Integer> dp = new HashMap<>();
        int ans = process3(stickerss, target,dp);

        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    public int process3(int[][] stickers, String target, Map<String,Integer> dp){
        if (dp.containsKey(target)){
            return dp.get(target);
        }

        //如果当前目标长度为0说明结束了
        if (target.length()==0){
            return 0;
        }

        //当前字符长度
        int N = target.length();
        //获取target char数组
        char[] targetCharArray = target.toCharArray();
        int[] targetArray = new int[26];
        //把targetCharArray也进行字符统计
        for (char c:targetCharArray){
            targetArray[c-'a']++;
        }

        int ans = Integer.MAX_VALUE;

        //第一层 选择某一张卡片
        for (int i = 0; i < stickers.length; i++) {
            int[] sticker = stickers[i];
            //减枝操作 - 按照顺序 每次只选择当前目标字符串中首字母存在的 贴纸。
            if (sticker[targetCharArray[0]-'a']>0){

                //拼接字符串
                StringBuffer sb = new StringBuffer();
                //第二层进行减少target字符串
                for (int j = 0; j < 26; j++) {
                    //这里的判断依据主要是，当前目标数字的字符是否存在，存在则需要贴纸
                    if (targetArray[j]>0){
                        int num = targetArray[j] - sticker[j];
                        for (int k = 0; k < num; k++) {
                            sb.append((char) (j+'a'));
                        }
                    }
                }
                // 更新 target 字符串，去除已经拼接的字符
                String rest = sb.toString();
                ans = Math.min(ans,process3(stickers,rest,dp));
                dp.put(rest,ans);
            }
        }

        return ans +(ans == Integer.MAX_VALUE?0:1);
    }

    public static void main(String[] args) {
        //测试
        String[] stickers = {"with", "example", "science"};
        String target = "thehat";
        贴纸拼词691二周目 贴纸拼词691二周目 = new 贴纸拼词691二周目();
        System.out.println(贴纸拼词691二周目.minStickers(stickers, target));

        //再来一个测试
        String[] stickers2 = {"notice", "possible"};
        String target2 = "thehat";
        System.out.println(贴纸拼词691二周目.minStickers(stickers2, target2));

        System.out.println(贴纸拼词691二周目.minStickers2(stickers, target));
        System.out.println(贴纸拼词691二周目.minStickers2(stickers2, target2));

        //
        System.out.println(贴纸拼词691二周目.minStickers3(stickers, target));
        System.out.println(贴纸拼词691二周目.minStickers3(stickers2, target2));
    }
}
