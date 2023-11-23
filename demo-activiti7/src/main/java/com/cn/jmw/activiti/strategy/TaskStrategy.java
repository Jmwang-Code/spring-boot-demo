package com.cn.jmw.activiti.strategy;

import org.activiti.engine.task.Task;

public interface TaskStrategy {
    void execute(Task task);
}
