package com.cn.jmw.数组原地交换;

public class InvertWordsInAString {

    //反转字符串中的单词
    // 1. 先逆转整个字符串
    // 2. 再逆转每个单词
    // 3. 去除多余空格
    public String reverseWords(String s) {
        if(s == null || s.length() == 0) return s;
        char[] arr = s.toCharArray();
        reverse(arr, 0, arr.length - 1);
        reverseWord(arr, arr.length);
        return new String(arr, 0, arr.length).replaceAll("\\s*"," ");
    }
    public void reverse(char[] arr, int start, int end) {
        while(start < end) {
            char temp = arr[start];
            arr[start++] = arr[end];
            arr[end--] = temp;
        }
    }
    public void reverseWord(char[] arr, int len){
        int slow = 0, fast = 0;
        while(fast < len){
            while(slow < fast || slow < len && arr[slow] == ' ') slow++;
            while(fast < slow || fast < len && arr[fast] != ' ') fast++;
            reverse(arr, slow, fast -1);
        }
    }
    public String cleanSpace(char[] arr, int len){
        int end = 0;
        for(int i = 0; i < len; i++){
            if(arr[i] == ' ' && (i == 0 || arr[i-1] == ' ')){
                continue;
            }
            arr[end++] = arr[i];
        }
        if(end > 0 && arr[end-1] == ' '){
            return new String(arr, 0, end -1);
        }
        return new String(arr, 0, end);
    }

}
