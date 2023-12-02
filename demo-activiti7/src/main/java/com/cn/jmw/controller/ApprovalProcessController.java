package com.cn.jmw.controller;

import com.cn.jmw.activiti.facade.WorkflowService;
import com.cn.jmw.aop.Workflow;
import com.cn.jmw.aop.WorkflowEnum;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 审批流
 *
 * 开始
 * 1. 申请人发起申请
 * 2. 审批人审批
 * 结束
 */
@RestController("/approval-process")
public class ApprovalProcessController {

    /**
     * RepositoryService：管理流程定义
     */
    private RepositoryService repositoryService;

    private WorkflowService workflowService;

    public ApprovalProcessController(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
        this.workflowService = workflowService;
    }

    /**
     * 创建流程
     */
    @Workflow(value = WorkflowEnum.APPROVAL_PROCESS)
    @GetMapping("/create-process")
    public void createProcess() {
        // 创建部署
        repositoryService.createDeployment()
                .addClasspathResource("processes/ApprovalProcess.bpmn20.xml")
                .deploy();
    }

    /**
     * 申请人发起申请
     */
    @Workflow(value = WorkflowEnum.APPROVAL_PROCESS)
    @GetMapping("/start-process")
    public String startProcess(String processKey) {
        //启动流程
        ProcessInstance processInstance = workflowService.startProcess(processKey);

        //实例ID,用于查询流程实例
        String processInstanceId = processInstance.getProcessInstanceId();

        //查询任务
        Task task = workflowService.byProcessInstanceIdQueryTodoTaskOne(processInstanceId);

        //任务负责人
        workflowService.replaceAssignee(task.getId(), "申请人");

        //完成任务
        workflowService.completeTask(task.getId());

        return processInstanceId;
    }

    /**
     * 审批人审批
     */
    @Workflow(value = WorkflowEnum.APPROVAL_PROCESS)
    @GetMapping("/approval-process")
    public void approvalProcess(String processInstanceId) {
        //查询任务
        Task task = workflowService.byProcessInstanceIdQueryTodoTaskOne(processInstanceId);

        //任务负责人
        workflowService.replaceAssignee(task.getId(), "审批人");

        //完成任务
        workflowService.completeTask(task.getId());
    }
}
