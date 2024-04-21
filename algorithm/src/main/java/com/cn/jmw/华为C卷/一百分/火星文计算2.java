package com.cn.jmw.华为C卷.一百分;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class 火星文计算2 {

    public static String getResult(String s) {
        Pattern compile = Pattern.compile("\\d\\$\\d");
        Matcher matcher = compile.matcher(s);

        while (true){
            if (!matcher.find()){
                break;
            }

            //整体
            String group = matcher.group(0);
            long x = Long.parseLong(matcher.group(1));
            long y = Long.parseLong(matcher.group(2));
            s.replaceFirst(group.replace("$", "\\$"),3*x+y+2+"");
        }


        return null;
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        System.out.println(getResult(s));
    }

}
