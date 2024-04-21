package com.cn.jmw.华为C卷.一百分;

import java.util.Scanner;

/**
 * ╭—————————————————————————————╮
 * │ 当前时间：2024-04-21 17:12:52 │
 * │ 完成时间：00:06:01            │
 * ╰—————————————————————————————╯
 * X 有X个员工
 * Y 有Y个字母
 * 返回Z Z需要数字的数量
 */
public class 工号不够用了怎么办_X_数学_简单 {

    public static int getResult(int X, int Y) {
        int Z = 1;

        while ((Math.pow(26, Y)) * (Math.pow(10, Z)) < X) {
            Z++;
        }

        return Z;
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int X = scanner.nextInt();
        int Y = scanner.nextInt();
        System.out.println(getResult(X, Y));
    }

}
