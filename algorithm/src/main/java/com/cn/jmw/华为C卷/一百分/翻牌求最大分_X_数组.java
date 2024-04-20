package com.cn.jmw.华为C卷.一百分;

import java.util.Arrays;
import java.util.Scanner;

/**
 *╭—————————————————————————————╮
 *│ 当前时间：2024-04-20 17:50:08 │
 *│ 完成时间：00:05:20            │
 *╰—————————————————————————————╯
 *
 */
public class 翻牌求最大分_X_数组 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int[] arr = Arrays.stream(scanner.nextLine().split(",")).mapToInt(Integer::parseInt).toArray();

        System.out.println(getResult(arr));
    }

    public static int getResult(int[] arr){
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            int result = sum + arr[i];
            if (result>0){
                sum = result;
            }else {
                sum = 0;
            }
        }
        return sum;
    }
}
