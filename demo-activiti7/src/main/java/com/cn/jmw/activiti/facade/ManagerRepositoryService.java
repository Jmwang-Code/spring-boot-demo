package com.cn.jmw.activiti.facade;

/**
 * 管理流程定义
 */
public interface ManagerRepositoryService {

    /**
     * 校验流程定义是否存在
     */
    public boolean checkProcessDefinition(String processKey);
}
