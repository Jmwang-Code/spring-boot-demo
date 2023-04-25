package com.cn.jmw.demodesignmode.adapter.loginadapter.v2.adapters;


import com.cn.jmw.demodesignmode.adapter.loginadapter.ResultMsg;

public class LoginForSinaAdapter implements LoginAdapter {
    public boolean support(Object adapter) {
        return adapter instanceof LoginForSinaAdapter;
    }
    public ResultMsg login(String id, Object adapter) {
        return null;
    }
}
