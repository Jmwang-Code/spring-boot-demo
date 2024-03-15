package com.cn.jmw.华为;

import java.util.ArrayList;
import java.util.List;

public class 合并两个二维数组求和法2570 {

    public int[][] mergeArrays(int[][] nums1, int[][] nums2) {
        //一开始就需要创建一个新的数组
        //计算一下x和y
        List<int[]> mergedList = new ArrayList<int[]>();
        int m = nums1.length, n = nums2.length;
        int index1 = 0, index2 = 0;
        while (index1 < m && index2 < n) {
            int id1 = nums1[index1][0], val1 = nums1[index1][1];
            int id2 = nums2[index2][0], val2 = nums2[index2][1];
            if (id1 < id2) {
                mergedList.add(new int[]{id1, val1});
                index1++;
            } else if (id1 > id2) {
                mergedList.add(new int[]{id2, val2});
                index2++;
            } else {
                mergedList.add(new int[]{id1, val1 + val2});
                index1++;
                index2++;
            }
        }
        while (index1 < m) {
            int id = nums1[index1][0], val = nums1[index1][1];
            mergedList.add(new int[]{id, val});
            index1++;
        }
        while (index2 < n) {
            int id = nums2[index2][0], val = nums2[index2][1];
            mergedList.add(new int[]{id, val});
            index2++;
        }
        return mergedList.toArray(new int[mergedList.size()][]);
    }

    public static void main(String[] args) {
        //测试2
        int[][] nums3 = {{1, 2}, {2, 3}, {4, 5}};
        int[][] nums4 = {{1, 4}, {3, 2}, {4, 1}};
        int[][] result2 = new 合并两个二维数组求和法2570().mergeArrays(nums3, nums4);
        for (int i = 0; i < result2.length; i++) {
            for (int j = 0; j < result2[i].length; j++) {
                System.out.print(result2[i][j] + " ");
            }
            System.out.println();
        }

        //测试3  nums1 = [[2,4],[3,6],[5,5]], nums2 = [[1,3],[4,3]]
        int[][] nums5 = {{2, 4}, {3, 6}, {5, 5}};
        int[][] nums6 = {{1, 3}, {4, 3}};
        int[][] result3 = new 合并两个二维数组求和法2570().mergeArrays(nums5, nums6);
        for (int i = 0; i < result3.length; i++) {
            for (int j = 0; j < result3[i].length; j++) {
                System.out.print(result3[i][j] + " ");
            }
            System.out.println();
        }

    }
}
