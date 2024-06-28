package com.cn.jmw.common.util.bean.id;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "bds.snowflake")
public class SnowflakeIdConfig {

    private int workerId;

    private int datacenterId;

}