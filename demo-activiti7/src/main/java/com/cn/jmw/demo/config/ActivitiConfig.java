package com.cn.jmw.demo.config;

import org.activiti.spring.SpringProcessEngineConfiguration;
import org.activiti.spring.boot.ProcessEngineConfigurationConfigurer;
import org.springframework.context.annotation.Configuration;

/**
 * Activiti7 配置类
 */
@Configuration
public class ActivitiConfig implements ProcessEngineConfigurationConfigurer {

    /**
     * 对当前spring中的ProcessEngine Bean 进行配置修改等操作
     */
    @Override
    public void configure(SpringProcessEngineConfiguration springProcessEngineConfiguration) {
        /**
         * 兼容达梦数据库配置
         *
         * 达梦数据库（Dameng Database）是一款中国自主研发的关系型数据库。在很多方面，达梦数据库与Oracle数据库有着相似的特性和行为，包括SQL语法、数据类型、函数等。
         *
         * Activiti是一个开源的工作流引擎，它支持多种数据库，包括MySQL、PostgreSQL、Oracle等，但它并不直接支持达梦数据库。然而，由于达梦数据库与Oracle数据库的相似性，你可以通过将Activiti的数据库类型设置为"oracle"，使Activiti能够与达梦数据库一起工作。
         *
         * 这种方法并不是完美的，可能会有一些特性或行为在达梦数据库中无法正常工作。在实际使用中，你可能需要进行一些额外的配置或修改，以确保Activiti能够正确地与达梦数据库一起工作。
         */
//        springProcessEngineConfiguration.setDatabaseType("oracle");
    }
}
