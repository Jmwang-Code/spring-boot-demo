package com.cn.jmw.华为C卷.一百分;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *╭—————————————————————————————╮
 *│ 当前时间：2024-04-10 20:15:40 │
 *│ 完成时间：00:05:51            │
 *╰—————————————————————————————╯
 *
 */
public class 单词加密X正则表达式 {

    public static void main(String[] args) {
        while (true){
            Scanner scanner = new Scanner(System.in);
            String s = scanner.nextLine();
            String[] words = s.split(" ");

            for (int i = 0; i < words.length; i++) {
                //包含这些词汇
                Matcher matcher = Pattern.compile("[aeiouAEIOU]").matcher(words[i]);
                if (matcher.find()) {
                    //替换成*
                    words[i] = matcher.replaceAll("*");
                } else {
                    char[] cArr = words[i].toCharArray();
                    char tmp = cArr[0];
                    cArr[0] = cArr[cArr.length - 1];
                    cArr[cArr.length - 1] = tmp;
                    words[i] = new String(cArr);
                }
            }

            System.out.println(String.join(" ", words));
        }
    }

    


    public static String getResult() {
	// 在这里编写你自定义的方法逻辑
        return null;
    }

}
