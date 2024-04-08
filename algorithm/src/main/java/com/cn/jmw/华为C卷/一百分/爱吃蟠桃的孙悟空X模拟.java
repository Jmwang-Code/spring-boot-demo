package com.cn.jmw.华为C卷.一百分;

import java.util.Arrays;
import java.util.Scanner;

/**
 *╭—————————————————————————————╮
 *│ 当前时间：2024-04-08 12:13:06 │
 *│ 完成时间：00:14:21            │
 *╰—————————————————————————————╯
 *
 */
public class 爱吃蟠桃的孙悟空X模拟 {

    //有N棵树
    //每小时吃K个
    //H小时后回来
    //每次选一棵树吃，吃k个，如果小于K就全吃完。

    //返回K，也就是用最小速度吃完。如果多大速度都吃不完，就返回0。
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //N个桃树，并且每个桃树的数量
        while (scanner.hasNext()){
            String line = scanner.nextLine();
            if (line.isEmpty()) // 如果输入行为空，则继续下一次循环
                continue;
            int[] N = Arrays.stream(line.split(" ")).mapToInt(Integer::parseInt).toArray();
            int H = scanner.nextInt();
            if (H<N.length){
                System.out.println(0);
            }else {
                //余
                int mod = H%N.length;
                Arrays.sort(N);
                System.out.println(N[N.length-1-mod]);
            }
        }
    }
}
