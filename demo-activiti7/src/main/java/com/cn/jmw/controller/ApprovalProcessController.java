package com.cn.jmw.controller;

import com.cn.jmw.activiti.facade.WorkflowService;
import org.activiti.engine.RepositoryService;
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
    @GetMapping("/create-process")
    public void createProcess() {
        // 校验流程定义是否存在
        boolean checkProcessDefinition = workflowService.checkProcessDefinition("ApprovalProcess");
        if (!checkProcessDefinition) {
            return;
        }

        // 创建部署
        repositoryService.createDeployment()
                .addClasspathResource("processes/ApprovalProcess.bpmn20.xml")
                .deploy();
    }

    /**
     * 申请人发起申请
     */
    public void startProcess() {

    }

    /**
     * 审批人审批
     */
    public void approvalProcess() {

    }
}
