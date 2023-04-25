package com.cn.jmw.demodesignmode.adapter.loginadapter.v2.adapters;


import com.cn.jmw.demodesignmode.adapter.loginadapter.ResultMsg;

public interface RegistAdapter {
    boolean support(Object adapter);
    ResultMsg login(String id, Object adapter);
}
