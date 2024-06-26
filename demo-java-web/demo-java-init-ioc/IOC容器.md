# 谁定义了bean的生命周期

实现InitializingBean Or DisposableBean接口，分别实现afterPropertiesSet()和destroy()方法，完成bean的初始化和销毁
实现BeanPostProcessor接口，分别实现postProcessBeforeInitialization()和postProcessAfterInitialization()
方法，完成bean的初始化前后的处理工作

```
执行步骤：
postProcessBeforeInitialization（）
↓
afterPropertiesSet（）
↓
postProcessAfterInitialization（）
↓
destroy（）
```

# IOC初始化 IOC启动阶段 (Spring容器的启动流程)

1.

Resource定位过程：这个过程是指定位BeanDefinition的资源，也就是配置文件（如xml）的位置，并将其封装成Resource对象。Resource对象是Spring用来抽象不同形式的BeanDefinition的接口。比如BeanDefinitionReader(
加载、Parse解析)

2.

BeanDefinition的载入：这个过程是将Resource定位到的信息，转换成IoC容器内部的数据结构，也就是BeanDefinition对象。BeanDefinition对象是用来描述Bean实例的属性，如类名，构造器参数，依赖的bean等。BeanDefinition生成

3.

BeanDefinition的注册：这个过程是将载入过程中得到的BeanDefinition对象注册到IoC容器中。注册过程是通过BeanDefinitionRegistry接口的实现来完成的。在IoC容器内部，BeanDefinition对象被存储在一个HashMap中。BeanDefinitionRegistry注册

![img_7.png](img_7.png)

# Spring-IOC是什么

Spring-IOC是Spring框架的核心，是一个容器，它负责实例化、定位、配置应用程序中的对象及建立这些对象间的依赖。

## IOC是什么

- 控制反转，指的是将对象的控制权交给Spring容器，由Spring来控制对象的生命周期和对象间的关系，而不是由对象自己控制。
  （原来是自己去new开辟空间等创建，自己通过close等方法进行销毁。）
- 自己控制对象缺点：使得代码耦合度高，不易于维护，不易于测试。
- IOC容器控制对象优点：使得代码耦合度低，易于维护，易于测试。

## DI是什么

依赖注入，指的是由Spring容器在运行期间，动态地将某种依赖关系注入到对象之中。

# 依赖注入 DI的三种方式

1. 构造器注入:通过构造器传入依赖对象。
2. Setter方法注入:通过Setter方法传入依赖对象。
3. 接口注入:通过接口的Setter方法传入依赖对象。

# Spring-AOP是什么

Spring-AOP是Spring框架的一个重要组成部分，它提供了面向切面的编程，通过预编译方式和运行期动态代理实现程序功能的统一维护的技术。
（比如HandlerInterceptor实现之后，就是通过动态代理，从调用接口实时的获得handler对象，而这个拦截器的接口就是切面）

## OOP

面向对象编程，允许开发者定义纵向关系，但并不适用于定义横向的关系，会导致大量代码的重复，而不利于各个模块的重用。

## AOP

面向切面编程，是对OOP的补充，它允许开发者定义横向关系，将系统中的关注点分离出来形成一个独立的模块，这个模块被称为切面，它的作用是与业务逻辑无关的，但是又为业务逻辑模块所共同调用。
“切面”（Aspect）可用于权限认证、日志、事务处理。

## AOP实现方式

AOP实现的关键在于 代理模式，AOP代理主要分为静态代理和动态代理。静态代理的代表为AspectJ；动态代理则以Spring AOP为代表。

（1）AspectJ是静态代理，也称为编译时增强，AOP框架会在编译阶段生成AOP代理类，并将AspectJ(切面)织入到Java字节码中，运行的时候就是增强之后的AOP对象。

（2）Spring AOP使用的动态代理，所谓的动态代理就是说AOP框架不会去修改字节码，而是每次运行时在内存中临时为方法生成一个AOP对象，这个AOP对象包含了目标对象的全部方法，并且在特定的切点做了增强处理，并回调原对象的方法。

（3）静态代理与动态代理区别在于生成AOP代理对象的时机不同，相对来说AspectJ的静态代理方式具有更好的性能，但是AspectJ需要特定的编译器进行处理，而Spring
AOP则无需特定的编译器处理。

### 动JDK动态代理和CGLIB动态代理 JDK动态代理和CGLIB动态代理

①
JDK动态代理只提供接口的代理，不支持类的代理，要求被代理类实现接口。JDK动态代理的核心是InvocationHandler接口和Proxy类，在获取代理对象时，使用Proxy类来动态创建目标类的代理类（即最终真正的代理类，这个类继承自Proxy并实现了我们定义的接口），当代理对象调用真实对象的方法时，
InvocationHandler 通过invoke()方法反射来调用目标类中的代码，动态地将横切逻辑和业务编织在一起；

InvocationHandler 的 invoke(Object proxy,Method method,Object[] args)：proxy是最终生成的代理对象; method 是被代理目标实例的某个具体方法;
args 是被代理目标实例某个方法的具体入参, 在方法反射调用时使用。

② 如果被代理类没有实现接口，那么Spring AOP会选择使用CGLIB来动态代理目标类。CGLIB（Code Generation
Library），是一个代码生成的类库，可以在运行时动态的生成指定类的一个子类对象，并覆盖其中特定方法并添加增强代码，从而实现AOP。CGLIB是通过继承的方式做的动态代理，因此如果某个类被标记为final，那么它是无法使用CGLIB做动态代理的。

### IOC 和 AOP 的联系和区别

IoC让相互协作的组件保持松散的耦合，而AOP编程允许你把遍布于应用各层的功能分离出来形成可重用的功能组件。

![](af602a222f414127b7afcb9b8edc0f08.png)

# BeanFactory和ApplicationContext有什么区别？

BeanFactory <span style="color:darkorange">(轻量级)</span> 和ApplicationContext<span style="color:darkorange">(高级特性和框架)</span>是Spring的两大核心接口，都可以当做Spring的容器。
（1）BeanFactory是Spring里面最底层的接口，是IoC的核心，定义了IoC的基本功能，包含了各种Bean的定义、加载、实例化，依赖注入和生命周期管理。ApplicationContext接口作为BeanFactory的子类，除了提供BeanFactory所具有的功能外，还提供了更完整的框架功能：
（2）

1. BeanFactroy采用的是延迟加载形式来注入Bean的，只有在使用到某个Bean时(调用getBean())
   ，才对该Bean进行加载实例化。这样，我们就不能提前发现一些存在的Spring的配置问题。如果Bean的某一个属性没有注入，BeanFacotry加载后，直至第一次使用调用getBean方法才会抛出异常。
2. ApplicationContext，它是在容器启动时，一次性创建了所有的Bean。这样，在容器启动时，我们就可以发现Spring中存在的配置错误，这样有利于检查所依赖属性是否注入。
3. ApplicationContext启动后预载入所有的单实例Bean，所以在运行的时候速度比较快，因为它们已经创建好了。相对于BeanFactory，ApplicationContext
   唯一的不足是占用内存空间，当应用程序配置Bean较多时，程序启动较慢。
   （3）BeanFactory和ApplicationContext都支持BeanPostProcessor、BeanFactoryPostProcessor的使用，但两者之间的区别是：BeanFactory需要手动注册，而ApplicationContext则是自动注册。
   （4）BeanFactory通常以编程的方式被创建，ApplicationContext还能以声明的方式创建，如使用ContextLoader。

```java
class A {
    void a() {
        // 创建BeanFactory容器
        BeanFactory factory = new XmlBeanFactory(new ClassPathResource("bean.xml"));

        // 从BeanFactory获取Bean实例
        UserService userService = factory.getBean("userService", UserService.class);
        userService.save();

        // 手动销毁Bean
        ((ConfigurableBeanFactory) factory).destroyBean("userService", userService);

        // 或者在bean.xml中配置destroy-method,在关闭容器时自动调用
        factory.close();
    }
}
```

# Spring框架中的Bean是线程安全的么？如果线程不安全，那么如何处理？
1. 对于prototype作用域的Bean，每次都创建一个新对象，也就是线程之间不存在Bean共享，因此不会有线程安全问题。
2. 对于singleton作用域的Bean，所有的线程都共享一个单例实例的Bean，因此是存在线程安全问题的。但是如果单例Bean是一个无状态Bean，也就是线程中的操作不会对Bean的成员执行查询以外的操作，那么这个单例Bean是线程安全的。比如Controller类、Service类和Dao等，这些Bean大多是无状态的，只关注于方法本身。
```
    无状态Bean(Stateless Bean)：就是没有实例变量的对象，不能保存数据，是不变类，是线程安全的。
    有状态Bean(Stateful Bean) ：就是有实例变量的对象，可以保存数据，是非线程安全的。
```

# 单例bean有状态，采用ThreadLocal解决线程安全问题
ThreadLocal和线程同步机制都是为了解决多线程中相同变量的访问冲突问题。同步机制采用了“时间换空间”的方式，仅提供一份变量，不同的线程在访问前需要获取锁，没获得锁的线程则需要排队。而ThreadLocal采用了“空间换时间”的方式。ThreadLocal会为每一个线程提供一个独立的变量副本，从而隔离了多个线程对数据的访问冲突。因为每一个线程都拥有自己的变量副本，从而也就没有必要对该变量进行同步了。


# Spring的Bean的生命周期

![img_9.png](img_9.png)
Spring的Bean的生命周期包括以下阶段：

- （1）实例化Instantiation
- （2）填充属性Populate properties
- （3）处理Aware接口的回调处理
- （4）BeanPostProcessor的前置处理
- （5）InitializingBean的afterPropertiesSet()方法
- （6）init-method属性指定的初始化方法
- （7）BeanPostProcessor的后置处理
- （8）DisposableBean的destroy()方法
- （9）destroy-method属性指定的初始化方法

bean四个阶段: 实例化、属性填充、初始化、销毁

1. [ ] 实例化Bean：

对于BeanFactory容器，当客户向容器请求一个尚未初始化的bean时，或初始化bean的时候需要注入另一个尚未初始化的依赖时，容器就会调用createBean进行实例化。

对于ApplicationContext容器，当容器启动结束后，通过获取BeanDefinition对象中的信息，实例化所有的bean。

2. [ ] 设置对象属性（依赖注入）：

实例化后的对象被封装在BeanWrapper对象中，紧接着，Spring根据BeanDefinition中的信息 以及 通过BeanWrapper提供的设置属性的接口完成属性设置与依赖注入。

3. [ ] 处理Aware接口：

Spring会检测该对象是否实现了xxxAware接口，通过Aware类型的接口，可以让我们拿到Spring容器的一些资源：

①如果这个Bean实现了BeanNameAware接口，会调用它实现的setBeanName(String beanId)方法，传入Bean的名字；
②如果这个Bean实现了BeanClassLoaderAware接口，调用setBeanClassLoader()方法，传入ClassLoader对象的实例。
②如果这个Bean实现了BeanFactoryAware接口，会调用它实现的setBeanFactory()方法，传递的是Spring工厂自身。
③如果这个Bean实现了ApplicationContextAware接口，会调用setApplicationContext(ApplicationContext)方法，传入Spring上下文；

4. [ ] BeanPostProcessor前置处理：
   如果想对Bean进行一些自定义的前置处理，那么可以让Bean实现了BeanPostProcessor接口，那将会调用postProcessBeforeInitialization(
   Object obj, String s)方法。

5. [ ] InitializingBean的afterPropertiesSet()方法：
   如果Bean实现了InitializingBean接口，执行afeterPropertiesSet()方法。

6. [ ] init-method属性指定的初始化方法：
   如果Bean在Spring配置文件中配置了 init-method 属性，则会自动调用其配置的初始化方法。

7. [ ] BeanPostProcessor后置处理：
   如果这个Bean实现了BeanPostProcessor接口，将会调用postProcessAfterInitialization(Object obj, String s)
   方法；由于这个方法是在Bean初始化结束时调用的，所以可以被应用于内存或缓存技术；

<span style="color: yellow">以上几个步骤完成后，Bean就已经被正确创建了，之后就可以使用这个Bean了。</span>

8. [ ] DisposableBean的destroy()方法：
   当Bean不再需要时，会经过清理阶段，如果Bean实现了DisposableBean这个接口，会调用其实现的destroy()方法；

9. [ ] destroy-method属性指定的初始化方法：
   最后，如果这个Bean的Spring配置中配置了destroy-method属性，会自动调用其配置的销毁方法。

# Spring的Bean的作用域

Spring的Bean的作用域包括以下几种：

- （1）singleton：单例模式，一个BeanFactory有且仅有一个实例。
- （2）prototype：原型模式，每次从BeanFactory获取Bean时，都会创建一个新的实例。(多例，原型的原理克隆拷贝)
- （3）request：每次request请求都会创建一个新的Bean，该作用域仅在基于web的Spring ApplicationContext情形下有效。
- （4）session：每次session请求都会创建一个新的Bean，该作用域仅在基于web的Spring ApplicationContext情形下有效。
- （5）application：所有会话共享一个Bean，该作用域仅在基于web的Spring ApplicationContext情形下有效。
  在编写代码时通常使用ApplicationContext容器，以便能够享受更多的功能和便利性。

# Spring如何解决循环依赖问题
循环依赖问题在Spring中主要有三种情况：

（1）通过构造方法进行依赖注入时产生的循环依赖问题。
（2）通过setter方法进行依赖注入且是在多例（原型）模式下产生的循环依赖问题。
（3）通过setter方法进行依赖注入且是在单例模式下产生的循环依赖问题。

在Spring中，只有第（3）种方式的循环依赖问题被解决了，其他两种方式在遇到循环依赖问题时都会产生异常。这是因为：

第一种构造方法注入的情况下，在new对象的时候就会堵塞住了，其实也就是”先有鸡还是先有蛋“的历史难题。
第二种setter方法（多例）的情况下，每一次getBean()时，都会产生一个新的Bean，如此反复下去就会有无穷无尽的Bean产生了，最终就会导致OOM问题的出现。
Spring在单例模式下的setter方法依赖注入引起的循环依赖问题，主要是通过二级缓存和三级缓存来解决的，其中三级缓存是主要功臣。解决的核心原理就是：在对象实例化之后，依赖注入之前，Spring提前暴露的Bean实例的引用在第三级缓存中进行存储。

1. 通过@Lazy注解构造方法解决循环依赖问题
2. setter方法进行依赖注入且是在多例（原型）模式，给所用的属性套用包装类型ObjectProvider<XXX>延迟获取的Bean接口，proAProvider.getIfAvailable();解决
3. 通过setter方法进行依赖注入且是在单例模式,改用构造方法注入@Lazy解决，或者@Autowired+@Lazy，一起解决。


# 自动装配
- 自动装配:在@Autowired注入点不指定Bean,Spring自动选择。
- 依赖注入:在@Qualifier或XML中明确指定要注入的Bean,然后由Spring注入。
基于注解的自动装配
- @Autowired:自动装配,默认按照类型注入,如果有多个Bean匹配,则按照属性名注入。（类型->属性）
- @Qualifier:指定注入的Bean的名称。
- @Primary:指定首选的Bean。
- @Resource:自动装配,默认按照名称注入,如果没有匹配的Bean,则按照类型注入。（名->类型）
- @Inject:自动装配,默认按照类型注入,如果有多个Bean匹配,则按照属性名注入。（类型->属性）

# Spring事务的实现方式
Spring事务是和数据库事务保持一致

Spring事务的实现方式主要有两种：编程式事务管理和声明式事务管理。
- ①编程式事务管理使用TransactionTemplate。
- ②声明式事务管理建立在AOP之上的。其本质是通过AOP功能，对方法前后进行拦截，将事务处理的功能编织到拦截的方法中，也就是在目标方法开始之前启动一个事务，在执行完目标方法之后根据执行情况提交或者回滚事务。
```
声明式事务管理的优点：
   不需要掺杂业务逻辑代码，@Transactional注解可以被添加到类级别和方法级别上。
声明式事务管理的缺点：
   无法做到细粒度的事务控制，无法做到像编程式事务那样可以作用到代码块级别。比如一个事务方法调用了多个DAO方法，希望其中某个DAO方法独立成为一个事务，这时候就办不到了。
```

# Spring 框架中都用到了哪些设计模式
1. 工厂模式：Spring使用工厂模式通过BeanFactory、ApplicationContext创建bean对象。
2. 代理模式：Spring AOP功能的实现。
3. 单例模式：Spring中的Bean默认都是单例的。
4. 模板方法模式：Spring中JdbcTemplate、HibernateTemplate等以Template结尾的对数据库操作的类，它们就使用到了模板模式。
5. 包装器模式：Spring中对Bean的装饰就使用到了装饰器模式，如各个ApplicationContext实现类中对Bean的装饰。
6. <span style="color:yellow">没了解过 </span>观察者模式：Spring事件驱动模型就是观察者模式很经典的一个应用。
7. <span style="color:yellow">没了解过 </span>适配器模式：Spring AOP的增强或通知（Advice）使用到了适配器模式、spring MVC中也是用到了适配器模式适配Controller。
8. <span style="color:yellow">没了解过 </span>迭代器模式：Spring中很多集合对象的遍历（如Spring MVC中model的遍历）都是使用迭代器模式。


# Spring框架中有哪些不同类型的事件
Spring 提供了以下5种标准的事件：
1. 上下文更新事件（ContextRefreshedEvent）：在调用ConfigurableApplicationContext 接口中的refresh()方法时被触发。
2. 上下文开始事件（ContextStartedEvent）：当容器调用ConfigurableApplicationContext的Start()方法开始/重新开始容器时触发该事件。
3. 上下文停止事件（ContextStoppedEvent）：当容器调用ConfigurableApplicationContext的Stop()方法停止容器时触发该事件。
4. 上下文关闭事件（ContextClosedEvent）：当ApplicationContext被关闭时触发该事件。容器被关闭时，其管理的所有单例Bean都被销毁。
5. 请求处理事件（RequestHandledEvent）：在Web应用中，当一个http请求（request）结束触发该事件。
如果一个bean实现了ApplicationListener接口，当一个ApplicationEvent 被发布以后，bean会自动被通知。
![img_10.png](img_10.png)


# Spring支持哪些Aware接口?
- ApplicationContextAware:获取ApplicationContext对象
- BeanFactoryAware:获取BeanFactory对象
- BeanNameAware:获取Bean的名称
- EnvironmentAware:获取Environment对象
- ResourceLoaderAware:获取ResourceLoader对象
- ServletContextAware:在Web应用中获取ServletContext对象
- BeanClassLoaderAware:获取加载当前Bean的ClassLoader对象
![img_12.png](img_12.png)

# Aware接口的优点
- 可以取得Spring容器中的各种对象和资源,如其他Bean、文件资源、环境变量等。
- 可以管理项目中的共享资源,如数据库连接池、配置属性等。
- 与Spring容器实现深度集成,可以根据运行环境动态调整Bean的行为。
- 简单而有效地扩展Spring Bean的功能。

# ApplicationContextAware和BeanFactoryAware的区别
这两个的区别不如说是ApplicationContext和BeanFactory的区别。
- ApplicationContext是BeanFactory的子接口，提供了更多的功能，比如国际化处理、事件传播、Bean自动装配等。
- ApplicationContext是在BeanFactory的基础上实现的，所以BeanFactory能做的ApplicationContext都能做，但是BeanFactory不能做的ApplicationContext不一定能做。
- ApplicationContext是在BeanFactory的基础上实现的，所以BeanFactory的性能比ApplicationContext好。
- ApplicationContext是在BeanFactory的基础上实现的，所以BeanFactory的扩展性比ApplicationContext好。
- ApplicationContext是在BeanFactory的基础上实现的，所以BeanFactory的轻量级比ApplicationContext好。
- ApplicationContext是在BeanFactory的基础上实现的，所以BeanFactory的灵活性比ApplicationContext好。

