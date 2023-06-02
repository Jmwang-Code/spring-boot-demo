package com.cn.jmw.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author jmw
 * @Description TODO
 * @date 2023年06月02日 16:30
 * @Version 1.0
 */
public class Application implements ApplicationContextAware {

    private static ApplicationContext applicationContex;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Application.applicationContex = applicationContext;
    }

    public static <T> T getBean(Class<T> clazz) {
        return applicationContex.getBean(clazz);
    }
}
