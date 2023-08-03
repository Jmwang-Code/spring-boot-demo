package com.cn.jmw.保留状态;

import org.junit.Test;

public class GasStation {

    // 134. 加油站
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int n = gas.length;
        int i = 0;
        while (i < n) {
            int sumOfGas = 0, sumOfCost = 0;
            int cnt = 0;
            while (cnt < n) {
                int j = (i + cnt) % n;
                sumOfGas += gas[j];
                sumOfCost += cost[j];
                if (sumOfCost > sumOfGas) {
                    break;
                }
                cnt++;
            }
            if (cnt == n) {
                return i;
            } else {
                i = i + cnt + 1;
            }
        }
        return -1;

    }

    @Test
    public void test(){
        int[] gas = {1,2,3,4,5};
        int[] cost = {3,4,5,1,2};

        int i = canCompleteCircuit(gas, cost);
        System.out.println(i);
    }
}
