package com.cn.jmw.demo.service;

public interface ApprovalProcess {

    /**
     * 创建流程
     */
    void createProcess();

    /**
     * 申请人发起申请
     */
    String startProcess(String processKey);

    /**
     * 审批人审批
     */
    void approval(String processInstanceId);

    /**
     * 查询流程
     */
    void queryProcess(String processInstanceId);
}
