package com.cn.jmw.杂题;

import java.util.*;

public class 整理字符串1544 {

    public String process(String str){
        StringBuffer sb = new StringBuffer();

        int n = str.length();
        int sbIndex = -1;
        for (int i = 0; i < n; i++) {
            if (sb.length()>0 &&
                str.charAt(i)!=sb.charAt(sbIndex) &&
                Character.toUpperCase(str.charAt(i))==Character.toUpperCase(sb.charAt(sbIndex))){
                sb.deleteCharAt(sbIndex);
                sbIndex--;
            }else {
                sb.append(str.charAt(i));
                sbIndex++;
            }
        }
        return sb.toString();
    }

    public String convertToBase7(int num) {
        if (num == 0) {
            return "0";
        }
        boolean negative = num < 0;
        num = Math.abs(num);
        StringBuffer digits = new StringBuffer();
        while (num > 0) {
            digits.append(num % 7);
            num /= 7;
        }
        if (negative) {
            digits.append('-');
        }
        return digits.reverse().toString();
    }

    public int accountBalanceAfterPurchase(int purchaseAmount) {
        return 100 - (purchaseAmount + 5) / 10 * 10;
    }

    public long pickGifts(int[] gifts, int k) {
        PriorityQueue<Integer> pq = new PriorityQueue<Integer>((a, b) -> b - a);
        for (int gift : gifts) {
            pq.offer(gift);
        }
        while (k > 0) {
            k--;
            int x = pq.poll();
            pq.offer((int) Math.sqrt(x));
        }
        long res = 0;
        while (!pq.isEmpty()) {
            res += pq.poll();
        }
        return res;
    }

    public static void main(String[] args) {
        //测试
        String str = "leEeetcode";
        整理字符串1544 hj3 = new 整理字符串1544();
        String res = hj3.process(str);
        System.out.println(res);

        System.out.println(hj3.convertToBase7(100));
        System.out.println(hj3.convertToBase7(-7));

        System.out.println(hj3.accountBalanceAfterPurchase(15));

        System.out.println(hj3.pickGifts(new int[]{25,64,9,4,100},4));
    }
}
