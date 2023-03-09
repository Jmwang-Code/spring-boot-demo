package com.cn.jmw.config;

import com.cn.jmw.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.authentication.PasswordEncoderParser;
import org.springframework.security.config.ldap.EmbeddedLdapServerContextSourceFactoryBean;
import org.springframework.security.config.ldap.LdapBindAuthenticationManagerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;

import javax.sql.DataSource;

@Configuration
public class WebSecurityConfigurer {


    private final MyUserDetailService myUserDetailService;

    private final UserDao userDao;

    @Autowired
    public WebSecurityConfigurer(MyUserDetailService myUserDetailService,UserDao userDao) {
        this.myUserDetailService = myUserDetailService;
        this.userDao = userDao;
    }


    //配置 HttpSecurity
    //spring3.0 开始使用SecurityFilterChain 对象直接处理
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .requestMatchers("/login.html").permitAll()
                .requestMatchers("/index").permitAll() //放行资源写在任何前面
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login.html") //用来指定默认登录页面 注意: 一旦自定义登录页面以后必须只能登录url
                .loginProcessingUrl("/doLogin")  //指定处理登录请求 url
                .usernameParameter("uname")
                .passwordParameter("passwd")
                //.successForwardUrl("/index") //认证成功 forward 跳转路径  始终在认证成功之后跳转到指定请求
                //.defaultSuccessUrl("/index", true) //认证成功 redirect 之后跳转   根据上一保存请求进行成功跳转
                .successHandler(new MyAuthenticationSuccessHandler()) //认证成功时处理 前后端分离解决方案
                //.failureForwardUrl("/login.html") //认证失败之后 forward 跳转
                //.failureUrl("/login.html") // 默认 认证失败之后 redirect 跳转
                .failureHandler(new MyAuthenticationFailureHandler()) //用来自定义认证失败之后处理  前后端分离解决方案
                .and()
                .logout()
                //.logoutUrl("/logout")  //指定注销登录 url 默认请求方式必须: GET
                .logoutRequestMatcher(new OrRequestMatcher(
                        new AntPathRequestMatcher("/aa", "GET"),
                        new AntPathRequestMatcher("/bb", "POST")
                ))
                .invalidateHttpSession(true) //默认 会话失效
                .clearAuthentication(true)   //默认 清楚认证标记
                //.logoutSuccessUrl("/login.html") //注销登录 成功之后跳转页面
                .logoutSuccessHandler(new MyLogoutSuccessHandler())  //注销登录成功之后处理
                .and()
                .csrf().disable();//禁止 csrf 跨站请求保护
        return http.build();
    }

    //配置网络安全
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/ignore1", "/ignore2");
    }

    @Bean
    PasswordEncoder encoder(){
        return NoOpPasswordEncoder.getInstance();
    }


//LDAP 身份验证
//    @Bean
//    public DataSource dataSource() {
//        return new EmbeddedDatabaseBuilder()
//                .setType(EmbeddedDatabaseType.H2)
//                .build();
//    }
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        UserDetails user = User.withDefaultPasswordEncoder()
//                .username("user")
//                .password("password")
//                .roles("USER")
//                .build();
//        auth.jdbcAuthentication()
//                .withDefaultSchema()
//                .dataSource(dataSource())
//                .withUser(user);
//    }

//JDBC
//    @Bean
//    public DataSource dataSource() {
//        return new EmbeddedDatabaseBuilder()
//                .setType(EmbeddedDatabaseType.H2)
//                .addScript(JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION)
//                .build();
//    }
//
//    @Bean
//    public UserDetailsManager users(DataSource dataSource) {
//        UserDetails user = User.withDefaultPasswordEncoder()
//                .username("user")
//                .password("password")
//                .roles("USER")
//                .build();
//        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
//        users.createUser(user);
//        return users;
//    }

}
