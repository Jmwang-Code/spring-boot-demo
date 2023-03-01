package com.cn.jmw.property.hutool.bean;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 一只小小狗
 * @version 1.0.0
 * @ClassName DeveloperProperty.java
 * @Description
 * @createTime 2023年02月21日 01:36:41
 */
@Data
@ConfigurationProperties(prefix = "developer")
@Component
public class DeveloperProperty {
    private String name;
    private String website;
    private String qq;
    private String phoneNumber;
}
