package com.cn.jmw.华为C卷.一百分;

import java.lang.reflect.Array;
import java.util.*;

public class 精准核酸检测_X_集合特性OR并查集 {


    public static int getResult(int n,int[] toConfirm,int[][] matrix) {
        //需要做核算的
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < toConfirm.length; i++) {
            set.add(toConfirm[i]);
        }

        //对角线
        for (int i = 0; i < matrix.length; i++) {
            for (int j = i+1; j < matrix[i].length; j++) {
                //接触过
                if (matrix[i][j] == 1){
                    if (set.contains(i)){
                        set.add(j);
                    }else if (set.contains(j)){
                        set.add(i);
                    }
                }
            }
        }
        
        return set.size()-toConfirm.length;
    }


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = Integer.parseInt(sc.nextLine());

        int[] toConfirm = Arrays.stream(sc.nextLine().split(",")).mapToInt(Integer::parseInt).toArray();

        int[][] matrix = new int[n][n];
        for (int i = 0; i < n; i++) {
            matrix[i] = Arrays.stream(sc.nextLine().split(",")).mapToInt(Integer::parseInt).toArray();
        }

        System.out.println(getResult(n, toConfirm, matrix));
    }

}
