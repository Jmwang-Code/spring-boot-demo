package com.cn.jmw.authentication.service;

import com.cn.jmw.authentication.Task;
import com.cn.jmw.authentication.commons.WorkflowVariables;
import com.cn.jmw.authentication.enums.Role;
import com.cn.jmw.authentication.enums.User;
import org.activiti.engine.HistoryService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TaskQueryServiceImpl implements TaskQueryService {

    @Autowired
    private HistoryService historyService;

    @Override
    public List<Task> getTasksForUser(User user, List<String> processKeys, Date startTime, Date endTime) {
        HistoricTaskInstanceQuery query = historyService.createHistoricTaskInstanceQuery()
                .taskAssignee(user.getUserId())
                .processDefinitionKeyIn(processKeys)
                .taskCreatedAfter(startTime)
                .taskCreatedBefore(endTime);

        // 根据用户权限添加查询条件
        addPermissionConditions(query, user);

        List<HistoricTaskInstance> historicTasks = query.list();
        // 转换为 Task 对象并返回
        return convertToTaskList(historicTasks);
    }

    private void addPermissionConditions(HistoricTaskInstanceQuery query, User user) {
        // 根据用户的角色和权限添加查询条件
        if (user.getRoles().contains(Role.CLUE_MANAGER)) {
            // 线索管理员可以查询外部流程
            query.processVariableValueEquals(WorkflowVariables.INTERNAL_DEPT_ID, null);
        }
        if (user.getRoles().contains(Role.INTERNAL_STAFF)) {
            // 内勤人员可以查询内部流程
            query.processVariableValueEquals(WorkflowVariables.INTERNAL_DEPT_ID, user.getInternalDeptId());
        }
        // 根据其他角色和权限添加条件...
    }

    private List<Task> convertToTaskList(List<HistoricTaskInstance> historicTasks) {
        List<Task> tasks = new ArrayList();
        // 实现转换逻辑...

        return tasks;
    }
}