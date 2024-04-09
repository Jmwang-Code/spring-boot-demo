package com.cn.jmw.华为C卷.一百分;

import java.util.*;

/**
 *╭—————————————————————————————╮
 *│ 当前时间：2024-04-09 17:17:41 │
 *│ 完成时间：00:30:00            │
 *╰—————————————————————————————╯
 * 
 */
public class 查找接口成功率最优时间段 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()){
            int minAverageLost = scanner.nextInt();
            //去掉空行
            scanner.nextLine();
            int[] arr = Arrays.stream(scanner.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            StringJoiner stringJoiner = new StringJoiner(" ");
            int sum = 0;
            int start = 0;
            for (int i = 0; i < arr.length; i++) {
                //是时间段 不是时间点
                if (i<=start)continue;
                int cur = arr[i];
                if ((double)(sum+cur)/(i-start+1)<=minAverageLost){
                    sum+=cur;
                    if (i == arr.length-1){
                        stringJoiner.add(start+"-"+(i));
                    }
                }else {
                    //cur这个可以当新的头
                    if (cur<=minAverageLost){
                        start = i;
                    }else {
                        sum = 0;
                        if (start!=i-1){
                            stringJoiner.add(start+"-"+(i-1));
                        }
                        start = i+1;
                    }
                }
            }
            if (stringJoiner.length()==0){
                System.out.println("NULL");
            }else {
                System.out.println(stringJoiner.toString());
            }
        }


    }

}
