package com.cn.jmw;


import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.builders.ProcessPayloadBuilder;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringBootTestActivitiApplication {

    /**
     * 生成activiti的数据库表
     */
    @Test
    void testCreateDbTable() {
        //使用默认配置文件
//        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //使用自定义配置文件
        //ProcessEngine processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml").buildProcessEngine();
        //使用自定义配置文件并指定bean的id
        //ProcessEngine processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml","processEngineConfiguration").buildProcessEngine();
        //使用自定义配置文件并指定bean的id和bean的名称
        //ProcessEngine processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml","processEngineConfiguration","processEngineConfiguration").buildProcessEngine();
        //使用自定义配置文件并指定bean的id和bean的名称和bean的别名
    }

}
