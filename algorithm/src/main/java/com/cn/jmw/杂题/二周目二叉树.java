package com.cn.jmw.杂题;

import org.junit.Test;

import java.util.*;

public class 二周目二叉树 {

    //前序遍历
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> list = new ArrayList<Integer>();
        preorder(list, root);
        return list;
    }

    public void preorder(List<Integer> list, TreeNode root) {
        if (root == null) return;

        list.add(root.val);
        preorder(list, root.left);
        preorder(list, root.right);
    }

    //中序遍历
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        dfsInorderTraversal(root, list);
        return list;
    }

    public void dfsInorderTraversal(TreeNode root, List<Integer> list) {
        if (root == null) {
            return;
        }

        dfsInorderTraversal(root.left, list);
        list.add(root.val);
        dfsInorderTraversal(root.right, list);
    }

    //后序遍历
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> list = new ArrayList<Integer>();
        postOrder(list, root);
        return list;
    }

    public void postOrder(List<Integer> list, TreeNode root) {
        if (root == null) return;

        postOrder(list, root.left);
        postOrder(list, root.right);
        list.add(root.val);
    }

    //二叉树的层序遍历
    public List<List<Integer>> levelOrder(TreeNode root) {
        if (root == null) return new ArrayList<>();
        //广度优先搜索树
        List<List<Integer>> lists = new ArrayList<>();

        //准备队列
        Queue<TreeNode> queue = new ArrayDeque();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int n = queue.size();
            List<Integer> level = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                TreeNode poll = queue.poll();
                level.add(poll.val);
                if (poll.left != null) {
                    queue.offer(poll.left);
                }
                if (poll.right != null) {
                    queue.offer(poll.right);
                }
            }
            lists.add(level);
        }

        return lists;
    }

    //103. 二叉树的锯齿形层序遍历
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> ans = new LinkedList<List<Integer>>();
        if (root == null) {
            return ans;
        }

        Queue<TreeNode> nodeQueue = new ArrayDeque<TreeNode>();
        nodeQueue.offer(root);
        boolean isOrderLeft = true;

        while (!nodeQueue.isEmpty()) {
            Deque<Integer> levelList = new LinkedList<Integer>();
            int size = nodeQueue.size();
            for (int i = 0; i < size; ++i) {
                TreeNode curNode = nodeQueue.poll();
                if (isOrderLeft) {
                    levelList.offerLast(curNode.val);
                } else {
                    levelList.offerFirst(curNode.val);
                }
                if (curNode.left != null) {
                    nodeQueue.offer(curNode.left);
                }
                if (curNode.right != null) {
                    nodeQueue.offer(curNode.right);
                }
            }
            ans.add(new LinkedList<Integer>(levelList));
            isOrderLeft = !isOrderLeft;
        }

        return ans;
    }


    @Test
    public void test(){
        二周目二叉树 二周目二叉树 = new 二周目二叉树();
        TreeNode treeNode = new TreeNode(1, new TreeNode(2, new TreeNode(4), null), new TreeNode(3, null, new TreeNode(5)));
        System.out.println(二周目二叉树.zigzagLevelOrder(treeNode));
    }
}


class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {
    }

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}