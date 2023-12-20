package com.cn.jmw.demo.activiti.strategy;

import org.activiti.engine.task.Task;

public class ReviewTaskStrategy implements TaskStrategy {
    @Override
    public void execute(Task task) {
        // 处理审核任务
        System.out.println("Executing review task: " + task.getName());
    }
}