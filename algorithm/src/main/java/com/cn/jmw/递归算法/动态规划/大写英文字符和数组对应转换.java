package com.cn.jmw.递归算法.动态规划;

public class 大写英文字符和数组对应转换 {

    /**
     * 规定1和A对应、2和B对应、3和C对应...回
     * 那么一个数字字符串比如"111”就可以转化为:"AAA"、“KA"和"AK"
     * 给定一个只有数字字符组成的字符串str，返回有多少种转化结果
     */

    //帮我写方法但是不要写具体内容
    public static int process(String str) {
        if (str==null || str.length()==0)return 0;
        return dfs(str.toCharArray(),0);
    }

    private static int dfs(char[] chars,int index) {
        //如果没有剩余元素，返回一种可能
        if (index==chars.length){
            return 1;        }
        //如果数组为0说明上个判断出错了，就终止这种可能性的返回 直接返回0
        if (chars[index]=='0')return 0;


        //可能性1,每一位都可以作为一个英文字母
        int sum = dfs(chars,index+1);

        //可能性2,每一次都跳2个数字，必须保留足够的数字，并且2个数字相加不超过26个英文字母
        if (chars.length>index+1 && (chars[index]-'0')*10+(chars[index+1]-'0')<27){
            sum += dfs(chars,index+2);
        }
        return sum;
    }

    // 使用动态规划，首先只有一个变量，所以需要一位缓存即可。
    // 我们认为index 可以从 0-n-1 等于n已经越界
    public static int process2(String s) {
        if (s==null || s.length()==0)return 0;
        char[] str = s.toCharArray();
        int N = str.length;
        int[] dp = new int[N+1];
        dp[N] = 1;
        for (int i = N-1; i >= 0; i--) {
            if (str[i]!='0'){
                int way = dp[i+1];
                int way2 = (str.length>i+1) && ((str[i]-'0')*10+(str[i+1]-'0')<27)?dp[i+2]:0;
                dp[i] = way + way2;
            }
        }
        return dp[0];
    }


    //测试案例字符串长一点
    public static void main(String[] args) {
        String str = "11016152023";
        System.out.println(process(str));

        System.out.println(process2(str));
    }

}
