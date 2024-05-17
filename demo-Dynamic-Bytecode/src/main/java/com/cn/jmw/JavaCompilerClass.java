package com.cn.jmw;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.FileWriter;
import java.io.IOException;

public class JavaCompilerClass {

    public static void main(String[] args) throws IOException {
        // 创建Java源代码
        String sourceCode = "public class DynamicClass {\n" +
                "    public void hello() {\n" +
                "        System.out.println(\"Hello, World!\");\n" +
                "    }\n" +
                "}";

        // 将源代码写入到.java文件中
        String fileName = "DynamicClass.java";
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(sourceCode);
        }

        // 获取系统Java编译器
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        // 编译源代码
        compiler.run(null, null, null, fileName);
    }
}
