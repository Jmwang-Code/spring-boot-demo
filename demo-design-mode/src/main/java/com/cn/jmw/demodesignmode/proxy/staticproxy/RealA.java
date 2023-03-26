package com.cn.jmw.demodesignmode.proxy.staticproxy;

import lombok.extern.slf4j.Slf4j;

/**
 * @author jmw
 * @Description TODO
 * @date 2023年03月24日 10:47
 * @Version 1.0
 */
@Slf4j
public class RealA implements A{

    public void findLove(){
        log.info("儿子要求：肤白貌美大长腿");
    }

    public void findJob(){

    }

    public void eat(){

    }
}
