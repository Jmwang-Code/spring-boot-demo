package com.cn.engine.process;

import org.springframework.core.io.ResourceLoader;
import com.cn.engine.pojo.ProcessCreatorParam;
import com.cn.engine.utils.SnowFlakeUtil;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.RepositoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class ProcessDynamicsFactory implements ProcessFactoryInterface{

    @Resource
    private RepositoryService repositoryService;

    @Resource
    private ResourceLoader resourceLoader;

    /**
     * 创建一个BPMN模型
     * @param processCreatorParam
     * @return
     */
    public boolean deployProcessDefinition(ProcessCreatorParam processCreatorParam) {
        // 创建BpmnModel对象
        BpmnModel bpmnModel = new BpmnModel();

        // 创建流程定义对象
        Process process = new Process();
        process.setId("customProcess");
        process.setName(SnowFlakeUtil.getSecondId()+processCreatorParam.getProcessName());
        bpmnModel.addProcess(process);

        // 创建创建人任务
        UserTask creatorTask = new UserTask();
        creatorTask.setId("creatorTask");
        creatorTask.setName("Create Task");
        creatorTask.setAssignee(processCreatorParam.getCreator());
        process.addFlowElement(creatorTask);

        // 创建审批任务
        for (String approver : processCreatorParam.getApprovers()) {
            UserTask approvalTask = new UserTask();
            approvalTask.setId("approvalTask_" + approver);
            approvalTask.setName("Approval Task for " + approver);
            approvalTask.setAssignee(approver);
            process.addFlowElement(approvalTask);
        }

        // 结束
        UserTask endTask = new UserTask();
        endTask.setId("endTask");
        endTask.setName("End Task");
        process.addFlowElement(endTask);

        // 输出BPMN XML到文件
        try {
            org.springframework.core.io.Resource resource = resourceLoader.getResource("classpath:processes/");
            String processesPath = resource.getFile().getAbsolutePath();

            // 输出 BPMN XML 到文件
            try (FileOutputStream fos = new FileOutputStream(new File(processesPath, process+".bpmn20.xml"))) {
                String bpmnXml = new String(new BpmnXMLConverter().convertToXML(bpmnModel));
                fos.write(bpmnXml.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

}
