package com.cn.jmw.华为C卷.一百分;

import java.util.Arrays;
import java.util.Scanner;

public class 猴子爬山_X_动态规划 {

    public static int getResult(int target,int indexCur,int[] arr) {
        if (indexCur>target){
            return 0;
        }

        if (arr[indexCur]!=-1){
            return arr[indexCur];
        }

        if (indexCur==target){
            return 1;
        }

        int sum = 0;
        sum += getResult(target,indexCur+1,arr);
        sum += getResult(target,indexCur+3,arr);

        arr[indexCur] = sum;

        return sum;
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int target = scanner.nextInt();
        int[] arr = new int[target+1];
        Arrays.fill(arr,-1);
        System.out.println(getResult(target,0,arr));
    }

}
