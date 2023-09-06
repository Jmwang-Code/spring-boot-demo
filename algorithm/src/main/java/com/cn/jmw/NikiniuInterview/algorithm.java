package com.cn.jmw.NikiniuInterview;

public class algorithm {

    public static double calculate_it()
    {
        double x = 0.0d;
        double y = 0.0d;
        int total = 0;
        for(int i = 0;i<7000000;i++){
            x = Math.random();
            y = Math.random();
            if(Math.sqrt(x*x+y*y) < 1)
                total++;
        }
        return total/1000000.0;
    }
}