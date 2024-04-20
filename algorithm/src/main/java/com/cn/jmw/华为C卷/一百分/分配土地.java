package com.cn.jmw.华为C卷.一百分;

import java.util.Scanner;

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
    }


    public static String getResult() {
	// 在这里编写你自定义的方法逻辑
        return null;
    }

}
