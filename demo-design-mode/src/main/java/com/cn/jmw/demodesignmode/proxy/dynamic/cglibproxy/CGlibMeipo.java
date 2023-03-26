package com.cn.jmw.demodesignmode.proxy.dynamic.cglibproxy;

import net.sf.cglib.core.ClassGenerator;
import net.sf.cglib.core.DefaultGeneratorStrategy;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import net.sf.cglib.transform.TransformingClassGenerator;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;


/**
 * Created by Tom on 2019/3/11.
 */
public class CGlibMeipo implements MethodInterceptor {


    public Object getInstance(Class<?> clazz) throws Exception {
        //相当于Proxy，代理的工具类
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(this);
        Method defineClassMethod = ClassLoader.class.getDeclaredMethod("findLove", String.class, byte[].class, int.class, int.class, clazz);
        CGlibMeipo.setAccessible(defineClassMethod);
        return enhancer.create();
    }

    public static void setAccessible(AccessibleObject obj) {
        try {
            //如果当前版本的 Java 在运行时启用了“模块化”特性，则使用以下代码：
//        Module module = obj.getClass().getModule();
//        if (module.isNamed()) {
//            module.addOpens(
//                    obj.getClass().getPackageName(),
//                    CGlibMeipo.class.getModule()
//            );
//        }

            //否则，如果当前版本的 Java 没有启用“模块化”特性，则使用以下代码：
            Field field = AccessibleObject.class.getDeclaredField("override");
            field.setAccessible(true);
            field.setBoolean(obj, true);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        before();
        Object obj = methodProxy.invokeSuper(o, objects);
        after();
        return obj;
    }

    private void before() {
        System.out.println("我是媒婆，我要给你找对象，现在已经确认你的需求");
        System.out.println("开始物色");
    }

    private void after() {
        System.out.println("OK的话，准备办事");
    }
}
