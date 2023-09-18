package allAlgorithm.BmodelLinkedList;

import allAlgorithm.对数器.链表.双向链表.BLinkedList;
import allAlgorithm.对数器.链表.双向链表.BidirectionalLinkedList;

/**
 * 双向链表
 */
public class DLinkedListTest {

    //双向链表翻转
    public static NodeD unidirectionalLinkedListFlipping(NodeD s) {
        //需要3个节点，保留2个节点
        NodeD pre = null, cur = s;
        while (cur != null) {
            //获取下一个节点
            NodeD next = cur.next;

            //指针改向
            cur.next = pre;
            cur.pre = next;

            pre = cur;
            cur = next;
        }
        return pre;
    }

    public static NodeD unidirectionalLinkedListFlippingDfs(NodeD s) {
        if (s.next==null)return s;

        NodeD next = unidirectionalLinkedListFlippingDfs(s.next);

        //next节点翻转
        s.next.next = s;
        s.pre = s.next;

        s.next = null;
        //原始整体
        //next 1->2->3->4->5
        //pre  1<-2<-3<-4<-5
        //第一轮
        //执行节点翻转后
        //next 1->2->3->4-><-5
        //pre  1<-2<-3<-4-><-5
        //所以需要暂时的指向空
        //next 1->2->3->4<-5
        //pre  1<-2<-3<-4->5

        return next;
    }

    public static void main(String[] args) {
        NodeD nodeD = BidirectionalLinkedList.generateRandomLinkedList(10, 20,10,20);
        BidirectionalLinkedList.printLinkedList(nodeD);
        NodeD nodeD1 = unidirectionalLinkedListFlippingDfs(nodeD);
        BidirectionalLinkedList.printLinkedList(nodeD1);

        BidirectionalLinkedList.LogarithmicDevice(10000000, 10, 20, 10, 20
                , new BLinkedList() {
                    @Override
                    public NodeD processNodeD(NodeD val) {
                        return unidirectionalLinkedListFlipping(val);
                    }
                }, new BLinkedList() {
                    @Override
                    public NodeD processNodeD(NodeD val) {
                        return unidirectionalLinkedListFlippingDfs(val);
                    }
                });
    }

}
