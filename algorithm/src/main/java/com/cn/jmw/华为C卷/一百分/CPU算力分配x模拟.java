package com.cn.jmw.华为C卷.一百分;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 *╭—————————————————————————————╮
 *│ 当前时间：2024-04-07 16:27:04 │
 *│ 完成时间：00:21:01            │
 *╰—————————————————————————————╯
 * 
 */
public class CPU算力分配x模拟 {

    @Test
    public void test(){
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()){
            int lengthA = sc.nextInt();
            int lengthB = sc.nextInt();

            int[] A = new int[lengthA];
            int sumA = 0;
            for (int i = 0; i < lengthA; i++) {
                A[i] = sc.nextInt();
                sumA += A[i];
            }

            int sumB = 0;
            Set<Integer> B = new HashSet<>();
            for (int i = 0; i < lengthB; i++) {
                int temp = sc.nextInt();
                sumB += temp;
                B.add(temp);
            }

            int cha = (sumA - sumB) / 2;

            int minA = Integer.MAX_VALUE;
            String ans = "";

            for (int a : A) {
                int b = a - cha;

                if (B.contains(b)) {
                    if (a < minA) {
                        minA = a;
                        ans = a + " " + b;
                    }
                }
            }
            System.out.println(ans);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        String[] split = s.split(" ");
        int lengthA = Integer.parseInt(split[0]);
        int lengthB = Integer.parseInt(split[1]);
        int[] A = Arrays.stream(sc.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int[] B = Arrays.stream(sc.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int sumA = Arrays.stream(A).sum();
        int sumB = Arrays.stream(B).sum();
        int max;
        int min;
        boolean logoMax = false;
        if (sumB>sumA){
            logoMax = false;
            max = sumB;
            min = sumA;
        }else if (sumB==sumA){
            return;
        }else {
            logoMax = true;
            max = sumA;
            min = sumB;
        }
        int cax = (max - min)/2;
        for (int i = 0; i < lengthA; i++) {
            for (int j = 0; j < lengthB; j++) {
                //如果A组大，
                if (logoMax){
                    if (A[i] - B[j] == cax){
                        System.out.println(A[i] +" "+ B[j]);
                       return;
                    }
                }else {
                    if (B[j] - A[i] == cax){
                        System.out.println(A[i] +" "+ B[j]);
                        return;
                    }
                }
            }
        }
    }
}
