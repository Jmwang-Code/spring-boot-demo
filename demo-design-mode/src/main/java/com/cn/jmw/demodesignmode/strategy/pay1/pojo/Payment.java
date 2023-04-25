package com.cn.jmw.demodesignmode.strategy.pay1.pojo;

/**
 * @author jmw
 * @Description 付款
 * @date 2023年04月21日 11:05
 * @Version 1.0
 */
public abstract class Payment {
    //支付类型
    public abstract String getName();

    //查询余额
    protected abstract double queryBalance(String uid);

}
