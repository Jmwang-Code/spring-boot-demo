package allAlgorithm.BmodelLinkedList;

import allAlgorithm.对数器.链表.单向链表.SLinkedList;
import allAlgorithm.对数器.链表.单向链表.SinglyLinkedList;
import allAlgorithm.对数器.链表.双向链表.BLinkedList;
import allAlgorithm.对数器.链表.双向链表.BidirectionalLinkedList;

import java.util.Stack;

/**
 * 单向链表
 */
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

    //打印2个有序链表的公共部分
    public static void printLinkedList(NodeS head1,NodeS head2){
        while (head1 != null && head2 != null) {
            if (head1.val < head2.val) {
                head1 = head1.next;
            } else if (head1.val > head2.val) {
                head2 = head2.next;
            } else {
                System.out.print(head1.val + " ");
                head1 = head1.next;
                head2 = head2.next;
            }
        }
    }

    public static NodeS heBing(NodeS head1,NodeS head2){
        NodeS prehead = new NodeS(-1);
        NodeS prev = prehead;

        while (head1!=null && head2!=null){
            if (head1.val <= head2.val) {
                prev.next = head1;
                head1 = head1.next;
            } else {
                prev.next = head2;
                head2 = head2.next;
            }
            prev = prev.next;
        }

        prev.next = head1==null ? head2:head1;
        return prehead.next;
    }

    /**
     * 回文链表 1.直接栈对比
     * 2.折半栈对比
     * 3.三指针回归
     */
//    public static boolean isPalindrome(NodeS head) {
//        Stack<Integer> stack = new Stack();
//        NodeS cur = head;
//
//        while (cur!=null){
//            stack.push(cur.val);
//            cur = cur.next;
//        }
//
//        cur = head;
//
//        while (cur!=null){
//            if (stack.pop()!=cur.val){
//                return false;
//            }
//            cur = cur.next;
//        }
//        return true;
//    }

    //快慢指针+栈
//    public static boolean isPalindrome(NodeS head) {
//        if (head == null || head.next == null) {
//            return true;
//        }
//        NodeS slow = head, fast = head;
//        Stack<Integer> stack = new Stack<>();
//        while (fast != null && fast.next != null) {
//            stack.push(slow.val);
//            slow = slow.next;
//            fast = fast.next.next;
//        }
//        if (fast != null) {
//            slow = slow.next;
//        }
//        while (slow != null) {
//            if (stack.pop() != slow.val) {
//                return false;
//            }
//            slow = slow.next;
//        }
//        return true;
//    }

    //了解怎么继续优化
    public static boolean isPalindrome(NodeS head) {
        if (head == null || head.next == null) {
            return true;
        }
        //区分了快慢指针
        NodeS fast = head;
        NodeS slow = head;
        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next; // slow -> 中部
        }
        fast = slow.next; // fast -> 右部分第一个节点
        slow.next = null; // mid.next -> null
        NodeS node = null;
        // 右半区反转
        while (fast != null) {
            node = fast.next; // 保存next节点
            fast.next = slow; // 当前节点next指向前一个节点
            slow = fast; // 前一个节点指针后移
            fast = node; // 当前节点指针后移
        }
        node = slow; // 保存最后一个节点，用于恢复链表
        fast = head;// 左边第一个节点
        boolean res = true;
        while (slow != null && fast != null) {
            if (slow.val != fast.val) {
                res = false;
                break;
            }
            slow = slow.next; // 左边往中间移动
            fast = fast.next; // 右边往中间移动
        }
        slow = node.next;
        node.next = null;
        while (slow != null) { // 恢复链表
            fast = slow.next;
            slow.next = node;
            node = slow;
            slow = fast;
        }
        return res;
    }

    public static void main(String[] args) {
        NodeS nodeS = SinglyLinkedList.generateRandomLinkedList(10, 20,10,20);
        SinglyLinkedList.printLinkedList(nodeS);
        NodeS nodeS1 = unidirectionalLinkedListFlippingDfs(nodeS);
        SinglyLinkedList.printLinkedList(nodeS1);

        SinglyLinkedList.LogarithmicDevice(100, 10, 20, 10, 20
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

        NodeS nodeS2 = SinglyLinkedList.generateRandomLinkedList(10, 20, 10, 20);
        NodeS nodeS3 = SinglyLinkedList.generateRandomLinkedList(10, 20, 10, 20);
        SinglyLinkedList.printLinkedList(nodeS2);
        SinglyLinkedList.printLinkedList(nodeS3);
        NodeS nodeS4 = heBing(nodeS2, nodeS3);
        SinglyLinkedList.printLinkedList(nodeS4);

        //回文链表
        NodeS s = new NodeS(1,new NodeS(1,new NodeS(2,new NodeS(1))));
        System.out.println(isPalindrome(s));
        NodeS s1 = new NodeS(1,new NodeS(2,new NodeS(3,new NodeS(2,new NodeS(1)))));
        System.out.println(isPalindrome(s1));
    }
}
