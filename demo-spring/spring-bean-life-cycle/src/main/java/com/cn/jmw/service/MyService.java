package com.cn.jmw.service;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * @author jmw
 * @Description TODO
 * @date 2023年06月01日 16:52
 * @Version 1.0
 */
@Service
public
class MyService extends BaseService implements BeanPostProcessor, ApplicationContextAware, DisposableBean, InitializingBean {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        System.out.println("1-初始化Aware扩展接口的方法");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("2-实例化Bean之后执行的方法");
    }

    //接口实现BeanPostProcessor 是通用的，所有的Bean都会执行
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        System.out.println("3-Bean初始化之前执行的方法");
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        System.out.println("4-Bean初始化之后执行的方法");
        return bean;
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("5-销毁Bean之前执行的方法");
    }

}