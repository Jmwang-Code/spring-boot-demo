package allAlgorithm.BmodelLinkedList;

import allAlgorithm.对数器.链表.单向链表.SLinkedList;
import allAlgorithm.对数器.链表.单向链表.SinglyLinkedList;
import allAlgorithm.对数器.链表.双向链表.BLinkedList;
import allAlgorithm.对数器.链表.双向链表.BidirectionalLinkedList;

public class SLinkedListTest {

    //单向链表翻转
    public static NodeS unidirectionalLinkedListFlipping(NodeS s) {
        //需要3个节点，保留2个节点
        NodeS pre = null, cur = s;
        while (cur != null) {
            //获取下一个节点
            NodeS next = cur.next;

            //指针改向
            cur.next = pre;

            pre = cur;
            cur = next;
        }
        return pre;
    }

    public static NodeS unidirectionalLinkedListFlippingDfs(NodeS head) {
        //下一个节点等于null 的时候就返回当前节点
        if (head.next == null) {
            return head;
        }

        //递归传入下一个节点，目的是为了到达最后一个节点
        NodeS next = unidirectionalLinkedListFlippingDfs(head.next);
        /**
         第一轮出栈，head为5，head.next为空，返回5
         第二轮出栈，head为4，head.next为5，执行head.next.next=head也就是5.next=4，
                    把当前节点的子节点的子节点指向当前节点
                    此时链表为1->2->3->4<->5，由于4与5互相指向，所以此处要断开4.next=null
                    此时链表为1->2->3->4<-5
                    返回节点5
         第三轮出栈，head为3，head.next为4，执行head.next.next=head也就是4.next=3，
                    此时链表为1->2->3<->4<-5，由于3与4互相指向，所以此处要断开3.next=null
                    此时链表为1->2->3<-4<-5
                    返回节点5
         第四轮出栈，head为2，head.next为3，执行head.next.next=head也就是3.next=2，
                    此时链表为1->2<->3<-4<-5，由于2与3互相指向，所以此处要断开2.next=null
                    此时链表为1->2<-3<-4<-5
                    返回节点5
         第五轮出栈，head为1，head.next为2，执行head.next.next=head也就是2.next=1，
                    此时链表为1<->2<-3<-4<-5，由于1与2互相指向，所以此处要断开1.next=null
                    此时链表为1<-2<-3<-4<-5
                    返回节点5
         出栈完成，最终头节点5->4->3->2->1
         */
        head.next.next = head;
        head.next = null;
        return next;
    }

    public static void main(String[] args) {
        NodeS nodeS = SinglyLinkedList.generateRandomLinkedList(10, 20);
        SinglyLinkedList.printLinkedList(nodeS);
        NodeS nodeS1 = unidirectionalLinkedListFlippingDfs(nodeS);
        SinglyLinkedList.printLinkedList(nodeS1);

        BidirectionalLinkedList.LogarithmicDevice(10000000, 10, 20, 10, 20
                , new SLinkedList() {
                    @Override
                    public NodeS processNodeS(NodeS val) {
                        return unidirectionalLinkedListFlipping(val);
                    }
                }, new SLinkedList() {
                    @Override
                    public NodeS processNodeS(NodeS val) {
                        return unidirectionalLinkedListFlippingDfs(val);
                    }
                });
    }
}
