package com.cn.jmw.demodesignmode.factory.factrory;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jmw
 * @Description TODO
 * @date 2023年03月15日 17:24
 * @Version 1.0
 */
@Slf4j
public class LoggerFactoryTest {

    public static void main(String[] args) {
        //这里就是绑定了某个类，打印日中
        Logger logger = LoggerFactory.getLogger(LoggerFactoryTest.class);
        logger.info("1");

    }
}
