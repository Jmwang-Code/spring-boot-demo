package com.cn.jmw.demodesignmode.proxy.dynamic.jdkproxy;

import com.cn.jmw.demodesignmode.proxy.Person;
import lombok.extern.slf4j.Slf4j;

@Slf4j
/**
 * Created by Tom on 2019/3/10.
 */
public class Girl implements Person {
    public void findLove() {
        log.info("高富帅");
        log.info("身高180m");
        log.info("有48块腹肌");
    }
}
