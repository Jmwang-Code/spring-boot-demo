package com.cn.jmw.engine.process;

import com.cn.jmw.engine.pojo.ProcessCreatorParam;
import com.cn.jmw.engine.utils.ResourcePathUtils;
import com.cn.jmw.engine.utils.SnowFlakeUtil;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.UserTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ProcessDynamicsFactory implements ProcessFactoryInterface {

    /**
     * 创建一个BPMN模型
     *
     * @param processCreatorParam
     * @return
     */
    @Override
    public boolean createBPMN(ProcessCreatorParam processCreatorParam) {
        // 创建BpmnModel对象
        BpmnModel bpmnModel = new BpmnModel();

        // 创建流程定义对象
        Process process = new Process();
        process.setId("customProcess");
        String snow = SnowFlakeUtil.getSecondId().toString();
        process.setName(snow);
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
        String classPath = ResourcePathUtils.getPath("config/processes");

        // 输出 BPMN XML 到文件
        try (FileOutputStream fos = new FileOutputStream(new File(classPath, snow + ".bpmn20.xml"))) {
            String bpmnXml = new String(new BpmnXMLConverter().convertToXML(bpmnModel));
            fos.write(bpmnXml.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean deployProcess(ProcessCreatorParam processCreatorParam) {
        return false;
    }
}
