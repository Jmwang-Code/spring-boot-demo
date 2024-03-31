package com.cn.jmw.递归算法.回溯;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LCR157套餐内商品的排列顺序 {

    List<String> res = new ArrayList<>();

    public String[] goodsOrder(String goods) {
        char[] goodsArr = goods.toCharArray();
        Arrays.sort(goodsArr);
        goods = new String(goodsArr);
        StringBuilder path = new StringBuilder();
        boolean[] visited = new boolean[goods.length()];
        dfs(goods, path, visited);
        return res.toArray(new String[0]);
    }

    public void dfs(String goods, StringBuilder path, boolean[] visited) {
        if (path.length() == goods.length()) {
            res.add(path.toString());
            return;
        }
        for (int i = 0; i < goods.length(); i++) {
            if (visited[i]) {
                continue;
            }
            if (i > 0 && goods.charAt(i) == goods.charAt(i - 1) && !visited[i - 1]) {
                continue;
            }
            visited[i] = true;
            path.append(goods.charAt(i));
            dfs(goods, path, visited);
            path.deleteCharAt(path.length() - 1);
            visited[i] = false;
        }
    }

    @Test
    public void test() {
        String goods = "ABC";
        String[] res = goodsOrder(goods);
        System.out.println(Arrays.toString(res));

        //agew
        String goods2 = "agew";
        String[] res2 = goodsOrder(goods2);
        System.out.println(Arrays.toString(res2));
    }
}
