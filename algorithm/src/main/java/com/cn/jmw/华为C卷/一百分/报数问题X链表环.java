package com.cn.jmw.华为C卷.一百分;

import java.util.Scanner;

/**
 *╭—————————————————————————————╮
 *│ 当前时间：2024-04-08 14:21:57 │
 *│ 完成时间：00:30:54            │
 *╰—————————————————————————————╯
 * 
 */
public class 报数问题X链表环 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()){
            int hua = scanner.nextInt();
            Node node = new Node(1);
            Node temp = node;
            for (int i = 2; i <= hua; i++) {
                temp.next = new Node(i);
                temp = temp.next;
            }

            temp.next = node;

            int size = 1;
            Node pre = node;
            Node cur = node;
            while (pre.next.var!= pre.var){
                if (size==3){
                    pre.next = cur.next;
                    size=1;
                    cur = cur.next;
                    continue;
                }
                pre = cur;
                cur = cur.next;
                size++;
            }

            System.out.println(cur.var);
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
