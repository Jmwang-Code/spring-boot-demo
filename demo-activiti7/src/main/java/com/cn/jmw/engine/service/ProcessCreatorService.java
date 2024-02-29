package com.cn.jmw.engine.service;

import com.cn.jmw.engine.pojo.ProcessCreatorParam;

/**
 * 流程创建服务
 */
public interface ProcessCreatorService {

    /**
     * 创建BPMN
     */
    public boolean createBPMN(ProcessCreatorParam processCreatorParam);

}
