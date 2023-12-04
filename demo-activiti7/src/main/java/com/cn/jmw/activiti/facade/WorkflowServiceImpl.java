package com.cn.jmw.activiti.facade;

import org.activiti.engine.*;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class WorkflowServiceImpl implements ManagerRepositoryService
                                            ,ManagerRuntimeService
                                            ,ManagerTaskService
                                            ,ManagerHistoryService
                                            ,ManagerProcessEngine
                                            ,WorkflowService{
    /**
     * RepositoryService：管理流程定义
     * RuntimeService：流程管理，执行管理，包括启动、推进、删除流程实例等操作
     * TaskService：任务管理
     * ProcessEngine：流程引擎对象
     */
    private RepositoryService repositoryService;
    private RuntimeService runtimeService;
    private TaskService taskService;
    private ProcessEngine processEngine;
    private HistoryService historyService;

    public WorkflowServiceImpl(ProcessEngine processEngine) {
        this.repositoryService = processEngine.getRepositoryService();
        this.processEngine = processEngine;
        this.runtimeService = processEngine.getRuntimeService();
        this.taskService = processEngine.getTaskService();
        this.historyService = processEngine.getHistoryService();
    }


    /**
     * ************************           ManagerRepositoryService           ************************
     * 校验流程定义是否存在
     *
     * @param processKey
     * @return true 当前流程定义存在，false 当前流程定义不存在
     */
    public boolean checkProcessDefinition(String processKey) {
        long count = repositoryService.createProcessDefinitionQuery().processDefinitionKey(processKey).count();
        return count > 0;
    }

    /**
     * ************************           ManagerRepositoryService           ************************
     * 启动流程
     *
     * @param processKey 流程key
     * @param variables  流程变量
     */
    public ProcessInstance startProcessVariable(String processKey, Map<String, Object> variables) {
        return runtimeService.startProcessInstanceByKey(processKey, variables);
    }

    /**
     * 启动流程
     *
     * @param processKey 流程key
     */
    public ProcessInstance startProcess(String processKey,Map<String, Object> processVariables) {
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processKey,processVariables);
        return processInstance;
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
     * @param assignee   任务负责人
     * @param processKey 流程key
     * @return 任务列表
     */
    public List<Task> queryTaskForList(String assignee, String processKey) {
        return taskService.createTaskQuery().taskAssignee(assignee).processDefinitionKey(processKey).list();
    }

    /**
     * 查询任务:查询一个流程的唯一性ID
     *
     * @param assignee   任务负责人
     * @param processKey 流程key
     * @return 一个任务
     */
    public Task queryTaskForOne(String assignee, String processKey) {
        return taskService.createTaskQuery().taskAssignee(assignee).processDefinitionKey(processKey).singleResult();
    }

    /**
     * 查询任务:查询一个流程的唯一性ID
     *
     * @param assignee    任务负责人
     * @param processKey  流程key
     * @param businessKey 业务key
     * @return 任务列表
     */
    public List<Task> queryTaskForList(String assignee, String processKey, String businessKey) {
        return taskService.createTaskQuery().taskAssignee(assignee).processDefinitionKey(processKey).processInstanceBusinessKey(businessKey).list();
    }

    /**
     * 查询任务:查询一个流程的唯一性ID
     *
     * @param assignee    任务负责人
     * @param processKey  流程key
     * @param businessKey 业务key
     * @return 一个任务
     */
    public Task queryTaskForOne(String assignee, String processKey, String businessKey) {
        return taskService.createTaskQuery().taskAssignee(assignee).processDefinitionKey(processKey).processInstanceBusinessKey(businessKey).singleResult();
    }

    /**
     * 挂起流程
     *
     * @param processInstanceId 流程实例id
     */
    public void suspendProcess(String processInstanceId) {
        runtimeService.suspendProcessInstanceById(processInstanceId);
    }

    /**
     * 激活流程
     *
     * @param processInstanceId 流程实例id
     */
    public void activateProcess(String processInstanceId) {
        runtimeService.activateProcessInstanceById(processInstanceId);
    }

    /**
     * 设置流程变量
     */
    public void setVariable(String taskId, String variableName, Object value) {
        taskService.setVariable(taskId, variableName, value);
    }

    /**
     * 完成任务
     *
     * @param taskId 任务id
     */
    public void completeTask(String taskId, Map<String, Object> variables) {
        taskService.complete(taskId, variables);
    }

    /**
     * 删除流程
     *
     * @param processInstanceId 流程实例id
     */
    public void deleteProcess(String processInstanceId) {
        runtimeService.deleteProcessInstance(processInstanceId, "删除流程");
    }

    /**
     * 删除流程
     *
     * @param processInstanceId 流程实例id
     * @param deleteReason 删除原因
     */
    public void deleteProcess(String processInstanceId, String deleteReason) {
        runtimeService.deleteProcessInstance(processInstanceId, deleteReason);
    }

    /**
     * 查询待办任务
     */
    public List<Task> byProcessInstanceIdQueryTodoTaskList(String processInstanceId) {
        return taskService.createTaskQuery().processInstanceId(processInstanceId).list();
    }

    /**
     * 查询待办任务 ONE
     */
    public Task byProcessInstanceIdQueryTodoTaskOne(String processInstanceId) {
        return taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
    }

    @Override
    public void replaceAssignee(String taskId, String userId) {
        taskService.setAssignee(taskId, userId);
    }

    /**
     * 查询流程实例
     */
    public ProcessInstance queryProcessInstance(String processInstanceId) {
        return runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
    }

    /**
     * 查询历史任务
     */
    public List<HistoricTaskInstance> queryHistoryTask(String processInstanceId) {
        return historyService.createHistoricTaskInstanceQuery().processInstanceId(processInstanceId).list();
    }

}
