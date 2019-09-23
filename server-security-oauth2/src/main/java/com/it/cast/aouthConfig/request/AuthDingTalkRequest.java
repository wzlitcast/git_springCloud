package com.it.cast.aouthConfig.request;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.it.cast.aouthConfig.config.AuthConfig;
import com.it.cast.aouthConfig.config.AuthSource;
import com.it.cast.aouthConfig.exception.AuthException;
import com.it.cast.aouthConfig.model.AuthDingTalkErrorCode;
import com.it.cast.aouthConfig.model.AuthToken;
import com.it.cast.aouthConfig.model.AuthUser;
import com.it.cast.aouthConfig.model.AuthUserGender;
import com.it.cast.aouthConfig.utils.GlobalAuthUtil;
import com.it.cast.aouthConfig.utils.UrlBuilder;


/**
 * 钉钉登录
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0
 * @since 1.8
 */
public class AuthDingTalkRequest extends BaseAuthRequest {

    public AuthDingTalkRequest(AuthConfig config) {
        super(config, AuthSource.DINGTALK);
    }

    @Override
    protected AuthToken getAccessToken(String code) {
        return AuthToken.builder().accessCode(code).build();
    }

    @Override
    protected AuthUser getUserInfo(AuthToken authToken) {
        String code = authToken.getAccessCode();
        // 根据timestamp, appSecret计算签名值
        String timestamp = System.currentTimeMillis() + "";
        String urlEncodeSignature = GlobalAuthUtil.generateDingTalkSignature(config.getClientSecret(), timestamp);
        JSONObject param = new JSONObject();
        param.put("tmp_auth_code", code);
        HttpResponse response = HttpRequest.post(UrlBuilder.getDingTalkUserInfoUrl(urlEncodeSignature, timestamp, config
                .getClientId())).body(param.toJSONString()).execute();
        String userInfo = response.body();
        JSONObject object = JSON.parseObject(userInfo);
        AuthDingTalkErrorCode errorCode = AuthDingTalkErrorCode.getErrorCode(object.getIntValue("errcode"));
        if (AuthDingTalkErrorCode.EC0 != errorCode) {
            throw new AuthException(errorCode.getDesc());
        }
        object = object.getJSONObject("user_info");
        AuthToken token = AuthToken.builder()
                .openId(object.getString("openid"))
                .unionId(object.getString("unionid"))
                .build();
        return AuthUser.builder()
                .uuid(object.getString("unionid"))
                .nickname(object.getString("nick"))
                .username(object.getString("nick"))
                .gender(AuthUserGender.UNKNOW)
                .source(AuthSource.DINGTALK)
                .token(token)
                .build();
    }

    /**
     * 返回认证url，可自行跳转页面
     *
     * @return 返回授权地址
     */
    @Override
    public String authorize() {
        return UrlBuilder.getDingTalkQrConnectUrl(config.getClientId(), config.getRedirectUri());
    }
}
