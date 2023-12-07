package com.cn.jmw.activiti.common;

/**
 * 公共枚举类
 */
public enum CommonEnum {

    /**
     * 流程状态
     */
    T1_ASSIGNEE("T1_Assignee"),
    T2_ASSIGNEE("T2_Assignee"),
    T3_ASSIGNEE("T3_Assignee"),
    T4_ASSIGNEE("T4_Assignee"),
    T5_ASSIGNEE("T5_Assignee");

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
