package com.cn.jmw.杂题;

import java.util.TreeSet;

public class HJ3明明的随机数 {

    public int[] procecss(int[] arr){
        TreeSet<Integer> set = new TreeSet();
        //输入
        for(int i =0 ; i < arr.length ;i++){
            set.add(arr[i]);
        }

        //输出
        int[] res = new int[set.size()];
        int index = 0;
        for (Integer integer : set) {
            res[index++] = integer;
        }
        return res;
    }

    public static void main(String[] args) {
        //测试
        int[] arr = {2,1,1};
        HJ3明明的随机数 hj3 = new HJ3明明的随机数();
        int[] res = hj3.procecss(arr);
        for (int i : res) {
            System.out.println(i);
        }
    }
}
