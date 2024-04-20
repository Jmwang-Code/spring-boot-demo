package com.cn.jmw.华为C卷.一百分;

import java.util.Scanner;

/**
 *
 * 这个分披萨就有成环的可能性，这个时候就要考虑到，
 * 当达到index==-1 ,就到最右端，
 * 而index==length，就到最左端。
 */
public class 分披萨_X_动态规划 {

    public static int getResult(int[] arr,int block) {
        //随机寻找数值返回最大的 吃货

        int max = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) {
            max = Math.max(max,dfs(arr,check(i-1,arr),check(i+1,arr))+ arr[i]);
        }

        return max;
    }


    public static int dfs(int[] arr,int l,int r) {
        //判断是向左还是向右，谁大向谁，这个也就是相当于馋嘴走到的，这个数值就被跳过了
        if (arr[l]>arr[r]){
            l = check(l-1,arr);
        }else {
            r = check(r+1,arr);
        }

        //l和r的索引相等的时候，就说明没有下一个值了
        if (l == r) {
            return arr[l];
        }else {
            //向左和向右，此时是向左大还是向右大
            return Math.max(dfs(arr,check(l-1,arr),r)+arr[l],dfs(arr,l,check(r+1,arr))+arr[r]);
        }
    }

    //这种环形的处理，使用数组，可以通过双指针的方式来表示不同出牌方。通过边界处理到环连接起来
    public static int check(int idx,int[] arr) {
        if (idx < 0) {
            idx = arr.length - 1;
        } else if (idx >= arr.length) {
            idx = 0;
        }

        return idx;
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int block = scanner.nextInt();
        int[] arr = new int[block];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = scanner.nextInt();
        }
        System.out.println(getResult(arr,block));
    }

}
