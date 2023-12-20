package com.cn.jmw.authentication.service;

import com.cn.jmw.authentication.Task;
import com.cn.jmw.authentication.enums.User;

import java.util.Date;
import java.util.List;

public interface TaskQueryService {

    List<Task> getTasksForUser(User user, List<String> processKeys, Date startTime, Date endTime);

}
