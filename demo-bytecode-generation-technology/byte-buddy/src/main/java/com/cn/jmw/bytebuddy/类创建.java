package com.cn.jmw.bytebuddy;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.NamingStrategy;
import net.bytebuddy.description.type.TypeDescription;
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
                //是Object的子类
                .subclass(Object.class)
                //包名.类名
                .name("Test1")
                .make();
        dynamicType.saveIn(new File("./target/classes"));
    }

    /**
     * 约定优于配置
     * <p>
     * 以上面的代码为例，通过使用ByteBuddy的`with`方法和`NamingStrategy`，我们可以定义一个命名策略。
     * 这种方法允许我们以约定的方式来配置类的命名，而不需要显式地进行配置。
     * <p>
     * 使用约定优于配置的方式，让代码更加简洁和灵活，同时也遵循了约定大于配置的设计原则，使得代码更易于理解和维护。
     */
    @Test
    public void test2() throws IOException {
        DynamicType.Unloaded<?> dynamicType = new ByteBuddy()
                // 约定大于配置 的 包名.类名
                .with(new NamingStrategy.AbstractBase() {
                    @Override
                    protected String name(TypeDescription superClass) {
                        return "i.love.ByteBuddy." + superClass.getSimpleName();
                    }
                })
                .subclass(Object.class)
                .make();
        dynamicType.saveIn(new File("./target/classes"));
    }

    /**
     * 每次创建的 全类名不一样 导致找不到
     */
    @Test
    public void test3() throws IOException {
        ByteBuddy byteBuddy = new ByteBuddy();
        //随机后缀
        byteBuddy.with((new NamingStrategy.SuffixingRandom("suffix")));
        DynamicType.Unloaded<?> dynamicType = byteBuddy.subclass(Object.class).make();
        dynamicType.saveIn(new File("./target/classes"));
    }

    /**
     * 重新定义已存在的类和对类变基(rebase)
     */
    @Test
    public void test4() throws IOException {
        DynamicType.Unloaded<Foo> make = new ByteBuddy().subclass(Foo.class).make();
        make.saveIn(new File("./target/classes"));
        DynamicType.Unloaded<Foo> make1 = new ByteBuddy().redefine(Foo.class).make();
        make1.saveIn(new File("./target/classes"));
        DynamicType.Unloaded<Foo> make2 = new ByteBuddy().rebase(Foo.class).make();
        make2.saveIn(new File("./target/classes"));
    }
    class Foo {
        String bar() {
            return "bar";
        }
    }
    /**
     * class Foo {
     *   String bar() { return "foo" + bar$original(); }
     *   private String bar$original() { return "bar"; }
     * }
     */

}

