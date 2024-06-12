package com.cn.jmw.dependencyinjection;

import java.lang.annotation.*;

/**
 * 这是一个名为Inject的自定义注解。
 * 它用于标记需要注入实例的字段。
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Inject {
}