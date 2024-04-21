package com.cn.jmw.华为C卷.一百分;

import java.util.Arrays;
import java.util.Scanner;

public class 冠亚军排名奖牌榜排名_X_数组排序 {


    public static void getResult(String[][] arr) {
        Arrays.sort(arr,(o1, o2) ->
                o1[1]!=o2[1]
                        ?(Integer.parseInt(o1[1])-Integer.parseInt(o2[1]))
                        :o1[2]!=o2[2]?(Integer.parseInt(o1[2])-Integer.parseInt(o2[2]))
                :(Integer.parseInt(o1[3])-Integer.parseInt(o2[3])));

        for (int i = arr.length-1; i >= 0; i--) {
            System.out.println(arr[i][0]);
        }
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int i = scanner.nextInt();
        String[][] arr = new String[i][4];
        scanner.nextLine();
        for (int j = 0; j < i; j++) {
            String[] split = scanner.nextLine().split(" ");
            arr[j][0] = split[0];
            arr[j][1] = split[1];
            arr[j][2] = split[2];
            arr[j][3] = split[3];
        }
        getResult(arr);
    }

}
