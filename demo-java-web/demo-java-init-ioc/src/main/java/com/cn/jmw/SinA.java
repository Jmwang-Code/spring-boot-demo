package com.cn.jmw;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
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
public class SinA {

    private SinB setting_sin_b;

    @Autowired
    @Lazy
    public void setSinB(SinB setting_sin_b) {
        this.setting_sin_b = setting_sin_b;
    }

    public SinB getSinB() {
        return setting_sin_b;
    }

}
