package com.cn.jmw.华为C卷.一百分;

import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class 火星文计算_X_正则 {

    public static long getResult(String s) {
        Pattern compile = Pattern.compile("(\\d+)\\$(\\d+)");

        while (true){
            Matcher matcher = compile.matcher(s);
            if (!matcher.find()){
                break;
            }

            //整体
            String group = matcher.group(0);
            long x = Long.parseLong(matcher.group(1));
            long y = Long.parseLong(matcher.group(2));
            s = s.replaceFirst(group.replace("$", "\\$"),3*x+y+2+"");
        }

        Pattern compile2 = Pattern.compile("(\\d+)#(\\d+)");

        while (true){
            Matcher matcher2 = compile2.matcher(s);
            if (!matcher2.find()){
                break;
            }

            //整体
            String group = matcher2.group(0);
            long x = Long.parseLong(matcher2.group(1));
            long y = Long.parseLong(matcher2.group(2));
            s = s.replaceFirst(group,2*x+3*y+4+"");
        }
        return Long.parseLong(s);
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        System.out.println(getResult(s));
    }

}
