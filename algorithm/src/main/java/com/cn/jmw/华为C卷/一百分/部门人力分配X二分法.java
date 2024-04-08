package com.cn.jmw.华为C卷.一百分;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 二分法
 *
 * 最主要就是定位 这个解的范围，通过二分的方式查找。并且要检查中间mid是否可以存在。
 */
public class 部门人力分配X二分法 {
    //需要最少的人力应该大于 （requirements的最大值）小于（requirements第一大+第二大）
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int M = Integer.parseInt(sc.nextLine());
        int[] N =
                Arrays.stream(sc.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        System.out.println(getResult(M, N));
    }

    private static int getResult(int M, int[] N) {
        //由题意可知,每个月最小人力资源的范围在1-sum(N)
        //由题意1 ≤ N/2 ≤ M ≤ N ≤ 10000可知,M和N的关系是余数，并且是1-2倍的关系
        //当前每个月最小人力 需要大于 N[N-1] - (N(N-1)+N(N-2))
        int n = N.length;

        int max = N[n-1]+N[n-2];
        int min = N[n-1];

        //TODO 给一个最坏的可以通过的 每月人力资源
        int ans = max;

        while (min<=max){
            int mid = (max+min)>>1;
            //检查当前mid人月是否可以满足M月内完成N需求
            if (check(min,M,N)){
                ans = mid;
                max = mid - 1;
            }else {
                min = mid + 1;
            }
        }

        return ans;
    }

    /**
     * 3
     * 1 2 3 6 6
     * 10
     */
    //min 是目标值
    //M 是M个月
    //N n个需求每个需求需要的人月
    private static boolean check(int min, int M, int[] N) {
        int r = N.length-1;
        int l = 0;

        int useMonth = 0;

        while (l<=r){
            if (N[l] + N[r] <= min){
                l++;
            }
            r--;
            //消耗一个月
            useMonth++;
        }

        return M>=useMonth;
    }

    /**
     * 3
     * 1 2 3 6 6
     * 6
     */
    // 检查是否可以在给定人力资源下完成所有需求
    private static boolean check2(int min, int M, int[] N) {
        int n = N.length;
        int left = 0; // 指向需求量最小的需求
        int right = n - 1; // 指向需求量最大的需求

        int useMonth = 0;

        // 贪心策略：每次处理多个需求
        while (left <= right) {
            int totalWorkload = 0;
            int used = 0;

            // 尽可能多地将需求分配给当前人力资源限制下的需求
            while (right >= left && totalWorkload + N[right] <= min) {
                totalWorkload += N[right];
                right--;
                used++;
            }

            // 如果没有使用当前人力资源限制下的所有需求，继续尝试分配下一个需求
            while (left <= right && totalWorkload + N[left] <= min) {
                totalWorkload += N[left];
                left++;
                used++;
            }

            // 如果没有任何需求被分配，说明当前人力资源限制无法满足最小需求量，返回 false
            if (used == 0) {
                return false;
            }

            // 消耗一个月
            useMonth++;
        }

        // 如果 M 大于等于消耗的月份，则可以完成所有需求
        return M >= useMonth;
    }

}
