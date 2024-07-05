package com.cn.jmw.bytebuddy;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class 类创建 {

    /**
     * 创建一个类
     * name 可以是类名 ，也可以是包名.类名
     */
    @Test
    public void test1() throws IOException {
        DynamicType.Unloaded<?> dynamicType = new ByteBuddy()
                .subclass(Object.class)
                .name("Test1")
                .make();
        dynamicType.saveIn(new File("./target/classes"));
    }


}
