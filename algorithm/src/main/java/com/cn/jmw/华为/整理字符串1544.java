package com.cn.jmw.华为;

public class 整理字符串1544 {

    public String process(String str){
        StringBuffer sb = new StringBuffer();

        int n = str.length();
        int sbIndex = -1;
        for (int i = 0; i < n; i++) {
            if (sb.length()>0 &&
                str.charAt(i)!=sb.charAt(sbIndex) &&
                Character.toUpperCase(str.charAt(i))==Character.toUpperCase(sb.charAt(sbIndex))){
                sb.deleteCharAt(sbIndex);
                sbIndex--;
            }else {
                sb.append(str.charAt(i));
                sbIndex++;
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        //测试
        String str = "leEeetcode";
        整理字符串1544 hj3 = new 整理字符串1544();
        String res = hj3.process(str);
        System.out.println(res);
    }
}
