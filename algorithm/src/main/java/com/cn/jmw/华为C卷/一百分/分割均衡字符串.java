package com.cn.jmw.华为C卷.一百分;

import java.util.Scanner;

/**
 *╭—————————————————————————————╮
 *│ 当前时间：2024-04-20 20:27:13 │
 *│ 完成时间：00:05:02            │
 *╰—————————————————————————————╯
 *
 */
public class 分割均衡字符串 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        System.out.println(getResult(s.toCharArray()));
    }

    public static int getResult(char[] chars){
        int x = 0;
        int y = 0;
        int count = 0;
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (c=='X')x++;
            else y++;

            if (x==y){
                count++;
                x = 0;
                y = 0;
            }
        }

        return count;
    }


}
