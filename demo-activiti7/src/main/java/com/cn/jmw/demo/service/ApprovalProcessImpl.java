package com.cn.jmw.demo.service;

import com.cn.jmw.demo.activiti.facade.WorkflowService;
import com.cn.jmw.demo.aop.Workflow;
import com.cn.jmw.demo.aop.WorkflowEnum;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Workflow(value = WorkflowEnum.APPROVAL_PROCESS)
public class ApprovalProcessImpl implements ApprovalProcess {

    /**
     * RepositoryService：管理流程定义
     */
    private RepositoryService repositoryService;

    private WorkflowService workflowService;

    public ApprovalProcessImpl(RepositoryService repositoryService, WorkflowService workflowService) {
        this.repositoryService = repositoryService;
        this.workflowService = workflowService;
    }

    @Override
    public void createProcess() {
        // 创建部署
        repositoryService.createDeployment()
                .addClasspathResource("processes/ApprovalProcess.bpmn20.xml")
                .deploy();
    }

    @Override
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

    @Override
    public void approval(String processInstanceId) {
        //查询任务
        Task task = workflowService.byProcessInstanceIdQueryTodoTaskOne(processInstanceId);

        //完成任务
        workflowService.completeTask(task.getId(), null);
    }

    @Override
    public void queryProcess(String processInstanceId) {
        //查询流程实例
        List<HistoricTaskInstance> historicTaskInstances = workflowService.queryHistoryTask(processInstanceId);
        historicTaskInstances.stream()
                .forEach(historicTaskInstance -> {
                    //使用log
                    log.info("流程实例ID:{}", historicTaskInstance.getProcessInstanceId());
                    log.info("任务ID:{}", historicTaskInstance.getId());
                    log.info("任务负责人:{}", historicTaskInstance.getAssignee());
                    log.info("任务名称:{}", historicTaskInstance.getName());
                    log.info("任务开始时间:{}", historicTaskInstance.getStartTime());
                    log.info("任务结束时间:{}", historicTaskInstance.getEndTime());
                    log.info("任务持续时间:{}", historicTaskInstance.getDurationInMillis());
                    log.info("############################################");
                });
    }
}
