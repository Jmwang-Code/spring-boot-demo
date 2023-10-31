# 一、工作流介绍

## 1.1 工作流(Workflow)概念

## 1.2 具体应用

# 二、Activiti

## 2.1 部署activiti

Activiti是一个工作流引擎（其实就是一堆jar包API），业务系统访问(操作)activiti的接口，就可以方便的操作流程相关 数据，这样就可以把工作流环境与业务系统的环境集成在一起。

## 2.2 流程概念定义

- 定义业务流程：通过的方式XML（.bpmn文件）自定义业务流程，通过BPM工具可以查看对应的图形化流程图。

- 数据存储：Activiti提供的API会走对应的.bpmn自定义流程，可以进行流程数据的存储（比如Task相关），也可以执行过程中查询定义的内容，activiti把流程定义存储到数据库中。

## 2.3 简单的使用步骤

1. 启动一个流程实例（果张三要请假就可以启动一个流程实例，如果李四要请假也启动一个流程实例，
   两个流程的执行互相不影响。）
2. 查询当前流程实例的任务（比如张三请假，那么张三的领导就可以查询到张三的请假任务）
3. 用户办理任务（比如张三的领导审批了张三的请假申请，那么张三的领导就可以完成这个任务）
4. 流程结束（张三的请假流程结束了）

# 三、Activiti环境

## 3.1 Activiti环境

### 3.1.1 POM依赖

```xml
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.activiti</groupId>
            <artifactId>activiti-dependencies</artifactId>
            <version>7.0.0.Beta1</version>
            <scope>import</scope>
            <type>pom</type>
        </dependency>
    </dependencies>
</dependencyManagement>
```

### 3.1.2 自动生成activiti表

1. 创建数据库 CREATE 
2. 连接数据源
3. 执行activiti提供的建表API