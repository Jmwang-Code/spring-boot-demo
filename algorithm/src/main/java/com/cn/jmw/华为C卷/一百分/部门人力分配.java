package com.cn.jmw.华为C卷.一百分;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 
 */
public class 部门人力分配 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int m = Integer.parseInt(sc.nextLine());
        int[] requirements =
                Arrays.stream(sc.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        System.out.println(getResult(m, requirements));
    }

    public static long getResult(int m, int[] requirements) {
        Arrays.sort(requirements);

        int n = requirements.length;

        // 每辆自行车的限重 至少是 最重的那个人的体重
        long min = requirements[n - 1];
        // 每辆自行车的限重 至多是 最重的和次重的那两个的体重
        long max = requirements[n - 2] + requirements[n - 1];

        long ans = max;

        // 二分取中间值
        while (min <= max) {
            long mid = (min + max) >> 1; // 需要注意的是，min，max单独看都不超过int，但是二者相加会超过int，因此需要用long类型

            if (check(mid, m, requirements)) {
                // 如果mid限重，可以满足m辆车带走n个人，则mid就是一个可能解，但不一定是最优解
                ans = mid;
                // 继续尝试更小的限重，即缩小右边界
                max = mid - 1;
            } else {
                // 如果mid限重，不能满足m辆车带走n个人，则mid限重小了，我们应该尝试更大的限重，即扩大左边界
                min = mid + 1;
            }
        }

        return ans;
    }

    /**
     * @param limit 每辆自行车的限重
     * @param m m辆自行车
     * @param requirements n个人的体重数组
     * @return m辆自行车，每辆限重limit的情况下，能否带走n个人
     */
    public static boolean check(long limit, int m, int[] requirements) {
        int l = 0; // 指向体重最轻的人
        int r = requirements.length - 1; // 指向体重最重的人

        // 需要的自行车数量
        int need = 0;

        while (l <= r) {
            // 如果最轻的人和最重的人可以共享一辆车，则l++,r--，
            // 否则最重的人只能单独坐一辆车，即仅r--
            if (requirements[l] + requirements[r] <= limit) {
                l++;
            }
            r--;
            // 用掉一辆车
            need++;
        }

        // 如果m >= need，当前有的自行车数量足够
        return m >= need;
    }
}
