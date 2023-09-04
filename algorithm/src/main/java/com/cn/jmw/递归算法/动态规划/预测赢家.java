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
        return process1(arr,0,arr.length-1,0);
    }

    //数组
    public static int process1(int[] arr,int left,int right,int sum){
        if (left>=right){
            return sum;
        }

        return Math.max(process1(arr,left+1,right-1,sum+arr[left]),process1(arr,left+1,right-1,sum+arr[right]));
    }

    //测试
    public static void main(String[] args) {
        int win = win(new int[]{50, 100, 20, 10});
        System.out.println(win);
    }
}
