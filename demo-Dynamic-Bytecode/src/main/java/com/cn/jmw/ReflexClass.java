package com.cn.jmw;

import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class ReflexClass {
    public static void main(String[] args) throws Exception  {

        // 获取新生成的.class文件的URL
        URL[] urls = new URL[]{new URL("file:/path/to/your/class/files/")};

        // 创建一个新的类加载器
        ClassLoader loader = new URLClassLoader(urls);

        // 加载类
        Class<?> clazz = loader.loadClass("DynamicClass");

        // 创建类的实例
        Object instance = clazz.getDeclaredConstructor().newInstance();

        // 获取方法
        Method method = clazz.getMethod("hello");

        // 调用方法
        method.invoke(instance);
    }
}
