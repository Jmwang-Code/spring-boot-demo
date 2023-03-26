package com.cn.jmw.demodesignmode.proxy.staticproxy;

/**
 * @author jmw
 * @Description TODO
 * @date 2023年03月24日 10:47
 * @Version 1.0
 */
public class ATest {
    public static void main(String[] args) {

        ProxyA father = new ProxyA(new RealA());
        father.findLove();

        //农村，媒婆
        //婚介所

        //大家每天都在用的一种静态代理的形式
        //对数据库进行分库分表
        //ThreadLocal
        //进行数据源动态切换
    }
}
