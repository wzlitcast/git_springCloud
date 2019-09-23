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
 * Facebook登录
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0
 * @since 1.8
 */
public class AuthFacebookRequest extends BaseAuthRequest {

    public AuthFacebookRequest(AuthConfig config) {
        super(config, AuthSource.FACEBOOK);
    }

    @Override
    protected AuthToken getAccessToken(String code) {
        String accessTokenUrl = UrlBuilder.getFacebookAccessTokenUrl(config.getClientId(), config.getClientSecret(), code, config
                .getRedirectUri());
        HttpResponse response = HttpRequest.post(accessTokenUrl).execute();
        JSONObject object = JSONObject.parseObject(response.body());

        if (object.containsKey("error")) {
            throw new AuthException(object.getJSONObject("error").getString("message"));
        }

        return AuthToken.builder()
                .accessToken(object.getString("access_token"))
                .expireIn(object.getIntValue("expires_in"))
                .tokenType(object.getString("token_type"))
                .build();
    }

    @Override
    protected AuthUser getUserInfo(AuthToken authToken) {
        String accessToken = authToken.getAccessToken();
        HttpResponse response = HttpRequest.get(UrlBuilder.getFacebookUserInfoUrl(accessToken)).execute();
        String userInfo = response.body();
        JSONObject object = JSONObject.parseObject(userInfo);
        if (object.containsKey("error")) {
            throw new AuthException(object.getJSONObject("error").getString("message"));
        }
        String picture = null;
        if (object.containsKey("picture")) {
            JSONObject pictureObj = object.getJSONObject("picture");
            pictureObj = pictureObj.getJSONObject("data");
            if (null != pictureObj) {
                picture = pictureObj.getString("url");
            }
        }
        return AuthUser.builder()
                .uuid(object.getString("id"))
                .username(object.getString("name"))
                .nickname(object.getString("name"))
                .avatar(picture)
                .location(object.getString("locale"))
                .email(object.getString("email"))
                .gender(AuthUserGender.getRealGender(object.getString("gender")))
                .token(authToken)
                .source(AuthSource.FACEBOOK)
                .build();
    }

    /**
     * 返回认证url，可自行跳转页面
     *
     * @return 返回授权地址
     */
    @Override
    public String authorize() {
        return UrlBuilder.getFacebookAuthorizeUrl(config.getClientId(), config.getRedirectUri());
    }
}
