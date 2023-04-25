package com.cn.jmw.demodesignmode.strategy.pay1.pojo;

/**
 * @author jmw
 * @Description TODO
 * @date 2023年04月21日 11:05
 * @Version 1.0
 */
public class JDPay extends Payment{
    public String getName() {
        return "京东白条";
    }

    protected double queryBalance(String uid) {
        return 500;
    }
}
