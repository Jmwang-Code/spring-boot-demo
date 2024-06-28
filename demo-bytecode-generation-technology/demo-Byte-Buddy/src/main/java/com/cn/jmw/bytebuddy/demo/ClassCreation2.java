package com.cn.jmw.bytebuddy.demo;

import cn.jt.bds.module.assets.bytebuddy.ClassGenerator;
import cn.jt.bds.module.assets.bytebuddy.FileDeleter;
import cn.jt.bds.module.assets.bytebuddy.Tests;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.NamingStrategy;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class ClassCreation2 implements Tests {

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, IOException {
        new ClassCreation2().test();
    }


    @Override
    public void test() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, IOException {
        DynamicType.Unloaded<?> dynamicType = new ByteBuddy()
                .subclass(Object.class)
                // 设置类名
                .name("example.Type")
                .make();

        boolean success = ClassGenerator.generateClass(dynamicType, "./demo-bytecode-generation-technology/demo-Byte-Buddy/target/classes");
        if (success) {
            System.out.println("Class file was successfully created.");
        } else {
            System.out.println("Failed to create class file.");
        }

        DynamicType.Unloaded<?> dynamicType2 = new ByteBuddy()
                .with(new NamingStrategy.AbstractBase() {
                    @Override
                    protected String name(TypeDescription superClass) {
                        return "i.love.ByteBuddy." + superClass.getSimpleName();
                    }
                })
                .subclass(Object.class)
                .make();

        boolean success2 = ClassGenerator.generateClass(dynamicType2, "./demo-bytecode-generation-technology/demo-Byte-Buddy/target/classes");

        if (success2) {
            System.out.println("Class file was successfully created.");
        } else {
            System.out.println("Failed to create class file.");
        }

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        FileDeleter.deleteFile("./demo-bytecode-generation-technology/demo-Byte-Buddy/target/classes/example/Type.class");
    }
}
