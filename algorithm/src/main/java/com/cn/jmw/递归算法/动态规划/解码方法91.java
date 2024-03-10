package com.cn.jmw.递归算法.动态规划;

/**
 * 一条包含字母 A-Z 的消息通过以下映射进行了 编码 ：
 *
 * 'A' -> "1"
 * 'B' -> "2"
 * ...
 * 'Z' -> "26"
 * 要 解码 已编码的消息，所有数字必须基于上述映射的方法，反向映射回字母（可能有多种方法）。例如，"11106" 可以映射为：
 *
 * "AAJF" ，将消息分组为 (1 1 10 6)
 * "KJF" ，将消息分组为 (11 10 6)
 * 注意，消息不能分组为  (1 11 06) ，因为 "06" 不能映射为 "F" ，这是由于 "6" 和 "06" 在映射中并不等价。
 *
 * 给你一个只含数字的 非空 字符串 s ，请计算并返回 解码 方法的 总数 。
 *
 * 题目数据保证答案肯定是一个 32 位 的整数。
 */
public class 解码方法91 {
    public int numDecodings(String s) {
        return process(s,s.length(),0);
    }

    //常式
    //s 字符串
    //totalLen 总长度固定的
    //curIndex 当前索引位置
    public int process(String s,int totalLen,int curIndex){
        if (curIndex == totalLen)return 1;

        //易错点1 终结只使用一个0当做转换字符的可能性
        if (s.charAt(curIndex)=='0')return 0;

        int ans1 = process(s,totalLen,curIndex+1);
        int ans2 = 0;
        //易错点2 判断边界curIndex+1<totalLen
        if (curIndex+1<totalLen && ((s.charAt(curIndex)-'0')*10 + (s.charAt(curIndex+1)-'0'))<27){ //
            ans2 = process(s,totalLen,curIndex+2);
        }

        return ans1+ans2;
    }

    public int numDecodings2(String s) {
        int n = s.length();
        int[] ans = new int[n+1];
        ans[n] = 1;

        for (int i = n-1; i >=0 ; i--) {

            if (s.charAt(i)=='0') continue;

            int temp = ans[i+1];
            if (i+1<n && ((s.charAt(i)-'0')*10 + (s.charAt(i+1)-'0'))<27){
                temp+=ans[i+2];
            }

            ans[i] = temp;
        }

        return ans[0];
    }


    public static void main(String[] args) {
        解码方法91 test = new 解码方法91();
        System.out.println(test.numDecodings("226"));

        System.out.println(test.numDecodings("06"));

        System.out.println(test.numDecodings("207"));

        System.out.println("------------------------");

        System.out.println(test.numDecodings2("226"));

        System.out.println(test.numDecodings2("06"));

        System.out.println(test.numDecodings2("207"));
    }
}
