package com.cn.jmw.华为C卷.tools;

import java.io.*;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;

public class AddMethodToJavaFiles {

    public static void main(String[] args) {
        String directoryPath = "C:\\Users\\wjm\\IdeaProjects\\spring-boot-demo\\algorithm\\src\\main\\java\\com\\cn\\jmw\\华为C卷\\一百分"; // 替换为你的目录路径
        String methodName = "\n    public static void main(String[] args) {\n\n    }\n";
        String pre = "    public static void main(String[] args) {";
        addMethodToJavaFiles(directoryPath, methodName,pre);
    }

    public static void addMethodToJavaFiles(String directoryPath, String method,String pre) {
        try {
            // 遍历目录下的所有文件
            Files.walk(Paths.get(directoryPath))
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".java"))
                    .forEach(path -> {
                        try {
                            // 读取文件内容
                            String content = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
                            // 检查文件中是否已存在相同名称的函数
                            if (!content.contains(pre.split("\\{")[0].trim())) {
                                // 找到最后一个 } 符号的位置
                                int lastIndex = content.lastIndexOf("}");
                                // 在最后一个 } 符号的上一行添加方法
                                String updatedContent = content.substring(0, lastIndex) + method + "\n" + content.substring(lastIndex);
                                // 将更新后的内容写回文件
                                Files.write(path, updatedContent.getBytes(StandardCharsets.UTF_8));
                                System.out.println("方法已成功添加到文件：" + path);
                            } else {
                                System.out.println("文件 " + path + " 中已存在相同名称的函数，不做任何修改。");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
            System.out.println("方法已成功添加到所有Java文件中！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
