package com.cn.jmw;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class At {

    // N字转换
    public String convert(String s, int numRows) {
        if(numRows < 2) return s;
        List<StringBuilder> rows = new ArrayList<StringBuilder>();
        for(int i = 0; i < numRows; i++) rows.add(new StringBuilder());
        int i = 0, flag = -1;
        for(char c : s.toCharArray()) {
            rows.get(i).append(c);
            if(i == 0 || i == numRows -1) flag = - flag;
            i += flag;
        }
        StringBuilder res = new StringBuilder();
        for(StringBuilder row : rows) res.append(row);
        return res.toString();
    }

    @Test
    public void test() {
        String s = "PAYPALISHIRING";
        int numRows = 4;
        System.out.println(convert(s, numRows));
    }


}
