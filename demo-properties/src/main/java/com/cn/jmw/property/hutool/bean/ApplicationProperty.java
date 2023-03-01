package com.cn.jmw.property.hutool.bean;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author 一只小小狗
 * @version 1.0.0
 * @ClassName ApplicationProperty.java
 * @Description
 * @createTime 2023年02月21日 01:40:41
 */
@Data
@Component
public class ApplicationProperty {
    @Value("${application.name}")
    private String name;
    @Value("${application.version}")
    private String version;
}
