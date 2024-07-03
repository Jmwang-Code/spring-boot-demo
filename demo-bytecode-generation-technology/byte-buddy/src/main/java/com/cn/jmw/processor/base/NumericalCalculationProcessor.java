package com.cn.jmw.processor.base;

import com.cn.jmw.processor.BaseProcessor;

public class NumericalCalculationProcessor extends BaseProcessor<Integer, Integer> {

    @Override
    public Integer process(Integer input, Object... data) throws Exception {
        return input + 1;
    }
}