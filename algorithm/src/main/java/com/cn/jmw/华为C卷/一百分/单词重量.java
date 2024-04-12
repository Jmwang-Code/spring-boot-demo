package com.cn.jmw.华为C卷.一百分;

import java.util.Scanner;

/**
 *╭—————————————————————————————╮
 *│ 当前时间：2024-04-13 02:32:53 │
 *│ 完成时间：00:01:13            │
 *╰—————————————————————————————╯
 *
 */
public class 单词重量 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String[] strings = scanner.nextLine().split(" ");
        double sum = 0;
        for (String s : strings) {
            sum += s.length();
        }
        System.out.println(String.format("%.2f", sum / strings.length).toString());
    }
}
