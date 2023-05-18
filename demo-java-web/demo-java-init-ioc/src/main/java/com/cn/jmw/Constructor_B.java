package com.cn.jmw;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * @author jmw
 * @Description TODO
 * @date 2023年05月18日 16:09
 * @Version 1.0
 */
@Service
public class Constructor_B {

    private Constructor_A constructorA;

    public Constructor_B(@Lazy Constructor_A constructorA) {
        this.constructorA = constructorA;
    }

    public Constructor_A getJmMapper() {
        return constructorA;
    }
}
