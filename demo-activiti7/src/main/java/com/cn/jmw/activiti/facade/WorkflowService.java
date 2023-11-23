package com.cn.jmw.activiti.facade;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;

public class WorkflowService {
    private ProcessEngine processEngine;
    private RuntimeService runtimeService;
    private TaskService taskService;

    public WorkflowService(ProcessEngine processEngine) {
        this.processEngine = processEngine;
        this.runtimeService = processEngine.getRuntimeService();
        this.taskService = processEngine.getTaskService();
    }

    public void startProcess(String processKey) {
        runtimeService.startProcessInstanceByKey(processKey);
    }

    public void completeTask(String taskId) {
        taskService.complete(taskId);
    }
}
