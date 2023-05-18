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
public class ProB {

//    private ProA setting_pro_a;
//
//    @Autowired
//    public void setProA(ProA setting_pro_a) {
//        this.setting_pro_a = setting_pro_a;
//    }
//
//    public ProA getProA() {
//        return setting_pro_a;
//    }

    private ObjectProvider<ProA> proAProvider;

    @Autowired
    public void setProA(ObjectProvider<ProA> proAProvider) {
        this.proAProvider = proAProvider;
    }

    public ProA getProA() {
        return proAProvider.getIfAvailable();
    }
}
