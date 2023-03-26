package com.cn.jmw.demodesignmode.proxy.dbroute;

import lombok.extern.slf4j.Slf4j;

@Slf4j
/**
 * Created by Tom.
 */
public class OrderDao {
    public int insert(Order order){
        log.info("OrderDao创建Order成功!");
        return 1;
    }
}
