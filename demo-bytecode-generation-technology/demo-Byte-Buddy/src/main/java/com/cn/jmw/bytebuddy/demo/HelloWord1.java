package com.cn.jmw.bytebuddy.demo;

import com.cn.jmw.bytebuddy.Tests;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class HelloWord1 implements Tests {

    public static void main(String[] args) throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        new HelloWord1().test();
    }

    @Override
    public void test() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, IOException {
        Class<?> dynamicType = new ByteBuddy()
                // 创建一个类
                .subclass(Object.class)
                // 创建一个方法
                .method(ElementMatchers.named("toString"))
                // 方法的返回值
                .intercept(FixedValue.value("Hello World!"))
                // 创建类
                .make()
                // 加载类
                .load(getClass().getClassLoader())
                // 获取加载后的类
                .getLoaded();

        assertThat(dynamicType.getDeclaredConstructor().newInstance().toString(), is("Hello World!"));
    }
}
