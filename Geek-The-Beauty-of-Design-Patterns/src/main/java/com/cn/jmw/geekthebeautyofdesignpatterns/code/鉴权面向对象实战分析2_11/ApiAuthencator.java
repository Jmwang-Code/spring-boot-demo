package com.cn.jmw.geekthebeautyofdesignpatterns.code.鉴权面向对象实战分析2_11;

public interface ApiAuthencator {

    //通过完整url解析鉴权
    void auth(String url);

    //通过鉴权需要的模板进行鉴权
    void auth(ApiRequest apiRequest);
}
