package com.cn.jmw.activiti.common;

public enum CommonEnum {

    /**
     * 流程状态
     */
    ASSIGNEE("T1_Assignee");

    private String code;

    CommonEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
