package com.cn.engine.utils;

import cn.hutool.core.io.resource.ResourceUtil;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;

public class ResourcePathUtils {

    /**
     * 通过ClassLoader获取Path
     * @param resourcePath 资源路径resources下比如"processes/"
     * @return
     */
    public static String getClassLoaderPath(String resourcePath) {
        String path = null;

        // 获取类加载器
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        // 使用类加载器获取资源路径
        URL resource = classLoader.getResource(resourcePath);

        if (resource != null) {
            path = new File(resource.getFile()).getAbsolutePath();
            System.out.println("ClassLoader: " + path);
        } else {
            System.out.println("Resource not found.");
        }
        return path;
    }

    /**
     * 通过SystemResource获取Path
     * @param resourcePath 资源路径resources下比如"processes/"
     * @return
     */
    public static String getSystemResourcePath(String resourcePath) {
        String path = null;

        // 使用ClassLoader获取资源路径
        URL resource = ClassLoader.getSystemResource(resourcePath);

        if (resource != null) {
            path = new File(resource.getFile()).getAbsolutePath();
            System.out.println("SystemResource: " + path);
        } else {
            System.out.println("Resource not found.");
        }
        return path;
    }

    /**
     * 通过ResourceUtil获取Path
     * @param resourcePath 资源路径resources下比如"/processes"
     * @return
     */
    public static String getClassPath(String resourcePath) {
        String path = null;

        // 使用类获取资源路径
        URL resource = ResourceUtil.class.getResource(resourcePath);

        if (resource != null) {
            path = new File(resource.getFile()).getAbsolutePath();
            System.out.println("Class: " + path);
        } else {
            System.out.println("Resource not found.");
        }
        return path;
    }

    public static void main(String[] args) {
        String resourcePath = "processes/";
        String classLoaderPath = getClassLoaderPath(resourcePath);
        String systemResourcePath = getSystemResourcePath(resourcePath);
        String classPath = getClassPath("/processes");
        System.out.println(classLoaderPath);
        System.out.println(systemResourcePath);
        System.out.println(classPath);
    }

}
