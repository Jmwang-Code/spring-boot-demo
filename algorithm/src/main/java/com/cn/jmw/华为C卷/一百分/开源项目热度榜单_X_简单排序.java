package com.cn.jmw.华为C卷.一百分;

import java.util.Arrays;
import java.util.Scanner;

public class 开源项目热度榜单_X_简单排序 {

    static class Project {
        String name;
        int hot;

        public Project(String name, int hot) {
            this.name = name;
            this.hot = hot;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        int[] weights = new int[5];
        for (int i = 0; i < 5; i++) {
            weights[i] = sc.nextInt();
        }

        Project[] projects = new Project[n];

        for (int i = 0; i < n; i++) {
            String name = sc.next();

            int hot = 0;
            for (int j = 0; j < 5; j++) {
                hot += sc.nextInt() * weights[j];
            }

            projects[i] = new Project(name, hot);
        }

        Arrays.sort(
                projects,
                (a, b) ->
                        a.hot != b.hot ? b.hot - a.hot : a.name.toLowerCase().compareTo(b.name.toLowerCase()));

        for (Project project : projects) {
            System.out.println(project.name);
        }
    }

}
