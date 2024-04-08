package com.cn.jmw.华为C卷.一百分;

import java.util.*;

/**
 * ╭—————————————————————————————╮
 * │ 当前时间：2024-04-07 21:19:09 │
 * │ 完成时间：00:25:53            │
 * ╰—————————————————————————————╯
 * ╭—————————————————————————————╮
 * │ 当前时间：2024-04-07 16:54:43 │
 * │ 完成时间：00:08:45            │
 * ╰—————————————————————————————╯
 * 并查集
 */
public class WeAreATeamX并查集 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (sc.hasNext()) {
            int n = sc.nextInt();
            int m = sc.nextInt();

            if (n < 1 || m >= 100000) {
                System.out.println("NULL");
                return;
            }

            int[][] msgs = new int[m][3];
            for (int i = 0; i < m; i++) {
                msgs[i][0] = sc.nextInt();
                msgs[i][1] = sc.nextInt();
                msgs[i][2] = sc.nextInt();
            }

            UnionFindSet unionFindSet = new UnionFindSet(n);

            //并查集
            for (int i = 0; i < m; i++) {
                if (msgs[i][0] < 1 || msgs[i][0] > n || msgs[i][1] < 1 || msgs[i][1] > n) {
                    System.out.println("da pian zi");
                }

                if (msgs[i][2] == 0) {
                    unionFindSet.union(msgs[i][0], msgs[i][1]);
                } else if (msgs[i][2] == 1) {
                    if (unionFindSet.isSameSet(msgs[i][0], msgs[i][1])) {
                        System.out.println("we are a team");
                    } else {
                        System.out.println("we are not a team");
                    }
                } else {
                    System.out.println("da pian zi");
                }
            }
        }
    }

    static class UnionFindSet {
        //比如 index位1 他指向1，他的层数为1
        private int[] parent;

        public UnionFindSet(int n) {
            parent = new int[n+1];
            for (int i = 0; i < n+1; i++) {
                parent[i] = i;
            }
        }

        // find方法是并查集的核心，用于查找元素x的根节点
        public int find(int x) {
            if (x != parent[x]) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }

        // union方法是并查集的核心，用于合并两个元素x和y
        public void union(int x, int y) {
            parent[find(x)] = find(y);
        }

        // isConnected方法是并查集的核心，用于判断两个元素x和y是否属于同一个集合
        public boolean isSameSet(int x, int y) {
            return find(x) == find(y);
        }
    }
}
