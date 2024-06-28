package com.cn.jmw.common.util.bean;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration(exclude = MongoAutoConfiguration.class)
public class CustomConfiguration {
    // 在这里添加你的自定义配置
}