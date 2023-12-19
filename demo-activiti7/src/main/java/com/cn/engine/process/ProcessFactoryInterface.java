package com.cn.engine.process;

import com.cn.engine.pojo.ProcessCreatorParam;

/**
 * 流程工厂接口
 */
public interface ProcessFactoryInterface {

    /**
     * 创建BPMN
     */
    public boolean createBPMN(ProcessCreatorParam processCreatorParam);

    /**
     * 流程定义
     */
    public boolean deployProcess(ProcessCreatorParam processCreatorParam);
}
