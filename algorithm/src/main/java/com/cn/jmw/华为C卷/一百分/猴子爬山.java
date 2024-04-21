package com.cn.jmw.华为C卷.一百分;

import java.util.Scanner;

public class 猴子爬山 {

    public static int getResult(int target,int indexCur) {
        if (indexCur>target){
            return 0;
        }

        if (indexCur==target){
            return 1;
        }

        int sum = 0;
        sum += getResult(target,indexCur+1);
        sum += getResult(target,indexCur+3);
        return sum;
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int target = scanner.nextInt();
        System.out.println(getResult(target,0));
    }

}
