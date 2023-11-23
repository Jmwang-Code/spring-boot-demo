package com.cn.jmw.controller;
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
@RestController
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
}