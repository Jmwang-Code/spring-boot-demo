# Spring-boot

# 优点（特点）（面试）

1.简化配置：约定优于配置的理念
2.自动配置

# bootstrap.yml文件的作用?

application.properties application.xml
application.yml aplication.yaml
bootstrap.yml在SpringBoot中默认是不支持的，需要在SpringCloud环境下才支持，作用是在SpringBoot项目启动之前启动的一个父容器，该父容器可以在SpringBoot容器启动之前完成一些加载初始化的操作。比如加载配置中心中的信息。

# SpringBoot项目中如何使用log4j
在src/main/resouces目录下创建log4j.properties文件，然后就可以进行日志的相关的配置.

#SpringBoot项目中是如何解决跨域问题
1.可以通过JSONP类解决跨域问题，但是只支持GET方式的请求
2.SpringBoot中我们可以通过WebMvcConfigurer重写里面的addCorsMappings方法，在这个方法中我们添加允许跨域的相关请求