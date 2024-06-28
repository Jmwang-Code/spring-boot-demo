package com.cn.jmw.processor.base;

import com.cn.jmw.processor.BaseProcessor;

public class Error608Processor extends BaseProcessor {

    @Override
    public Object process(Object input, Object... data) throws Exception {
        System.out.println("A");
        return null;
    }
}
