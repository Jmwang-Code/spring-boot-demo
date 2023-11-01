package com.cn.jmw.aspect;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE})
@Documented
public @interface Loggable {

    DBXSLogEnum value() default DBXSLogEnum.OTHER;

}
