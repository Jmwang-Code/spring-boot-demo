package com.cn.jmw.华为C卷.一百分;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println(getResult(sc.nextInt()));
    }

    public static int getResult(int n) {
        CycleLinkedList list = new CycleLinkedList();
        for (int i = 1; i <= n; i++) list.append(i);

        int num = 1;
        Node cur = list.head;

        while (list.size > 1) {
            if (num == 3) {
                num = 1;
                cur = list.remove(cur);
            } else {
                num++;
                cur = cur.next;
            }
        }

        return cur.val;
    }
}

class Node {
    int val;
    Node prev;
    Node next;

    public Node(int val) {
        this.val = val;
        this.prev = null;
        this.next = null;
    }
}

class CycleLinkedList {
    Node head;
    Node tail;
    int size;

    public CycleLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public void append(int val) {
        Node node = new Node(val);

        if (this.size > 0) {
            this.tail.next = node;
            node.prev = this.tail;
        } else {
            this.head = node;
        }

        this.tail = node;

        this.head.prev = this.tail;
        this.tail.next = this.head;
        this.size++;
    }

    public Node remove(Node cur) {
        Node pre = cur.prev;
        Node nxt = cur.next;

        pre.next = nxt;
        nxt.prev = pre;

        cur.next = cur.prev = null;

        if (this.head == cur) {
            this.head = nxt;
        }

        if (this.tail == cur) {
            this.tail = pre;
        }

        this.size--;

        return nxt;
    }

    


    public static String getResult() {
	// 在这里编写你自定义的方法逻辑
        return null;
    }

}