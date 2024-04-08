package com.cn.jmw.华为C卷.一百分;

import java.util.LinkedList;
import java.util.Scanner;

/**
 *╭—————————————————————————————╮
 *│ 当前时间：2024-04-08 15:07:36 │
 *│ 完成时间：00:03:25            │
 *╰—————————————————————————————╯
 *╭—————————————————————————————╮
 *│ 当前时间：2024-04-08 15:04:08 │
 *│ 完成时间：00:17:25            │
 *╰—————————————————————————————╯
 *
 */
public class 表达式括号匹配X栈或LinkedList {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        char[] charArray = scanner.nextLine().toCharArray();
        LinkedList<String> strings = new LinkedList<>();
        int count = 0;
        for (int i = 0; i < charArray.length; i++) {
            if (charArray[i] == '(') {
                strings.add("(");
            } else if (charArray[i] == ')') {
                if (strings.size()!=0 && strings.getLast().equals("(")) {
                    count++;
                    strings.removeLast();
                }else {
                    count = -1;
                    break;
                }
            }
        }
        if (strings.size()!=0)count=-1;
        System.out.println(count);
    }
}
