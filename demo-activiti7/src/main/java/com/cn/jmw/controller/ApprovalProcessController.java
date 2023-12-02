package com.cn.jmw.controller;

import com.cn.jmw.activiti.facade.WorkflowService;
import com.cn.jmw.aop.Workflow;
import com.cn.jmw.aop.WorkflowEnum;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public ApprovalProcessController(RepositoryService repositoryService, WorkflowService workflowService) {
        this.repositoryService = repositoryService;
        this.workflowService = workflowService;
    }

    /**
     * 创建流程
     *
     * http://localhost:8080/create-process3
     */
    @Workflow(value = WorkflowEnum.APPROVAL_PROCESS)
    @GetMapping("/create-process3")
    public void createProcess() {
        // 创建部署
        repositoryService.createDeployment()
                .addClasspathResource("processes/ApprovalProcess.bpmn20.xml")
                .deploy();
    }

    /**
     * 申请人发起申请
     *
     * http://localhost:8080/start-process3?processKey=ApprovalProcess
     */
    @Workflow(value = WorkflowEnum.APPROVAL_PROCESS)
    @GetMapping("/start-process3")
    public String startProcess(String processKey) {
        //变量
        Map<String, Object> variablesA = new HashMap<>();
        variablesA.put("create_approval_assignee", "开始人");

        //启动流程
        ProcessInstance processInstance = workflowService.startProcess(processKey, variablesA);

        //实例ID,用于查询流程实例
        String processInstanceId = processInstance.getProcessInstanceId();

        //查询任务
        Task task = workflowService.byProcessInstanceIdQueryTodoTaskOne(processInstanceId);

        Map<String, Object> variablesB = new HashMap<>();
        variablesB.put("approves_assignee", "审批人");

        //完成任务
        workflowService.completeTask(task.getId(), variablesB);

        return processInstanceId;
    }

    /**
     * 审批人审批
     *
     * http://localhost:8080/end-process3?processInstanceId=c7504ea7-913f-11ee-a357-ce47401b8f36
     */
    @Workflow(value = WorkflowEnum.APPROVAL_PROCESS)
    @GetMapping("/end-process3")
    public void approvalProcess(String processInstanceId) {
        //查询任务
        Task task = workflowService.byProcessInstanceIdQueryTodoTaskOne(processInstanceId);

        //完成任务
        workflowService.completeTask(task.getId(), null);
    }

    /**
     * 查询流程实例
     *
     * http://localhost:8080/query-process3?processInstanceId=c7504ea7-913f-11ee-a357-ce47401b8f36
     */
    @Workflow(value = WorkflowEnum.APPROVAL_PROCESS)
    @GetMapping("/query-process3")
    public void queryProcess(String processInstanceId) {
        //查询流程实例
        List<HistoricTaskInstance> historicTaskInstances = workflowService.queryHistoryTask(processInstanceId);
        historicTaskInstances.stream()
                .forEach(historicTaskInstance -> {
                    System.out.println("任务ID：" + historicTaskInstance.getId());
                    System.out.println("任务名称：" + historicTaskInstance.getName());
                    System.out.println("任务的创建时间：" + historicTaskInstance.getCreateTime());
                    System.out.println("任务的结束时间：" + historicTaskInstance.getEndTime());
                    System.out.println("任务持续的时间：" + historicTaskInstance.getDurationInMillis());
                    System.out.println("任务的办理人：" + historicTaskInstance.getAssignee());
                    System.out.println("流程实例ID：" + historicTaskInstance.getProcessInstanceId());
                    System.out.println("############################################");
                });
    }
}
