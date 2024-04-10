package com.cn.jmw.华为C卷.一百分;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 *╭—————————————————————————————╮
 *│ 当前时间：2024-04-10 20:08:12 │
 *│ 完成时间：00:20:11            │
 *╰—————————————————————————————╯
 *
 */
public class 查找众数及中位数 {

    public static void main(String[] args) {


        while (true){
            Scanner scanner = new Scanner(System.in);
            int[] s = Arrays.stream(scanner.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();

            int[] arr = new int[1001];
            for (int i = 0; i < s.length; i++) {
                arr[s[i]]++;
            }

            int max = Integer.MIN_VALUE;
            //关于arr的索引 众数
            List zd = new ArrayList();
            for (int i = 0; i < arr.length; i++) {
                if (arr[i]==0)continue;
                if (arr[i]<max)continue;
                else if (arr[i]==max){
                    zd.add(i);
                }else {
                    zd = new ArrayList();
                    zd.add(i);
                    max = arr[i];
                }
            }

            if (zd.size()==1){
                System.out.println(zd.get(0));
            }else {
                int[] ji = new int[max*zd.size()];
                int index = 0;
                for (int i = 0; i < zd.size(); i++) {
                    for (int j = 0; j < arr[(int) zd.get(i)]; j++) {
                        ji[index++] = (int) zd.get(i);
                    }
                }
                if (ji.length%2==1){
                    System.out.println(ji[ji.length/2]);
                }else {
                    System.out.println((ji[ji.length/2]+ji[ji.length/2-1])/2);
                }
            }

        }
    }
}
