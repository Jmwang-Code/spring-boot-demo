package allAlgorithm.BmodelLinkedList;

import org.junit.Test;

import java.util.List;

public class 链表二周目 {

    // 单链表节点
    private class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    // 双向链表节点
    private class DoubleListNode {
        int val;
        DoubleListNode pre;
        DoubleListNode next;

        DoubleListNode(int x) {
            val = x;
        }
    }

    //顺序输出链表的打印方法
    public void printList(ListNode head) {
        ListNode node = head;
        while (node != null) {
            System.out.print(node.val + " ");
            node = node.next;
        }
        System.out.println();
    }

    // 206. 反转链表
    public ListNode reverseListI(ListNode head) {
        ListNode pre = null;
        ListNode cur = head;
        while (cur!=null){
            ListNode next = cur.next;
            cur.next = pre;

            pre = cur;
            cur = next;
        }

        return pre;
    }

    // 206. 反转链表
    @Test
    public void testReverseListI() {
        ListNode head = new ListNode(1);
        ListNode node = head;
        for (int i = 2; i <= 5; i++) {
            node.next = new ListNode(i);
            node = node.next;
        }
        ListNode node1 = reverseListI(head);
        printList(node1);

        //再来一个案例
        ListNode head2 = new ListNode(1);
        ListNode node2 = head2;
        for (int i = 2; i <= 6; i++) {
            node2.next = new ListNode(i);
            node2 = node2.next;
        }
        ListNode node3 = reverseListI(head2);
        printList(node3);

        //再来测试边界的案例
        ListNode head3 = new ListNode(1);
        ListNode node4 = head3;
        for (int i = 2; i <= 1; i++) {
            node4.next = new ListNode(i);
            node4 = node4.next;
        }
        ListNode node5 = reverseListI(head3);
        printList(node5);

    }

    // 92. 反转链表 II
    public ListNode reverseBetween(ListNode head,  int left, int right) {
        // 设置 dummyNode 是这一类问题的一般做法
        ListNode dummyNode = new ListNode(-1);
        dummyNode.next = head;
        ListNode pre = dummyNode;
        for (int i = 0; i < left - 1; i++) {
            pre = pre.next;
        }
        ListNode cur = pre.next;

        ListNode next;
        for (int i = 0; i < right - left; i++) {
            next = cur.next;
            cur.next = next.next;
            next.next = pre.next;
            pre.next = next;
        }

        return dummyNode.next;
    }

    // 92. 反转链表 II
    @Test
    public void testReverseBetween() {
        ListNode head = new ListNode(1);
        ListNode node = head;
        for (int i = 2; i <= 5; i++) {
            node.next = new ListNode(i);
            node = node.next;
        }
        ListNode node1 = reverseBetween(head, 2, 4);
        printList(node1);

        //再来一个案例
        ListNode head2 = new ListNode(1);
        ListNode node2 = head2;
        for (int i = 2; i <= 6; i++) {
            node2.next = new ListNode(i);
            node2 = node2.next;
        }
        ListNode node3 = reverseBetween(head2, 2, 5);
        printList(node3);

        //再来测试边界的案例
        ListNode head3 = new ListNode(1);
        ListNode node4 = head3;
        for (int i = 2; i <= 1; i++) {
            node4.next = new ListNode(i);
            node4 = node4.next;
        }
        ListNode node5 = reverseBetween(head3, 1, 1);
        printList(node5);
    }

    // 21. 合并两个有序链表
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        return null;
    }

    // 21. 合并两个有序链表
    @Test
    public void testMergeTwoLists() {
        ListNode head1 = new ListNode(1);
        ListNode node1 = head1;
        for (int i = 3; i <= 5; i += 2) {
            node1.next = new ListNode(i);
            node1 = node1.next;
        }
        ListNode head2 = new ListNode(2);
        ListNode node2 = head2;
        for (int i = 4; i <= 6; i += 2) {
            node2.next = new ListNode(i);
            node2 = node2.next;
        }
        ListNode node = mergeTwoLists(head1, head2);
        printList(node);

        //再来一个案例
        ListNode head3 = new ListNode(1);
        ListNode node3 = head3;
        for (int i = 3; i <= 5; i += 2) {
            node3.next = new ListNode(i);
            node3 = node3.next;
        }
        ListNode head4 = new ListNode(2);
        ListNode node4 = head4;
        for (int i = 4; i <= 6; i += 2) {
            node4.next = new ListNode(i);
            node4 = node4.next;
        }
        ListNode node5 = mergeTwoLists(head3, head4);
        printList(node5);

        //再来测试边界的案例
        ListNode head5 = new ListNode(1);
        ListNode node6 = head5;
        for (int i = 3; i <= 5; i += 2) {
            node6.next = new ListNode(i);
            node6 = node6.next;
        }
        ListNode head6 = null;
        ListNode node7 = mergeTwoLists(head5, head6);
        printList(node7);

    }

    // 83. 删除排序链表中的重复元素
    public ListNode deleteDuplicates(ListNode head) {
        return null;
    }

    // 83. 删除排序链表中的重复元素
    @Test
    public void testDeleteDuplicates() {
        ListNode head = new ListNode(1);
        ListNode node = head;
        for (int i = 1; i <= 5; i++) {
            node.next = new ListNode(i);
            node = node.next;
        }
        ListNode node1 = deleteDuplicates(head);
        printList(node1);

        //再来一个案例
        ListNode head2 = new ListNode(1);
        ListNode node2 = head2;
        for (int i = 1; i <= 6; i++) {
            node2.next = new ListNode(i);
            node2 = node2.next;
        }
        ListNode node3 = deleteDuplicates(head2);
        printList(node3);

        //再来测试边界的案例
        ListNode head3 = new ListNode(1);
        ListNode node4 = head3;
        for (int i = 1; i <= 1; i++) {
            node4.next = new ListNode(i);
            node4 = node4.next;
        }
        ListNode node5 = deleteDuplicates(head3);
        printList(node5);
    }

    // 141. 环形链表
    public boolean hasCycle(ListNode head) {
        return false;
    }

    // 141. 环形链表
    @Test
    public void testHasCycle() {
        ListNode head = new ListNode(1);
        ListNode node = head;
        for (int i = 2; i <= 5; i++) {
            node.next = new ListNode(i);
            node = node.next;
        }
        node.next = head;
        boolean b = hasCycle(head);
        System.out.println(b);

        //再来一个案例
        ListNode head2 = new ListNode(1);
        ListNode node2 = head2;
        for (int i = 2; i <= 6; i++) {
            node2.next = new ListNode(i);
            node2 = node2.next;
        }
        node2.next = head2;
        boolean b2 = hasCycle(head2);
        System.out.println(b2);

        //再来测试边界的案例
        ListNode head3 = new ListNode(1);
        ListNode node3 = head3;
        for (int i = 2; i <= 1; i++) {
            node3.next = new ListNode(i);
            node3 = node3.next;
        }
        boolean b3 = hasCycle(head3);
        System.out.println(b3);
    }

    // 160. 相交链表
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        return null;
    }

    // 160. 相交链表
    @Test
    public void testGetIntersectionNode() {
        ListNode head1 = new ListNode(1);
        ListNode node1 = head1;
        for (int i = 2; i <= 5; i++) {
            node1.next = new ListNode(i);
            node1 = node1.next;
        }
        ListNode head2 = new ListNode(1);
        ListNode node2 = head2;
        for (int i = 2; i <= 5; i++) {
            node2.next = new ListNode(i);
            node2 = node2.next;
        }
        ListNode node = getIntersectionNode(head1, head2);
        System.out.println(node == null ? "null" : node.val);

        //再来一个案例
        ListNode head3 = new ListNode(1);
        ListNode node3 = head3;
        for (int i = 2; i <= 5; i++) {
            node3.next = new ListNode(i);
            node3 = node3.next;
        }
        ListNode head4 = new ListNode(1);
        ListNode node4 = head4;
        for (int i = 2; i <= 6; i++) {
            node4.next = new ListNode(i);
            node4 = node4.next;
        }
        ListNode node5 = getIntersectionNode(head3, head4);
        System.out.println(node5 == null ? "null" : node5.val);

        //再来测试边界的案例
        ListNode head5 = new ListNode(1);
        ListNode node6 = head5;
        for (int i = 2; i <= 5; i++) {
            node6.next = new ListNode(i);
            node6 = node6.next;
        }
        ListNode head6 = null;
        ListNode node7 = getIntersectionNode(head5, head6);
        System.out.println(node7 == null ? "null" : node7.val);
    }

    // 203. 移除链表元素
    public ListNode removeElements(ListNode head, int val) {
        return null;
    }

    // 203. 移除链表元素
    @Test
    public void testRemoveElements() {
        ListNode head = new ListNode(1);
        ListNode node = head;
        for (int i = 2; i <= 5; i++) {
            node.next = new ListNode(i);
            node = node.next;
        }
        ListNode node1 = removeElements(head, 3);
        printList(node1);

        //再来一个案例
        ListNode head2 = new ListNode(1);
        ListNode node2 = head2;
        for (int i = 2; i <= 6; i++) {
            node2.next = new ListNode(i);
            node2 = node2.next;
        }
        ListNode node3 = removeElements(head2, 6);
        printList(node3);

        //再来测试边界的案例
        ListNode head3 = new ListNode(1);
        ListNode node4 = head3;
        for (int i = 2; i <= 1; i++) {
            node4.next = new ListNode(i);
            node4 = node4.next;
        }
        ListNode node5 = removeElements(head3, 1);
        printList(node5);
    }

    // 234. 回文链表
    public boolean isPalindrome(ListNode head) {
        return false;
    }

    // 234. 回文链表
    @Test
    public void testIsPalindrome() {
        ListNode head = new ListNode(1);
        ListNode node = head;
        for (int i = 2; i <= 5; i++) {
            node.next = new ListNode(i);
            node = node.next;
        }
        boolean b = isPalindrome(head);
        System.out.println(b);

        //再来一个案例
        ListNode head2 = new ListNode(1);
        ListNode node2 = head2;
        for (int i = 2; i <= 6; i++) {
            node2.next = new ListNode(i);
            node2 = node2.next;
        }
        boolean b2 = isPalindrome(head2);
        System.out.println(b2);

        //再来测试边界的案例
        ListNode head3 = new ListNode(1);
        ListNode node3 = head3;
        for (int i = 2; i <= 1; i++) {
            node3.next = new ListNode(i);
            node3 = node3.next;
        }
        boolean b3 = isPalindrome(head3);
        System.out.println(b3);
    }

    // LCR 141. 训练计划 III
    public int minStartValue(int[] nums) {
        return 0;
    }

    // LCR 141. 训练计划 III
    @Test
    public void testMinStartValue() {
        int[] nums = {1, 2};
        int i = minStartValue(nums);
        System.out.println(i);

        //再来一个案例
        int[] nums2 = {1, -2, 3};
        int i2 = minStartValue(nums2);
        System.out.println(i2);

        //再来测试边界的案例
        int[] nums3 = {1, -2, 3, 4};
        int i3 = minStartValue(nums3);
        System.out.println(i3);
    }

    // LCR 136. 删除链表的节点
    public ListNode deleteNode(ListNode head, int val) {
        return null;
    }

    // LCR 136. 删除链表的节点
    @Test
    public void testDeleteNode() {
        ListNode head = new ListNode(1);
        ListNode node = head;
        for (int i = 2; i <= 5; i++) {
            node.next = new ListNode(i);
            node = node.next;
        }
        ListNode node1 = deleteNode(head, 3);
        printList(node1);

        //再来一个案例
        ListNode head2 = new ListNode(1);
        ListNode node2 = head2;
        for (int i = 2; i <= 6; i++) {
            node2.next = new ListNode(i);
            node2 = node2.next;
        }
        ListNode node3 = deleteNode(head2, 6);
        printList(node3);

        //再来测试边界的案例
        ListNode head3 = new ListNode(1);
        ListNode node4 = head3;
        for (int i = 2; i <= 1; i++) {
            node4.next = new ListNode(i);
            node4 = node4.next;
        }
        ListNode node5 = deleteNode(head3, 1);
        printList(node5);
    }


}
