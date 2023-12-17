package com.cn.engine.controller;

import com.cn.engine.pojo.ProcessCreatorParam;
import com.cn.engine.service.ProcessCreatorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ProcessCreatorController {

    @Resource
    private ProcessCreatorService processCreatorService;

    /**
     * 创建BPMN
     */
    @GetMapping("/createBPMN")
    public boolean createBPMN() {
        List<String> approversList = new ArrayList<>();
        approversList.add("A");
        approversList.add("B");
        approversList.add("C");

        ProcessCreatorParam processCreatorParam = ProcessCreatorParam.builder()
                .processName("请假流程")
                .creator("张三")
                .approvers(approversList)
                .build();

        return processCreatorService.createBPMN(processCreatorParam);
    }
}
