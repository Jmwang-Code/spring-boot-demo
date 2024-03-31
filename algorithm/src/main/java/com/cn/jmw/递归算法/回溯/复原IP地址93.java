package com.cn.jmw.递归算法.回溯;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class 复原IP地址93 {

    int IP_LENGTH = 4;

    int[] ipAddress = new int[IP_LENGTH];

    List<String> addresses = new ArrayList<>();

    public List<String> restoreIpAddresses(String s) {
        char[] charArray = s.toCharArray();
        int[] arr = new int[charArray.length];
        for (int i = 0; i < charArray.length; i++) {
            arr[i] = charArray[i] - '0';
        }

        dfs(arr, 0, 0);
        return addresses;
    }

    public void dfs(int[] arr, int ipAddressIndex,int arrIndex) {
        //说明是正常终止
        if (ipAddressIndex == IP_LENGTH) {
            if (arrIndex == arr.length) {
                //拼接字符串并且加入addresses
                StringBuilder ipAddr = new StringBuilder();
                for (int i = 0; i < 4; i++) {
                    ipAddr.append(ipAddress[i]);
                    if (i != 4 - 1) {
                        ipAddr.append('.');
                    }
                }
                addresses.add(ipAddr.toString());
            }
            return;
        }

        //如果已经没有剩余的ipAddressIndex 没有形成
        if (arrIndex == arr.length) {
            return;
        }

        //如果当前索引下arr等于0需要单独当做一行
        if (arr[arrIndex] == 0) {
            ipAddress[ipAddressIndex] = 0;
            dfs(arr, ipAddressIndex + 1, arrIndex + 1);
        }

        int address = 0;
        for (int i = arrIndex; i < arr.length; i++) {
            address = address * 10 + arr[i];
            //如果在0-225
            if (address > 0 && address <= 0xff) {
                ipAddress[ipAddressIndex] = address;
                dfs(arr, ipAddressIndex + 1, i + 1);
            } else {
                break;
            }
        }
    }

    @Test
    public void test() {
        String s = "25525511135";
        System.out.println(new 复原IP地址93().restoreIpAddresses(s));
        //继续案例
        s = "0000";
        System.out.println(new 复原IP地址93().restoreIpAddresses(s));
        s = "1111";
        System.out.println(new 复原IP地址93().restoreIpAddresses(s));
        //101023
        s = "101023";
        System.out.println(new 复原IP地址93().restoreIpAddresses(s));
    }
}
