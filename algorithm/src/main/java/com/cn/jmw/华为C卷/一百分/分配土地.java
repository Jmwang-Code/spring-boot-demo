package com.cn.jmw.华为C卷.一百分;

import java.util.*;

/**
 *╭—————————————————————————————╮
 *│ 当前时间：2024-04-21 16:59:04 │
 *│ 完成时间：00:22:01            │
 *╰—————————————————————————————╯
 * 
 */
public class 分配土地 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int length = scanner.nextInt();
        int weight = scanner.nextInt();

        int[][] arr = new int[weight][length];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                arr[i][j] = scanner.nextInt();
            }
        }

        System.out.println(getResult(arr));
    }


    public static int getResult(int[][] arr) {
        int sum = Integer.MAX_VALUE;
        int top = -1, left = -1, right = -1, di = -1;
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                int key = arr[i][j];
                if (key == 0) continue;
                set.add(key);
            }
        }

        for (int key : set) {
            for (int i = 0; i < arr.length; i++) {
                for (int j = 0; j < arr[i].length; j++) {
                    int value = arr[i][j];
                    //说明是一个插旗的位置
                    if (value == key) {
                        if (top == -1) {
                            top = j;
                            di = j;
                            right = i;
                            left = i;
                        } else {
                            if (top > j) {
                                top = j;
                            }
                            if (di < j) {
                                di = j;
                            }
                            if (left > i) {
                                left = i;
                            }
                            if (right < i) {
                                right = i;
                            }
                        }
                    }
                }
            }
            sum = (di - top + 1) * (right - left + 1);
            top = -1;
            left = -1;
            right = -1;
            di = -1;
        }

        return sum == Integer.MAX_VALUE ? 0 : sum;
    }

}
