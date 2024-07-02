package com.cn.jmw.bytebuddy.utils;

import net.bytebuddy.dynamic.DynamicType;

import java.io.File;
import java.io.IOException;

public class ClassGenerator {

    //保存.class文件的基本路径
    static final String BASE_PATH = "./demo-bytecode-generation-technology/demo-Byte-Buddy/target/classes";


    public static boolean generateClass(DynamicType.Unloaded<?> dynamicType, String directoryPath) {

        // Save the .class file in the specified directory
        try {
            directoryPath = new File(directoryPath).getAbsolutePath();
            dynamicType.saveIn(new File(directoryPath));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}