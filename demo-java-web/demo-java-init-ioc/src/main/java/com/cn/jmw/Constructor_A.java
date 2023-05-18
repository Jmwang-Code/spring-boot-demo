package com.cn.jmw;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author jmw
 * @Description TODO
 * @date 2023年05月18日 18:06
 * @Version 1.0
 */
@Service
public class Constructor_A {

    private Constructor_B constructorB;

    public Constructor_A(Constructor_B constructorB) {
        this.constructorB = constructorB;
    }

    public Constructor_B getJmwService() {
        return constructorB;
    }
}
