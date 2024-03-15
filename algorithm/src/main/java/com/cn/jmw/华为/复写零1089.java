package com.cn.jmw.华为;

import java.util.Arrays;

public class 复写零1089 {

    public void duplicateZeros(int[] arr) {

        for (int i = 0; i < arr.length; i++) {
            int cur = arr[i];
            if (cur==0){
                int j = arr.length-2;
                while (j>=i+1){
                    arr[j+1] = arr[j];
                    j--;
                }
                if (i != arr.length-1){
                    arr[i+1] = 0;
                }
                i++;
            }
        }
    }

    public void duplicateZeros2(int[] arr) {
        int[] temp = new int[arr.length];

        int zero = 0;
        for (int i = 0; i < arr.length; i++) {
           if (arr[i] ==0)zero++;
           if (i+zero<arr.length) {
               temp[i + zero] = arr[i];
           }
           arr[i] = temp[i];
        }

    }

    public static void main(String[] args) {
        //测试
        int[] arr = {1,0,2,3,0,4,5,0};
        复写零1089 复写零1089 = new 复写零1089();
        复写零1089.duplicateZeros2(arr);
        System.out.println(Arrays.toString(arr));

        int[] arr2 = {0,0,0,0,0,0,0};
        复写零1089.duplicateZeros2(arr2);
        System.out.println(Arrays.toString(arr2));
    }
}
