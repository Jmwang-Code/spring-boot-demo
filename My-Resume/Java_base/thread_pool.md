# 1. 你能聊聊线程池么？（简单介绍一下线程池！ 你使用过线程池么？）
一般我们认为线程池就是所谓的自定义ThreadPoolExecutor。

当需要大量异步任务的时候，如果为每个任务都创建一个新线程，会导致资源浪费。

因此线程池是一种常见的解决方案：
1. 它可以使得线程的复用，防止线程在创建和销毁上的资源浪费。
2. 还可以控制线程的数量，防止线程等待时间过长，从而回收等。

# 2.线程池的几种实现？（线程池的实现方式有哪些？它们有什么区别？）

1. FixedThreadPool : 固定大小的线程池，只有核心线程。
2. CachedThreadPool : 缓存线程池，只有非核心线程，线程数量不限制。
3. ScheduledThreadPool : 定时任务线程池，使用延迟工作队列。
4. SingleThreadExecutor : 单线程的线程池，只有一个核心线程。

# 3.线程池的参数有哪些？它们的含义是什么？

1. corePoolSize : 核心线程数。
2. maximumPoolSize : 最大线程数。
3. keepAliveTime : 线程空闲时间。
4. unit : 时间单位。
5. workQueue : 工作队列。
6. threadFactory : 线程工厂。
7. handler : 拒绝策略。

# 3.1 拒绝策略常用有那些？分别是什么?

- **`ThreadPoolExecutor.AbortPolicy`**：直接抛出异常。
- **`ThreadPoolExecutor.CallerRunsPolicy`**：用调用者所在的线程来执行任务。
- **`ThreadPoolExecutor.DiscardPolicy`**：不处理，丢弃掉。
- **`ThreadPoolExecutor.DiscardOldestPolicy`**：此策略将丢弃最早的未处理的任务请求，并且添加当前任务进入队列。

# 4. 你在实际项目中是如何使用线程池的？
<font color='red'><h1>model one</h1></font>
实体识别树中加载模块为了快速加载不同的数据源，此时此刻明显需要多个异步任务完成。

①需要

<img src="img.png" width="50%" height="auto">

# 0.线程base
# 0.1 线程的生命周期
- 新建状态（New）：当线程对象被创建时，它处于新建状态。此时线程对象已经被创建，但尚未启动线程。
- 就绪状态（Runnable）：当线程调用start()方法后，它进入就绪状态。此时线程已经准备好运行，但尚未获得CPU资源。
- 运行状态（Running）：当线程获得CPU资源后，它进入运行状态。此时线程正在执行run()方法中的代码。
- 阻塞状态（Blocked）：当线程等待某个条件时，它进入阻塞状态。例如，当线程调用sleep()方法、等待I/O操作完成或等待锁时，它会进入阻塞状态。在阻塞状态下，线程不会占用CPU资源。
- 死亡状态（Dead）：当线程完成run()方法中的代码或调用stop()方法时，它进入死亡状态。此时线程已经结束，不再占用CPU资源。

# 1. `Executor`框架三大组成部分
**1、任务(`Runnable` /`Callable`)**

执行任务需要实现的接口，**`Runnable` 接口** 或 **`Callable`接口**。

**2、任务的执行(`Executor`接口)**

执行任务的服务，**`Executor`接口**，**`ExecutorService`接口**，**`ScheduledExecutorService`接口**，**`AbstractExecutorService`抽象类**，**`ThreadPoolExecutor`线程池**，**`ScheduledThreadPoolExecutor`线程池**。

**3、异步计算的结果(`Future`接口)**

`Future`接口，`FutureTask`类都可以代表异步计算的结果。

![Executor 框架的使用示意图](../images/image1008611.png)

# 2.`ThreadPoolExecutor` 类 线程池执行器 （核心）
`ThreadPoolExecutor`类是`ExecutorService`接口的实现类，是线程池的核心实现类，用来执行被提交的任务。

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