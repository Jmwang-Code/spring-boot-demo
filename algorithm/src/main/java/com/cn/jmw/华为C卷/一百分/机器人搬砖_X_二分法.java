package com.cn.jmw.华为C卷.一百分;

import java.util.Arrays;
import java.util.Scanner;

public class 机器人搬砖_X_二分法 {

    public static int getResult(int[] split) {
        int n = split.length;

        if (n>8){
            return -1;
        }

        int yu = 8%n;
        //最小值
        int min = split[n-yu-1];
        int max = split[n-1];

        if (n==8){
            return max;
        }

        int ans = max;
        // 二分法
        while (min <= max) {
            // 取中间值
            int mid = (min + max) >> 1;

            //check是指当前的速度是否能8小时之内搬完
            if (check(mid, 8, split)) {
                // 如果每小时充mid格能量，就能在8小时内，搬完所有砖头，则mid就是一个可能解
                ans = mid;
                // 但mid不一定是最优解，因此继续尝试更小的能量块
                max = mid - 1;
            } else {
                // 如果每小时充mid能量块，无法在8小时能完成工作，则说明每天能量块充少了，下次应该尝试充更多能量块
                min = mid + 1;
            }
        }

        return ans;
    }

    public static boolean check(int energy, int limit, int[] bricks) {
        // 已花费的小时数
        int cost = 0;

        for (int brick : bricks) {
            //需要几个小时+如果有剩余就需要+1
            cost += brick / energy + (brick % energy > 0 ? 1 : 0);

            // 如果搬砖过程中发现，花费时间已经超过限制，则直接返回false
            if (cost > limit) {
                return false;
            }
        }

        return true;
    }


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[] split = Arrays.stream(sc.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        Arrays.stream(split).sorted();
        System.out.println(getResult(split));
    }

}
