package allAlgorithm.BmodelLinkedList;

import allAlgorithm.对数器.链表.单向链表.SLinkedList;
import allAlgorithm.对数器.链表.单向链表.SinglyLinkedList;
import allAlgorithm.对数器.链表.单向随机链表.RandomLinkedList;
import allAlgorithm.对数器.链表.双向链表.BLinkedList;
import allAlgorithm.对数器.链表.双向链表.BidirectionalLinkedList;
import com.cn.jmw.递归算法.动态规划.机器人寻路;
import org.w3c.dom.Node;

import java.util.*;

/**
 * 单向链表
 * <p>
 * arrPartition需要理解一下3指针
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
    public static void printLinkedList(NodeS head1, NodeS head2) {
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

    public static NodeS heBing(NodeS head1, NodeS head2) {
        NodeS prehead = new NodeS(-1);
        NodeS prev = prehead;

        while (head1 != null && head2 != null) {
            if (head1.val <= head2.val) {
                prev.next = head1;
                head1 = head1.next;
            } else {
                prev.next = head2;
                head2 = head2.next;
            }
            prev = prev.next;
        }

        prev.next = head1 == null ? head2 : head1;
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

    /**
     * 给你一个链表的头节点 head 和一个特定值 x ，请你对链表进行分隔，使得所有 小于 x 的节点都出现在 大于或等于 x 的节点之前。
     * 你应当 保留 两个分区中每个节点的初始相对位置。
     * <p>
     * 也就是稳定性
     */
    public static NodeS partition(NodeS head, int x) {
        if (head == null) return head;
        NodeS cur = head;
        int i = 0;
        //将当前顺序取出
        while (cur != null) {
            i++;
            cur = cur.next;
        }

        //存放临时值
        NodeS[] nodeS = new NodeS[i];
        i = 0;
        cur = head;
        for (i = 0; i != nodeS.length; i++) {
            nodeS[i] = cur;
            cur = cur.next;
            nodeS[i].next = null;
        }
        arrPartition(nodeS, x);
        //重新组装链表
        for (i = 1; i != nodeS.length; i++) {
            nodeS[i - 1].next = nodeS[i];
        }
        //关键操作将之前的节点结尾指向null
        return nodeS[0];
    }

    /**
     * 分区数组
     * <p>
     * 需要记录几个点位，比如index(0-N-1) ， 记录小数索引 less=-1 ，记录大数索引 more=n
     * <p>
     * 移动情况
     * 1. 当arr[index]<target,说明出现了一个小数 ，交换++less,index++数组位置。（具体表现就是，index位增加，小数位置增加）
     * 2. 当arr[index]>target,说明出现了一个大数 ，交换--more,index当前值。
     * 3. 当arr[index]=target,说明此为止暂时不用移动。
     */
    public static void arrPartition(NodeS[] nodeS, int x) {
        int less = -1; // less指针指向小于x的元素的最后一个位置
        int more = nodeS.length; // more指针指向大于x的元素的第一个位置
        int index = 0; // index指针用于遍历数组
        while (index != more) { // 当index指针小于more指针时，继续遍历数组
            if (nodeS[index].val < x) { // 如果当前元素小于x
                swap(nodeS, ++less, index++); // 将当前元素与less指针指向的下一个元素交换，并将less指针和index指针都向右移动一位
            } else if (nodeS[index].val > x) { // 如果当前元素大于x
                swap(nodeS, --more, index); // 将当前元素与more指针指向的上一个元素交换，并将more指针向左移动一位
            } else { // 如果当前元素等于x
                index++; // 将index指针向右移动一位
            }
        }
    }

    public static void swap(NodeS[] nodeS, int i, int j) {
        NodeS temp = nodeS[i];
        nodeS[i] = nodeS[j];
        nodeS[j] = temp;
    }

    public static NodeS partitionTwo(NodeS head, int x) {
        NodeS preBegin = null, preEnd = null, midBegin = null, midEnd = null, nextBegin = null, nextEnd = null;
        NodeS next = head;
        while (head != null) {
            //保留下一个值 next
            next = head.next;
            //保证每个分解出来的链都是单个的
            head.next = null;
            if (head.val < x) {
                if (preBegin == null) {
                    preBegin = head;
                    preEnd = head;
                } else {
                    preEnd.next = head;
                    preEnd = head;
                }
            } else if (head.val == x) {
                if (midBegin == null) {
                    midBegin = head;
                    midEnd = head;
                } else {
                    midEnd.next = head;
                    midEnd = head;
                }
            } else {
                if (nextBegin == null) {
                    nextBegin = head;
                    nextEnd = head;
                } else {
                    nextEnd.next = head;
                    nextEnd = head;
                }
            }
            head = next;
        }

        if (preBegin != null) {
            if (midBegin != null) {
                preEnd.next = midBegin;
                if (nextBegin != null) {
                    midEnd.next = nextBegin;
                }
            } else if (nextBegin != null) {
                preEnd.next = nextBegin;
            }

        } else if (midBegin != null) {
            preBegin = midBegin;
            if (nextBegin != null) {
                midEnd.next = nextBegin;
            }
        } else if (nextBegin != null) {
            preBegin = nextBegin;
        }


        return preBegin;
    }

    public static NodeRandom copyRandomList(NodeRandom head) {
        //用作缓存
        HashMap<NodeRandom, NodeRandom> nodeRandomNodeRandomHashMap = new HashMap<>();
        NodeRandom temp = head;
        while (temp != null) {
            nodeRandomNodeRandomHashMap.put(temp, new NodeRandom(temp.val));
            temp = temp.next;
        }

        //赋值next
        temp = head;
        while (temp != null) {
            NodeRandom nodeRandom = nodeRandomNodeRandomHashMap.get(temp);
            nodeRandom.next = nodeRandomNodeRandomHashMap.get(temp.next);
            temp = temp.next;
        }

        //复制random
        temp = head;
        while (temp != null) {
            NodeRandom nodeRandom = nodeRandomNodeRandomHashMap.get(temp);
            nodeRandom.random = nodeRandomNodeRandomHashMap.get(temp.random);
            temp = temp.next;
        }
        return nodeRandomNodeRandomHashMap.get(head);
    }

    /**
     * 两个链表的第一个公共节点
     * <p>
     * 可能存在环
     */
    public static NodeS getIntersectionNode(NodeS headA, NodeS headB) {
        //有环
        NodeS cycleNode = getCycleNode(headA);
        if (cycleNode != null) return cycleNode;

        //无环
        NodeS A = headA, B = headB;
        int lenA = 0, lenB = 0;
        while (A.next != null) {
            A = A.next;
            lenA++;
        }
        while (B.next != null) {
            B = B.next;
            lenB++;
        }

        if (A != B) return null;

        //loop 大-小 大的先走 （大-小）的步数 ,默认A是大的
        int pre;
        if (lenA > lenB) {
            pre = lenA - lenB;
            A = headA;
            B = headB;
        } else {
            pre = lenB - lenA;
            A = headB;
            B = headA;
        }

        while (pre > 0) {
            A = A.next;
            pre--;
        }

        while (A != null) {
            if (A == B) return A;
            A = A.next;
            B = B.next;
        }
        return null;
    }

    //判断一个链表有环返回第一个入环节点 快慢指针
    public static NodeS getCycleNode(NodeS s) {
        if (s == null || s.next == null || s.next.next == null) return null;
        NodeS fast = s, low = s;
        //fast n ,low = m
        //fast是慢的两倍 n = 2m fast走过的距离为low+环的长度 n = m + k + r
        //也就是说2m = m + k + r =》 m = k + r
        //所以当fast和low相遇的时候，low走过的距离为k + r =》 k = r
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            low = low.next;
            if (low == fast) break;
        }

        fast = s;
        while (fast != null && low != null) {
            if (low == fast) return low;
            fast = fast.next;
            low = low.next;
        }

        return null;
    }

    //判断有环并且返回第一个成环节点（hash表）
    public static NodeS getCycleNode2(NodeS s) {
        NodeS nodeS = s;
        Set<NodeS> set = new HashSet();
        while (nodeS != null) {
            if (!set.contains(nodeS)) set.add(nodeS);
            else return nodeS;
            nodeS = nodeS.next;

        }
        return null;
    }

    public static NodeS removeElements(NodeS head, int val) {
        //有换头的可能所以需要虚拟节点
        NodeS dummyNode = new NodeS(-1);
        dummyNode.next = head;
        NodeS pre = dummyNode;

        //pre.next = cur
        while (pre.next != null) {
            if (pre.next.val == val) {
                pre.next = pre.next.next;
            } else {
                pre = pre.next;
            }
        }

        return dummyNode.next;
    }

    public static NodeS removeElementsDfs(NodeS head, int val) {
        if (head == null) return head;
        head.next = removeElementsDfs(head.next, val);
        return head.val == val ? head.next : head;
    }

    public static NodeS swapPairs(NodeS head) {
        if (head == null || head.next == null) return head;

        NodeS node = new NodeS(-1);
        node.next = head;

        NodeS pre = node;
        NodeS cur = head;
        NodeS next = head.next;

        while (cur != null && next != null) {
            cur.next = next.next;
            next.next = cur;
            pre.next = next;

            pre = cur;
            cur = cur.next;
            if (cur != null) next = cur.next;
        }
        return node.next;
    }

    public static NodeS rotateRight(NodeS head, int k) {
        if (k == 0 || head == null || head.next == null) return head;
        //记录一个锚点
        NodeS temp = head;
        int count = 0;

        while (temp != null) {
            count++;
            temp = temp.next;
        }
        if (k % count==0) return head;
        k =  count - k % count;
        temp = head;

        //寻找分开节点
        while (k-- > 1) {
            temp = temp.next;
        }
        NodeS next = temp.next, cpm = temp.next;
        NodeS end = temp;
        end.next = null;

        while (cpm.next != null) {
            cpm = cpm.next;
        }

        cpm.next = head;
        return next;
    }

    public static NodeS detectCycle(NodeS head) {
        NodeS fast = head, slow = head;
        while (true) {
            if (fast == null || fast.next == null) return null;
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) break;
        }
        fast = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        return fast;
    }

    public static void main(String[] args) {
//        NodeS nodeS = SinglyLinkedList.generateRandomLinkedList(10, 20, 10, 20);
//        SinglyLinkedList.printLinkedList(nodeS);
//        NodeS nodeS1 = unidirectionalLinkedListFlippingDfs(nodeS);
//        SinglyLinkedList.printLinkedList(nodeS1);
//
//        SinglyLinkedList.LogarithmicDevice(100, 10, 20, 10, 20
//                , new SLinkedList() {
//                    @Override
//                    public NodeS processNodeS(NodeS val) {
//                        return unidirectionalLinkedListFlipping(val);
//                    }
//                }, new SLinkedList() {
//                    @Override
//                    public NodeS processNodeS(NodeS val) {
//                        return unidirectionalLinkedListFlippingDfs(val);
//                    }
//                });
//
//        NodeS nodeS2 = SinglyLinkedList.generateRandomLinkedList(10, 20, 10, 20);
//        NodeS nodeS3 = SinglyLinkedList.generateRandomLinkedList(10, 20, 10, 20);
//        SinglyLinkedList.printLinkedList(nodeS2);
//        SinglyLinkedList.printLinkedList(nodeS3);
//        NodeS nodeS4 = heBing(nodeS2, nodeS3);
//        SinglyLinkedList.printLinkedList(nodeS4);
//
//        //回文链表
//        NodeS s = new NodeS(1, new NodeS(1, new NodeS(2, new NodeS(1))));
//        System.out.println(isPalindrome(s));
//        NodeS s1 = new NodeS(1, new NodeS(2, new NodeS(3, new NodeS(2, new NodeS(1)))));
//        System.out.println(isPalindrome(s1));

//        System.out.println("=======================分隔链表=========================");
//        //分隔链表
//        NodeS s2 = SinglyLinkedList.generateRandomLinkedList(10, 20, 1, 20);
//        SinglyLinkedList.printLinkedList(s2);//12 12 6 3 11
//        NodeS partition = partition(s2,10);
//        NodeS partition2 = partitionTwo(s2, 10);
//        SinglyLinkedList.printLinkedList(partition);
//        SinglyLinkedList.printLinkedList(partition2);
//
//        //使用NodeS 15 5 2 18 4 12 8 5 16 7
//        NodeS s = new NodeS(15, new NodeS(5, new NodeS(2, new NodeS(18, new NodeS(4, new NodeS(12, new NodeS(8, new NodeS(5, new NodeS(16, new NodeS(7))))))))));
//        SinglyLinkedList.printLinkedList(s);
//        NodeS partition1 = partitionTwo(s, 10);
//        SinglyLinkedList.printLinkedList(partition1);

//        System.out.println("=======================复制带随机指针的链表=========================");
//        //复制带随机指针的链表
//        NodeRandom nodeRandom = new NodeRandom(7);
//        NodeRandom nodeRandom1 = new NodeRandom(13);
//        NodeRandom nodeRandom2 = new NodeRandom(11);
//        NodeRandom nodeRandom3 = new NodeRandom(10);
//        NodeRandom nodeRandom4 = new NodeRandom(1);
//        nodeRandom.next = nodeRandom1;
//        nodeRandom1.next = nodeRandom2;
//        nodeRandom2.next = nodeRandom3;
//        nodeRandom3.next = nodeRandom4;
//        nodeRandom1.random = nodeRandom;
//        nodeRandom2.random = nodeRandom4;
//        nodeRandom3.random = nodeRandom2;
//        RandomLinkedList.printLinkedList(nodeRandom);
//        NodeRandom nodeRandom5 = copyRandomList(nodeRandom);
//        RandomLinkedList.printLinkedList(nodeRandom5);
//        System.out.println(nodeRandom==nodeRandom5);

//        System.out.println("=======================两个链表的第一个公共节点=========================");
//        //两个链表的第一个公共节点
//        NodeS s = new NodeS(1, new NodeS(8, new NodeS(4, new NodeS(5))));
//        NodeS nodeS = new NodeS(4, s);
//        NodeS nodeS1 = new NodeS(5, new NodeS(0, s));
//        SinglyLinkedList.printLinkedList(nodeS);
//        SinglyLinkedList.printLinkedList(nodeS1);
//
//        //成环
//        NodeS nodeS2 = new NodeS(1, new NodeS(2, new NodeS(3, new NodeS(4, new NodeS(5)))));
//        nodeS2.next.next.next.next.next = nodeS2.next.next;
//        NodeS cycleNode = getCycleNode(nodeS2);
//        System.out.println(cycleNode.val);
//        NodeS cycleNode2 = getCycleNode2(nodeS2);
//        System.out.println(cycleNode2.val);
//
//        NodeS intersectionNode = getIntersectionNode(nodeS, nodeS1);
//        System.out.println(intersectionNode.val);
//
//        NodeS intersectionNode2 = getIntersectionNode(null, null);

//        System.out.println("=======================移除链表元素=========================");
//        //移除链表元素
//        NodeS sRemoveElements = SinglyLinkedList.generateRandomLinkedList(30, 40, 1, 10);
//        SinglyLinkedList.printLinkedList(sRemoveElements);
//        NodeS nodeS = removeElements(sRemoveElements, 5);
//        SinglyLinkedList.printLinkedList(nodeS);

//        System.out.println("=======================两两交换链表中的节点=========================");
//        //两两交换链表中的节点
//        NodeS n1 = new NodeS(1, new NodeS(2, new NodeS(3, new NodeS(4))));
//        NodeS s = swapPairs(n1);
//
//        System.out.println("=======================旋转链表=========================");
//        //旋转链表
//        NodeS n2 = new NodeS(1, new NodeS(2, new NodeS(3, new NodeS(4, new NodeS(5)))));
//        NodeS nodeS = rotateRight(n2, 2);
//
//        NodeS nodeS2 = rotateRight(null, 0);
//
//        NodeS nodeS3 = rotateRight(new NodeS(1, new NodeS(2)), 2);
        System.out.println("=======================环形链表 II=========================");
        //环形链表 II
        NodeS n3 = new NodeS(3, new NodeS(2, new NodeS(0, new NodeS(-4))));
        n3.next.next.next.next = n3.next;
        NodeS nodeS = detectCycle(n3);

        NodeS n4 = new NodeS(1);
        NodeS nodeS2 = detectCycle(n4);
    }
}
