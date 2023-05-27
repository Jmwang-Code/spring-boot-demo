# 1. `Executor`框架三大组成部分
**1、任务(`Runnable` /`Callable`)**

执行任务需要实现的接口，**`Runnable` 接口** 或 **`Callable`接口**。

**2、任务的执行(`Executor`接口)**

执行任务的服务，**`Executor`接口**，**`ExecutorService`接口**，**`ScheduledExecutorService`接口**，**`AbstractExecutorService`抽象类**，**`ThreadPoolExecutor`线程池**，**`ScheduledThreadPoolExecutor`线程池**。

**3、异步计算的结果(`Future`接口)**

`Future`接口，`FutureTask`类都可以代表异步计算的结果。

![Executor 框架的使用示意图](images/image1008611.png)

# 2.`ThreadPoolExecutor` 类 线程池执行器 （核心）
`ThreadPoolExecutor`类是`ExecutorService`接口的实现类，是线程池的核心实现类，用来执行被提交的任务。

## 2.1 `ThreadPoolExecutor` 3 个最重要的参数：
- **`corePoolSize` :** 任务队列未达到队列容量时，最大可以同时运行的线程数量。
- **`maximumPoolSize` :** 任务队列中存放的任务达到队列容量的时候，当前可以同时运行的线程数量变为最大线程数。
- **`workQueue`:** 新任务来的时候会先判断当前运行的线程数量是否达到核心线程数，如果达到的话，新任务就会被存放在队列中。

`ThreadPoolExecutor`其他常见参数 :

- **`keepAliveTime`**:线程池中的线程数量大于 `corePoolSize` 的时候，如果这时没有新的任务提交，核心线程外的线程不会立即销毁，而是会等待，直到等待的时间超过了 `keepAliveTime`才会被回收销毁。
- **`unit`** : `keepAliveTime` 参数的时间单位。
- **`threadFactory`** :executor 创建新线程的时候会用到。
- **`handler`** :饱和策略。关于饱和策略下面单独介绍一下。

## 2.2 `ThreadPoolExecutor` 饱和策略定义:
- **`ThreadPoolExecutor.AbortPolicy`**：抛出 `RejectedExecutionException`来拒绝新任务的处理。
- **`ThreadPoolExecutor.CallerRunsPolicy`**：调用执行自己的线程运行任务，也就是直接在调用`execute`方法的线程中运行(`run`)被拒绝的任务，如果执行程序已关闭，则会丢弃该任务。因此这种策略会降低对于新任务提交速度，影响程序的整体性能。如果您的应用程序可以承受此延迟并且你要求任何一个任务请求都要被执行的话，你可以选择这个策略。
- **`ThreadPoolExecutor.DiscardPolicy`**：不处理新任务，直接丢弃掉。
- **`ThreadPoolExecutor.DiscardOldestPolicy`**：此策略将丢弃最早的未处理的任务请求。

## 2.3 线程池自定义创建
通过`ThreadPoolExecutor`构造函数来创建（推荐）。
```java
 ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                    .setNameFormat("ConfigurationCheck-pool-%d").build();
            log.info(ColorEnum8.BLUE.getColoredString(Thread.currentThread().getName()+"——可用运行线程为" + runnableThreadNum));
            configurationCheckThreadPool = new ThreadPoolExecutor(
                    runnableThreadNum,
                    10,
                    0L,
                    TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<Runnable>(1024),
                    namedThreadFactory,
                    new ThreadPoolExecutor.AbortPolicy());
```

## 2.4 `Runnable` 和 `Callable` 的区别
`Runnable`接口和`Callable`接口都是用来定义任务的，但是`Runnable`接口不会返回结果，而`Callable`接口可以返回结果。

## 2.5 `execute()` 和 `submit()` 的区别
`execute()`方法用来提交不需要返回值的任务，所以无法判断任务是否被线程池执行成功与否；
`submit()`方法用来提交需要返回值的任务。线程池会返回一个`Future`类型的对象，通过这个`Future`对象可以判断任务是否执行成功，并且可以通过`Future`的`get()`方法来获取返回值，`get()`方法会阻塞当前线程直到任务完成，而使用`get(long timeout, TimeUnit unit)`方法则会阻塞当前线程一段时间后立即返回，这时候有可能任务没有执行完。

# 3. 案例 (为什么会这样？)
## 3.1 最大线程池数量多会发挥作用？
我们在代码中模拟了10个任务，我们配置的核心线程数为5，最大线程数为10，任务队列的容量为100，所以当有10个任务提交的时候，前5个任务会被立即执行，而后面5个任务会被放入任务队列中等待执行，当任务队列满了以后，还有任务提交的话，就会创建新的线程执行任务，直到线程数达到最大线程数10，如果还有任务提交的话，就会根据我们配置的饱和策略来处理。

**转折点1**
当前任务超过了核心线程数，但是队列未满，其中5个任务会被立即执行，而后面5个任务会被放入任务队列中等待执行。

**转折点2**
当前任务超过了核心线程数，而且队列也满了，其中5个任务会被立即执行，而后面5个任务会被创建新的线程执行，直到线程数达到最大线程数10，如果还有任务提交的话，就会根据我们配置的饱和策略来处理。