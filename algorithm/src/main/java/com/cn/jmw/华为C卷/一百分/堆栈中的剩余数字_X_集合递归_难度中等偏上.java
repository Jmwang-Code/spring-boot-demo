package com.cn.jmw.华为C卷.一百分;

import java.util.*;

/**
 *╭—————————————————————————————╮
 *│ 当前时间：2024-04-18 19:49:39 │
 *│ 完成时间：00:35:31            │
 *╰—————————————————————————————╯
 *
 * 核心: 最新的一个值，去在之前插入的栈中的最近连续只和 是否相等。如果相等就重新插入，如果不相等就直接插入栈中
 *
 * 1 2 5 7 9 1 2 2
 * 比如: 1 2 5 7  => 1 2+5=7 => 1 14
 * 比如: 1 2 5 7 9 1 2 2 => 1 2+5=7 9 1 2 2 => 1 14 9 1 2 2 => 1 14 9 1 2+2=4 => 1 14 9 1 4
 */
public class 堆栈中的剩余数字_X_集合递归_难度中等偏上 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[] nums = Arrays.stream(sc.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        System.out.println(getResult(nums));
    }

    public static String getResult(int[] nums) {
        LinkedList<Integer> stack = new LinkedList<>();
        //初始栈
        stack.add(nums[0]);

        for (int i = 1; i < nums.length; i++) {
            //插入下一个
            push(nums[i], stack);
        }

        StringJoiner sj = new StringJoiner(" ");
        while (stack.size() > 0) {
            sj.add(stack.removeLast() + "");
        }
        return sj.toString();
    }

    public static void push(int num, LinkedList<Integer> stack) {
        //把当前数值当做可以合并的数值，
        int sum = num;

        //用栈输入的顺序判断，前缀和是否鱼最新的值相等
        for (int i = stack.size() - 1; i >= 0; i--) {
            //把当前的减去之前的数值
            sum -= stack.get(i);

            //如果相等
            if (sum == 0) {
                stack.subList(i, stack.size()).clear(); //清空栈和当前位数字 相等的值
                push(num * 2, stack); //插入他们的和
                return;
            } else if (sum < 0) {
                break;
            }
        }
        // 如果不相等就直接插入
        stack.add(num);
    }
}
