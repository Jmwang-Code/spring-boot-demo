package com.cn.jmw.demodesignmode.strategy.pay1.pojo;

/**
 * @author jmw
 * @Description TODO
 * @date 2023年04月21日 11:05
 * @Version 1.0
 */
public class AliPay extends Payment{
    public String getName() {
        return "支付宝";
    }

    protected double queryBalance(String uid) {
        return 900;
    }
}
