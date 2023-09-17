package allAlgorithm.对数器.链表.单向链表;

import allAlgorithm.BmodelLinkedList.NodeD;
import allAlgorithm.BmodelLinkedList.NodeS;
import allAlgorithm.对数器.链表.LinkedList;
import allAlgorithm.对数器.链表.双向链表.BLinkedList;

public class SinglyLinkedList {

    //随机生成一个长度为n的双向链表，每个节点的值是0~value-1之间的一个随机数
    public static NodeS generateRandomLinkedList(int n, int value) {
        int size = (int) (Math.random() * (n + 1));
        if (size == 0) {
            return null;
        }
        size--;
        NodeS head = new NodeS((int) (Math.random() * value));
        NodeS pre = head;
        while (size != 0) {
            NodeS cur = new NodeS((int) (Math.random() * value));
            pre.next = cur;
            pre = cur;
            size--;
        }
        return head;
    }

    //随机生成一个长度最小为n到最大长度为m的双向链表，每个节点的值是最小值为x~最大值为y之间的一个随机数
    public static NodeS generateRandomLinkedList(int n, int m, int x, int y) {
        int size = (int) (Math.random() * (m - n + 1) + n);
        if (size == 0) {
            return null;
        }
        size--;
        NodeS head = new NodeS((int) (Math.random() * (y - x + 1) + x));
        NodeS pre = head;
        while (size != 0) {
            NodeS cur = new NodeS((int) (Math.random() * (y - x + 1) + x));
            pre.next = cur;
            pre = cur;
            size--;
        }
        return head;
    }

    //打印出上述双向链表的值
    public static void printLinkedList(NodeS head) {
        System.out.print("Linked List: ");
        while (head != null) {
            System.out.print(head.val + " ");
            head = head.next;
        }
        System.out.println();
    }

    //复制一个NodeS节点
    public static NodeS copyNodeS(NodeS head) {
        if (head == null) {
            return null;
        }
        NodeS res = new NodeS(head.val);
        NodeS cur = res;
        head = head.next;
        while (head != null) {
            cur.next = new NodeS(head.val);
            cur = cur.next;
            head = head.next;
        }
        return res;
    }

    //两个节点是否相等
    public static boolean isEqual(NodeS head1, NodeS head2) {
        while (head1 != null && head2 != null) {
            if (head1.val != head2.val) {
                return false;
            }
            head1 = head1.next;
            head2 = head2.next;
        }
        return head1 == null && head2 == null;
    }

    //生成一个对数器的方法，用来验证自己写的方法是否正确
    public static void LogarithmicDevice(int testTimes, int minSize, int maxSize, int minValue, int maxValue, LinkedList... a) {
        //testTime绝对正确的方法与被测方法的比较次数，一旦有1次结果不同立刻打印错误用例并跳出
        boolean succeed = true;
        for (int i = 0; i < testTimes; i++) {
            NodeS nodeS1 = generateRandomLinkedList(minSize, maxSize, minValue, maxValue);
            for (int j = 0; j < a.length - 1; j++) {
                NodeS nodeS2 = copyNodeS(nodeS1);
                NodeS nodeS3 = copyNodeS(nodeS1);
                a[j].processNodeS(nodeS2);
                a[j + 1].processNodeS(nodeS3);
                if (!isEqual(nodeS2, nodeS3)) {
                    succeed = false;
                    System.out.println("当前是第" + i + "个测试用例");
                    printLinkedList(nodeS2);
                    printLinkedList(nodeS3);
                    break;
                }
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");
    }

    public static void main(String[] args) {
        //测试对数器
        LogarithmicDevice(1000, 10, 100, 10, 100, new SLinkedList() {
            @Override
            public NodeS processNodeS(NodeS val) {
                return null;
            }
        }, new SLinkedList() {
            @Override
            public NodeS processNodeS(NodeS val) {
                return null;
            }
        });
    }

}
