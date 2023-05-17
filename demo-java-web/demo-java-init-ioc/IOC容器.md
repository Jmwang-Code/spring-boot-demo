# 谁定义了bean的生命周期
实现InitializingBean Or DisposableBean接口，分别实现afterPropertiesSet()和destroy()方法，完成bean的初始化和销毁
实现BeanPostProcessor接口，分别实现postProcessBeforeInitialization()和postProcessAfterInitialization()方法，完成bean的初始化前后的处理工作
执行步骤：
postProcessBeforeInitialization（）
↓
afterPropertiesSet（）
↓
postProcessAfterInitialization（）
↓
destroy（）

