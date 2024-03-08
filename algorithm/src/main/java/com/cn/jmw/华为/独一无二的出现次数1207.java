package com.cn.jmw.华为;

import java.util.*;

public class 独一无二的出现次数1207 {

    private boolean uniqueOccurrences(int[] arr) {
        Map<Integer, Integer> occur = new HashMap<Integer, Integer>();
        for (int x : arr) {
            occur.put(x, occur.getOrDefault(x, 0) + 1);
        }
        Set<Integer> times = new HashSet<Integer>();
        //Set去重Map的Value，当Value被去重了1-n个就说明false
        for (Map.Entry<Integer, Integer> x : occur.entrySet()) {
            times.add(x.getValue());
        }
        return times.size() == occur.size();
    }

    public static void main(String[] args) {
        //测试
        int[] arr = {1, 2, 2, 1, 1, 3};
        独一无二的出现次数1207 test = new 独一无二的出现次数1207();
        System.out.println(test.uniqueOccurrences(arr));

    }


}
