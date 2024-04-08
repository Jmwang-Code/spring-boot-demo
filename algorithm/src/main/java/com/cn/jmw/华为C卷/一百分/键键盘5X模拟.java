package com.cn.jmw.华为C卷.一百分;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 *╭—————————————————————————————╮
 *│ 当前时间：2024-04-03 16:30:14 │
 *│ 完成时间：00:20:02            │
 *╰—————————————————————————————╯
 * 题目描述
 * 有一个特殊的5键键盘，上面有a，ctrl-c，ctrl-x，ctrl-v，ctrl-a五个键。
 *
 * a键在屏幕上输出一个字母a；
 *
 * ctrl-c将当前选择的字母复制到剪贴板；
 *
 * ctrl-x将当前选择的字母复制到剪贴板，并清空选择的字母；
 *
 * ctrl-v将当前剪贴板里的字母输出到屏幕；
 *
 * ctrl-a选择当前屏幕上的所有字母。
 *
 * 注意：
 *
 * 剪贴板初始为空，新的内容被复制到剪贴板时会覆盖原来的内容
 * 当屏幕上没有字母时，ctrl-a无效
 * 当没有选择字母时，ctrl-c和ctrl-x无效
 * 当有字母被选择时，a和ctrl-v这两个有输出功能的键会先清空选择的字母，再进行输出
 * 给定一系列键盘输入，输出最终屏幕上字母的数量。
 *
 * 输入描述
 * 输入为一行，为简化解析，用数字1 2 3 4 5代表a，ctrl-c，ctrl-x，ctrl-v，ctrl-a五个键的输入，数字用空格分隔。
 * 输出描述
 * 输出一个数字，为最终屏幕上字母的数量。
 */
public class 键键盘5X模拟 {
    public static void main(String[] args) {
        //帮我举出一堆测试案例 最好极端
        //比如 1 1 5 1 5 2 4 4
        Scanner sc = new Scanner(System.in);
        int[] commands = Arrays.stream(sc.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        System.out.println(getResult(commands));
    }

    public static int getResult(int[] commands) {
        // 用于存储屏幕上的字符
        ArrayList<String> screen = new ArrayList<>();
        // 用于存储剪贴板上的字符
        ArrayList<String> clip = new ArrayList<>();
        boolean isSelect = false;

        for (int command : commands) {
            switch (command) {
                case 1: // a
                    if (isSelect) screen.clear();
                    screen.add("a");
                    isSelect = false;
                    break;
                case 2: // ctrl-c
                    if (isSelect) {
                        clip.clear();
                        clip.addAll(screen);
                    }
                    break;
                case 3: // ctrl-x
                    if (isSelect) {
                        clip.clear();
                        clip.addAll(screen);
                        screen.clear();
                        isSelect = false;
                    }
                    break;
                case 4: // ctrl-v
                    if (isSelect) screen.clear();
                    screen.addAll(clip);
                    isSelect = false;
                    break;
                case 5: // ctrl-a
                    if (screen.size() != 0) isSelect = true;
                    break;
            }
        }

        return screen.size();
    }
}
