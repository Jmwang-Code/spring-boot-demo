public class Solution {
    private int MOD = 1000000007;

    public int firstDayBeenInAllRooms(int[] nextVisit) {
        int n = nextVisit.length;
        long[] dp = new long[n]; // dp[i] 表示访问完第 i 个房间的第一天的日期编号
        for (int i = 1; i < n; i++) {
            long pre = (dp[i - 1] - dfs(nextVisit, i - 1, dp) + MOD) % MOD;
            dp[i] = (pre + dp[i - 1] + 2) % MOD;
        }
        return (int) dp[n - 1];
    }

    private long dfs(int[] nextVisit, int room, long[] dp) {
        if (room == 0) {
            return 0;
        }
        if (dp[room] > 0) {
            return dp[room];
        }
        long pre = (dfs(nextVisit, room - 1, dp) - dfs(nextVisit, nextVisit[room - 1], dp) + MOD) % MOD;
        dp[room] = (pre + dfs(nextVisit, room - 1, dp) + 2) % MOD;
        return dp[room];
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        int[] nextVisit = {0, 0, 2};
        System.out.println(solution.firstDayBeenInAllRooms(nextVisit)); // 输出: 6
    }
}
