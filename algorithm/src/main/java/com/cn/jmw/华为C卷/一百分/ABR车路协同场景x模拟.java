package com.cn.jmw.华为C卷.一百分;

import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *╭—————————————————————————————╮
 *│ 当前时间：2024-04-03 18:03:07 │
 *│ 完成时间：00:25:32            │
 *╰—————————————————————————————╯
 *
 *
 * 有一个数组A 和 数组B  有一个距离差值R
 *
 * 0. A > B,A就舍弃
 * 1. A<=B ,①B-A<=R，就直接append ②获取一个最近的就是大于A的第一个
 *
 */
public class ABR车路协同场景x模拟 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        Matcher matcher = Pattern.compile("A=\\{(.+)},B=\\{(.+)},R=(.+)").matcher(s);
        if (matcher.matches()) {
            int[] A = Arrays.stream(matcher.group(1).split(",")).mapToInt(Integer::parseInt).toArray();
            int[] B = Arrays.stream(matcher.group(2).split(",")).mapToInt(Integer::parseInt).toArray();
            Integer R = Integer.parseInt(matcher.group(3));
            System.out.println(getResult(A, B, R));
        }
    }

    public static String getResult(int[] A, int[] B, Integer R) {
        StringBuilder sb = new StringBuilder();

        //循环A
        for (int a : A) {
            //循环B
            int cnt = 0;
            for (int b : B) {
                //如果B小于A，直接跳过
                if (b < a) continue;
                //如果B-A小于R，或者是第一个，就添加
                if (b - a <= R || cnt == 0) {
                    sb.append("(").append(a).append(",").append(b).append(")");
                    cnt++;
                } else {
                    break;
                }
            }
        }

        return sb.toString();
    }
}
