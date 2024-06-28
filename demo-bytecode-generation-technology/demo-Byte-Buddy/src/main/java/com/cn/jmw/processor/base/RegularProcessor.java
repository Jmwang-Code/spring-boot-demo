package com.cn.jmw.processor.base;

import com.cn.jmw.processor.BaseProcessor;

public class RegularProcessor extends BaseProcessor<String, String>{

    @Override
    public String process(String input, Object... data) throws Exception {
        //正则判断 开头为JMWANG数字
        if(input.matches("^JMWANG\\d+$")){
            return input;
        }
        return "";
    }
}
