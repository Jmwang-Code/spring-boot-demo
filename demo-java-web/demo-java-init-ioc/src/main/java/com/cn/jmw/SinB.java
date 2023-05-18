package com.cn.jmw;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * @author jmw
 * @Description TODO
 * @date 2023年05月18日 18:20
 * @Version 1.0
 */
@Service
public class SinB {

    private SinA setting_sin_a;

    @Autowired
    @Lazy
    public void setSinA(SinA setting_sin_a) {
        this.setting_sin_a = setting_sin_a;
    }

    public SinA getSinA() {
        return setting_sin_a;
    }

}
