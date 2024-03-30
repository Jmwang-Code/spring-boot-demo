package com.cn.jmw.递归算法;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class LCR087复原IP地址 {

    int SEG_COUNT = 4;
    List<String> ret = new ArrayList<>();
    int[] segments = new int[4];

    public List<String> restoreIpAddresses(String s) {
        dfs(s, 0, 0);

        List<String> list = new ArrayList<>();
        return ret;

    }

    private void dfs(String s, int segId, int segStart) {
        if (segId == 4) {
            if (segStart == s.length()) {
                StringBuilder ipAddr = new StringBuilder();
                for (int i = 0; i < 4; i++) {
                    ipAddr.append(segments[i]);
                    if (i != 4 - 1) {
                        ipAddr.append('.');
                    }
                }
                ret.add(ipAddr.toString());
            }
            return;
        }
        if (segStart == s.length()) {
            return;
        }
        if (s.charAt(segStart) == '0') {
            segments[segId] = 0;
            dfs(s, segId + 1, segStart + 1);
        }
        int addr = 0;
        for (int segEnd = segStart; segEnd < s.length(); segEnd++) {
            addr = addr * 10 + (s.charAt(segEnd) - '0');
            if (addr > 0 && addr <= 0xff) {
                segments[segId] = addr;
                dfs(s, segId + 1, segEnd + 1);
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
