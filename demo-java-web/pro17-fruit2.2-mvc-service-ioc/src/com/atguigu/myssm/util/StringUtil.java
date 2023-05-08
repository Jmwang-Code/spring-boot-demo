package com.atguigu.myssm.util;

/**
 * Created with IntelliJ IDEA.
 * To change it use File | Settings | Editor | File and Code Templates.
 *
 * @author Peter
 * @date 2022/3/21 14:58
 * @description TODO
 */
public class StringUtil {
    /**
     * 判断字符串是否为空或null
     */
    public static boolean isEmpty(String str) {
        return str == null || "".equals(str);
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

}
