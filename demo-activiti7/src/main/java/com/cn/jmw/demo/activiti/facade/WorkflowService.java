package com.cn.jmw.demo.activiti.facade;

import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import java.util.List;
import java.util.Map;

public interface WorkflowService {


    /**
     * 启动流程
     *
     * @param processKey 流程key
     * @param variables  流程变量
     */
    public ProcessInstance startProcessVariable(String processKey, Map<String, Object> variables);

    /**
     * 启动流程
     *
     * @param processKey 流程key
     */
    public ProcessInstance startProcess(String processKey,Map<String, Object> processVariables);

    /**
     * 查询任务
     *
     * @param assignee 任务负责人
     * @return 任务列表
     */
    public List<Task> queryTaskForList(String assignee);

    /**
     * 查询任务
     *
     * @param assignee 任务负责人
     * @return 一个任务
     */
    public Task queryTaskForOne(String assignee);

    /**
     * 查询任务:查询一个流程的唯一性ID
     *
     * @param assignee   任务负责人
     * @param processKey 流程key
     * @return 任务列表
     */
    public List<Task> queryTaskForList(String assignee, String processKey);

    /**
     * 查询任务:查询一个流程的唯一性ID
     *
     * @param assignee   任务负责人
     * @param processKey 流程key
     * @return 一个任务
     */
    public Task queryTaskForOne(String assignee, String processKey);

    /**
     * 查询任务:查询一个流程的唯一性ID
     *
     * @param assignee    任务负责人
     * @param processKey  流程key
     * @param businessKey 业务key
     * @return 任务列表
     */
    public List<Task> queryTaskForList(String assignee, String processKey, String businessKey);

    /**
     * 查询任务:查询一个流程的唯一性ID
     *
     * @param assignee    任务负责人
     * @param processKey  流程key
     * @param businessKey 业务key
     * @return 一个任务
     */
    public Task queryTaskForOne(String assignee, String processKey, String businessKey);

    /**
     * 挂起流程
     *
     * @param processInstanceId 流程实例id
     */
    public void suspendProcess(String processInstanceId);

    /**
     * 激活流程
     *
     * @param processInstanceId 流程实例id
     */
    public void activateProcess(String processInstanceId);

    /**
     * 设置流程变量
     */
    public void setVariable(String taskId, String variableName, Object value);

    /**
     * 完成任务
     *
     * @param taskId 任务id
     */
    public void completeTask(String taskId, Map<String, Object> variables);

    /**
     * 删除流程
     *
     * @param processInstanceId 流程实例id
     */
    public void deleteProcess(String processInstanceId);

    /**
     * 删除流程
     *
     * @param processInstanceId 流程实例id
     * @param deleteReason 删除原因
     */
    public void deleteProcess(String processInstanceId, String deleteReason);

    /**
     * 根据流程实例id查询流程实例
     *
     * @param processInstanceId 流程实例id
     */
    public List<Task> byProcessInstanceIdQueryTodoTaskList(String processInstanceId);

    /**
     * 根据流程实例id查询流程实例
     *
     * @param processInstanceId 流程实例id
     */
    public Task byProcessInstanceIdQueryTodoTaskOne(String processInstanceId);

    /**
     * 替换任务负责人
     */
    public void replaceAssignee(String taskId, String userId);

    /**
     * 查询流程实例
     */
    public ProcessInstance queryProcessInstance(String processInstanceId);

    /**
     * 查询历史任务
     */
    public List<HistoricTaskInstance> queryHistoryTask(String processInstanceId);
}
