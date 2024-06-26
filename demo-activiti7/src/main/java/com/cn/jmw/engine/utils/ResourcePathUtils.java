package com.cn.jmw.engine.utils;

import java.io.File;
import java.net.URISyntaxException;
import java.security.CodeSource;

public class ResourcePathUtils {


    /**
     * 获取对应资源的绝对路径，比如jar同级目录下的processes文件夹，或者idea的resources目录下的processes文件夹
     */
    public static String getPath(String path) {
        //没有processes目录，就创建
        if (!new File(path).exists()) {
            new File(path).mkdirs();
        }

        String result = null;
        try {
            CodeSource codeSource = ResourcePathUtils.class.getProtectionDomain().getCodeSource();
            File jarFile = new File(codeSource.getLocation().toURI().getPath());
            result = jarFile.getParentFile().getPath() + File.separator + path;
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(getPath("processes"));
    }

}
