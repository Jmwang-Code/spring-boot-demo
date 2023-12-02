package com.cn.jmw.controller;
import com.cn.jmw.activiti.common.CommonEnum;
import com.cn.jmw.activiti.facade.WorkflowService;
import com.cn.jmw.activiti.factory.ProcessEngineFactory;
import org.activiti.engine.*;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController("/activiti")
public class ActivitiController {

    /**
     * RepositoryService：管理流程定义
     * RuntimeService：执行管理，包括启动、推进、删除流程实例等操作
     * TaskService：任务管理
     */
    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private ProcessEngineFactory processEngineFactory;

    @Autowired
    private WorkflowService workflowService;

    @GetMapping("/start-process")
    public String startProcess() {
        repositoryService.createDeployment()
                .addClasspathResource("processes/my-process.bpmn20.xml")
                .deploy();

        Map<String, Object> variables = new HashMap<>();
        variables.put("assignee", "user1");

        ProcessInstance processInstance =
                runtimeService.startProcessInstanceByKey("my-process", variables);

        ProcessEngine instance = processEngineFactory.getInstance();

        return "Process started. Process id: " + processInstance.getId();
    }

    @GetMapping("/complete-task")
    public String completeTask() {
        List<Task> list = taskService.createTaskQuery().list();
        list.stream().forEach(task -> taskService.complete(task.getId()));
        return "Task completed";
    }

    //请假流程
    @GetMapping("/start-process2")
    public String startProcess2() {
        repositoryService.createDeployment()
                .addClasspathResource("processes/LeaveApplicationProcess.bpmn20.xml")
                .deploy();

        Map<String, Object> variables = new HashMap<>();
        variables.put(CommonEnum.T1_ASSIGNEE.getCode(), "hello");

        // 校验流程定义是否存在
        if (this.repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey("LeaveApplicationProcess")
                .count() <= 0) throw new RuntimeException("指定流程定义不存在");

        ProcessInstance processInstance = workflowService.startProcessVariable("LeaveApplicationProcess", variables);

//        ProcessEngine instance = processEngineFactory.getInstance();

        return "Process started. Process id: " + processInstance.getId();
    }

    //请假流程
    @GetMapping("/complete-task2")
    public String completeTask2() {
        List<Task> list = taskService.createTaskQuery().list();
        list.stream().forEach(task -> taskService.complete(task.getId()));
        return "Task completed";
    }
}