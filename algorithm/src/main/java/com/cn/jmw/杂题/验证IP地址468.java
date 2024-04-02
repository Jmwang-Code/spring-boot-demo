package com.cn.jmw.杂题;

import org.junit.Test;

public class 验证IP地址468 {

    static String Neither = "Neither";
    static String IPv4 = "IPv4";
    static String IPv6 = "IPv6";

    public String validIPAddress(String queryIP) {
        //判断是 . 还是 :
        if (queryIP.contains(".") && !queryIP.matches(".*[a-zA-Z].*") && queryIP.length()<16) {
            String[] split = queryIP.split("\\.",-1);
            if (split.length != 4) return Neither;
            for (int i = 0; i < split.length; i++) {
                String cur = split[i];
                if (cur.startsWith("0") && cur.length() > 1 || "".equals(cur)) {
                    return Neither;
                }
                if (Integer.parseInt(cur) < 0 || Integer.parseInt(cur) > 255) {
                    return Neither;
                }
            }
            return IPv4;
        } else if (queryIP.contains(":") && !queryIP.matches(".*[g-zG-Z].*") && queryIP.length()<39) {
            //IPV6
            String[] split = queryIP.split(":",-1);
            if (split.length != 8) return Neither;
            for (int i = 0; i < split.length; i++) {
                String cur = split[i];
                if (cur.length() > 4  || "".equals(cur)) return Neither;
                if (Integer.parseInt(cur,16) < 0 || Integer.parseInt(cur,16) > Integer.parseInt("ffff",16)){
                    return Neither;
                }
            }
            return IPv6;
        }
        return Neither;
    }

    public String validIPAddress2(String queryIP) {
        if (queryIP.indexOf('.') >= 0) {
            return isIpV4(queryIP) ? "IPv4" : "Neither";
        } else {
            return isIpV6(queryIP) ? "IPv6" : "Neither";
        }
    }

    public boolean isIpV4(String queryIP) {
        //加-1是防止出现空字符串无法计数 比如192.168.1.1. 后边多了一个点，不加-1会被忽略后边的空串
        String[] split = queryIP.split("\\.",-1);
        //个数不是4个
        if (split.length != 4) {
            return false;
        }
        for (String s : split) {
            //每个长度不在 1-3之间
            if (s.length() > 3 || s.length() == 0) {
                return false;
            }
            //有前导0 并且长度不为1
            if (s.charAt(0) == '0' && s.length() != 1) {
                return false;
            }
            //计算数字

            for (int j = 0; j < s.length(); j++) {
                char c = s.charAt(j);
                //不是数字
                if (!Character.isDigit(c)) {
                    return false;
                }

            }
            //数字超过255
            if (Integer.parseInt(s)  > 255) {
                return false;
            }
        }
        return true;
    }

    public boolean isIpV6(String queryIP) {
        String[] split = queryIP.split(":",-1);
        //数量不是8个
        if (split.length != 8) {
            return false;
        }
        for (String s : split) {
            //每个串 长度不在1-4之间
            if (s.length() > 4 || s.length() == 0) {
                return false;
            }
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                //不是数字并且字母不在 a-f之间
                if (!Character.isDigit(c) && !(Character.toLowerCase(c) >= 'a') || !(Character.toLowerCase(c) <= 'f')) {
                    return false;
                }
            }
        }
        return true;
    }


    @Test
    public void test() {
        //案例1
//        System.out.println(validIPAddress("172.16.254.1"));
//        //案例2
//        System.out.println(validIPAddress("2001:0db8:85a3:0:0:8A2E:0370:7334"));
//        //案例3
//        System.out.println(validIPAddress("256.256.256.256"));
//        //案例4
//        System.out.println(validIPAddress("2001:0db8:85a3:0:0:8A2E:0370:7334:"));
        //案例5
        System.out.println(validIPAddress("1e1.4.5.6"));
    }
}
