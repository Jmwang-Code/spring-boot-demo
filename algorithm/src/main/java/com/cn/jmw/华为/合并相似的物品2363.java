package com.cn.jmw.华为;

import java.util.*;

public class 合并相似的物品2363 {

    public List<List<Integer>> mergeSimilarItems2(int[][] items1, int[][] items2) {
        Map<Integer,Integer> map = new HashMap<>();

        //item1
        for (int i = 0; i < items1.length; i++) {
            map.put(items1[i][0],map.getOrDefault(items1[i][0],0)+items1[i][1]);
        }

        for (int i = 0; i < items2.length; i++) {
            map.put(items2[i][0],map.getOrDefault(items2[i][0],0)+items2[i][1]);
        }

        List<List<Integer>> list  = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entries:map.entrySet()){
            List<Integer> integers = new ArrayList<>();
            integers.add(entries.getKey());
            integers.add(entries.getValue());
            list.add(integers);
        }
        Collections.sort(list, (a, b) -> a.get(0) - b.get(0));
        return list;
    }

    //有题可知，最长也就是1000
    public List<List<Integer>> mergeSimilarItems(int[][] items1, int[][] items2) {
        List<List<Integer>> list  = new ArrayList<>();

        int[] val2wei = new int[1024];
        for (int[] i :items1) {
            val2wei[i[0]] += i[1];
        }

        for (int[] i :items2) {
            val2wei[i[0]] += i[1];
        }

        for (int i = 0; i < val2wei.length; i++) {
            if (val2wei[i]!=0){
                list.add(List.of(i,val2wei[i]));
            }
        }

        return list;
    }

    public static void main(String[] args) {
        //测试2
        int[][] nums3 = {{1, 2}, {2, 3}, {4, 5}};
        int[][] nums4 = {{1, 4}, {3, 2}, {4, 1}};
        List<List<Integer>> result = new 合并相似的物品2363().mergeSimilarItems(nums3, nums4);
        //打印
        for (List<Integer> list : result) {
            for (Integer integer : list) {
                System.out.print(integer + " ");
            }
            System.out.println();
        }


        //测试3  nums1 = [[2,4],[3,6],[5,5]], nums2 = [[1,3],[4,3]]
        int[][] nums5 = {{2, 4}, {3, 6}, {5, 5}};
        int[][] nums6 = {{1, 3}, {4, 3}};
        List<List<Integer>> result2  = new 合并相似的物品2363().mergeSimilarItems(nums5, nums6);
        //打印
        for (List<Integer> list : result2) {
            for (Integer integer : list) {
                System.out.print(integer + " ");
            }
            System.out.println();
        }

    }
}
