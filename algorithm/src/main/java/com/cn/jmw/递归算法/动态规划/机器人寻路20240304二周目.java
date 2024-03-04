package com.cn.jmw.递归算法.动态规划;

//机器人寻路
//一条路上有n个地点,比如n = 7
//初始位置为start = 2
//目标位置aim = 4
//一定要走的步长k = 4

//返回有几种走法
public class 机器人寻路20240304二周目 {

    public static int way1(int totalLength, int startPosition, int targetPosition, int totalStep) {
        return process1(totalLength, startPosition, targetPosition, totalStep);
    }

    /**
     * 常用递归
     * totalLength 总长度
     * curPosition 当前位置
     * targetPosition 目标位置
     * restStep 剩余步数
     */
    public static int process1(int totalLength, int curPosition, int targetPosition, int restStep) {
        //如果剩余步数为零，说明结束当前可能性
        if (restStep == 0) {
            return targetPosition == curPosition ? 1 : 0;
        }

        //如果达到最左端 ，则向右走
        if (curPosition == 0) {
            return process1(totalLength, curPosition + 1, targetPosition, restStep - 1);
        }

        //如果达到最右端 ，则向左走
        if (curPosition == totalLength) {
            return process1(totalLength, curPosition - 1, targetPosition, restStep - 1);
        }

        return process1(totalLength, curPosition - 1, targetPosition, restStep - 1) +
                process1(totalLength, curPosition + 1, targetPosition, restStep - 1);
    }

    /**
     * 从顶向下的动态规划（记忆化搜索）
     * 本质就是使用缓存，减少重复运算
     */
    //优化重复的叶子节点，使用缓存法
    public static int way2(int totalLength, int startPosition, int targetPosition, int totalStep) {
        //创建DP缓存
        int[][] dp = new int[totalLength + 1][totalStep + 1];
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[i].length; j++) {
                dp[i][j] = -1;
            }
        }

        int ans = process2(totalLength, startPosition, targetPosition, totalStep, dp);
        return ans;
    }

    //cur 1-n
    //rest 0-k
    //cur rest 是可变的，需要一个二维表存储，存储下来所有对应出现的参数路径
    public static int process2(int totalLength, int curPosition, int targetPosition, int restStep, int[][] dp) {
        //如果DP方程中有就直接拿
        if (dp[curPosition][restStep] != -1) {
            return dp[curPosition][restStep];
        }

        int ans = 0;

        //如果剩余步数为零，说明结束当前可能性
        if (restStep == 0) {
            ans = targetPosition == curPosition ? 1 : 0;
        } else if (curPosition == 0) {
            ans = process2(totalLength, curPosition + 1, targetPosition, restStep - 1, dp);
        } else if (curPosition == totalLength) {
            ans = process2(totalLength, curPosition - 1, targetPosition, restStep - 1, dp);
        } else {
            ans = process2(totalLength, curPosition - 1, targetPosition, restStep - 1, dp) +
                    process2(totalLength, curPosition + 1, targetPosition, restStep - 1, dp);
        }

        dp[curPosition][restStep] = ans;

        return ans;
    }

    /**
     * 构建DP方程
     * <p>
     * 通过DP二维数组递推
     */
    public static int way3(int totalLength, int startPosition, int targetPosition, int totalStep) {
        //判断越界
        if (totalLength < 2 || totalStep < 1 || startPosition < 1 || startPosition > totalLength || totalStep < 1 || totalStep > totalLength)
            return 0;
        //y为totalLength cur
        //x为totalStep rest
        int[][] dp = new int[totalLength + 1][totalStep + 1];

//        if (restStep == 0) {
//            return targetPosition == curPosition ? 1 : 0;
//        }
        dp[targetPosition][0] = 1;

        //数组的坐标和正常的坐标是颠倒的，这时候使用X轴，也就是totalStep
        for (int rest = 1; rest <= totalStep; rest++) {
//        //如果达到最左端 ，则向右走
//        if (curPosition == 0) {
//            return process3(totalLength, curPosition + 1, targetPosition, restStep - 1);
//        }
            dp[1][rest] = dp[2][rest - 1];

//        return process3(totalLength, curPosition - 1, targetPosition, restStep - 1) +
//                process3(totalLength, curPosition + 1, targetPosition, restStep - 1);
            for (int cur = 2; cur < totalLength; cur++) {
                dp[cur][rest] = dp[cur - 1][rest - 1] + dp[cur + 1][rest - 1];
            }

//        //如果达到最右端 ，则向左走
//        if (curPosition == totalLength) {
//            return process3(totalLength, curPosition - 1, targetPosition, restStep - 1);
//        }
            dp[totalLength][rest] = dp[totalLength - 1][rest - 1];
        }
        return dp[startPosition][totalStep];
    }


    //测试way1方法
    public static void main(String[] args) {
        //2 3 4 5 4
        //2 3 4 3 4
        //2 3 2 3 4
        //2 1 2 3 4
        int way1 = way1(7, 2, 4, 4);
        System.out.println(way1);

        int way2 = way2(7, 2, 4, 4);
        System.out.println(way2);

        int way3 = way3(7, 2, 4, 4);
        System.out.println(way3);
    }
}
