package com.cn.jmw.华为C卷.一百分;

import java.util.Arrays;
import java.util.Scanner;

public class 计算礼品发放的最小分组数目_X_数组 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int max = Integer.parseInt(sc.nextLine());
        Integer[] arr =
                Arrays.stream(sc.nextLine().split(" ")).map(Integer::parseInt).toArray(Integer[]::new);

        System.out.println(getResult(max, arr));
    }

    public static int getResult(int max, Integer[] arr) {
        // 将商品按价格从小到大排序
        Arrays.sort(arr);

        int count = 0;
        int l = 0; // l指针指向最小价格的商品
        int r = arr.length - 1; // r指针指向最大价格的商品

        // 如果商品价格不超过上限，则优先最小价格和最大价格组合
        while (l < r) {
            int sum = arr[l] + arr[r];

            // 如果最小价格+最大价格 不超过上限，则组合，否则最大价格独立一组
            if (sum <= max) l++;
            r--;
            count++;
        }

        if (l == r) count++;

        return count;
    }

}
