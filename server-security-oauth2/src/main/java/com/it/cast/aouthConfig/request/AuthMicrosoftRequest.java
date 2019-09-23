package com.it.cast.aouthConfig.request;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.it.cast.aouthConfig.config.AuthConfig;
import com.it.cast.aouthConfig.config.AuthSource;
import com.it.cast.aouthConfig.exception.AuthException;
import com.it.cast.aouthConfig.model.AuthResponse;
import com.it.cast.aouthConfig.model.AuthToken;
import com.it.cast.aouthConfig.model.AuthUser;
import com.it.cast.aouthConfig.model.AuthUserGender;
import com.it.cast.aouthConfig.utils.UrlBuilder;


import java.util.HashMap;
import java.util.Map;

/**
 * 微软登录
 *
 * @author yangkai.shen (https://xkcoding.com)
 * @version 1.5
 * @since 1.5
 */
public class AuthMicrosoftRequest extends BaseAuthRequest {
    public AuthMicrosoftRequest(AuthConfig config) {
        super(config, AuthSource.MICROSOFT);
    }

    @Override
    protected AuthToken getAccessToken(String code) {
        String accessTokenUrl = UrlBuilder.getMicrosoftAccessTokenUrl(config.getClientId(), config.getClientSecret(), config
                .getRedirectUri(), code);

        return getToken(accessTokenUrl);
    }

    /**
     * 获取token，适用于获取access_token和刷新token
     *
     * @param accessTokenUrl 实际请求token的地址
     * @return token对象
     */
    private AuthToken getToken(String accessTokenUrl) {
        Map<String, Object> paramMap = new HashMap<>(6);
        HttpUtil.decodeParamMap(accessTokenUrl, "UTF-8").forEach(paramMap::put);
        HttpResponse response = HttpRequest.post(accessTokenUrl)
                .header("Host", "https://login.microsoftonline.com")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .form(paramMap)
                .execute();
        String accessTokenStr = response.body();
        JSONObject object = JSONObject.parseObject(accessTokenStr);

        this.checkResponse(object);

        return AuthToken.builder()
                .accessToken(object.getString("access_token"))
                .expireIn(object.getIntValue("expires_in"))
                .scope(object.getString("scope"))
                .tokenType(object.getString("token_type"))
                .refreshToken(object.getString("refresh_token"))
                .build();
    }

    private void checkResponse(JSONObject response) {
        if (response.containsKey("error")) {
            throw new AuthException(response.getString("error_description"));
        }
    }

    @Override
    protected AuthUser getUserInfo(AuthToken authToken) {
        String token = authToken.getAccessToken();
        String tokenType = authToken.getTokenType();
        String jwt = tokenType + " " + token;
        HttpResponse response = HttpRequest.get(UrlBuilder.getMicrosoftUserInfoUrl())
                .header("Authorization", jwt)
                .execute();
        String userInfo = response.body();
        JSONObject object = JSONObject.parseObject(userInfo);
        return AuthUser.builder()
                .uuid(object.getString("id"))
                .username(object.getString("userPrincipalName"))
                .nickname(object.getString("displayName"))
                .location(object.getString("officeLocation"))
                .email(object.getString("mail"))
                .gender(AuthUserGender.UNKNOW)
                .token(authToken)
                .source(AuthSource.MICROSOFT)
                .build();
    }

    /**
     * 返回认证url，可自行跳转页面
     *
     * @return 返回授权地址
     */
    @Override
    public String authorize() {
        return UrlBuilder.getMicrosoftAuthorizeUrl(config.getClientId(), config.getRedirectUri());
    }

    /**
     * 刷新access token （续期）
     *
     * @param authToken 登录成功后返回的Token信息
     * @return AuthResponse
     */
    @Override
    public AuthResponse refresh(AuthToken authToken) {
        String refreshTokenUrl = UrlBuilder.getMicrosoftRefreshUrl(config.getClientId(), config.getClientSecret(), config
                .getRedirectUri(), authToken.getRefreshToken());

        return AuthResponse.builder().code(ResponseStatus.SUCCESS.getCode()).data(getToken(refreshTokenUrl)).build();
    }
}
