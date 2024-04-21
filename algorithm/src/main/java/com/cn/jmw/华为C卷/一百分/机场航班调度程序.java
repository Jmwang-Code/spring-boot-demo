package com.cn.jmw.华为C卷.一百分;

import java.util.Arrays;
import java.util.Scanner;
import java.util.StringJoiner;

public class 机场航班调度程序 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String[] flights = sc.nextLine().split(",");

        StringJoiner sj = new StringJoiner(",");
        Arrays.stream(flights)
                .sorted(
                        (a, b) -> {
                            String abbr1 = a.substring(0, 2);
                            String num1 = a.substring(2);

                            String abbr2 = b.substring(0, 2);
                            String num2 = b.substring(2);

                            if (abbr1.equals(abbr2)) {
                                return num1.compareTo(num2);
                            } else {
                                return abbr1.compareTo(abbr2);
                            }
                        })
                .forEach(sj::add);

        System.out.println(sj);
    }

}
