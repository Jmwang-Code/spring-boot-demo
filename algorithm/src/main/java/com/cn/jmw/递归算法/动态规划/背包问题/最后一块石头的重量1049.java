package com.cn.jmw.递归算法.动态规划.背包问题;

/**
 * 有一堆石头，用整数数组 stones 表示。其中 stones[i] 表示第 i 块石头的重量。
 * <p>
 * 每一回合，从中选出任意两块石头，然后将它们一起粉碎。假设石头的重量分别为 x 和 y，且 x <= y。那么粉碎的可能结果如下：
 * <p>
 * 如果 x == y，那么两块石头都会被完全粉碎；
 * 如果 x != y，那么重量为 x 的石头将会完全粉碎，而重量为 y 的石头新重量为 y-x。
 * 最后，最多只会剩下一块 石头。返回此石头 最小的可能重量 。如果没有石头剩下，就返回 0。
 */
public class 最后一块石头的重量1049 {

    //常式
    public int lastStoneWeightII(int[] stones) {
        //计算总重量
        int totalSum = 0;
        for (int stone : stones) {
            totalSum += stone;
        }

        return process(stones, totalSum, 0, 0);
    }

    //石头数组 stones
    //总重量 totalSum
    //当前数组位置 index 0-(stones.length-1)
    //当前重量 sum - totalSum 其实因为一直有减法所以最多到（totalSum/2）
    private int process(int[] stones, int totalSum, int index, int sum) {

        if (index == stones.length) {
            //相当于最后一块时候的时候，判断和上一块石头碰撞等于多少。
            //比如sum = 1  totalSum = 1, 1-1=0 ,或者 sum = 2 totalSum = 1,1-4=-3 |-3| = 3
            return Math.abs(totalSum - 2 * sum);
        }

        //01背包 0
        int res = process(stones, totalSum, index + 1, sum);
        //01背包 1
        int res1 = process(stones, totalSum, index + 1, sum + stones[index]);

        return Math.min(res1, res);
    }




    public static void main(String[] args) {
        最后一块石头的重量1049 s = new 最后一块石头的重量1049();
        int[] stones = {2, 7, 4, 1, 8, 1};
        System.out.println(s.lastStoneWeightII(stones));


        int[] stones2 = {89, 23, 100, 93, 82, 98, 91, 85, 33, 95, 72, 98, 63, 46, 17, 91, 92, 72, 77, 79, 99, 96, 55, 72, 24, 98, 79, 93, 88, 92};
        System.out.println(s.lastStoneWeightII(stones2));

    }
}
