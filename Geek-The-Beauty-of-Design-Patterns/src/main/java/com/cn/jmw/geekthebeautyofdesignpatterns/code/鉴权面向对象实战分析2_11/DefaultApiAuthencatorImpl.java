package com.cn.jmw.geekthebeautyofdesignpatterns.code.鉴权面向对象实战分析2_11;

public class DefaultApiAuthencatorImpl implements ApiAuthencator{

    //
    private CredentialStorage credentialStorage;

    public DefaultApiAuthencatorImpl(){}

    public DefaultApiAuthencatorImpl(CredentialStorage credentialStorage){
        this.credentialStorage = credentialStorage;
    }

    @Override
    public void auth(String url) {
        ApiRequest apiRequest = ApiRequest.createFromFullUrl(url);
        auth(apiRequest);
    }

    @Override
    public void auth(ApiRequest apiRequest) {
        String appId = apiRequest.getAppId();
        String token = apiRequest.getToken();
        long timestamp = apiRequest.getTimestamp();
        String originalUrl = apiRequest.getBaseUrl();

        //授权令牌
        AuthToken clientAuthToken = new AuthToken(token, timestamp);
        //令牌已超时
        if (clientAuthToken.isExpired()) {
            throw new RuntimeException("Token is expired.");
        }

        //通过appid，获取密码等信息
        String password = credentialStorage.getPasswordByAppId(appId);
        AuthToken serverAuthToken = AuthToken.createToken(originalUrl,timestamp,null);

        if (!serverAuthToken.match(clientAuthToken)) {
            throw new RuntimeException("Token verfication failed.");
        }
    }
}
