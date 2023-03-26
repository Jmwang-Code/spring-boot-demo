package com.cn.jmw.demodesignmode.proxy.staticproxy;

import lombok.extern.slf4j.Slf4j;

/**
 * @author jmw
 * @Description TODO
 * @date 2023年03月24日 10:47
 * @Version 1.0
 */
@Slf4j
public class ProxyA implements A{
    private RealA person;

    public ProxyA(RealA person){
        this.person = person;
    }

    public void findLove(){
        log.info("父亲物色对象");
        this.person.findLove();
        log.info("双方父母同意");
    }

    public void findJob(){

    }
}
