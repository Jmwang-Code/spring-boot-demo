package com.cn.jmw.华为C卷.一百分;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *╭—————————————————————————————╮
 *│ 当前时间：2024-04-18 18:27:46 │
 *│ 完成时间：00:14:36            │
 *╰—————————————————————————————╯
 * 
 */
public class 第k个排列XDFS {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()){
            //装排列的字符串
            List<String> list = new ArrayList();

            int location = scanner.nextInt();
            int max = scanner.nextInt();

            dfs(list,1,max,new StringBuffer());

            System.out.println(list.get(location-1));
        }

    }

    public static void dfs(List<String> list,int index,int max,StringBuffer sb){
        if (index>max){
            list.add(sb.toString());
            return;
        }

        for (int i = 1; i <= max; i++) {
            if (sb.toString().contains(String.valueOf(i))){
                continue;
            }
            sb.append(i);

            dfs(list,index+1,max,sb);

            sb.deleteCharAt(sb.length() - 1);
        }

    }

    


    public static String getResult() {
	// 在这里编写你自定义的方法逻辑
        return null;
    }

}
