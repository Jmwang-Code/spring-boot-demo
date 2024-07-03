package com.cn.jmw.common.util.bean.id;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SnowflakeIdUtil {

    private static SnowflakeIdGenerator idGenerator;

    @Autowired
    public SnowflakeIdUtil(SnowflakeIdConfig config) {
        long workerId = config.getWorkerId();
        long datacenterId = config.getDatacenterId();
        idGenerator = new SnowflakeIdGenerator(workerId, datacenterId);
    }

    public static long generateId() {
        return idGenerator.nextId();
    }
}