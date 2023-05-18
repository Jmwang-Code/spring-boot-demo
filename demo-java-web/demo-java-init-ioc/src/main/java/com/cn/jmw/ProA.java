package com.cn.jmw;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * @author jmw
 * @Description TODO
 * @date 2023年05月18日 18:37
 * @Version 1.0
 */
@Service
@Scope("prototype")
public class ProA {

//    private ProB setting_pro_b;
//
//    @Autowired
//    public void setProB(ProB setting_pro_b) {
//        this.setting_pro_b = setting_pro_b;
//    }
//
//    public ProB getProB() {
//        return setting_pro_b;
//    }

    private ObjectProvider<ProB> proBProvider;

    @Autowired
    public void setProB(ObjectProvider<ProB> proBProvider) {
        this.proBProvider = proBProvider;
    }

    public ProB getProB() {
        return proBProvider.getIfAvailable();
    }
}

