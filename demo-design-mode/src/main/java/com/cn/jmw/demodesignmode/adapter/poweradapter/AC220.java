package com.cn.jmw.demodesignmode.adapter.poweradapter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AC220 {

    public int outputAC220V(){
        int output = 220;
        log.info("输出电流" + output + "V");
        return output;
    }
}
