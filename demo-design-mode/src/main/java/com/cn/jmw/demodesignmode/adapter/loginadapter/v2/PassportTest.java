package com.cn.jmw.demodesignmode.adapter.loginadapter.v2;

import org.springframework.aop.framework.adapter.AdvisorAdapter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerAdapter;

public class PassportTest {

    public static void main(String[] args) {

        IPassportForThird passportForThird = new PassportForThirdAdapter();

        passportForThird.loginForQQ("");


    }

}
