# Spring的事务是如何回滚的？

Spring的事务回滚是通过AOP实现的，其大致流程如下：

1. 通过AOP为@Transactional注解的方法生成代理对象；
2. 代理对象在执行目标方法之前，会先执行TransactionInterceptor的invoke方法；
3. TransactionInterceptor的invoke方法中，会先执行事务的开启，然后执行目标方法，最后执行事务的提交或回滚。

# Spring的事务传播行为有哪些？

一共7种，常用2种

Required：如果当前存在事务，则加入该事务；如果当前没有事务，则创建一个新的事务。

Requireds_new：创建一个新的事务，如果当前存在事务，则把当前事务挂起。