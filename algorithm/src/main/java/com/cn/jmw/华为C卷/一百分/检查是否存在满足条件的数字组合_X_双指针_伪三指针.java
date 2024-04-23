package com.cn.jmw.华为C卷.一百分;

import java.util.Arrays;
import java.util.Scanner;

public class 检查是否存在满足条件的数字组合_X_双指针_伪三指针 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = Integer.parseInt(sc.nextLine());
        Integer[] arr =
                Arrays.stream(sc.nextLine().split(" ")).map(Integer::parseInt).toArray(Integer[]::new);

        System.out.println(getResult(n, arr));
    }

    public static String getResult2(int n, Integer[] arr) {
        Arrays.sort(arr, (a, b) -> b - a);

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    if (arr[i] == arr[j] + 2 * arr[k]) {
                        return arr[i] + " " + arr[j] + " " + arr[k];
                    }
                    if (arr[i] == arr[k] + 2 * arr[j]) {
                        return arr[i] + " " + arr[k] + " " + arr[j];
                    }
                }
            }
        }

        return "0";
    }

    public static String getResult(int n, Integer[] arr) {
        Arrays.sort(arr, (a, b) -> a - b);

        for (int i = arr.length-1; i > 1; i--) {
            int right = i, middle = 1, left = 0;
            int target =  arr[right];
            while (left < middle && middle < right) {
                if (target > arr[middle] + arr[left] * 2) {
                    //是否一起移动
                    if (target > arr[middle+1] + arr[left] * 2){
                        middle++;
                        left++;
                    }else {
                        //移动mid
                        middle++;
                    }
                } else if (target < arr[middle] + arr[left] * 2) {
                    break;
                } else {
                    return arr[right] + " " + arr[middle] + " " + arr[left];
                }
            }
        }

        return "0";
    }

}
