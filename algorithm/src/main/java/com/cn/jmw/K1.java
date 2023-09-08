package com.cn.jmw;

import java.util.Arrays;

public class K1 {

    //1、2、3、4、5、6、7、8

//    public static int[] getArr(int[] arr,int node){
//        int N = arr.length;
//        for (int i = 0; i < N; i+=node) {   // arr.length / 3
//            if (N-i>=node){
//                for (int l = i,r = l+node-1; l < r; l++,r--) {    //需要比较一个O(n)/2
//                    swap(l,r,arr);
//                }
//            }else {
//                for (int l = i,r = N-1; l < r; l++,r--) {
//                    swap(l,r,arr);
//                }
//            }
//        }
//        return arr;
//    }
//
//    public static void swap(int a, int b, int[] arr){
//        arr[a]^=arr[b];
//        arr[b]^=arr[a];
//        arr[a]^=arr[b];
//    }



//    public static void main(String[] args) {
//        int[] arr = new int[]{1,2,3,4,5,6,7,8};
//        System.out.println(Arrays.toString(getArr(new int[]{1, 2, 3, 4, 5, 6, 7, 8}, 3)));
//    }


    public static void reverse(Node node){
        Node cur = node;
        dp(cur);
    }

    public static void dp(Node node){
        //节点范围 1-3
        Node pre = node;

        Node cur = pre.next;
        Node next = cur.next;
        // cur.next = pre;
        // pre.next = next.next;
        // next.next = cur;

        // 1 2 3 4
        // 1->4
        // 2->1
        // 3->2
    }


    public static void main(String[] args) {
        //测试案例 帮我形成一条链表
        Node node1 = new Node(null,1);
        Node node2 = new Node(node1,2);
        Node node3 = new Node(node2,3);
        Node node4 = new Node(node3,4);
        Node node5 = new Node(node4,5);
        Node node6 = new Node(node5,6);
        Node node7 = new Node(node6,7);
        Node node8 = new Node(node7,8);


    }

}

class Node{

    //当前数据
    private int cur;

    public Node next;

    public Node(Node next,int cur){
        this.cur = cur;
        this.next = next;
    }


}