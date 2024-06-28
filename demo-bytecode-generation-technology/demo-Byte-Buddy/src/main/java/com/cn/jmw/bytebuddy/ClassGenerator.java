package com.cn.jmw.bytebuddy;

import net.bytebuddy.dynamic.DynamicType;

import java.io.File;
import java.io.IOException;

public class ClassGenerator {
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