package com.cn.jmw.递归算法;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class LCR087复原IP地址 {

    int IP_LENGTH = 4;
    List<String> addresses = new ArrayList<>();
    int[] ipAddress = new int[IP_LENGTH];

    public List<String> restoreIpAddresses(String s) {
        dfs(s, 0, 0);

        return addresses;

    }

    //segId 表示当前是第几段，segStart表示当前段的起始位置
    //segStart表示当前段的起始位置
    private void dfs(String s, int ipAddressIndex, int arrIndex) {
        if (ipAddressIndex == IP_LENGTH) {
            if (arrIndex == s.length()) {
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
        if (arrIndex == s.length()) {
            return;
        }
        if (s.charAt(arrIndex) == '0') {
            ipAddress[ipAddressIndex] = 0;
            dfs(s, ipAddressIndex + 1, arrIndex + 1);
        }
        int addr = 0;
        for (int segEnd = arrIndex; segEnd < s.length(); segEnd++) {
            addr = addr * 10 + (s.charAt(segEnd) - '0');
            if (addr > 0 && addr <= 0xff) {
                ipAddress[ipAddressIndex] = addr;
                dfs(s, ipAddressIndex + 1, segEnd + 1);
            } else {
                break;
            }
        }
    }

    @Test
    public void test() {
        String s = "25525511135";
        System.out.println(restoreIpAddresses(s));
    }
}
