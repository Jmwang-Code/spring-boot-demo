package allAlgorithm.BmodelLinkedList;

import allAlgorithm.对数器.链表.单向链表.SinglyLinkedList;

public class SLinkedListTest {

    //单向链表翻转
    public static NodeS unidirectionalLinkedListFlipping(NodeS s){
        //需要3个节点，保留2个节点
        NodeS pre = null,cur = s;
        while (cur!=null){
            //获取下一个节点
            NodeS next = cur.next;

            //指针改向
            cur.next = pre;

            pre = cur;
            cur = next;
        }
        return pre;
    }

    public static void main(String[] args) {
        NodeS nodeS = SinglyLinkedList.generateRandomLinkedList(10, 20);
        SinglyLinkedList.printLinkedList(nodeS);
        NodeS nodeS1 = unidirectionalLinkedListFlipping(nodeS);
        SinglyLinkedList.printLinkedList(nodeS1);
    }
}
