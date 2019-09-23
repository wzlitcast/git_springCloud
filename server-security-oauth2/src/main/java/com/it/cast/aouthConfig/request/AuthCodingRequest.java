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
 * Cooding登录
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0
 * @since 1.8
 */
public class AuthCodingRequest extends BaseAuthRequest {

    public AuthCodingRequest(AuthConfig config) {
        super(config, AuthSource.CODING);
    }

    @Override
    protected AuthToken getAccessToken(String code) {
        String accessTokenUrl = UrlBuilder.getCodingAccessTokenUrl(config.getClientId(), config.getClientSecret(), code);
        HttpResponse response = HttpRequest.get(accessTokenUrl).execute();
        JSONObject accessTokenObject = JSONObject.parseObject(response.body());
        if (accessTokenObject.getIntValue("code") != 0) {
            throw new AuthException("Unable to get token from coding using code [" + code + "]");
        }
        return AuthToken.builder().accessToken(accessTokenObject.getString("access_token")).build();
    }

    @Override
    protected AuthUser getUserInfo(AuthToken authToken) {
        String accessToken = authToken.getAccessToken();
        HttpResponse response = HttpRequest.get(UrlBuilder.getCodingUserInfoUrl(accessToken)).execute();
        JSONObject object = JSONObject.parseObject(response.body());
        if (object.getIntValue("code") != 0) {
            throw new AuthException(object.getString("msg"));
        }

        object = object.getJSONObject("data");
        return AuthUser.builder()
                .uuid(object.getString("id"))
                .username(object.getString("name"))
                .avatar("https://coding.net/" + object.getString("avatar"))
                .blog("https://coding.net/" + object.getString("path"))
                .nickname(object.getString("name"))
                .company(object.getString("company"))
                .location(object.getString("location"))
                .gender(AuthUserGender.getRealGender(object.getString("sex")))
                .email(object.getString("email"))
                .remark(object.getString("slogan"))
                .token(authToken)
                .source(AuthSource.CODING)
                .build();
    }

    /**
     * 返回认证url，可自行跳转页面
     *
     * @return 返回授权地址
     */
    @Override
    public String authorize() {
        return UrlBuilder.getCodingAuthorizeUrl(config.getClientId(), config.getRedirectUri());
    }
}
