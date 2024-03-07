package com.cn.jmw.递归算法.动态规划;

/**
 * 小朋友 A 在和 ta 的小伙伴们玩传信息游戏，游戏规则如下：
 *
 * 有 n 名玩家，所有玩家编号分别为 0 ～ n-1，其中小朋友 A 的编号为 0
 * 每个玩家都有固定的若干个可传信息的其他玩家（也可能没有）。传信息的关系是单向的（比如 A 可以向 B 传信息，但 B 不能向 A 传信息）。
 * 每轮信息必须需要传递给另一个人，且信息可重复经过同一个人
 * 给定总玩家数 n，以及按 [玩家编号,对应可传递玩家编号] 关系组成的二维数组 relation。返回信息从小 A (编号 0 ) 经过 k 轮传递到编号为 n-1 的小伙伴处的方案数；若不能到达，返回 0。
 *
 *
 * 输入：n = 5, relation = [[0,2],[2,1],[3,4],[2,3],[1,4],[2,0],[0,4]], k = 3
 *
 * 输出：3
 ***********************|
 * 输入：n = 3, relation = [[0,2],[2,1]], k = 2
 *
 * 输出：0
 */
public class LCP07传递信息 {

    /**
     * 常式
     */
    public int numWays(int n, int[][] relation, int k) {
        return way(n,relation,k,0);
    }

    //有N个玩家
    //有向箭头
    //还剩几步
    //当前位置
    public int way(int n, int[][] relation, int k,int cur){
        //如果K==0了说明结束了,cur不等于n-1说明返回0
        if (k==0){
            return n-1 == cur?1:0;
        }

        int sum = 0;
        for (int i = 0; i < relation.length; i++) {
            if (relation[i][0]==cur){
                sum+=way(n,relation,k-1,relation[i][1]);
            }
        }
        return sum;
    }

    /**
     * dp缓存法
     *
     * 减枝
     */
    public int numWays2(int n, int[][] relation, int k) {
        //创建DP方程   还剩3步  一共5个位置
        int[][] dp = new int[k+1][n];
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[i].length; j++) {
                dp[i][j] = -1;
            }
        }

        return way2(n,relation,k,0,dp);
    }

    //有N个玩家
    //有向箭头
    //还剩几步
    //当前位置
    // 很明显：还剩几步、当前位置是可变的
    public int way2(int n, int[][] relation, int k,int cur,int[][] dp){
        if (dp[k][cur]!=-1){
            return dp[k][cur];
        }

        //如果K==0了说明结束了,cur不等于n-1说明返回0
        if (k==0){
            return n-1 == cur?1:0;
        }

        int sum = 0;
        for (int i = 0; i < relation.length; i++) {
            if (relation[i][0]==cur){
                sum+=way2(n,relation,k-1,relation[i][1],dp);
            }
        }
        dp[k][cur] = sum;
        return sum;
    }

    /**
     * 动态规划DP方程 空间复杂的O(N)
     */
    public int numWays3(int n, int[][] relation, int k) {
        //创建DP方程   还剩3步  一共5个位置
        int[][] dp = new int[k+1][n];

        //初始化K==0行 n-1 == cur设置为1
        dp[0][n-1] = 1;

        for (int i = 0; i < k; i++) {
            for (int[] edge : relation) {
                //dst当前Cur  src是目标方向
                int dst = edge[0], src = edge[1];
                dp[i + 1][dst] += dp[i][src];
            }
        }

        return dp[dp.length-1][0];
    }

    /**
     * 动态规划DP方程 空间复杂的O(1)
     * TODO
     */
    public int numWays4(int n, int[][] relation, int k) {
        //创建DP方程   还剩3步  一共5个位置
        int[] dp = new int[n];

        //初始化K==0行 n-1 == cur设置为1
        dp[n-1] = 1;

        for (int i = 0; i < k; i++) {
            int[] next = new int[n];
            for (int[] edge : relation) {
                //dst当前Cur  src是目标方向
                int dst = edge[0], src = edge[1];
                next[dst] += dp[src];
            }
            dp = next;
        }

        return dp[0];
    }

    public static void main(String[] args) {
        //测试
        int n = 5;
        int[][] relation = {{0, 2}, {2, 1}, {3, 4}, {2, 3}, {1, 4}, {2, 0}, {0, 4}};
        int k = 3;
        LCP07传递信息 lcp07 = new LCP07传递信息();

        System.out.println(lcp07.numWays(n, relation, k));

        System.out.println(lcp07.numWays2(n, relation, k));

        System.out.println(lcp07.numWays3(n, relation, k));

        System.out.println(lcp07.numWays4(n, relation, k));
    }
}
