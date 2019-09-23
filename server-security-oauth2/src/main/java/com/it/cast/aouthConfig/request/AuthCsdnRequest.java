package com.it.cast.aouthConfig.request;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSONObject;
import com.it.cast.aouthConfig.config.AuthConfig;
import com.it.cast.aouthConfig.config.AuthSource;
import com.it.cast.aouthConfig.exception.AuthException;
import com.it.cast.aouthConfig.model.AuthToken;
import com.it.cast.aouthConfig.model.AuthUser;
import com.it.cast.aouthConfig.model.AuthUserGender;
import com.it.cast.aouthConfig.utils.UrlBuilder;


/**
 * CSDN登录
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0
 * @since 1.8
 */
public class AuthCsdnRequest extends BaseAuthRequest {

    public AuthCsdnRequest(AuthConfig config) {
        super(config, AuthSource.CSDN);
    }

    @Override
    protected AuthToken getAccessToken(String code) {
        String accessTokenUrl = UrlBuilder.getCsdnAccessTokenUrl(config.getClientId(), config.getClientSecret(), code, config
                .getRedirectUri());
        HttpResponse response = HttpRequest.post(accessTokenUrl).execute();
        JSONObject accessTokenObject = JSONObject.parseObject(response.body());
        if (accessTokenObject.containsKey("error_code")) {
            throw new AuthException("Unable to get token from csdn using code [" + code + "]");
        }
        return AuthToken.builder().accessToken(accessTokenObject.getString("access_token")).build();
    }

    @Override
    protected AuthUser getUserInfo(AuthToken authToken) {
        String accessToken = authToken.getAccessToken();
        HttpResponse response = HttpRequest.get(UrlBuilder.getCsdnUserInfoUrl(accessToken)).execute();
        JSONObject object = JSONObject.parseObject(response.body());
        if (object.containsKey("error_code")) {
            throw new AuthException(object.getString("error"));
        }
        return AuthUser.builder()
                .uuid(object.getString("username"))
                .username(object.getString("username"))
                .remark(object.getString("description"))
                .blog(object.getString("website"))
                .gender(AuthUserGender.UNKNOW)
                .token(authToken)
                .source(AuthSource.CSDN)
                .build();
    }

    /**
     * 返回认证url，可自行跳转页面
     *
     * @return 返回授权地址
     */
    @Override
    public String authorize() {
        return UrlBuilder.getCsdnAuthorizeUrl(config.getClientId(), config.getRedirectUri());
    }
}
