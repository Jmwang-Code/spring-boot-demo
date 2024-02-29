package com.cn.jmw.demo.activiti.factory;


import org.activiti.engine.ProcessEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * ProcessEngine是Activiti的核心，使用它可以完成很多操作，比如：
 *
 * 1. 部署流程定义
 * 2. 启动流程实例
 * 3. 查询流程实例、任务等
 * 4. 完成任务等
 *
 */
@Component
public class ProcessEngineFactory {

    private final ProcessEngine processEngine;

    @Autowired
    public ProcessEngineFactory(ProcessEngine processEngine) {
        this.processEngine = processEngine;
    }

    public ProcessEngine getInstance() {
        return processEngine;
    }
}
