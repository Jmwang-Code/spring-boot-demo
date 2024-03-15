package com.cn.jmw.华为;

public class 柠檬水找零860 {

    public boolean lemonadeChange(int[] bills) {
        int five = 0,ten = 0;
        for (int i = 0; i < bills.length; i++) {
            if (bills[i]==5){
                five++;
            }else if (bills[i]==10){
                if (five>0){
                    ten++;
                    five--;
                }else {
                    return false;
                }
            }else {
                if (ten>0 && five>0){
                    ten--;
                    five--;
                }else if (ten<=0 && five>2){
                    five = five-3;
                }else {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int[] arr = {5,5,5,10,20};
        柠檬水找零860 柠檬水找零860 = new 柠檬水找零860();
        System.out.println(柠檬水找零860.lemonadeChange(arr));
        int[] arr2 = {5,5,10,10,20};
        System.out.println(柠檬水找零860.lemonadeChange(arr2));
    }
}
