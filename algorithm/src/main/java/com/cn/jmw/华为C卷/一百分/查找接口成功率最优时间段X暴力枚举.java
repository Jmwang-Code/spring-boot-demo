package com.cn.jmw.华为C卷.一百分;

import java.util.*;

/**
 *╭—————————————————————————————╮
 *│ 当前时间：2024-04-09 17:17:41 │
 *│ 完成时间：00:30:00            │
 *╰—————————————————————————————╯
 * 
 */
public class 查找接口成功率最优时间段X暴力枚举 {


    //用双层for来试试
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int minAverageLost = Integer.parseInt(sc.nextLine());
        int[] nums = Arrays.stream(sc.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        System.out.println(getResult(nums, minAverageLost));
    }

    public static String getResult(int[] nums, int minAverageLost) {
        int n = nums.length;

        //preSum 是前缀和数组
        int[] preSum = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            preSum[i] = preSum[i - 1] + nums[i - 1];
        }
        //ans 是答案数组
        ArrayList<int[]> ans = new ArrayList<>();
        //maxLen 是最大长度
        int maxLen = 0;

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j <= n; j++) {
                // sum 是 区间 [i, j-1] 的和
                int sum = preSum[j] - preSum[i];
                int len = j - i;
                int lost = len * minAverageLost;

                // 如果区间和小于等于 lost，那么就是一个符合条件的区间
                if (sum <= lost) {
                    // 如果区间长度大于等于 maxLen，那么就是一个更优的区间
                    if (len >= maxLen) {
                        if (len > maxLen) {
                            //只要是更好的区间，就清空之前的，如果只是相等的话，就添加最新的
                            ans = new ArrayList<>();
                        }
                        ans.add(new int[] {i, j - 1});
                        maxLen = len;
                    }
                }
            }
        }

        if (ans.size() == 0) return "NULL";

        ans.sort((a, b) -> a[0] - b[0]);

        StringJoiner sj = new StringJoiner(" ");
        for (int[] an : ans) sj.add(an[0] + "-" + an[1]);
        return sj.toString();
    }



    


    public static String getResult() {
	// 在这里编写你自定义的方法逻辑
        return null;
    }

}
