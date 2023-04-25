package com.cn.jmw.demodesignmode.adapter.poweradapter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PowerAdapter implements DC5 {

   private AC220 ac220;

    public PowerAdapter(AC220 ac220) {
        this.ac220 = ac220;
    }

    public int outoupDC5V() {
        int adapterInput = ac220.outputAC220V();
        int adapterOutput = adapterInput / 44;
        log.info("使用PowerAdapter输入AC:{},输出DC：{}", adapterInput, adapterOutput);
        return adapterOutput;
    }
}
