# Quartz

## 1. 介绍

### 1.1 什么是Quartz

Quartz是一个开源的作业调度框架，它完全由Java编写，为在Java应用程序中进行作业调度提供了简单却强大的机制。它包含了许多企业级作业调度的高级特性，如JTA事务、分布式作业执行、集群、插件等。

### 1.2 Quartz的特点

- 开源免费
- 功能强大
- 使用简单
- 集群支持
- 作业调度

### 1.3 Quartz的应用场景

- 定时任务
- 任务调度
- 任务管理

## 2. Quartz的使用

### 2.1 Quartz的基本使用

#### 2.1.1 Quartz的基本概念

- Job：任务，要执行的具体内容，实现Job接口
- JobDetail：任务详情，定义了Job的实现类以及其他相关信息
- Trigger：触发器，定义了任务执行的时间规则
- Scheduler：任务调度器，调度JobDetail和Trigger

#### 2.1.2 Quartz的基本使用

1. 创建Job类，实现Job接口，重写execute方法

   ```java
   public class HelloJob implements Job {
       @Override
       public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
           System.out.println("Hello Quartz");
       }
   }
   ```

2. 创建JobDetail对象，指定Job的实现类

    ```java
    JobDetail jobDetail = JobBuilder.newJob(HelloJob.class).build();
    ```

3. 创建Trigger对象，指定触发的时间规则

    ```java
    Trigger trigger = TriggerBuilder.newTrigger()
            .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever())
            .build();
    ```

4. 创建Scheduler对象，调度JobDetail和Trigger

    ```java
    Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
    scheduler.scheduleJob(jobDetail, trigger);
    scheduler.start();
    ```

#### 2.1.3 Quartz的时间规则

- SimpleScheduleBuilder
  - withIntervalInSeconds(int intervalInSeconds)：每隔多少秒执行一次
  - withIntervalInMinutes(int intervalInMinutes)：每隔多少分钟执行一次
  - withIntervalInHours(int intervalInHours)：每隔多少小时执行一次
  - repeatSecondlyForever()：每隔一秒执行一次
  - repeatMinutelyForever()：每隔一分钟执行一次
  - repeatHourlyForever()：每隔一小时执行一次
- CronScheduleBuilder
    - withSchedule(CronExpression cronExpression)：指定Cron表达式
    - withMisfireHandlingInstructionDoNothing()：不触发立即执行
    - withMisfireHandlingInstructionIgnoreMisfires()：以错过的第一个频率时间立刻开始执行
    - withMisfireHandlingInstructionFireAndProceed()：以当前时间为触发频率立刻触发一次执行
    - cronSchedule(String cronExpression)：指定Cron表达式
    - cronSchedule(CronExpression cronExpression)：指定Cron表达式
    - cronSchedule(String cronExpression, TimeZone tz)：指定Cron表达式和时区
    - cronSchedule(String cronExpression, TimeZone tz, int misfireInstruction)：指定Cron表达式、时区和错误处理策略
    - cronSchedule(CronExpression cronExpression, int misfireInstruction)：指定Cron表达式和错误处理策略

#### 2.1.4 Quartz的API

- JobBuilder：用于创建JobDetail对象
  - newJob(Class<? extends Job> jobClass)：创建JobDetail对象
  - withIdentity(String name, String group)：设置Job的名称和分组
  - withDescription(String description)：设置Job的描述
  - storeDurably()：设置JobDetail对象持久化
  - requestRecovery()：设置JobDetail对象可恢复
  - usingJobData(String key, String value)：设置JobDetail对象的数据
  - build()：创建JobDetail对象
- TriggerBuilder：用于创建Trigger对象
    - newTrigger()：创建Trigger对象
    - withIdentity(String name, String group)：设置Trigger的名称和分组
    - withDescription(String description)：设置Trigger的描述
    - startAt(Date startTime)：设置Trigger的开始时间
    - endAt(Date endTime)：设置Trigger的结束时间
    - forJob(String jobName, String jobGroup)：设置Trigger的Job名称和分组
    - withSchedule(ScheduleBuilder<? extends Trigger> scheduleBuilder)：设置Trigger的时间规则
    - build()：创建Trigger对象
- SchedulerFactory：用于创建Scheduler对象
    - getDefaultScheduler()：创建Scheduler对象
- Scheduler：用于调度JobDetail和Trigger

### 2.2 Quartz的JobDataMap

#### 2.2.1 JobDataMap的概念

JobDataMap是JobDetail和Trigger的一部分，用于存储JobDetail和Trigger的数据，可以用来传递数据。

#### 2.2.2 JobDataMap的使用

1. 创建JobDetail对象，指定Job的实现类

    ```java
    JobDetail jobDetail = JobBuilder.newJob(HelloJob.class)
            .withIdentity("job1", "group1")
            .usingJobData("message", "hello job1")
            .usingJobData("count", 1)
            .build();
    ```

2. 创建Trigger对象，指定触发的时间规则

    ```java
    Trigger trigger = TriggerBuilder.newTrigger()
            .withIdentity("trigger1", "group1")
            .usingJobData("message", "hello trigger1")
            .usingJobData("count", 1)
            .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever())
            .build();
    ```

3. 创建Scheduler对象，调度JobDetail和Trigger

    ```java
    Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
    scheduler.scheduleJob(jobDetail, trigger);
    scheduler.start();
    ```

4. 创建Job类，实现Job接口，重写execute方法

    ```java
    public class HelloJob implements Job {
        @Override
        public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
            JobDetail jobDetail = jobExecutionContext.getJobDetail();
            String message = jobDetail.getJobDataMap().getString("message");
            int count = jobDetail.getJobDataMap().getInt("count");
            System.out.println(message + " " + count);
            count++;
            jobDetail.getJobDataMap().put("count", count);
        }
    }
    ```

#### 2.2.3 JobDataMap的API

- JobDetail
  - getJobDataMap()：获取JobDetail的JobDataMap
- Trigger
    - getJobDataMap()：获取Trigger的JobDataMap
- JobExecutionContext
    - getJobDetail()：获取JobDetail
    - getTrigger()：获取Trigger

### 2.3 Quartz的Listener

#### 2.3.1 Listener的概念

Listener是Quartz的监听器，用于监听JobDetail和Trigger的状态变化。

#### 2.3.2 Listener的使用

1. 创建Job类，实现Job接口，重写execute方法

    ```java
    public class HelloJob implements Job {
        @Override
        public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
            System.out.println("Hello Quartz");
        }
    }
    ``` 

2. 创建TriggerListener类，实现TriggerListener接口，重写方法

    ```java 
    public class HelloTriggerListener implements TriggerListener {
        @Override
        public String getName() {
            return "HelloTriggerListener";
        }
    
        @Override
        public void triggerFired(Trigger trigger, JobExecutionContext jobExecutionContext) {
            System.out.println("triggerFired");
        }
    
        @Override
        public boolean vetoJobExecution(Trigger trigger, JobExecutionContext jobExecutionContext) {
            System.out.println("vetoJobExecution");
            return false;
        }
    
        @Override
        public void triggerMisfired(Trigger trigger) {
            System.out.println("triggerMisfired");
        }
    
        @Override
        public void triggerComplete(Trigger trigger, JobExecutionContext jobExecutionContext, Trigger.CompletedExecutionInstruction completedExecutionInstruction) {
            System.out.println("triggerComplete");
        }
    }
    ``` 

3. 创建Scheduler对象，调度JobDetail和Trigger

    ```java     
    Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
    scheduler.getListenerManager().addTriggerListener(new HelloTriggerListener());
    scheduler.scheduleJob(jobDetail, trigger);
    scheduler.start();
    ```

#### 2.3.3 Listener的API

- JobListener
    - getName()：获取监听器的名称
    - jobToBeExecuted(JobExecutionContext context)：在JobDetail将要被执行时调用
    - jobExecutionVetoed(JobExecutionContext context)：在JobDetail将要被执行，但被TriggerListener否决时调用
    - jobWasExecuted(JobExecutionContext context, JobExecutionException jobException)：在JobDetail被执行之后调用
- TriggerListener
    - getName()：获取监听器的名称
    - triggerFired(Trigger trigger, JobExecutionContext context)：在Trigger被触发时调用
    - vetoJobExecution(Trigger trigger, JobExecutionContext context)：在Trigger触发时，JobDetail将要被执行时调用
    - triggerMisfired(Trigger trigger)：在Trigger错过触发时调用
    - triggerComplete(Trigger trigger, JobExecutionContext context, Trigger.CompletedExecutionInstruction triggerInstructionCode)：在Trigger触发后调用

### 2.4 Quartz的JobStore

#### 2.4.1 JobStore的概念

JobStore是Quartz的存储器，用于存储JobDetail和Trigger。

#### 2.4.2 JobStore的使用

1. 创建JobDetail对象，指定Job的实现类

    ```java
    JobDetail jobDetail = JobBuilder.newJob(HelloJob.class)
            .withIdentity("job1", "group1")
            .build();
    ```

2. 创建Trigger对象，指定触发的时间规则

    ```java
    Trigger trigger = TriggerBuilder.newTrigger()
            .withIdentity("trigger1", "group1")
            .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever())
            .build();
    ```

3. 创建SchedulerFactory对象，指定JobStore的类型

    ```java
    SchedulerFactory schedulerFactory = new StdSchedulerFactory("quartz.properties");
    ```

4. 创建Scheduler对象，调度JobDetail和Trigger

    ```java
    Scheduler scheduler = schedulerFactory.getScheduler();
    scheduler.scheduleJob(jobDetail, trigger);
    scheduler.start();
    ```

#### 2.4.3 JobStore的API

- RAMJobStore：将JobDetail和Trigger存储在内存中
- JDBCJobStore：将JobDetail和Trigger存储在数据库中
- JobStoreTX：将JobDetail和Trigger存储在数据库中，使用事务
- JobStoreCMT：将JobDetail和Trigger存储在数据库中，使用容器管理事务

### 2.5 Quartz的JobStoreTX

#### 2.5.1 JobStoreTX的概念

JobStoreTX是Quartz的存储器，将JobDetail和Trigger存储在数据库中，使用事务。

#### 2.5.2 JobStoreTX的使用

1. 创建JobDetail对象，指定Job的实现类

    ```java
    JobDetail jobDetail = JobBuilder.newJob(HelloJob.class)
            .withIdentity("job1", "group1")
            .build();
    ```

2. 创建Trigger对象，指定触发的时间规则

    ```java
    Trigger trigger = TriggerBuilder.newTrigger()
            .withIdentity("trigger1", "group1")
            .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever())
            .build();
    ```

3. 创建SchedulerFactory对象，指定JobStore的类型

    ```java
    SchedulerFactory schedulerFactory = new StdSchedulerFactory("quartz.properties");
    ```

4. 创建Scheduler对象，调度JobDetail和Trigger

    ```java
    Scheduler scheduler = schedulerFactory.getScheduler();
    scheduler.scheduleJob(jobDetail, trigger);
    scheduler.start();
    ```

#### 2.5.3 JobStoreTX的API

- org.quartz.jobStore.driverDelegateClass：指定数据库的驱动类
- org.quartz.jobStore.dataSource：指定数据源
- org.quartz.jobStore.tablePrefix：指定表的前缀
- org.quartz.jobStore.isClustered：指定是否为集群
- org.quartz.jobStore.clusterCheckinInterval：指定集群检查间隔
- org.quartz.jobStore.misfireThreshold：指定触发器的失效时间
- org.quartz.jobStore.txIsolationLevelSerializable：指定事务的隔离级别
- org.quartz.jobStore.acquireTriggersWithinLock：指定是否在锁中获取触发器
- org.quartz.jobStore.dontSetAutoCommitFalse：指定是否自动提交
- org.quartz.jobStore.class：指定JobStore的实现类

### 2.6 Quartz的JobStoreCMT

#### 2.6.1 JobStoreCMT的概念

JobStoreCMT是Quartz的存储器，将JobDetail和Trigger存储在数据库中，使用容器管理事务。

#### 2.6.2 JobStoreCMT的使用

1. 创建JobDetail对象，指定Job的实现类

    ```java
    JobDetail jobDetail = JobBuilder.newJob(HelloJob.class)
            .withIdentity("job1", "group1")
            .build();
    ```

2. 创建Trigger对象，指定触发的时间规则

    ```java
    Trigger trigger = TriggerBuilder.newTrigger()
            .withIdentity("trigger1", "group1")
            .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever())
            .build();
    ```

3. 创建SchedulerFactory对象，指定JobStore的类型

    ```java
    SchedulerFactory schedulerFactory = new StdSchedulerFactory("quartz.properties");
    ```

4. 创建Scheduler对象，调度JobDetail和Trigger

    ```java
    Scheduler scheduler = schedulerFactory.getScheduler();
    scheduler.scheduleJob(jobDetail, trigger);
    scheduler.start();
    ```

#### 2.6.3 JobStoreCMT的API

- org.quartz.jobStore.driverDelegateClass：指定数据库的驱动类
- org.quartz.jobStore.dataSource：指定数据源
- org.quartz.jobStore.tablePrefix：指定表的前缀
- org.quartz.jobStore.isClustered：指定是否为集群
- org.quartz.jobStore.clusterCheckinInterval：指定集群检查间隔
- org.quartz.jobStore.misfireThreshold：指定触发器的失效时间
- org.quartz.jobStore.txIsolationLevelSerializable：指定事务的隔离级别
- org.quartz.jobStore.acquireTriggersWithinLock：指定是否在锁中获取触发器
- org.quartz.jobStore.dontSetAutoCommitFalse：指定是否自动提交
- org.quartz.jobStore.class：指定JobStore的实现类

### 2.7 Quartz的JobStoreTX和JobStoreCMT的区别

- JobStoreTX：使用JDBC事务，需要手动提交事务
- JobStoreCMT：使用容器管理事务，不需要手动提交事务

### 2.8 Quartz的JobStoreTX和JobStoreCMT的选择

- JobStoreTX：适用于非J2EE环境
- JobStoreCMT：适用于J2EE环境

### 2.9 Quartz的JobStoreTX和JobStoreCMT的配置

#### 2.9.1 JobStoreTX的配置

```properties
# 指定数据库的驱动类
org.quartz.jobStore.driverDelegateClass=org.quartz.impl.jdbcjobstore.StdJDBCDelegate
# 指定数据源
org.quartz.jobStore.dataSource=quartzDataSource
# 指定表的前缀
org.quartz.jobStore.tablePrefix=QRTZ_
# 指定是否为集群
org.quartz.jobStore.isClustered=true
# 指定集群检查间隔
org.quartz.jobStore.clusterCheckinInterval=20000
# 指定触发器的失效时间
org.quartz.jobStore.misfireThreshold=60000
# 指定事务的隔离级别
org.quartz.jobStore.txIsolationLevelSerializable=true
# 指定是否在锁中获取触发器
org.quartz.jobStore.acquireTriggersWithinLock=true
# 指定是否自动提交
org.quartz.jobStore.dontSetAutoCommitFalse=false
# 指定JobStore的实现类
org.quartz.jobStore.class=org.quartz.impl.jdbcjobstore.JobStoreTX
```

#### 2.9.2 JobStoreCMT的配置

```properties
# 指定数据库的驱动类
org.quartz.jobStore.driverDelegateClass=org.quartz.impl.jdbcjobstore.StdJDBCDelegate
# 指定数据源
org.quartz.jobStore.dataSource=quartzDataSource
# 指定表的前缀
org.quartz.jobStore.tablePrefix=QRTZ_
# 指定是否为集群
org.quartz.jobStore.isClustered=true
# 指定集群检查间隔
org.quartz.jobStore.clusterCheckinInterval=20000
# 指定触发器的失效时间
org.quartz.jobStore.misfireThreshold=60000
# 指定事务的隔离级别
org.quartz.jobStore.txIsolationLevelSerializable=true
# 指定是否在锁中获取触发器
org.quartz.jobStore.acquireTriggersWithinLock=true
# 指定是否自动提交
org.quartz.jobStore.dontSetAutoCommitFalse=false
# 指定JobStore的实现类
org.quartz.jobStore.class=org.quartz.impl.jdbcjobstore.JobStoreCMT
```

## 3. Quartz的Job

### 3.1 Job的概念

Job是Quartz的任务，需要实现org.quartz.Job接口。

### 3.2 Job的使用

1. 创建JobDetail对象，指定Job的实现类

    ```java
    JobDetail jobDetail = JobBuilder.newJob(HelloJob.class)
            .withIdentity("job1", "group1")
            .build();
    ```

2. 创建Trigger对象，指定触发的时间规则

    ```java
    Trigger trigger = TriggerBuilder.newTrigger()
            .withIdentity("trigger1", "group1")
            .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever())
            .build();
    ```

3. 创建SchedulerFactory对象，指定JobStore的类型

    ```java
    SchedulerFactory schedulerFactory = new StdSchedulerFactory("quartz.properties");
    ```

4. 创建Scheduler对象，调度JobDetail和Trigger

    ```java
    Scheduler scheduler = schedulerFactory.getScheduler();
    scheduler.scheduleJob(jobDetail, trigger);
    scheduler.start();
    ```

### 3.3 Job的API

- org.quartz.Job：Job的接口
- org.quartz.JobExecutionContext：Job的上下文
- org.quartz.JobDataMap：Job的数据
- org.quartz.JobBuilder：Job的构建器
- org.quartz.JobDetail：Job的详细信息
- org.quartz.JobKey：Job的标识
- org.quartz.JobListener：Job的监听器

### 3.4 Job的实现

```java
public class HelloJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("Hello Quartz!");
    }
}
```

### 3.5 Job的配置

```properties
# 指定Job的实现类
org.quartz.jobStore.driverDelegateClass=org.quartz.impl.jdbcjobstore.StdJDBCDelegate
# 指定数据源
org.quartz.jobStore.dataSource=quartzDataSource
# 指定表的前缀
org.quartz.jobStore.tablePrefix=QRTZ_
# 指定是否为集群
org.quartz.jobStore.isClustered=true
# 指定集群检查间隔
org.quartz.jobStore.clusterCheckinInterval=20000
# 指定触发器的失效时间
org.quartz.jobStore.misfireThreshold=60000
# 指定事务的隔离级别
org.quartz.jobStore.txIsolationLevelSerializable=true
# 指定是否在锁中获取触发器
org.quartz.jobStore.acquireTriggersWithinLock=true
# 指定是否自动提交
org.quartz.jobStore.dontSetAutoCommitFalse=false
# 指定JobStore的实现类
org.quartz.jobStore.class=org.quartz.impl.jdbcjobstore.JobStoreTX
# 指定Job的实现类
org.quartz.jobFactory.class=org.quartz.simpl.SimpleJobFactory
```

## 4. Quartz的Trigger

### 4.1 Trigger的概念

Trigger是Quartz的触发器，需要实现org.quartz.Trigger接口。

### 4.2 Trigger的使用

1. 创建JobDetail对象，指定Job的实现类

    ```java
    JobDetail jobDetail = JobBuilder.newJob(HelloJob.class)
            .withIdentity("job1", "group1")
            .build();
    ```

2. 创建Trigger对象，指定触发的时间规则

    ```java
    Trigger trigger = TriggerBuilder.newTrigger()
            .withIdentity("trigger1", "group1")
            .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever())
            .build();
    ```

3. 创建SchedulerFactory对象，指定JobStore的类型

    ```java
    SchedulerFactory schedulerFactory = new StdSchedulerFactory("quartz.properties");
    ```

4. 创建Scheduler对象，调度JobDetail和Trigger

    ```java
    Scheduler scheduler = schedulerFactory.getScheduler();
    scheduler.scheduleJob(jobDetail, trigger);
    scheduler.start();
    ```

### 4.3 Trigger的API

- org.quartz.Trigger：Trigger的接口
- org.quartz.TriggerBuilder：Trigger的构建器
- org.quartz.TriggerKey：Trigger的标识
- org.quartz.TriggerListener：Trigger的监听器

### 4.4 Trigger的实现

### 4.5 Trigger的配置

```properties
# 指定Job的实现类
org.quartz.jobStore.driverDelegateClass=org.quartz.impl.jdbcjobstore.StdJDBCDelegate
# 指定数据源
org.quartz.jobStore.dataSource=quartzDataSource
# 指定表的前缀
org.quartz.jobStore.tablePrefix=QRTZ_
# 指定是否为集群
org.quartz.jobStore.isClustered=true
# 指定集群检查间隔
org.quartz.jobStore.clusterCheckinInterval=20000
# 指定触发器的失效时间
org.quartz.jobStore.misfireThreshold=60000
# 指定事务的隔离级别
org.quartz.jobStore.txIsolationLevelSerializable=true
# 指定是否在锁中获取触发器
org.quartz.jobStore.acquireTriggersWithinLock=true
# 指定是否自动提交
org.quartz.jobStore.dontSetAutoCommitFalse=false
# 指定JobStore的实现类
org.quartz.jobStore.class=org.quartz.impl.jdbcjobstore.JobStoreTX
# 指定Job的实现类
org.quartz.jobFactory.class=org.quartz.simpl.SimpleJobFactory
```

## 5. Quartz的Listener

### 5.1 Listener的概念

Listener是Quartz的监听器，需要实现org.quartz.Listener接口。

### 5.2 Listener的使用

1. 创建JobDetail对象，指定Job的实现类

    ```java
    JobDetail jobDetail = JobBuilder.newJob(HelloJob.class)
            .withIdentity("job1", "group1")
            .build();
    ```

2. 创建Trigger对象，指定触发的时间规则

    ```java
    Trigger trigger = TriggerBuilder.newTrigger()
            .withIdentity("trigger1", "group1")
            .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever())
            .build();
    ```

3. 创建SchedulerFactory对象，指定JobStore的类型

    ```java
    SchedulerFactory schedulerFactory = new StdSchedulerFactory("quartz.properties");
    ```

4. 创建Scheduler对象，调度JobDetail和Trigger

    ```java
    Scheduler scheduler = schedulerFactory.getScheduler();
    scheduler.scheduleJob(jobDetail, trigger);
    scheduler.start();
    ```

### 5.3 Listener的API

- org.quartz.Listener：Listener的接口
- org.quartz.JobListener：Job的监听器
- org.quartz.TriggerListener：Trigger的监听器
- org.quartz.SchedulerListener：Scheduler的监听器

### 5.4 Listener的实现

### 5.5 Listener的配置

```properties
# 指定Job的实现类
org.quartz.jobStore.driverDelegateClass=org.quartz.impl.jdbcjobstore.StdJDBCDelegate
# 指定数据源
org.quartz.jobStore.dataSource=quartzDataSource
# 指定表的前缀
org.quartz.jobStore.tablePrefix=QRTZ_
# 指定是否为集群
org.quartz.jobStore.isClustered=true
# 指定集群检查间隔
org.quartz.jobStore.clusterCheckinInterval=20000
# 指定触发器的失效时间
org.quartz.jobStore.misfireThreshold=60000
# 指定事务的隔离级别
org.quartz.jobStore.txIsolationLevelSerializable=true
# 指定是否在锁中获取触发器
org.quartz.jobStore.acquireTriggersWithinLock=true
# 指定是否自动提交
org.quartz.jobStore.dontSetAutoCommitFalse=false
# 指定JobStore的实现类
org.quartz.jobStore.class=org.quartz.impl.jdbcjobstore.JobStoreTX
# 指定Job的实现类
org.quartz.jobFactory.class=org.quartz.simpl.SimpleJobFactory
```