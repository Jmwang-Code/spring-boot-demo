package com.cn.jmw.controller;

import cn.hutool.core.lang.Dict;
import com.cn.jmw.property.hutool.bean.ApplicationProperty;
import com.cn.jmw.property.hutool.bean.DeveloperProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 一只小小狗
 * @version 1.0.0
 * @ClassName PropertyController.java
 * @Description
 * @createTime 2023年02月21日 01:40:04
 */
@RestController
public class PropertyController {
    private final ApplicationProperty applicationProperty;
    private final DeveloperProperty developerProperty;

    @Autowired
    public PropertyController(ApplicationProperty applicationProperty, DeveloperProperty developerProperty) {
        this.applicationProperty = applicationProperty;
        this.developerProperty = developerProperty;
    }

    @GetMapping("/property")
    public Dict index() {
        return Dict.create().set("applicationProperty", applicationProperty).set("developerProperty", developerProperty);
    }
}
