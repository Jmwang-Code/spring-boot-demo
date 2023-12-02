package com.cn.jmw.aop;

import com.cn.jmw.activiti.facade.WorkflowService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;

@Slf4j
public enum WorkflowEnum implements WorkflowEnumInterface{
    //Approval_process
    APPROVAL_PROCESS("ApprovalProcess") {

    };

    private String processId;

    private WorkflowService workflowService;

    WorkflowEnum(String processId) {
        this.processId = processId;
    }

    /**
     * 初始化
     */
    public void init() {
        workflowService = SpringBeanUtil.getBean(WorkflowService.class);
    }

    public String getProcessId() {
        return processId;
    }

    @Override
    public void handle(JoinPoint joinPoint, Object request, Object result) {
        boolean b = workflowService.checkProcessDefinition(this.getProcessId());
        //true 当前流程定义存在，false 当前流程定义不存在
        log.info("当前ProcessId:{}流程定义是否存在: {}", this.getProcessId(),b);
    }
}
