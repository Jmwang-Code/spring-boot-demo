package allAlgorithm.对数器.链表.双向链表;

import allAlgorithm.BmodelLinkedList.NodeD;
import allAlgorithm.对数器.一维.OneArray;
import allAlgorithm.对数器.链表.LinkedList;
import org.w3c.dom.ls.LSOutput;

public class BidirectionalLinkedList {

    //随机生成一个长度为n的单向链表，每个节点的值是0~value-1之间的一个随机数
    public static NodeD generateRandomLinkedList(int n, int value) {
        int size = (int) (Math.random() * (n + 1));
        if (size == 0) {
            return null;
        }

        size--;
        NodeD head = new NodeD((int) (Math.random() * value));
        NodeD pre = head;
        while (size != 0) {
            NodeD cur = new NodeD((int) (Math.random() * value));
            pre.next = cur;
            cur.pre = pre;
            pre = cur;
            size--;
        }

        return head;
    }

    //随机生成一个长度最小为n到最大长度为m的单向链表，每个节点的值是最小值为x~最大值为y之间的一个随机数
    public static NodeD generateRandomLinkedList(int n, int m, int x, int y) {
        int size = (int) (Math.random() * (m - n + 1) + n);
        if (size == 0) {
            return null;
        }

        size--;
        NodeD head = new NodeD((int) (Math.random() * (y - x + 1) + x));
        NodeD pre = head;
        while (size != 0) {
            NodeD cur = new NodeD((int) (Math.random() * (y - x + 1) + x));
            pre.next = cur;
            cur.pre = pre;
            pre = cur;
            size--;
        }

        return head;
    }


    //打印出上述单向链表的值
    public static void printLinkedList(NodeD head) {
        System.out.print("Linked List: ");
        while (head != null) {
            System.out.print(head.val + " ");
            head = head.next;
        }

        System.out.println();
    }

    //复制一个NodeD节点
    public static NodeD copyNodeD(NodeD head) {
        if (head == null) {
            return null;
        }
        NodeD res = new NodeD(head.val);
        NodeD cur = res;
        while (head.next != null) {
            cur.next = new NodeD(head.next.val);
            cur.next.pre = cur;
            cur = cur.next;
            head = head.next;
        }
        return res;
    }

    //两个节点是否相等
    public static boolean isEqual(NodeD head1, NodeD head2) {
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
            NodeD nodeD1 = generateRandomLinkedList(minSize, maxSize, minValue, maxValue);
            for (int j = 0; j < a.length - 1; j++) {
                NodeD nodeD2 = copyNodeD(nodeD1);
                NodeD nodeD3 = copyNodeD(nodeD1);
                a[j].processNodeD(nodeD2);
                a[j + 1].processNodeD(nodeD3);
                if (!isEqual(nodeD2, nodeD3)) {
                    succeed = false;
                    printLinkedList(nodeD2);
                    printLinkedList(nodeD3);
                    break;
                }
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");
    }


    public static void main(String[] args) {
        //测试对数器
        LogarithmicDevice(1000, 10, 100, 10, 100, new BLinkedList() {
            @Override
            public NodeD processNodeD(NodeD val) {
                return null;
            }
        }, new BLinkedList() {
            @Override
            public NodeD processNodeD(NodeD val) {
                return null;
            }
        });
    }
}
