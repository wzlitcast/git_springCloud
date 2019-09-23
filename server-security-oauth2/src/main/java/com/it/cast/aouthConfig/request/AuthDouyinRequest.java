package com.it.cast.aouthConfig.request;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSONObject;
import com.it.cast.aouthConfig.config.AuthConfig;
import com.it.cast.aouthConfig.config.AuthSource;
import com.it.cast.aouthConfig.exception.AuthException;
import com.it.cast.aouthConfig.model.AuthResponse;
import com.it.cast.aouthConfig.model.AuthToken;
import com.it.cast.aouthConfig.model.AuthUser;
import com.it.cast.aouthConfig.model.AuthUserGender;
import com.it.cast.aouthConfig.utils.UrlBuilder;



/**
 * 抖音登录
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0
 * @since 1.8
 */
public class AuthDouyinRequest extends BaseAuthRequest {

    public AuthDouyinRequest(AuthConfig config) {
        super(config, AuthSource.DOUYIN);
    }

    @Override
    protected AuthToken getAccessToken(String code) {
        String accessTokenUrl = UrlBuilder.getDouyinAccessTokenUrl(config.getClientId(), config.getClientSecret(), code);
        return this.getToken(accessTokenUrl);
    }

    @Override
    protected AuthUser getUserInfo(AuthToken authToken) {
        String accessToken = authToken.getAccessToken();
        String openId = authToken.getOpenId();
        String url = UrlBuilder.getDouyinUserInfoUrl(accessToken, openId);
        HttpResponse response = HttpRequest.get(url).execute();
        JSONObject object = JSONObject.parseObject(response.body());

        JSONObject userInfoObject = this.checkResponse(object);

        return AuthUser.builder()
                .uuid(userInfoObject.getString("union_id"))
                .username(userInfoObject.getString("nickname"))
                .nickname(userInfoObject.getString("nickname"))
                .avatar(userInfoObject.getString("avatar"))
                .remark(userInfoObject.getString("description"))
                .gender(AuthUserGender.UNKNOW)
                .token(authToken)
                .source(AuthSource.DOUYIN)
                .build();
    }

    /**
     * 返回认证url，可自行跳转页面
     *
     * @return 返回授权地址
     */
    @Override
    public String authorize() {
        return UrlBuilder.getDouyinAuthorizeUrl(config.getClientId(), config.getRedirectUri());
    }

    @Override
    public AuthResponse refresh(AuthToken oldToken) {
        String refreshTokenUrl = UrlBuilder.getDouyinRefreshUrl(config.getClientId(), oldToken.getRefreshToken());
        return AuthResponse.builder()
                .code(ResponseStatus.SUCCESS.getCode())
                .data(this.getToken(refreshTokenUrl))
                .build();
    }

    /**
     * 检查响应内容是否正确
     *
     * @param object 请求响应内容
     * @return 实际请求数据的json对象
     */
    private JSONObject checkResponse(JSONObject object) {
        String message = object.getString("message");
        JSONObject data = object.getJSONObject("data");
        int errorCode = data.getIntValue("error_code");
        if ("error".equals(message) || errorCode != 0) {
            throw new AuthException(errorCode, data.getString("description"));
        }
        return data;
    }

    /**
     * 获取token，适用于获取access_token和刷新token
     *
     * @param accessTokenUrl 实际请求token的地址
     * @return token对象
     */
    private AuthToken getToken(String accessTokenUrl) {
        HttpResponse response = HttpRequest.post(accessTokenUrl).execute();
        String accessTokenStr = response.body();
        JSONObject object = JSONObject.parseObject(accessTokenStr);

        JSONObject accessTokenObject = this.checkResponse(object);
        return AuthToken.builder()
                .accessToken(accessTokenObject.getString("access_token"))
                .openId(accessTokenObject.getString("open_id"))
                .expireIn(accessTokenObject.getIntValue("expires_in"))
                .refreshToken(accessTokenObject.getString("refresh_token"))
                .scope(accessTokenObject.getString("scope"))
                .build();
    }
}
