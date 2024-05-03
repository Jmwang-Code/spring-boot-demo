package com.cn.jmw.od;

import java.util.*;

public class Demo {

    static Set<String> set = new HashSet();

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        while (in.hasNextInt()) { // 注意 while 处理多个 case
            int m = in.nextInt(); //m个员工
            int n = in.nextInt(); //n个月饼
            int[][] arr = new int[m+1][n+1];
            for (int i = 0; i < arr.length; i++) {
                Arrays.fill(arr[i],-1);
            }
            dfs(m, n, new ArrayList<>(),arr);
            System.out.println(set.size());
        }
    }


    public static void dfs(int m, int n, List<Integer> list,int[][] arr) {
        if (n < m || n < 0 || (m == 0 && n != 0)) {
            return;
        }
        if (arr[m][n]!=-1){
            return;
        }

        if (m == 0 && n == 0) {
            ArrayList<Integer> integers = new ArrayList<>();
            integers.addAll(list);
            integers.sort((o1, o2) -> o1 - o2);
            StringJoiner string = new StringJoiner("");
            for (int i = 0; i < integers.size() - 1; i++) {
                if (integers.get(i + 1) - integers.get(i) > 3) {
                    return;
                }
            }
            for (int i : integers) {
                string.add(i + "");
            }
            set.add(string.toString());
            return;
        }
        for (int i = 1; i <= n; i++) {
            if (m > n) {
                break;
            }
            list.add(i);
            dfs(m - 1, n - i, list,arr);
            list.remove(list.size() - 1);
        }

    }

}
