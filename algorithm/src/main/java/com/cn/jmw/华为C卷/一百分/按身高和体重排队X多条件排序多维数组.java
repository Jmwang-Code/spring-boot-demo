package com.cn.jmw.华为C卷.一百分;

import java.util.Arrays;
import java.util.Scanner;
import java.util.StringJoiner;

/**
 * ╭—————————————————————————————╮
 * │ 当前时间：2024-04-08 12:26:12 │
 * │ 完成时间：00:12:01            │
 * ╰—————————————————————————————╯
 */
public class 按身高和体重排队X多条件排序多维数组 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //n个学生
        int n = scanner.nextInt();
        int[] heights = new int[n];
        for (int i = 0; i < n; i++) {
            heights[i] = scanner.nextInt();
        }

        int[] weights = new int[n];
        for (int i = 0; i < n; i++) {
            weights[i] = scanner.nextInt();
        }

        //多维数组比较
        int[][] students = new int[n][3];

        for (int i = 0; i < n; i++) {
            students[i] = new int[]{heights[i], weights[i], i + 1};
        }

        Arrays.sort(
                students, (a, b) -> a[0] != b[0] ? a[0] - b[0] : a[1] != b[1] ? a[1] - b[1] : a[2] - b[2]);

        StringJoiner sj = new StringJoiner(" ");
        for (int[] student : students) {
            sj.add(student[2] + "");
        }
        System.out.println(sj);
    }
}
