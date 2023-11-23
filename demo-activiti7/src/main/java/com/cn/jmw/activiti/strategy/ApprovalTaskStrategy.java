package com.cn.jmw.activiti.strategy;

import org.activiti.engine.task.Task;

public class ApprovalTaskStrategy implements TaskStrategy {
    @Override
    public void execute(Task task) {
        // 处理审批任务
        System.out.println("Executing approval task: " + task.getName());
    }
}