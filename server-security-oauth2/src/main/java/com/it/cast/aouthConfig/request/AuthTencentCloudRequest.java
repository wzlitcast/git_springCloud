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
 * 腾讯云登录
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0
 * @since 1.8
 */
public class AuthTencentCloudRequest extends BaseAuthRequest {

    public AuthTencentCloudRequest(AuthConfig config) {
        super(config, AuthSource.TENCENT_CLOUD);
    }

    @Override
    protected AuthToken getAccessToken(String code) {
        String accessTokenUrl = UrlBuilder.getTencentCloudAccessTokenUrl(config.getClientId(), config.getClientSecret(), code);
        HttpResponse response = HttpRequest.get(accessTokenUrl).execute();
        JSONObject object = JSONObject.parseObject(response.body());
        if (object.getIntValue("code") != 0) {
            throw new AuthException("Unable to get token from tencent cloud using code [" + code + "]: " + object.get("msg"));
        }
        return AuthToken.builder().accessToken(object.getString("access_token")).build();
    }

    @Override
    protected AuthUser getUserInfo(AuthToken authToken) {
        String accessToken = authToken.getAccessToken();
        HttpResponse response = HttpRequest.get(UrlBuilder.getTencentCloudUserInfoUrl(accessToken)).execute();
        JSONObject object = JSONObject.parseObject(response.body());
        if (object.getIntValue("code") != 0) {
            throw new AuthException(object.getString("msg"));
        }
        object = object.getJSONObject("data");
        return AuthUser.builder()
                .uuid(object.getString("id"))
                .username(object.getString("name"))
                .avatar("https://dev.tencent.com/" + object.getString("avatar"))
                .blog("https://dev.tencent.com/" + object.getString("path"))
                .nickname(object.getString("name"))
                .company(object.getString("company"))
                .location(object.getString("location"))
                .gender(AuthUserGender.getRealGender(object.getString("sex")))
                .email(object.getString("email"))
                .remark(object.getString("slogan"))
                .token(authToken)
                .source(AuthSource.TENCENT_CLOUD)
                .build();
    }

    /**
     * 返回认证url，可自行跳转页面
     *
     * @return 返回授权地址
     */
    @Override
    public String authorize() {
        return UrlBuilder.getTencentCloudAuthorizeUrl(config.getClientId(), config.getRedirectUri());
    }
}
