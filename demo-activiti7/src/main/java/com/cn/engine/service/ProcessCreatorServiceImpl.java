package com.cn.engine.service;

import com.cn.engine.pojo.ProcessCreatorParam;
import com.cn.engine.process.ProcessDynamicsFactory;
import org.springframework.stereotype.Service;

@Service
public class ProcessCreatorServiceImpl implements ProcessCreatorService{

    @Override
    public boolean createBPMN(ProcessCreatorParam processCreatorParam) {
        return new ProcessDynamicsFactory().createBPMN(processCreatorParam);
    }
}
