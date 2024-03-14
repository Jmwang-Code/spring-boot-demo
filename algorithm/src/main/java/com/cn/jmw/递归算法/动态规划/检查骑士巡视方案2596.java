package com.cn.jmw.递归算法.动态规划;

/**
 * 分析此题：
 * 由于他其实只有一种正确走法，不管怎么走，其实都是一种走法，所以我们可以用递归来解决这个问题
 */
public class 检查骑士巡视方案2596 {

    //递归常式
    //从(0,0)出发,重点是N × N步。当前数字刚好等于(N x N)-1
    //每一步的当前数字都等于 步数-1
    public boolean checkValidGrid(int[][] grid) {
        return process(grid,0,0,0);
    }

    public boolean process(int[][] grid,int x,int y,int step){
        if (x < 0 || x > grid.length-1 || y < 0 || y > grid.length-1) {
            return false;
        }

        //只有一种情况说明结束了
        if (step == (grid.length * grid.length)-1 && grid[x][y] == (grid.length * grid.length)-1 ){
            return true;
        }

        if (grid[x][y] != step ){
            return false;
        }

        boolean way = false;
        way |= process(grid,x+2,y+1,step+1);
        way |= process(grid,x+2,y-1,step+1);
        way |= process(grid,x-2,y+1,step+1);
        way |= process(grid,x-2,y-1,step+1);
        way |= process(grid,x+1,y+2,step+1);
        way |= process(grid,x+1,y-2,step+1);
        way |= process(grid,x-1,y+2,step+1);
        way |= process(grid,x-1,y-2,step+1);

        return way;
    }

    public static void main(String[] args) {
        // 测试 [[0,11,16,5,20],[17,4,19,10,15],[12,1,8,21,6],[3,18,23,14,9][24,13,2,7,22]
        int [][] grid2 = new int[][]{
            {0, 11, 16, 5, 20},
            {17, 4, 19, 10, 15},
            {12, 1, 8, 21, 6},
            {3, 18, 23, 14, 9},
            {24,13,2,7,22}
        };
        System.out.println(new 检查骑士巡视方案2596().checkValidGrid(grid2)); // true
        // [[0,3,6],[5,8,1],[2,7,4]]
        int [][] grid3 = new int[][]{
            {0, 3, 6},
            {5, 8, 1},
            {2, 7, 4}
        };
        System.out.println(new 检查骑士巡视方案2596().checkValidGrid(grid3)); // true

    }
}
