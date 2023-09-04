package com.cn.jmw.递归算法.动态规划;


/**
 * 486.预测赢家
 *
 * 给定一个整型数组arr，代表数值不同的纸牌排成一条线玩家A和玩家B依次拿走每张纸牌
 * 规定玩家A先拿，玩家B后拿
 * 但是每个玩家每次只能拿走最左或最右的纸牌玩家A和玩家B都绝顶聪明
 * 请返回最后获胜者的分数。
 */
public class 预测赢家 {

    /**
     * 常式 暴力递归
     */
    public static int win(int[] arr){
        if (arr==null || arr.length==0)return 0;
        int first = f(arr,0,arr.length-1);
        int last = l(arr,0,arr.length-1);
        return Math.max(first,last);
    }

    //先手
    public static int f(int[] arr,int left,int right){
        if (left==right)return arr[left];

        int p1 = arr[left] + l(arr,left+1,right);
        int p2 = arr[right] + l(arr,left,right-1);
        //先手选取一种 当前牌中的最优
        return Math.max(p1,p2);
    }

    //后手
    public static int l(int[] arr,int left,int right){
        if (left==right)return 0;

        //后手选 先手剩下的牌中的最优
        int p1 =  f(arr,left+1,right);
        int p2 =  f(arr,left,right-1);
        return Math.min(p1,p2);
    }

    //测试
    public static void main(String[] args) {
        int win = win(new int[]{50, 100, 20, 10});
        System.out.println(win);
    }
}
