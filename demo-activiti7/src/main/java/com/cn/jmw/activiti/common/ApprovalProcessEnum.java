package com.cn.jmw.activiti.common;

public enum ApprovalProcessEnum {

    /**
     * 创建审批
     */
     CREATE_APPROVAL("create_approval_assignee"),

    /**
     * 审批
     */
    APPROVES("approves_assignee");

    private String code;

    ApprovalProcessEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
