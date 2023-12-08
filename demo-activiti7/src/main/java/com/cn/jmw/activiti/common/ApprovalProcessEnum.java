package com.cn.jmw.activiti.common;

/**
 * 审批流程枚举
 */
public enum ApprovalProcessEnum {

    /**
     * 创建审批
     */
     CREATE_APPROVAL("create_approval_assignee"),

    /**
     * 审批
     */
    APPROVES("approves_assignee");

    private final String code;

    ApprovalProcessEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return code;
    }
}
