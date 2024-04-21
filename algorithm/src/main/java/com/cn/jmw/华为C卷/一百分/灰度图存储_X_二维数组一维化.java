package com.cn.jmw.华为C卷.一百分;

import java.util.Arrays;
import java.util.Scanner;

public class 灰度图存储_X_二维数组一维化 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int[] nums = Arrays.stream(sc.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int[] pos = Arrays.stream(sc.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        int rows = nums[0];
        int cols = nums[1];

        int[] graph = new int[rows * cols];

        int start = 0;

        for (int i = 2; i < nums.length; i += 2) {
            // 灰阶值
            int gray = nums[i];
            // 该灰阶值从左到右，从上到下（可理解为二维数组按行存储在一维矩阵中）的连续像素个数
            int len = nums[i + 1];
            Arrays.fill(graph, start, start + len, gray);
            start += len;
        }

        // 将二维坐标转为一维坐标
        int target = pos[0] * cols + pos[1];
        System.out.println(graph[target]);
    }

}
