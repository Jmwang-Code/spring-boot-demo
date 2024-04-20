package com.cn.jmw.华为C卷.一百分;

import java.util.Arrays;
import java.util.Scanner;

/**
 *╭—————————————————————————————╮
 *│ 当前时间：2024-04-08 21:17:48 │
 *│ 完成时间：00:27:43            │
 *╰—————————————————————————————╯
 * 
 */
public class 测试用例执行计划X多维数组排序 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            //n个特性
            int N = scanner.nextInt();
            //m个测试用例
            int M = scanner.nextInt();
            int[] arr = new int[N];
            for (int i = 0; i < N; i++) {
                arr[i] = scanner.nextInt();
            }

            // 消耗掉换行符
            scanner.nextLine();

            int[] test = new int[M];
            int[] testTemp = new int[M];
            for (int i = 0; i < M; i++) {
                int[] s = Arrays.stream(scanner.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
                int sum = 0;
                for (int j = 0; j < s.length; j++) {
                    sum+=arr[s[j]-1];
                }
                test[i] =sum;
                testTemp[i] = i+1;
            }

            //多维数组比较
            int[][] students = new int[M][2];
            for (int i = 0; i < M; i++) {
                students[i] = new int[]{test[i],testTemp[i]};
            }

            Arrays.sort(students,(a,b)->a[0]!=b[0]?b[0]-a[0]:a[1]-b[1]);

            for (int i = 0; i < M; i++) {
                System.out.println(students[i][1]);
            }


        }
    }

    


    public static String getResult() {
	// 在这里编写你自定义的方法逻辑
        return null;
    }

}
