package com.cn.jmw.logarithmic;

public class Logarithmic {

    //随机生成1个int数组，可以自定义长度，最大值，最小值
    public static int[] randomArray(int length, int max, int min) {
        int[] arr = new int[length];
        for (int i = 0; i < length; i++) {
            arr[i] = (int)(Math.random()*(max-min+1)+min);
        }
        return arr;
    }


}
