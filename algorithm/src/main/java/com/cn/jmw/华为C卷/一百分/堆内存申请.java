package com.cn.jmw.华为C卷.一百分;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Scanner;

/**
 *╭—————————————————————————————╮
 *│ 当前时间：2024-04-18 18:52:28 │
 *│ 完成时间：00:22:45            │
 *╰—————————————————————————————╯
 *
 */
public class 堆内存申请 {

    static class Memory {
        // 内存块起始位置
        int offset;
        // 内存块大小
        int size;

        public Memory(int offset, int size) {
            this.offset = offset;
            this.size = size;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int[] arr = new int[100];

        // 要申请的内存大小
        int malloc_size = Integer.parseInt(scanner.nextLine());

        // 已占用的内存
        ArrayList<Memory> used_memory = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            // 本地测试使用空行作为结束条件
            if (line.length() == 0) break;

            int[] tmp = Arrays.stream(line.split(" ")).mapToInt(Integer::parseInt).toArray();
            used_memory.add(new Memory(tmp[0], tmp[1]));
        }

        for (int i = 0; i < used_memory.size(); i++) {
            Memory memory = used_memory.get(i);
            for (int j = memory.offset; j < memory.size+memory.offset; j++) {
                arr[j] = 1;
            }
        }

        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i]!=1){
                sum++;
                if (sum>=malloc_size){
                    System.out.println(i-malloc_size+1);
                    break;
                }
            }else {
                sum=0;
            }
        }

        if (sum<malloc_size){
            System.out.println(-1);
        }

    }
}
