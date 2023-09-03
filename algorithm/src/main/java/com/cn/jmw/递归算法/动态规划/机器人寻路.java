package com.cn.jmw.递归算法.动态规划;

//机器人寻路
//一条路上有n个地点,比如n = 7
//初始位置为start = 2
//目标位置aim = 4
//一定要走的步长k = 4

//返回有几种走法
public class 机器人寻路 {

    public static int way1(int n,int start,int aim,int k){
        return process1(start,k,aim,n);
    }

    /**
     * 常用递归
     */

    public static int process1(int cur,int rest,int aim,int n){
        if (rest==0){
            return cur==aim?1:0;
        }

        if (cur==1){
            process1(cur+1,rest-1,aim,n);
        }
        if (cur==n){
            process1(cur-1,rest-1,aim,n);
        }
        return process1(cur-1,rest-1,aim,n) + process1(cur+1,rest-1,aim,n);
    }

    /**
     * 从顶向下的动态规划
     * 本质就是使用缓存，减少重复运算
     */
    //优化重复的叶子节点，使用缓存法
    public static int way2(int n,int start,int aim,int k){
        int[][] dp = new int[n+1][k+1];
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[i].length; j++) {
                dp[i][j] = -1;
            }
        }
        return process2(start,k,aim,n,dp);
    }

    //cur 1-n
    //rest 0-k
    //cur rest 是可变的，需要一个二维表存储，存储下来所有对应出现的参数路径
    public static int process2(int cur,int rest,int aim,int n,int[][] dp) {
        if (dp[cur][rest]!=-1)return dp[cur][rest];

        //之前没算过的
        int ans = 0;
        if (rest==0){
            ans = cur==aim?1:0;
        }else if (cur==1){
            ans = process2(cur+1,rest-1,aim,n,dp);
        }else if (cur==n){
            ans = process2(cur-1,rest-1,aim,n,dp);
        }else {
            ans = process2(cur+1,rest-1,aim,n,dp) + process2(cur-1,rest-1,aim,n,dp);
        }

        dp[cur][rest] = ans;
        return ans;
    }


    //测试way1方法
    public static void main(String[] args) {
        int way1 = way1(7, 2, 4, 4);
        System.out.println(way1);

        int way2 = way2(7, 2, 4, 4);
        System.out.println(way2);
    }
}
