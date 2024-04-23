package com.cn.jmw.华为C卷.一百分;

import java.util.Scanner;

public class 计算三叉搜索树的高度_X_生成树 {

    static class TreeNode {
        int val; // 节点值
        int height; // 节点所在高度
        TreeNode left; // 左子树
        TreeNode mid; // 中子树
        TreeNode right; // 右子树

        public TreeNode(int val) {
            this.val = val;
        }
    }

    static class Tree{
        TreeNode root;
        int height;

        public void add(int val){
            //新添加的三叉树
            TreeNode node = new TreeNode(val);

            //根元素不存在,当前元素就是根
            if (root==null){
                node.height = 1;
                this.root = node;
                this.root.height = 1;
            }else {
                //不是根节点
                //记录当前的位置
                TreeNode cur = this.root;


                while (true){
//                    //进入下一层需要高度+1
                    node.height = cur.height + 1;
//                    //更新根树高度
                    this.height = Math.max(node.height, this.height);

                    //左子树
                    if (node.val+500<cur.val){
                        //停止探索,继续下一层
                       if (cur.left==null){
                           cur.left = node;
                           break;
                       }else {
                           cur = cur.left;
                       }
                    }else if (node.val-500>cur.val){
                        if (cur.right==null){
                            cur.right = node;
                            break;
                        }else {
                            cur = cur.right;
                        }
                    }else {
                        if (cur.mid==null){
                            cur.mid = node;
                            break;
                        }else {
                            cur = cur.mid;
                        }
                    }
                }
            }
        }

    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        Tree tree = new Tree();
        for (int i = 0; i < n; i++) {
            tree.add(scanner.nextInt());
        }
        System.out.println(tree.height);
    }

}
