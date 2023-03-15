package com.cn.jmw.service;

import com.cn.jmw.dao.UserDao;
import com.cn.jmw.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

//自定义 UserDetailService 实现
@Service
public class MyUserDetailService implements UserDetailsService {


    private final UserDao userDao;

    @Autowired
    public MyUserDetailService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userDao.loadUserByUsername(username);
        if (ObjectUtils.isEmpty(user)) throw new RuntimeException("用户名不存在!");
        user.setRoles(userDao.getRolesByUid(user.getId()));
        return user;
    }
}
