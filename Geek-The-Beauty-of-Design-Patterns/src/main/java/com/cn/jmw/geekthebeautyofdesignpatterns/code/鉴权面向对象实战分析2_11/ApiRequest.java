package com.cn.jmw.geekthebeautyofdesignpatterns.code.鉴权面向对象实战分析2_11;

/**
 * API请求信息
 */
public class ApiRequest {

    private String baseUrl;

    private String token;

    private String appId;

    private long timestamp;

    public ApiRequest(
            String baseUrl,
            String token,
            String appId,
            long createTime
        ){
        this.baseUrl = baseUrl;
        this.token = token;
        this.appId = appId;
        this.timestamp = timestamp;
    }

    public static ApiRequest createFromFullUrl(String url){
        //url解析
        return new ApiRequest(null,null,null,-1);
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getToken() {
        return token;
    }

    public String getAppId() {
        return appId;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
