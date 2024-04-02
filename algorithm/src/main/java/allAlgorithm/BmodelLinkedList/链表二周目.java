package allAlgorithm.BmodelLinkedList;

import allAlgorithm.CmodelBinaryTree.TreeNode;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class 链表二周目 {

    // 单链表节点
    private class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }

        ListNode(int x, ListNode next) {
            val = x;
            this.next = next;
        }

        //打印链表
        public void printList(ListNode head) {
            ListNode node = head;
            while (node != null) {
                System.out.print(node.val + " ");
                node = node.next;
            }
            System.out.println();
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
        while (cur != null) {
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
    public ListNode reverseBetween(ListNode head, int left, int right) {
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
        ListNode listNode = new ListNode(-1);

        ListNode cur = listNode;
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                cur.next = l1;
                ListNode next = l1.next;
                l1.next = null;
                l1 = next;
            } else {
                cur.next = l2;
                ListNode next = l2.next;
                l2.next = null;
                l2 = next;
            }
            cur = cur.next;
        }

        if (l1 != null) {
            cur.next = l1;
        }
        if (l2 != null) {
            cur.next = l2;
        }

        return listNode.next;
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
        if (head == null) return head;
        ListNode listNode = new ListNode(-1);
        listNode.next = head;

        ListNode pre = head;
        ListNode cur = head.next;

        while (cur != null) {
            ListNode next = cur.next;
            if (pre.val == cur.val) {
                pre.next = next;
                cur = next;
            } else {
                pre = cur;
                cur = next;
            }
        }
        return listNode.next;
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
        if (head == null || head.next == null) {
            return false;
        }
        ListNode fast = head;
        ListNode slow = head;

        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;

            if (fast == slow) return true;
        }

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
        if (headA == null || headB == null) {
            return null;
        }
        ListNode pA = headA, pB = headB;
        while (pA != pB) {
            pA = pA == null ? headB : pA.next;
            pB = pB == null ? headA : pB.next;
        }
        return pA;
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
        ListNode dummyHead = new ListNode(0);
        dummyHead.next = head;
        ListNode temp = dummyHead;
        while (temp.next != null) {
            if (temp.next.val == val) {
                temp.next = temp.next.next;
            } else {
                temp = temp.next;
            }
        }
        return dummyHead.next;
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
        if (head == null) {
            return true;
        }

        // 找到前半部分链表的尾节点并反转后半部分链表
        ListNode firstHalfEnd = endOfFirstHalf(head);
        ListNode secondHalfStart = reverseList(firstHalfEnd.next);

        // 判断是否回文
        ListNode p1 = head;
        ListNode p2 = secondHalfStart;
        boolean result = true;
        while (result && p2 != null) {
            if (p1.val != p2.val) {
                result = false;
            }
            p1 = p1.next;
            p2 = p2.next;
        }

        // 还原链表并返回结果
        firstHalfEnd.next = reverseList(secondHalfStart);
        return result;
    }

    private ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;
        while (curr != null) {
            ListNode nextTemp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nextTemp;
        }
        return prev;
    }

    private ListNode endOfFirstHalf(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;
        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
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

    // 876. 链表的中间结点
    public ListNode middleNode(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow;
    }

    // 876. 链表的中间结点
    @Test
    public void testMiddleNode() {
        ListNode head = new ListNode(1);
        ListNode node = head;
        for (int i = 2; i <= 5; i++) {
            node.next = new ListNode(i);
            node = node.next;
        }
        ListNode node1 = middleNode(head);
        System.out.println(node1.val);

        //再来一个案例
        ListNode head2 = new ListNode(1);
        ListNode node2 = head2;
        for (int i = 2; i <= 6; i++) {
            node2.next = new ListNode(i);
            node2 = node2.next;
        }
        ListNode node3 = middleNode(head2);
        System.out.println(node3.val);

        //再来测试边界的案例
        ListNode head3 = new ListNode(1);
        ListNode node4 = head3;
        for (int i = 2; i <= 1; i++) {
            node4.next = new ListNode(i);
            node4 = node4.next;
        }
        ListNode node5 = middleNode(head3);
        System.out.println(node5.val);
    }

    //725. 分隔链表
    public ListNode[] splitListToParts(ListNode root, int k) {
        //剪断连接 存入List
        List<ListNode> listNodes = new ArrayList<>();
        ListNode node = root;
        while (node != null) {
            listNodes.add(node);
            ListNode next = node.next;
            node.next = null;
            node = next;
        }

        //计算每个链表的长度
        int len = listNodes.size();
        int avg = len / k;
        int mod = len % k;


        ListNode[] res = new ListNode[k];
        for (int i = 0; i < k; i++) {
            ListNode head = new ListNode(-1);
            ListNode cur = head;
            for (int j = 0; j < avg + (i < mod ? 1 : 0); j++) {
                cur.next = listNodes.remove(0);
                cur = cur.next;
            }
            res[i] = head.next;
        }

        return res;
    }

    //725. 分隔链表
    @Test
    public void testSplitListToParts() {
        //head = [1,2,3], k = 5
        ListNode head = new ListNode(1);
        ListNode node = head;
        for (int i = 2; i <= 3; i++) {
            node.next = new ListNode(i);
            node = node.next;
        }
        ListNode[] listNodes = splitListToParts(head, 5);

        //head = [1,2,3,4,5,6,7,8,9,10], k = 3
        ListNode head2 = new ListNode(1);
        ListNode node2 = head2;
        for (int i = 2; i <= 10; i++) {
            node2.next = new ListNode(i);
            node2 = node2.next;
        }
        ListNode[] listNodes2 = splitListToParts(head2, 3);

    }

    //109. 有序链表转换二叉搜索树
    public TreeNode sortedListToBST(ListNode head) {
        if (head == null) return null;
        if (head.next == null) return new TreeNode(head.val);
        ListNode cur = fastSlow(head);

        TreeNode node = new TreeNode(cur.val);
        node.left = sortedListToBST(head);
        node.right = sortedListToBST(cur.next);

        return node;
    }

    public ListNode fastSlow(ListNode head) {
        if (head == null) return null;
        if (head.next == null) return head;
        ListNode fast = head;
        ListNode slow = head;

        ListNode slowPre = null;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slowPre = slow;
            slow = slow.next;
        }
        slowPre.next = null;

        return slow;
    }

    //109. 有序链表转换二叉搜索树
    @Test
    public void testSortedListToBST() {
        ListNode head = new ListNode(1);
        ListNode node = head;
        for (int i = 2; i <= 5; i++) {
            node.next = new ListNode(i);
            node = node.next;
        }
        TreeNode treeNode = sortedListToBST(head);
        treeNode.printTree(treeNode);

        //head = [-10,-3,0,5,9]
        ListNode head2 = new ListNode(-10);
        ListNode node2 = head2;
        for (int i = -3; i <= 9; i += 3) {
            node2.next = new ListNode(i);
            node2 = node2.next;
        }
        TreeNode treeNode2 = sortedListToBST(head2);
        treeNode.printTree(treeNode2);

        //head = []
        ListNode head3 = null;
        TreeNode treeNode3 = sortedListToBST(head3);
        treeNode.printTree(treeNode3);

    }

    //147. 对链表进行插入排序
    public ListNode insertionSortList(ListNode head) {
        // 1. 首先判断给定的链表是否为空，若为空，则不需要进行排序，直接返回。
        if (head == null) {
            return head;
        }

        // 2. 链表初始化操作
        ListNode dummyHead = new ListNode(0); // 引入哑节点
        dummyHead.next = head;                // 目的是在head之前插入节点
        ListNode lastSorted = head;           // 维护lastSorted为链表已经排好序的最后一个节点并初始化
        ListNode curr = head.next;            // 维护curr 为待插入的元素并初始化

        // 3. 插入排序
        while (curr != null) {
            if (lastSorted.val <= curr.val) {     // 说明curr应该位于lastSorted之后
                lastSorted = lastSorted.next;   // 将lastSorted后移一位,curr变成新的lastSorted
            } else {                              // 否则,从链表头结点开始向后遍历链表中的节点
                ListNode prev = dummyHead;      // 从链表头开始遍历 prev是插入节点curr位置的前一个节点
                while (prev.next.val <= curr.val) { // 循环退出的条件是找到curr应该插入的位置
                    prev = prev.next;
                }
                // 以下三行是为了完成对curr的插入（配合题解动图可以直观看出）
                lastSorted.next = curr.next;
                curr.next = prev.next;
                prev.next = curr;
            }
            curr = lastSorted.next; // 此时 curr 为下一个待插入的元素
        }
        // 返回排好序的链表
        return dummyHead.next;
    }

    // 147. 对链表进行插入排序
    @Test
    public void testInsertionSortList() {
        //head = [4,2,1,3]
        ListNode head = new ListNode(4);
        head.next = new ListNode(2, new ListNode(1, new ListNode(3)));
        ListNode node = head;
        ListNode node1 = insertionSortList(node);
        printList(node1);

        //head = [-1,5,3,4,0]
        ListNode head2 = new ListNode(-1);
        head2.next = new ListNode(5, new ListNode(3, new ListNode(4, new ListNode(0))));
        ListNode node2 = head2;
        ListNode node3 = insertionSortList(node2);
        printList(node3);
    }

    //148. 排序链表
    public ListNode sortList(ListNode head) {
        ListNode node = head;

        int count = 0;
        while (node!=null){
            count++;
            node = node.next;
        }
        int[] arr =new int[count];
        node = head;

        for (int i = 0; i < arr.length; i++) {
            arr[i] = node.val;
            node = node.next;
        }
        Arrays.sort(arr);
        ListNode tempNode = new ListNode(0);
        ListNode re = tempNode;
        for (int i = 0; i < arr.length; i++) {
            tempNode.next = new ListNode(arr[i]);
            tempNode = tempNode.next;
        }

        return re.next;
    }

    //148. 排序链表
    @Test
    public void testSortList() {
        //head = [4,2,1,3]
        ListNode head = new ListNode(4);
        head.next = new ListNode(2, new ListNode(1, new ListNode(3)));
        ListNode node = head;
        ListNode node1 = sortList(node);
        printList(node1);

        //head = [-1,5,3,4,0]
        ListNode head2 = new ListNode(-1);
        head2.next = new ListNode(5, new ListNode(3, new ListNode(4, new ListNode(0))));
        ListNode node2 = head2;
        ListNode node3 = sortList(node2);
        printList(node3);
    }

    //数组快排
    public void quickSort(int[] arr, int left, int right) {
        if (left < right) {
            int i = left, j = right, x = arr[left];
            while (i < j) {
                while (i < j && arr[j] >= x) {
                    j--;
                }
                if (i < j) {
                    arr[i++] = arr[j];
                }
                while (i < j && arr[i] < x) {
                    i++;
                }
                if (i < j) {
                    arr[j--] = arr[i];
                }
            }
            arr[i] = x;
            quickSort(arr, left, i - 1);
            quickSort(arr, i + 1, right);
        }
    }

    //328. 奇偶链表
    public ListNode oddEvenList(ListNode head) {
        ListNode preF = head;
        ListNode pre = new ListNode(-1);
        ListNode next = new ListNode(-1);
        ListNode end = next;
        boolean logo = true;
        while (preF!=null){
            if (logo){
                pre.next = preF;
                pre = pre.next;
                logo = false;
            }else {
                next.next = preF;
                next = next.next;
                logo = true;
            }
            preF = preF.next;
        }

        next.next = null;
        pre.next = end.next;

        return head;
    }

    //328. 奇偶链表
    @Test
    public void testOddEvenList() {
        //head = [1,2,3,4,5]
        ListNode head = new ListNode(1);
        head.next = new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5))));
        ListNode node = head;
        ListNode node1 = oddEvenList(node);
        printList(node1);

        //head = [2,1,3,5,6,4,7]
        ListNode head2 = new ListNode(2);
        head2.next = new ListNode(1, new ListNode(3, new ListNode(5, new ListNode(6, new ListNode(4, new ListNode(7))))));
        ListNode node2 = head2;
        ListNode node3 = oddEvenList(node2);
        printList(node3);
    }
}
