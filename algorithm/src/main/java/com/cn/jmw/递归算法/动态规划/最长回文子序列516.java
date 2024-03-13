package com.cn.jmw.递归算法.动态规划;

//序列是可以非连续的
public class 最长回文子序列516 {

    public int longestPalindromeSubseq(String s) {
        //边界判断
        if (s == null || s.length() == 0) {
            return 0;
        }

        char[] chars = s.toCharArray();

        return way(chars, 0, s.length() - 1);
    }

    //l-r的范围内 最长回文子序列长度
    public int way(char[] chars, int l, int r) {
        if (l == r) {
            return 1;
        } else if (r - l == 1) {
            return chars[l] == chars[r] ? 2 : 1;
        } else {
            //1. L不是回文
            //2. R不是回文
            //3. L R都不是回文
            //4. L和R可能是回文：如果不相等，0，如果相等则+2
            int ans1 = way(chars, l + 1, r);
            int ans2 = way(chars, l, r - 1);
            int ans3 = way(chars, l + 1, r - 1);
            int ans4 = chars[l] != chars[r] ? 0 : (2 + way(chars, l + 1, r - 1));
            return Math.max(ans1, Math.max(ans2, Math.max(ans3, ans4)));
        }
    }

    /**
     * 缓存
     * <p>
     * 减少冗余运算
     */
    public int longestPalindromeSubseq2(String s) {
        //边界判断
        if (s == null || s.length() == 0) {
            return 0;
        }

        char[] chars = s.toCharArray();

        int N = s.length();
        int[][] dp = new int[N + 1][N + 1];
        for (int i = 0; i < N+1; i++) {
            for (int j = 0; j < N+1; j++) {
                dp[i][j] = -1;
            }
        }

        return way2(chars, 0, s.length() - 1, dp);
    }

    public int way2(char[] chars, int l, int r, int[][] dp) {
        if (dp[l][r] != -1) {
            return dp[l][r];
        } else if (l == r) {
            return 1;
        } else if (r - l == 1) {
            return chars[l] == chars[r] ? 2 : 1;
        } else {
            //1. L不是回文
            //2. R不是回文
            //3. L R都不是回文
            //4. L和R可能是回文：如果不相等，0，如果相等则+2
            int ans1 = way2(chars, l + 1, r,dp);
            int ans2 = way2(chars, l, r - 1,dp);
            int ans3 = way2(chars, l + 1, r - 1,dp);
            int ans4 = chars[l] != chars[r] ? 0 : (2 + way2(chars, l + 1, r - 1,dp));
            int max = Math.max(ans1, Math.max(ans2, Math.max(ans3, ans4)));
            dp[l][r] = max;
            return max;
        }
    }

    /**
     * 动态规划
     */
    public int longestPalindromeSubseq3(String s) {
        //边界判断
        if (s == null || s.length() == 0) {
            return 0;
        }

        char[] chars = s.toCharArray();

        int N = s.length();
        int[][] dp = new int[N][N];

        //l==r的时候对角线都是1
        for (int i = 0; i < N; i++) {
            dp[i][i] = 1;
        }

        //l+1=r 斜线如果chars[l] == chars[r] ? 2 : 1
        for (int i = 0; i < N-1; i++) {
            dp[i][i+1] = chars[i] == chars[i+1] ? 2 : 1;
        }

        for (int i = 2; i < N; i++) {
            int y = i;
            int x = 0;
            while (y<N){
                dp[x][y] = Math.max(dp[x+1][y], Math.max(dp[x][y-1], Math.max(dp[x+1][y-1], (chars[x]!=chars[y]?0 : (2 + dp[x+1][y-1])))));
                x++;
                y++;
            }
        }

        return dp[0][N-1];
    }

    /**
     * 动态规划
     *
     * 优化：比较分析
     *
     * 在当前dp中 一个随机元素只依赖 左、左下、下、当前。进行比较
     *
     * A ？
     * B C
     * 其中4个位置找最大值返回给？位置
     *
     * 但是A一定比B大，C也比B大，此时B可以舍弃。
     */
    public int longestPalindromeSubseq4(String s) {
        //边界判断
        if (s == null || s.length() == 0) {
            return 0;
        }

        char[] chars = s.toCharArray();

        int N = s.length();
        int[][] dp = new int[N][N];

        //l==r的时候对角线都是1
        for (int i = 0; i < N; i++) {
            dp[i][i] = 1;
        }

        //l+1=r 斜线如果chars[l] == chars[r] ? 2 : 1
        for (int i = 0; i < N-1; i++) {
            dp[i][i+1] = chars[i] == chars[i+1] ? 2 : 1;
        }

        for (int i = 2; i < N; i++) {
            int y = i;
            int x = 0;
            while (y<N){
                dp[x][y] = Math.max(dp[x+1][y], Math.max(dp[x][y-1], (chars[x]!=chars[y]?0 : (2 + dp[x+1][y-1]))));
                x++;
                y++;
            }
        }

        return dp[0][N-1];
    }

    /**
     * 动态规划
     *
     * 优化：比较分析
     *
     * 在当前dp中 一个随机元素只依赖 左、左下、下、当前。进行比较
     * A ？
     * B C
     * 其中4个位置找最大值返回给？位置
     * ，但是A一定比B大，C也比B大，此时B可以舍弃。
     *
     * 通过以上理论 可以变成一维数组，进行规划
     */
    public int longestPalindromeSubseq5(String s) {
        //边界判断
        if (s == null || s.length() == 0) {
            return 0;
        }

        char[] chars = s.toCharArray();

        int N = s.length();
        int[] dp = new int[N];


        //l+1=r 斜线如果chars[l] == chars[r] ? 2 : 1
        for (int i = 0; i < N; i++) {
            dp[i] =  1;
        }

        for (int i = N-2; i >= 0; i--) {
            int pre = 0;
            for (int j = i+1; j < N; j++) {
                int temp = dp[j];
                if (chars[i] == chars[j]) {
                    dp[j] = pre + 2;
                } else {
                    dp[j] = Math.max(dp[j], dp[j-1]);
                }
                pre = temp;
            }
        }


        return dp[N-1];
    }


    public static void main(String[] args) {
        //测试
        String s = "bbbab";
        最长回文子序列516 最长回文子序列516 = new 最长回文子序列516();
//        System.out.println(最长回文子序列516.longestPalindromeSubseq(s));
//
//        //测试
        String s1 = "cbbd";
//        System.out.println(最长回文子序列516.longestPalindromeSubseq(s1));
//
//        //测试
        String s2 = "a";
//        System.out.println(最长回文子序列516.longestPalindromeSubseq(s2));
//
//        //测试来个复杂的
        String s3 = "abacab";
//        System.out.println(最长回文子序列516.longestPalindromeSubseq(s3));

//        System.out.println(最长回文子序列516.longestPalindromeSubseq2(s));
//        System.out.println(最长回文子序列516.longestPalindromeSubseq2(s1));
//        System.out.println(最长回文子序列516.longestPalindromeSubseq2(s2));
//        System.out.println(最长回文子序列516.longestPalindromeSubseq2(s3));

        System.out.println(最长回文子序列516.longestPalindromeSubseq3(s));
        System.out.println(最长回文子序列516.longestPalindromeSubseq3(s1));
        System.out.println(最长回文子序列516.longestPalindromeSubseq3(s2));
        System.out.println(最长回文子序列516.longestPalindromeSubseq3(s3));

        System.out.println(最长回文子序列516.longestPalindromeSubseq5(s));
        System.out.println(最长回文子序列516.longestPalindromeSubseq5(s1));
        System.out.println(最长回文子序列516.longestPalindromeSubseq5(s2));
        System.out.println(最长回文子序列516.longestPalindromeSubseq5(s3));
    }
}
