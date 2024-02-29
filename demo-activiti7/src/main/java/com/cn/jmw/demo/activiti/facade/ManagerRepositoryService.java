package com.cn.jmw.demo.activiti.facade;

/**
 * 管理流程定义
 */
public interface ManagerRepositoryService {

    /**
     * 校验流程定义是否存在
     */
    public boolean checkProcessDefinition(String processKey);
}
