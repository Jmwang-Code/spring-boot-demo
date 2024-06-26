package com.cn.jmw.华为C卷.一百分;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * ╭—————————————————————————————╮
 * │ 当前时间：2024-04-18 18:52:28 │
 * │ 完成时间：00:30:45            │
 * ╰—————————————————————————————╯
 */
public class 堆内存申请X模拟数组 {

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

        System.out.println(getResult(arr,malloc_size,used_memory));
    }

    public static int getResult(int[] arr, int malloc_size, ArrayList<Memory> used_memory) {
        for (int i = 0; i < used_memory.size(); i++) {
            Memory memory = used_memory.get(i);
            for (int j = memory.offset; j < memory.size + memory.offset; j++) {
                if (arr[j] == 1) {
                    return -1;
                } else {
                    arr[j] = 1;
                }
            }
        }

        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != 1) {
                sum++;
                if (sum >= malloc_size) {
                    return i - malloc_size + 1;
                }
            } else {
                sum = 0;
            }
        }

        return -1;
    }

    


    public static String getResult() {
	// 在这里编写你自定义的方法逻辑
        return null;
    }

}
