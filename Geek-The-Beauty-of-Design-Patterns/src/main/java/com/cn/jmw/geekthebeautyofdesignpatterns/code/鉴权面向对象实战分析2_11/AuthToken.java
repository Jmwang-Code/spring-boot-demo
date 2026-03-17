package com.cn.jmw.geekthebeautyofdesignpatterns.code.鉴权面向对象实战分析2_11;


import java.util.Map;

/**
 * @author 79283
 */
public class AuthToken {

    /**
     * 令牌超时（毫秒）静态
     */
    private static final long TOKEN_TIMEOUT_IN_MILLISECONDS = 1 * 60 * 1000;

    /***
     * 令牌
     */
    private String token;

    /***
     * 时间戳
     */
    private long createTime;

    /**
     * 令牌超时（毫秒）
     */
    private long tokenTimeoutInMilliseconds = TOKEN_TIMEOUT_IN_MILLISECONDS;

    /**
     * 默认构造
     * @param token 令牌
     * @param createTime 时间戳验证
     */
    public AuthToken(String token,long createTime){
        this.token = token;
        this.createTime = createTime;
    }

    /**
     * 默认构造
     * @param token 令牌
     * @param createTime 时间戳验证
     * @param tokenTimeoutInMilliseconds 自定义超时时间
     */
    public AuthToken(String token,long createTime,long tokenTimeoutInMilliseconds){
        this.token = token;
        this.createTime = createTime;
        this.tokenTimeoutInMilliseconds = tokenTimeoutInMilliseconds;
    }

    /**
     * 创建令牌
     * @return 令牌
     */
    public static AuthToken createToken(String baseUrl, long createTime, Map<String,String> params){
        //一些列处理
        AuthToken authToken = new AuthToken(baseUrl,createTime);

        return authToken;
    }

    /**
     * 获取令牌
     * @return 令牌字符串
     */
    public String getToken() {
        return token;
    }

    /**
     * 验证令牌是否过期
     */
    public boolean isExpired(){
        return true;
    }

    /**
     * 验证令牌是否一致
     */
    public boolean match(AuthToken authToken){
        return true;
    }

}
