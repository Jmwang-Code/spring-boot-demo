package com.cn.jmw.demodesignmode.proxy.dbroute;


import com.cn.jmw.demodesignmode.proxy.dbroute.proxy.OrderServiceDynamicProxy;
import com.cn.jmw.demodesignmode.proxy.dbroute.proxy.OrderServiceStaticProxy;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Tom on 2019/3/10.
 */
public class DbRouteProxyTest {
    public static void main(String[] args) {
        try {
            Order order = new Order();

//            order.setCreateTime(new Date().getTime());

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            Date date = sdf.parse("2017/02/01");
            order.setCreateTime(date.getTime());

            //动态代理
            IOrderService orderService = (IOrderService)new OrderServiceDynamicProxy().getInstance(new OrderService());
            orderService.createOrder(order);

            System.out.println();
            //静态代理
            new OrderServiceStaticProxy(new OrderService()).createOrder(order);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
