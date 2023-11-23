package com.cn.jmw.activiti.facade;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class WorkflowService {
    private ProcessEngine processEngine;
    private RuntimeService runtimeService;
    private TaskService taskService;

    public WorkflowService(ProcessEngine processEngine) {
        this.processEngine = processEngine;
        this.runtimeService = processEngine.getRuntimeService();
        this.taskService = processEngine.getTaskService();
    }

    /**
     * 启动流程
     *
     * @param processKey 流程key
     * @param variables  流程变量
     */
    public void startProcessVariable(String processKey, Map<String, Object> variables) {
        runtimeService.startProcessInstanceByKey(processKey, variables);
    }

    /**
     * 启动流程
     *
     * @param processKey 流程key
     */
    public void startProcess(String processKey) {
        runtimeService.startProcessInstanceByKey(processKey);
    }

    /**
     * 查询任务
     *
     * @param assignee 任务负责人
     * @return 任务列表
     */
    public List<Task> queryTaskForList(String assignee) {
        return taskService.createTaskQuery().taskAssignee(assignee).list();
    }

    /**
     * 查询任务
     *
     * @param assignee 任务负责人
     * @return 一个任务
     */
    public Task queryTaskForOne(String assignee) {
        return taskService.createTaskQuery().taskAssignee(assignee).singleResult();
    }

    /**
     * 查询任务:查询一个流程的唯一性ID
     *
     * @param assignee 任务负责人
     * @param processKey 流程key
     * @return 任务列表
     */
    public List<Task> queryTaskForList(String assignee, String processKey) {
        return taskService.createTaskQuery().taskAssignee(assignee).processDefinitionKey(processKey).list();
    }

    /**
     * 查询任务:查询一个流程的唯一性ID
     *
     * @param assignee 任务负责人
     * @param processKey 流程key
     * @return 一个任务
     */
    public Task queryTaskForOne(String assignee, String processKey) {
        return taskService.createTaskQuery().taskAssignee(assignee).processDefinitionKey(processKey).singleResult();
    }

    /**
     * 完成任务
     *
     * @param taskId 任务id
     */
    public void completeTask(String taskId) {
        taskService.complete(taskId);
    }
}
