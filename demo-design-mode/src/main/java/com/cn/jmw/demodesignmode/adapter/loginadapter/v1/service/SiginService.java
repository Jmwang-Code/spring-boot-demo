package com.cn.jmw.demodesignmode.adapter.loginadapter.v1.service;

import com.cn.jmw.demodesignmode.adapter.loginadapter.ResultMsg;
import com.cn.jmw.demodesignmode.template.jdbc.Member;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SiginService {

    /**
     * 注册方法
     * @param username
     * @param password
     * @return
     */
    public ResultMsg regist(String username, String password){
        return  new ResultMsg(200,"注册成功",new Member());
    }


    /**
     * 登录的方法
     * @param username
     * @param password
     * @return
     */
    public ResultMsg login(String username,String password){
        log.info("登录的用户名是：{}",username);
        return null;
    }

}
