package com.cn.jmw.华为C卷.一百分;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

/**
 *╭—————————————————————————————╮
 *│ 当前时间：2024-04-10 20:38:38 │
 *│ 完成时间：00:20:42            │
 *╰—————————————————————————————╯
 *
 * 分组:通过首字母进行分组。并且当前组内LinkedList需要保持顺序。是通过这个规则 :
 * (当存在多个首字母相同的单词时，取长度最长的单词，如果长度也相等，则取字典序最小的单词；已经参与接龙的单词不能重复使用。)
 *
 * 然后
 */
public class 单词接龙X比较器和哈希表 {

    public static void main(String[] args) {
        while (true){
            Scanner scanner = new Scanner(System.in);

            int startIndex = scanner.nextInt();
            //单词个数
            int n = scanner.nextInt();
            //本来的位置
            String[] words = new String[n];
            for (int i = 0; i < n; i++) words[i] = scanner.next();

            ArrayList<String> chain = new ArrayList<>();
            //第一个一开始就确定了
            chain.add(words[startIndex]);

            words[startIndex] = null;

            //整理所有前缀
            HashMap<Character, LinkedList<String>> prefix = new HashMap<>();
            for (String word : words) {
                if (word != null) {
                    char c = word.charAt(0);
                    prefix.putIfAbsent(c, new LinkedList<>());
                    prefix.get(c).add(word);
                }
            }

            for (Character c : prefix.keySet()) {
                prefix
                        .get(c)
                        .sort((a, b) -> a.length() != b.length() ? b.length() - a.length() : a.compareTo(b));
            }

            while (true) {
                String tail = chain.get(chain.size() - 1);
                char c = tail.charAt(tail.length() - 1);

                if (prefix.containsKey(c) && prefix.get(c).size() > 0) {
                    chain.add(prefix.get(c).removeFirst());
                } else {
                    break;
                }
            }

            StringBuilder sb = new StringBuilder();
            for (String s : chain) sb.append(s);
            System.out.println(sb.toString());

        }
    }

    


    public static String getResult() {
	// 在这里编写你自定义的方法逻辑
        return null;
    }

}
