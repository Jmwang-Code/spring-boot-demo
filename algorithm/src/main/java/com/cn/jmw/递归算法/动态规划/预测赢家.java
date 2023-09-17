package com.cn.jmw.递归算法.动态规划;


/**
 * 486.预测赢家
 *
 * 给定一个整型数组arr，代表数值不同的纸牌排成一条线玩家A和玩家B依次拿走每张纸牌
 * 规定玩家A先拿，玩家B后拿
 * 但是每个玩家每次只能拿走最左或最右的纸牌玩家A和玩家B都绝顶聪明
 * 请返回最后获胜者的分数。
 */
public class 预测赢家 {

    /**
     * 常式 暴力递归.md
     */
    public static int win(int[] arr){
        if (arr==null || arr.length==0)return 0;
        int first = f1(arr,0,arr.length-1);
        int last = l1(arr,0,arr.length-1);
        return Math.max(first,last);
    }

    //先手
    public static int f1(int[] arr,int left,int right){
        if (left==right)return arr[left];

        int p1 = arr[left] + l1(arr,left+1,right);
        int p2 = arr[right] + l1(arr,left,right-1);
        //先手选取一种 当前牌中的最优
        return Math.max(p1,p2);
    }

    //后手
    public static int l1(int[] arr,int left,int right){
        if (left==right)return 0;

        //后手选 先手剩下的牌中的最优
        int p1 =  f1(arr,left+1,right);
        int p2 =  f1(arr,left,right-1);
        return Math.min(p1,p2);
    }








    /**
     * 缓存
     */
    public static int win2(int[] arr){
        if (arr==null || arr.length==0)return 0;

        int[][] dpF = new int[arr.length][arr.length];
        int[][] dpL = new int[arr.length][arr.length];
        for (int i = 0; i < dpF.length; i++) {
            for (int j = 0; j < dpF.length; j++) {
                dpF[i][j] = -1;
                dpL[i][j] = -1;
            }
        }
        int first = f(arr,0,arr.length-1,dpF,dpL);
        int last = l(arr,0,arr.length-1,dpF,dpL);
        return Math.max(first,last);
    }

    //先手
    public static int f(int[] arr,int left,int right,int[][] dpF,int[][] dpL){
        if (dpF[left][right]!=-1){
            return dpF[left][right];
        }
        int ans = 0;
        if (left==right) {
            return arr[left];
        }else {
            int p1 = arr[left] + l(arr,left+1,right,dpF,dpL);
            int p2 = arr[right] + l(arr,left,right-1,dpF,dpL);
            ans = Math.max(p1,p2);
        }

        //先手选取一种 当前牌中的最优
        dpF[left][right] = ans;
        return ans;
    }

    //后手
    public static int l(int[] arr,int left,int right,int[][] dpF,int[][] dpL){
        if (dpL[left][right]!=-1)return dpL[left][right];

        int ans = 0;
        if (left!=right) {
            int p1 =  f(arr,left+1,right,dpF,dpL);
            int p2 =  f(arr,left,right-1,dpF,dpL);
            ans = Math.min(p1, p2);
        }
        dpL[left][right] = ans;
        return ans;
    }


    /**
     * 通过常式进行递推
     *
     * 递推公式
     * First缓存表位置确定： f(x,y) = 最小值（l(x-1,y) , l(x,y+1)）+ arr[x]
     * Last缓存表位置确定： l(x,y) = 最大值（f(x-1,y) , f(x,y+1))
     */
    public static int win3(int[] arr){
        if (arr==null || arr.length==0)return 0;
        int ans = 0;

        int N = arr.length;
        int[][] dpF = new int[N][N];
        int[][] dpL = new int[N][N];
        //初始化dpF
        for (int i = 0; i < N; i++) {
            dpF[i][i] = arr[i];
        }

        //开始递推
        for (int startCol = 1; startCol < N; startCol++) {
            int l = 0;  //列
            int r = startCol;
            while (r<N){
                dpF[l][r] = Math.max(arr[l]+dpL[l+1][r],arr[r]+dpL[l][r-1]) ;
                dpL[l][r] = Math.min(dpF[l+1][r],dpF[l][r-1]);
                l++;
                r++;
            }
        }


        return Math.max(dpF[0][N-1],dpL[0][N-1]);
    }


        //测试
    public static void main(String[] args) {
        int win = win(new int[]{50, 100, 20, 10});
        System.out.println(win);

        int win2 = win2(new int[]{50, 100, 20, 10});
        System.out.println(win2);

        int win3 = win3(new int[]{50, 100, 20, 10});
        System.out.println(win3);
    }
}
