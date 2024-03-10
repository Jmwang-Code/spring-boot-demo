package com.cn.jmw.递归算法.动态规划;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class HJ8合并表记录 {

    /**
     *
     *
     输入
     4
     0 1
     0 2
     1 2
     3 4
     输出
     0 3
     1 2
     3 4
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tableSize = scanner.nextInt();
        Map<Integer, Integer> table = new HashMap<>(tableSize);
        for (int i = 0; i < tableSize; i++) {
            int key = scanner.nextInt();
            int value = scanner.nextInt();
            if (table.containsKey(key)) {
                table.put(key, table.get(key) + value);
            } else {
                table.put(key, value);
            }
        }
        for (Integer key : table.keySet()) {
            System.out.println( key + " " + table.get(key));
        }
    }
}
