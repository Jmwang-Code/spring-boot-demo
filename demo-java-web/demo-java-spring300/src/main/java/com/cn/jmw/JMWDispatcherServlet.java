package com.cn.jmw;

import com.cn.jmw.annotation.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author jmw
 * @Description
 * @date 2023年05月12日 0:49
 * @Version 1.0
 */
public class JMWDispatcherServlet extends HttpServlet {

    //保存application.properties配置文件中的内容
    private Properties contextConfig = new Properties();

    //保存扫描的所有的类名
    private List<String> classNames = new ArrayList<String>();

    //传说中的IOC容器，我们来揭开它的神秘面纱
    //为了简化程序，暂时不考虑ConcurrentHashMap
    // 主要还是关注设计思想和原理
    //这里 TODO 可以增加单例模式或者多利模式
    private Map<String,Object> ioc = new ConcurrentHashMap();

    //保存url和Method的对应关系
    private Map<String,Method> handlerMapping = new HashMap<String,Method>();


    //初始化
    @Override
    public void init(){
        //1.加载配置文件
        doLoadConfig("contextConfigLocation");

        //2.扫描相关类
        doScanner(contextConfig.getProperty("scanPackage"));

        //3、初始化扫描到的类，并且将它们放入到IOC容器之中
        doInstance();

        //4、完成依赖注入 DI
        doAutowired();

        //5、初始化HandlerMapping
        initHandlerMapping();
        System.out.println("init finish!");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //6、调用，运行阶段
        try {
            doDispatch(req,resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().write("500 Exection,Detail : " + Arrays.toString(e.getStackTrace()));
        }


    }

    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        //绝对路径
        String url = req.getRequestURI();
        //处理成相对路径
        String contextPath = req.getContextPath();
        url = url.replaceAll(contextPath,"").replaceAll("/+","/");

        if(!this.handlerMapping.containsKey(url)){
            resp.getWriter().write("404 Not Found!!!");
            return;
        }


        Method method = this.handlerMapping.get(url);


        //从reqest中拿到url传过来的参数
        Map<String,String[]> params = req.getParameterMap();

        //获取方法的形参列表
        Class<?> [] parameterTypes = method.getParameterTypes();

        Object [] paramValues = new Object[parameterTypes.length];

        for (int i = 0; i < parameterTypes.length; i ++) {
            Class parameterType = parameterTypes[i];
            //不能用instanceof，parameterType它不是实参，而是形参
            if(parameterType == HttpServletRequest.class){
                paramValues[i] = req;
                continue;
            }else if(parameterType == HttpServletResponse.class){
                paramValues[i] = resp;
                continue;
            }else if(parameterType == String.class){
                JMWRequestParam requestParam = (JMWRequestParam)parameterType.getAnnotation(JMWRequestParam.class);
                if(params.containsKey(requestParam.value())) {
                    for (Map.Entry<String,String[]> param : params.entrySet()){
                        String value = Arrays.toString(param.getValue())
                                .replaceAll("\\[|\\]","")
                                .replaceAll("\\s",",");
                        paramValues[i] = value;
                    }
                }
            }
        }

        //投机取巧的方式
        //通过反射拿到method所在class，拿到class之后还是拿到class的名称
        //再调用toLowerFirstCase获得beanName
        String beanName  = toLowerFirstCase(method.getDeclaringClass().getSimpleName());
        method.invoke(ioc.get(beanName),paramValues);
    }

    private void initHandlerMapping() {
        if(ioc.isEmpty()){ return; }
        for (Map.Entry<String, Object> entry : ioc.entrySet()) {
            Class<?> clazz = entry.getValue().getClass();

            if (!clazz.isAnnotationPresent(JMWController.class)) {
                continue;
            }

            //保存写在类上面的@GPRequestMapping("/test")
            String baseUrl = "";
            if (clazz.isAnnotationPresent(JMWRequestMapping.class)) {
                JMWRequestMapping requestMapping = clazz.getAnnotation(JMWRequestMapping.class);
                baseUrl = requestMapping.value();
            }

            //默认获取所有的public方法
            for (Method method : clazz.getMethods()) {
                if (!method.isAnnotationPresent(JMWRequestMapping.class)) {
                    continue;
                }

                JMWRequestMapping requestMapping = method.getAnnotation(JMWRequestMapping.class);
                //优化
                // //demo///query
                String url = ("/" + baseUrl + "/" + requestMapping.value())
                        .replaceAll("/+", "/");
                handlerMapping.put(url, method);
                System.out.println("Mapped :" + url + "," + method);

            }
        }
    }

    private void doAutowired() {
        if(ioc.isEmpty()){return;}

        for (Map.Entry<String, Object> entry : ioc.entrySet()) {
            //Declared 所有的，特定的 字段，包括private/protected/default
            //正常来说，普通的OOP编程只能拿到public的属性
            Field[] declaredFields = entry.getValue().getClass().getDeclaredFields();
            for (Field field:declaredFields) {
                if (field.isAnnotationPresent(JMWAutowired.class)){
                    JMWAutowired annotation = field.getAnnotation(JMWAutowired.class);
                    //如果用户没有自定义beanName，默认就根据类型注入
                    String beanName = toLowerFirstCase(annotation.value().trim());

                    if("".equals(beanName)){
                        //获得接口的类型，作为key待会拿这个key到ioc容器中去取值
                        beanName = field.getType().getName();
                    }

                    //如果是public以外的修饰符，只要加了@Autowired注解，都要强制赋值
                    //反射中叫做暴力访问
                    field.setAccessible(true);

                    try {
                        //用反射机制，动态给字段赋值
                        field.set(entry.getValue(),ioc.get(beanName));
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    private void doInstance() {
        if (classNames.isEmpty())return;

        for (String className:classNames){
            try {
                Class<?> clazz = Class.forName(className);
                //什么样的类才需要初始化呢？
                //加了注解的类，才初始化，怎么判断？
                //为了简化代码逻辑，主要体会设计思想，只举例 @Controller和@Service,
                // @Componment...就一一举例了
                if (clazz.isAnnotationPresent(JMWController.class)){
                    //创建一个无参实例，这里的前提是当前的简化类只要无参的实例，如果需要通过别的方式实例化 可以进行加载
                    Object instance = clazz.getDeclaredConstructor().newInstance();
                    //Spring默认类名首字母小写
                    String beanName = toLowerFirstCase(clazz.getSimpleName());
                    ioc.put(beanName,instance);
                }else if (clazz.isAnnotationPresent(JMWService.class)){
                    //1、自定义的beanName
                    JMWService service = clazz.getAnnotation(JMWService.class);
                    String beanName = service.value();
                    //2、默认类名首字母小写
                    if("".equals(beanName.trim())){
                        beanName = toLowerFirstCase(clazz.getSimpleName());
                    }

                    Object instance = clazz.getDeclaredConstructor().newInstance();
                    ioc.put(beanName,instance);
                    //3、根据类型自动赋值,投机取巧的方式
                    for (Class<?> i : clazz.getInterfaces()) {
                        if(ioc.containsKey(i.getName())){
                            throw new Exception("The “" + i.getName() + "” is exists!!");
                        }
                        //把接口的类型直接当成key了
                        ioc.put(i.getName(),instance);
                    }
                }
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private String toLowerFirstCase(String simpleName) {
        char [] chars = simpleName.toCharArray();
        //之所以加，是因为大小写字母的ASCII码相差32，
        // 而且大写字母的ASCII码要小于小写字母的ASCII码
        //在Java中，对char做算学运算，实际上就是对ASCII码做算学运算
        chars[0] += 32;
        return String.valueOf(chars);
    }

    //扫描类
    private void doScanner(String scanPackage) {
        URL url = this.getClass().getClassLoader().getResource(scanPackage.replaceAll("\\.","/"));
        File classPath = new File(url.getFile());
        for (File file:classPath.listFiles()){
            if (file.isDirectory()){
                doScanner((scanPackage+"."+file.getName()));
            }else{
                if (!file.getName().endsWith(".class"))continue;
                else {
                    String className = (scanPackage + "." + file.getName().replace(".class",""));
                    classNames.add(className);
                }
            }
        }
    }

    //加载配置文件
    private void doLoadConfig(String contextConfigLocation) {
        //直接从类路径下找到Spring主配置文件所在的路径
        //并且将其读取出来放到Properties对象中
        //相对于scanPackage=com.cn.jmw 从文件中保存到了内存中
        InputStream fis = this.getClass().getClassLoader().getResourceAsStream("application.properties");
        try {
            contextConfig.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(null != fis){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
