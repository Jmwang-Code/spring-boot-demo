package com.cn.jmw.demo.controller;

import com.cn.jmw.demo.aop.Workflow;
import com.cn.jmw.demo.aop.WorkflowEnum;
import com.cn.jmw.demo.service.ApprovalProcess;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RestController("/approval-process")
public class ApprovalProcessController {

    /**
     * RepositoryService：管理流程定义
     */
    private ApprovalProcess approvalProcess;

    public ApprovalProcessController(ApprovalProcess approvalProcess) {
        this.approvalProcess = approvalProcess;
    }

    /**
     * 创建流程
     *
     * http://localhost:8080/create-process3
     */
    @Workflow(value = WorkflowEnum.APPROVAL_PROCESS)
    @GetMapping("/create-process3")
    public void createProcess() {
        approvalProcess.createProcess();
    }

    /**
     * 申请人发起申请
     *
     * http://localhost:8080/start-process3?processKey=ApprovalProcess
     */
    @Workflow(value = WorkflowEnum.APPROVAL_PROCESS)
    @GetMapping("/start-process3")
    public String startProcess(String processKey) {
        return approvalProcess.startProcess(processKey);
    }

    /**
     * 审批人审批
     *
     * http://localhost:8080/end-process3?processInstanceId=c7504ea7-913f-11ee-a357-ce47401b8f36
     */
    @Workflow(value = WorkflowEnum.APPROVAL_PROCESS)
    @GetMapping("/end-process3")
    public void approvalProcess(String processInstanceId) {
        approvalProcess.approval(processInstanceId);
    }

    /**
     * 查询流程实例
     *
     * http://localhost:8080/query-process3?processInstanceId=c7504ea7-913f-11ee-a357-ce47401b8f36
     */
    @Workflow(value = WorkflowEnum.APPROVAL_PROCESS)
    @GetMapping("/query-process3")
    public void queryProcess(String processInstanceId) {
        approvalProcess.queryProcess(processInstanceId);
    }
}
