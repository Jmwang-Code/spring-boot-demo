package log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class LoggingProxy implements InvocationHandler {
    private Object target;

    public LoggingProxy(Object target) {
        this.target = target;
    }

    public static Object createProxy(Object target) {
        return Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new LoggingProxy(target)
        );
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 记录方法执行前的日志信息
        long startTime = System.currentTimeMillis();
        System.out.println("方法 " + method.getName() + " is about to be executed.");
        System.out.println("Method arguments: " + Arrays.toString(args));

        // 调用目标方法
        Object result = method.invoke(target, args);

        // 记录方法执行后的日志信息
        long endTime = System.currentTimeMillis();
        System.out.println("方法 " + method.getName() + " execution completed.");
        System.out.println("Execution time: " + (endTime - startTime) + " milliseconds");

        return result;
    }

    public static void main(String[] args) {
        // 创建原始对象
        UserService userService = new UserServiceImpl();

        // 创建代理对象
        UserService proxy = (UserService) LoggingProxy.createProxy(userService);

        // 调用代理对象的方法
        proxy.addUser("爱丽丝");
        proxy.getUser(1);
    }
}

interface UserService {
    void addUser(String name);
    String getUser(int id);
}

class UserServiceImpl implements UserService {
    @Override
    public void addUser(String name) {
        // 模拟方法执行
        System.out.println("正在添加用户: " + name);
    }

    @Override
    public String getUser(int id) {
        // 模拟方法执行
        System.out.println("正在检索ID为的用户: " + id);
        return "用户" + id;
    }
}
