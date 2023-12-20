package com.cn.jmw.engine.service;

import com.cn.jmw.engine.pojo.ProcessCreatorParam;
import com.cn.jmw.engine.process.ProcessDynamicsFactory;
import org.springframework.stereotype.Service;

@Service
public class ProcessCreatorServiceImpl implements ProcessCreatorService{

    @Override
    public boolean createBPMN(ProcessCreatorParam processCreatorParam) {
        return new ProcessDynamicsFactory().createBPMN(processCreatorParam);
    }
}
