package com.cn.jmw.华为C卷.一百分;

import java.util.Arrays;
import java.util.Scanner;
import java.util.StringJoiner;

/**
 *╭—————————————————————————————╮
 *│ 当前时间：2024-04-08 14:40:10 │
 *│ 完成时间：00:16:19            │
 *╰—————————————————————————————╯
 *
 */
public class 报数游戏X链表环 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()){
            int M = scanner.nextInt();

            if (M<1 || M>100){
                System.out.println("ERROR");
                continue;
            }

            //1-100的环
            Node node = new Node(1);
            Node temp = node;
            for (int i = 2; i <= 100; i++) {
                temp.next = new Node(i);
                temp = temp.next;
            }
            temp.next = node;

            int size = 100;
            int index = 1;
            Node pre = node,cur = node;
            //返回跳出循环
            while (size>=M){
                if (index == M){
                    pre.next = cur.next;
                    cur = cur.next;
                    index=1;
                    size--;
                }else {
                    pre = cur;
                    cur = cur.next;
                    index++;
                }
            }

            StringJoiner stringJoiner = new StringJoiner(",");
            int[] arr = new int[size];
            for (int i = 0; i < size; i++) {
                arr[i] = cur.var;
                cur = cur.next;
            }
            Arrays.sort(arr);
            for (int i = 0; i < arr.length; i++) {
                stringJoiner.add(arr[i]+"");
            }
            System.out.println(stringJoiner.toString());
        }
    }

    static class Node{

        int var;
        Node next;

        public Node(int var){
            this.var = var;
        }

    }
}
