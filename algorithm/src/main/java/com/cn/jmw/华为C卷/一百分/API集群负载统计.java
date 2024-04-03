package com.cn.jmw.华为C卷.一百分;

import java.util.Scanner;

/**
 *╭—————————————————————————————╮
 *│ 当前时间：2024-04-03 18:32:58 │
 *│ 完成时间：00:15:56            │
 *╰—————————————————————————————╯
 *
 */
public class API集群负载统计 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int begin = Integer.parseInt(scanner.nextLine());
        String[] arr = new String[begin];
        for (int i = 0; i < begin; i++) {
            arr[i] = scanner.nextLine();
        }
        String[] end = scanner.nextLine().split(" ");
        //end[0] 就是层数  end[1] 就是关键词
        int end0 = Integer.parseInt(end[0]);

        //count就是关键词在层数上的次数
        int count = 0;
        for (int i = 0; i < arr.length; i++) {
            String string = arr[i];
            String[] split = string.split("/");
            if (split.length<end0)continue;
            else {
                if (end0> split.length-1){
                    continue;
                } else if(split[end0].equals(end[1])){
                    count++;
                }
            }
        }


        System.out.println(count);
    }

}
