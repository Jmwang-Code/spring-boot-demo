package com.cn.jmw.华为C卷.一百分;

import java.util.*;

/**
 *╭—————————————————————————————╮
 *│ 当前时间：2024-04-20 17:40:59 │
 *│ 完成时间：00:20:44            │
 *╰—————————————————————————————╯
 *
 */
public class 多段线数据压缩_X_数学向量 {

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
        System.out.println(getResult(locations));
    }

    public static String getResult(List<Location> locations){
        StringJoiner stringJoiner = new StringJoiner(" ");
        if (locations.size()<=2){
            for (int i = 0; i < locations.size(); i++) {
                Location location = locations.get(i);
                stringJoiner.add(location.y+" "+location.x);
            }
            return stringJoiner.toString();
        }
        //向量值
        int Xv = locations.get(0).x - locations.get(1).x;
        int Yv = locations.get(0).y - locations.get(1).y;

        for (int i = 2; i < locations.size(); i++) {
            Location pre = locations.get(i - 1);
            Location cur = locations.get(i);
            //当前向量 Xcur Ycur
            int Xcur = pre.x - cur.x;
            int Ycur = pre.y - cur.y;
            if (Xcur==Xv && Ycur ==Yv){
                locations.set(i-1,null);
            }
            Xv = Xcur;
            Yv = Ycur;
        }


        for (int i = 0; i < locations.size(); i++) {
            Location location = locations.get(i);
            if (location==null) continue;
            stringJoiner.add(location.y+" "+location.x);
        }
        return stringJoiner.toString();
    }

    


    public static String getResult() {
	// 在这里编写你自定义的方法逻辑
        return null;
    }

}
