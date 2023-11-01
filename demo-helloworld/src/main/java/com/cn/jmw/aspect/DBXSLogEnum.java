package com.cn.jmw.aspect;

public enum DBXSLogEnum {

    /**
     * 初筛线索_待办线索、待办线索_拆分、待办线索_合并、待办线索_分流、
     * 待办线索_审批通过、待办线索_审批驳回、待办线索_目标接收、待办线索_目标拒绝、
     * 待办线索_分配、待办线索_分配通过、待办线索_分配驳回、待处理线索_办结
     */

    /**
     *  其他
     */
    OTHER,

    /**
     * 初筛线索_待办线索
     */
    CLUE_SCREENING_TO_DO_CLUE,

    /**
     * 待办线索_拆分
     */
    TO_DO_CLUE_SPLIT,

    /**
     * 待办线索_合并
     */
    TO_DO_CLUE_MERGE,

    /**
     * 待办线索_分流
     */
    TO_DO_CLUE_FLOW,

    /**
     * 待办线索_审批通过
     */
    TO_DO_CLUE_APPROVE_PASS,

    /**
     * 待办线索_审批驳回
     */
    TO_DO_CLUE_APPROVE_REJECT,

    /**
     * 待办线索_目标接收
     */
    TO_DO_CLUE_TARGET_RECEIVE,

    /**
     * 待办线索_目标拒绝
     */
    TO_DO_CLUE_TARGET_REJECT,

    /**
     * 待办线索_分配
     */
    TO_DO_CLUE_DISTRIBUTE,

    /**
     * 待办线索_分配通过
     */
    TO_DO_CLUE_DISTRIBUTE_PASS,

    /**
     * 待办线索_分配驳回
     */
    TO_DO_CLUE_DISTRIBUTE_REJECT,

    /**
     * 待处理线索_办结
     */
    TO_DO_CLUE_FINISH;

}
