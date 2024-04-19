package com.cn.jmw.华为C卷.一百分;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 *
 */
public class 多段线数据压缩 {

    static class Location{
        int x;
        int y;

        public Location(int y,int x){
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Location> locations = new ArrayList<>();
        int[] arr = Arrays.stream(scanner.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        for (int i = 0; i < arr.length; i+=2) {
            locations.add(new Location(arr[i],arr[i+1]));
        }
        System.out.println(getResult());
    }

    public static String getResult(){
        //向量值
        int Xv = 0;
        int Yv = 0;
        return null;
    }
}
