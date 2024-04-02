package com.cn.jmw.杂题;

public class 检查两个字符串数组是否相等1662 {

    // O(n^m)
    public boolean arrayStringsAreEqual(String[] word1, String[] word2) {
        if (word1[word1.length-1].charAt(word1[word1.length-1].length()-1) !=
                word2[word2.length-1].charAt(word2[word2.length-1].length()-1)){
            return false;
        }

        int w1 = 0,w2 = 0;
        int i = 0,j = 0;
        while (w1<word1.length && w2<word2.length){

            char[] chars1 = word1[w1].toCharArray();
            char[] chars2 = word2[w2].toCharArray();
            while (i<chars1.length && j<chars2.length){
                if (chars1[i++]!=chars2[j++]){
                    return false;
                }
            }
            if (i==chars1.length){
                w1++;
                i=0;
            } else if (j==chars2.length) {
                w2++;
                j=0;
            }
        }

        return true;
    }

    //O(N)
    public boolean arrayStringsAreEqual2(String[] word1, String[] word2) {
        StringBuilder wordOne = new StringBuilder();
        StringBuilder wordTwo = new StringBuilder();
        for(String word: word1){
            wordOne.append(word);
        }
        for(String word: word2){
            wordTwo.append(word);
        }
        return wordOne.toString().equals(wordTwo.toString());
    }

    public static void main(String[] args) {
        //测试1
        String[] word1 = {"abc", "d", "defg"},  word2 = {"abcddef"};
        System.out.println(new 检查两个字符串数组是否相等1662().arrayStringsAreEqual(word1,word2));
        String[] word3 = {"a", "cb"},  word4 = {"ab", "c"};
        System.out.println(new 检查两个字符串数组是否相等1662().arrayStringsAreEqual(word3,word4));
    }
}
