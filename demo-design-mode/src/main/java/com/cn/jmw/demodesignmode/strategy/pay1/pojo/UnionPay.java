package com.cn.jmw.demodesignmode.strategy.pay1.pojo;

/**
 * @author jmw
 * @Description TODO
 * @date 2023年04月21日 11:07
 * @Version 1.0
 */
public class UnionPay extends Payment{
    public String getName() {
        return "银联支付";
    }

    protected double queryBalance(String uid) {
        return 100;
    }
}
