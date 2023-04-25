package com.cn.jmw.demodesignmode.strategy.strategyanddelegate;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jmw
 * @Description TODO
 * @date 2023年04月21日 11:36
 * @Version 1.0
 */
public class DispatcherServlet extends HttpServlet {

    private List<Handler> handlerMapping = new ArrayList<>();

    @Override
    public void init() throws ServletException {
        try {
            Class<?> memberControllerClass = MemberController.class;
            handlerMapping.add(new Handler()
                    .setController(memberControllerClass.getConstructor().newInstance())
                    .setMethod(memberControllerClass.getMethod("getMemberById", String.class))
                    .setUrl("/web/getMemberById.json"));
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }


    private void doDispatch(HttpServletRequest request, HttpServletResponse response) {
//1、获取用户请求的 url
// 如果按照 J2EE 的标准、每个 url 对对应一个 Serlvet，url 由浏览器输入
        String uri = request.getRequestURI();
//2、Servlet 拿到 url 以后，要做权衡（要做判断，要做选择）
// 根据用户请求的 URL，去找到这个 url 对应的某一个 java 类的方法
//3、通过拿到的 URL 去 handlerMapping（我们把它认为是策略常量）
        Handler handle = null;
        for (Handler h : handlerMapping) {
            if (uri.equals(h.getUrl())) {
                handle = h;
                break;
            }
        }
//4、将具体的任务分发给 Method（通过反射去调用其对应的方法）
        Object object = null;
        try {
            object = handle.getMethod().invoke(handle.getController(), request.getParameter("mid"));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
//5、获取到 Method 执行的结果，通过 Response 返回出去
        try {
            response.getWriter().write(object.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void service(HttpServletRequest req, HttpServletResponse resp) throws
            ServletException, IOException {
        try {
            doDispatch(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class Handler {
        private Object controller;
        private Method method;
        private String url;

        public Object getController() {
            return controller;
        }

        public Handler setController(Object controller) {
            this.controller = controller;
            return this;
        }

        public Method getMethod() {
            return method;
        }

        public Handler setMethod(Method method) {
            this.method = method;
            return this;
        }

        public String getUrl() {
            return url;
        }

        public Handler setUrl(String url) {
            this.url = url;
            return this;
        }
    }

    public static void main(String[] args) {
        DispatcherServlet dispatcherServlet = new DispatcherServlet();
        try {
            dispatcherServlet.init();
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }
}
