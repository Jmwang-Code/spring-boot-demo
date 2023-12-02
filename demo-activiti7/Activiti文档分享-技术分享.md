# 一、工作流介绍
- 工作流是什么？

- 抽象概念：

- 现实举例：

## 	1.1 适用行业

## 	1.2 具体应用


# 二、Activiti介绍

## 	2.1 Activiti简介

- BPM
- BPMN

## 	2.2 Activiti架构
​		Activiti采用了一个分层架构完成自底向上的包装
![img.png](src/assets/img.png)

# 三、Activiti使用

## 	3.1 Springboot基础整合

## 	3.2 Springboot自动建表

## 	3.3 表结构介绍
| **表分类**   | **表名**              | **解释**                                           |
| ------------ | --------------------- | -------------------------------------------------- |
| 一般数据     |                       |                                                    |
|              | [ACT_GE_BYTEARRAY]    | 通用的流程定义和流程资源                           |
|              | [ACT_GE_PROPERTY]     | 系统相关属性                                       |
| 流程历史记录 |                       |                                                    |
|              | [ACT_HI_ACTINST]      | 历史的流程实例                                     |
|              | [ACT_HI_ATTACHMENT]   | 历史的流程附件                                     |
|              | [ACT_HI_COMMENT]      | 历史的说明性信息                                   |
|              | [ACT_HI_DETAIL]       | 历史的流程运行中的细节信息                         |
|              | [ACT_HI_IDENTITYLINK] | 历史的流程运行过程中用户关系                       |
|              | [ACT_HI_PROCINST]     | 历史的流程实例                                     |
|              | [ACT_HI_TASKINST]     | 历史的任务实例                                     |
|              | [ACT_HI_VARINST]      | 历史的流程运行中的变量信息                         |
| 流程定义表   |                       |                                                    |
|              | [ACT_RE_DEPLOYMENT]   | 部署单元信息                                       |
|              | [ACT_RE_MODEL]        | 模型信息                                           |
|              | [ACT_RE_PROCDEF]      | 已部署的流程定义                                   |
| 运行实例表   |                       |                                                    |
|              | [ACT_RU_EVENT_SUBSCR] | 运行时事件                                         |
|              | [ACT_RU_EXECUTION]    | 运行时流程执行实例                                 |
|              | [ACT_RU_IDENTITYLINK] | 运行时用户关系信息，存储任务节点与参与者的相关信息 |
|              | [ACT_RU_JOB]          | 运行时作业                                         |
|              | [ACT_RU_TASK]         | 运行时任务                                         |
|              | [ACT_RU_VARIABLE]     | 运行时变量表                                       |


## 	3.4 关系类图
![img.png](src/assets/clip_image002.jpg)
IdentityService，FormService两个Serivce都已经删除
## 	3.5 工作流引擎使用

# 四、Activiti BPMN建模

## 	4.1 什么是BPMN

## 	4.2 BPMN基础元素

## 	4.3 BPMN高级元素

## 	4.4 BPMN-File 简单案例

## 	4.5 图形化输出

# 五、Activiti API

- 流程定义：

## 	5.1 启动流程实例

## 	5.2 查询流程实例

## 	5.3 流程任务处理

## 	5.4 流程定义信息查询

## 	5.5 流程删除

## 	5.6 流程资源下载

## 	5.7 流程历史信息的查看

# 六、Activiti 高级功能（API）

## 	6.1 挂起与激活流程

## 	6.2 单个流程实例挂起与激活

## 	6.3 分配任务负责人

## 	6.4 查询任务负责人的待办任务

## 	6.5 办理任务

## 	6.6 流程变量 ☆

# 七、组任务

# 八、网管

# 九、Activiti 与 Springboot 简单整合实例（傻瓜式）

# 十、Activiti 与 springboot 版本对照表 （JDK8友好）
| Activiti版本 | SpringBoot要求最低版本 | SpringCloud要求最低版本 |
|:----------:|:----------------:|:-----------------:|
|  7.1.0.M4  |  2.1.6.RELEASE   |   Greenwich.SR2   |
|  7.1.0.M5  |  2.1.10.RELEASE  |   Greenwich.SR3   |
|  7.1.0.M6  |  2.1.12.RELEASE  |   Greenwich.SR5   |

现在中央仓库中更新到了7.1.0.M6版本。但是最新版本是8.0.0，但是这个版本还没有发布到中央仓库中，所以我们只能使用7.1.0.M6版本。
除非使用官网pom地址

