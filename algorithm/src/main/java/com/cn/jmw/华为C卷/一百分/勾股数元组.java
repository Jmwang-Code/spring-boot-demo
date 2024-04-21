package com.cn.jmw.华为C卷.一百分;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringJoiner;

/**
 *╭—————————————————————————————╮
 *│ 当前时间：2024-04-21 17:33:35 │
 *│ 完成时间：00:18:01            │
 *╰—————————————————————————————╯
 * 
 */
public class 勾股数元组 {


    public static void getResult(int X, int Y) {
        List<int[]> list = new ArrayList<>();

        for (int i = X; i <= Y - 2; i++) {
            for (int j = i + 1; j <= Y - 1; j++) {
                for (int k = j + 1; k <= Y; k++) {
                    if (Math.pow(i, 2) + Math.pow(j, 2) == Math.pow(k, 2)) {
                        if (isRelativePrime(i, j) && isRelativePrime(k, j) && isRelativePrime(i, k)) {
                            list.add(new int[]{i, j, k});
                        }
                    }
                }
            }
        }

        for (int[] ar : list) {
            StringJoiner stringJoiner = new StringJoiner(" ");
            for (int i : ar) {
                stringJoiner.add(i + "");
            }

            System.out.println(stringJoiner);
        }
        if (list.size()==0){
            System.out.println("NA");
        }

    }

    // 判断两个数是否互质，辗转相除
    public static boolean isRelativePrime(int x, int y) {
        while (y > 0) {
            int mod = x % y;
            x = y;
            y = mod;
        }

        return x == 1;
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int X = scanner.nextInt();
        int Y = scanner.nextInt();
        getResult(X, Y);
    }

}
