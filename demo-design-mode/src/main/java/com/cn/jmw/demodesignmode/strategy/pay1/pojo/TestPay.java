package com.cn.jmw.demodesignmode.strategy.pay1.pojo;

/**
 * @author jmw
 * @Description TODO
 * @date 2023年04月21日 11:25
 * @Version 1.0
 */
public class TestPay {

    public static void main(String[] args) {
        Payment aliPay = PayStrategy.get("AliPay");
        System.out.println(aliPay.getName());
    }
}
