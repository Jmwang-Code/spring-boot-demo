package com.cn.jmw.华为C卷.tools;

import java.io.*;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;

public class RemoveMethodFromJavaFiles {

    public static void main(String[] args) {
        String directoryPath = "C:\\Users\\wjm\\IdeaProjects\\spring-boot-demo\\algorithm\\src\\main\\java\\com\\cn\\jmw\\华为C卷\\一百分"; // 替换为你的目录路径
        String methodName = "myMethod"; // 要删除的函数名称

        removeMethodFromJavaFiles(directoryPath, methodName);
    }

    public static void removeMethodFromJavaFiles(String directoryPath, String methodName) {
        try {
            // 遍历目录下的所有文件
            Files.walk(Paths.get(directoryPath))
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".java"))
                    .forEach(path -> {
                        try {
                            // 读取文件内容
                            String content = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
                            // 删除所有符合条件的函数
                            content = content.replaceAll("public static String\\s+" + methodName + "\\s*\\(\\s*\\)[^\\{]*\\{[^\\}]*\\}", "");
                            // 将更新后的内容写回文件
                            Files.write(path, content.getBytes(StandardCharsets.UTF_8));
                            System.out.println("已从文件 " + path + " 中删除所有名为 " + methodName + "() 的函数。");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
            System.out.println("已从所有Java文件中删除所有名为 " + methodName + "() 的函数。");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
