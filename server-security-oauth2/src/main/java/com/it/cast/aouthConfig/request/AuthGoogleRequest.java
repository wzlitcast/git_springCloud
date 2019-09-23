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
 * Google登录
 *
 * @author yangkai.shen (https://xkcoding.com)
 * @version 1.3
 * @since 1.3
 */
public class AuthGoogleRequest extends BaseAuthRequest {

    public AuthGoogleRequest(AuthConfig config) {
        super(config, AuthSource.GOOGLE);
    }

    @Override
    protected AuthToken getAccessToken(String code) {
        String accessTokenUrl = UrlBuilder.getGoogleAccessTokenUrl(config.getClientId(), config.getClientSecret(), code, config
                .getRedirectUri());
        HttpResponse response = HttpRequest.post(accessTokenUrl).execute();
        JSONObject object = JSONObject.parseObject(response.body());

        if (object.containsKey("error") || object.containsKey("error_description")) {
            throw new AuthException("get google access_token has error:[" + object.getString("error") + "], error_description:[" + object
                    .getString("error_description") + "]");
        }

        return AuthToken.builder()
                .accessToken(object.getString("access_token"))
                .expireIn(object.getIntValue("expires_in"))
                .scope(object.getString("scope"))
                .tokenType(object.getString("token_type"))
                .idToken(object.getString("id_token"))
                .build();
    }

    @Override
    protected AuthUser getUserInfo(AuthToken authToken) {
        String accessToken = authToken.getIdToken();
        HttpResponse response = HttpRequest.get(UrlBuilder.getGoogleUserInfoUrl(accessToken)).execute();
        String userInfo = response.body();
        JSONObject object = JSONObject.parseObject(userInfo);
        return AuthUser.builder()
                .uuid(object.getString("sub"))
                .username(object.getString("name"))
                .avatar(object.getString("picture"))
                .nickname(object.getString("name"))
                .location(object.getString("locale"))
                .email(object.getString("email"))
                .gender(AuthUserGender.UNKNOW)
                .token(authToken)
                .source(AuthSource.GOOGLE)
                .build();
    }

    /**
     * 返回认证url，可自行跳转页面
     *
     * @return 返回授权地址
     */
    @Override
    public String authorize() {
        return UrlBuilder.getGoogleAuthorizeUrl(config.getClientId(), config.getRedirectUri());
    }
}
