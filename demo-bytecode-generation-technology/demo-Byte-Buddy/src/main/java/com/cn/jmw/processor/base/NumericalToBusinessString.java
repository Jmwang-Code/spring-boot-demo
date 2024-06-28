package com.cn.jmw.processor.base;

import com.cn.jmw.processor.BaseProcessor;

public class NumericalToBusinessString extends BaseProcessor<Integer, String>{

    @Override
    public String process(Integer input, Object... data) throws Exception {
        return "JMWANG"+input.toString();
    }
}
