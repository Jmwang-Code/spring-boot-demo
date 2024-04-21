package com.cn.jmw.华为C卷.一百分;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class 会议室占用时间 {

    // 输入输出处理
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        int[][] roomTimes = new int[n][2];
        for (int i = 0; i < n; i++) {
            roomTimes[i][0] = sc.nextInt();
            roomTimes[i][1] = sc.nextInt();
        }

        int[][] res = merge(roomTimes);
        for (int[] time : res) {
            System.out.println(time[0] + " " + time[1]);
        }
    }

    // 本题实际考试时会核心代码模式，无需处理输入输出，只需要写出merge方法实现即可
    public static int[][] merge(int[][] roomTimes) {
        // 将各个会议按照开始时间升序
        Arrays.sort(roomTimes, (a, b) -> a[0] - b[0]);

        // 记录合并后的会议室占用时间段
        ArrayList<int[]> list = new ArrayList<>();

        // 上一个会议占用时间段
        int[] pre = roomTimes[0];

        for (int i = 1; i < roomTimes.length; i++) {
            // 当前会议占用时间段
            int[] cur = roomTimes[i];

            if (cur[0] <= pre[1]) {
                // 当前会议开始时间 <= 上一个会议结束时间，则两个会议时间重叠，可以合并
                // 注意合并时，结束时间取两个时间段中较大的结束时间
                pre[1] = Math.max(pre[1], cur[1]);
            } else {
                // 否则不可以合并
                list.add(pre);
                pre = cur;
            }
        }

        list.add(pre);

        return list.toArray(new int[0][]);
    }

}
