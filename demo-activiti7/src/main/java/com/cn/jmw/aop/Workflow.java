package com.cn.jmw.aop;

import org.jetbrains.annotations.NotNull;
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE})
@Documented
public @interface Workflow {

    /**
     * 选择的日志存储动作
     */
    @NotNull
    WorkflowEnum value();
}
