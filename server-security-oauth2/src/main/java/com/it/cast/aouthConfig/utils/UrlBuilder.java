package com.it.cast.aouthConfig.utils;



import com.it.cast.aouthConfig.config.AuthSource;

import java.text.MessageFormat;

/**
 * Url构建工具类
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0
 * @since 1.0
 */
public class UrlBuilder {

    private static final String GITHUB_ACCESS_TOKEN_PATTERN = "{0}?client_id={1}&client_secret={2}&code={3}&redirect_uri={4}";
    private static final String GITHUB_USER_INFO_PATTERN = "{0}?access_token={1}";
    private static final String GITHUB_AUTHORIZE_PATTERN = "{0}?client_id={1}&state=1&redirect_uri={2}";

    private static final String GOOGLE_AUTHORIZE_PATTERN = "{0}?client_id={1}&response_type=code&scope=openid%20email%20profile&redirect_uri={2}&state={3}";
    private static final String GOOGLE_ACCESS_TOKEN_PATTERN = "{0}?client_id={1}&client_secret={2}&code={3}&redirect_uri={4}&grant_type=authorization_code";
    private static final String GOOGLE_USER_INFO_PATTERN = "{0}?id_token={1}";

    private static final String WEIBO_ACCESS_TOKEN_PATTERN = "{0}?client_id={1}&client_secret={2}&grant_type=authorization_code&code={3}&redirect_uri={4}";
    private static final String WEIBO_USER_INFO_PATTERN = "{0}?{1}";
    private static final String WEIBO_AUTHORIZE_PATTERN = "{0}?client_id={1}&response_type=code&redirect_uri={2}";

    private static final String GITEE_ACCESS_TOKEN_PATTERN = "{0}?client_id={1}&client_secret={2}&grant_type=authorization_code&code={3}&redirect_uri={4}";
    private static final String GITEE_USER_INFO_PATTERN = "{0}?access_token={1}";
    private static final String GITEE_AUTHORIZE_PATTERN = "{0}?client_id={1}&response_type=code&redirect_uri={2}";

    private static final String DING_TALK_QRCONNECT_PATTERN = "{0}?appid={1}&response_type=code&scope=snsapi_login&state=STATE&redirect_uri={2}";
    private static final String DING_TALK_USER_INFO_PATTERN = "{0}?signature={1}&timestamp={2}&accessKey={3}";

    private static final String BAIDU_ACCESS_TOKEN_PATTERN = "{0}?client_id={1}&client_secret={2}&grant_type=authorization_code&code={3}&redirect_uri={4}";
    private static final String BAIDU_USER_INFO_PATTERN = "{0}?access_token={1}";
    private static final String BAIDU_AUTHORIZE_PATTERN = "{0}?client_id={1}&response_type=code&redirect_uri={2}&display=popup";
    private static final String BAIDU_REVOKE_PATTERN = "{0}?access_token={1}";

    private static final String CSDN_ACCESS_TOKEN_PATTERN = "{0}?client_id={1}&client_secret={2}&grant_type=authorization_code&code={3}&redirect_uri={4}";
    private static final String CSDN_USER_INFO_PATTERN = "{0}?access_token={1}";
    private static final String CSDN_AUTHORIZE_PATTERN = "{0}?client_id={1}&response_type=code&redirect_uri={2}";

    private static final String CODING_ACCESS_TOKEN_PATTERN = "{0}?client_id={1}&client_secret={2}&grant_type=authorization_code&code={3}";
    private static final String CODING_USER_INFO_PATTERN = "{0}?access_token={1}";
    private static final String CODING_AUTHORIZE_PATTERN = "{0}?client_id={1}&response_type=code&redirect_uri={2}&scope=user";

    private static final String TENCENT_ACCESS_TOKEN_PATTERN = "{0}?client_id={1}&client_secret={2}&grant_type=authorization_code&code={3}";
    private static final String TENCENT_USER_INFO_PATTERN = "{0}?access_token={1}";
    private static final String TENCENT_AUTHORIZE_PATTERN = "{0}?client_id={1}&response_type=code&redirect_uri={2}&scope=user";

    private static final String OSCHINA_ACCESS_TOKEN_PATTERN = "{0}?client_id={1}&client_secret={2}&grant_type=authorization_code&code={3}&redirect_uri={4}&dataType=json";
    private static final String OSCHINA_USER_INFO_PATTERN = "{0}?access_token={1}&dataType=json";
    private static final String OSCHINA_AUTHORIZE_PATTERN = "{0}?client_id={1}&response_type=code&redirect_uri={2}";

    private static final String ALIPAY_AUTHORIZE_PATTERN = "{0}?app_id={1}&scope=auth_user&redirect_uri={2}&state=init";

    private static final String QQ_ACCESS_TOKEN_PATTERN = "{0}?client_id={1}&client_secret={2}&grant_type=authorization_code&code={3}&redirect_uri={4}";
    private static final String QQ_USER_INFO_PATTERN = "{0}?oauth_consumer_key={1}&access_token={2}&openid={3}";
    private static final String QQ_AUTHORIZE_PATTERN = "{0}?client_id={1}&response_type=code&redirect_uri={2}&state={3}";
    private static final String QQ_OPENID_PATTERN = "{0}?access_token={1}&unionid=1";

    private static final String WECHAT_AUTHORIZE_PATTERN = "{0}?appid={1}&redirect_uri={2}&response_type=code&scope=snsapi_login&state={3}#wechat_redirect";
    private static final String WECHAT_ACCESS_TOKEN_PATTERN = "{0}?appid={1}&secret={2}&code={3}&grant_type=authorization_code";
    private static final String WECHAT_REFRESH_TOKEN_PATTERN = "{0}?appid={1}&grant_type=refresh_token&refresh_token={2}";
    private static final String WECHAT_USER_INFO_PATTERN = "{0}?access_token={1}&openid={2}&lang=zh_CN";

    private static final String TAOBAO_AUTHORIZE_PATTERN = "{0}?response_type=code&client_id={1}&redirect_uri={2}&state={3}&view=web";
    private static final String TAOBAO_ACCESS_TOKEN_PATTERN = "{0}?client_id={1}&client_secret={2}&code={3}&redirect_uri={4}&grant_type=authorization_code";

    private static final String FACEBOOK_AUTHORIZE_PATTERN = "{0}?client_id={1}&redirect_uri={2}&state={3}&response_type=code&scope=";
    private static final String FACEBOOK_ACCESS_TOKEN_PATTERN = "{0}?client_id={1}&client_secret={2}&code={3}&redirect_uri={4}&grant_type=authorization_code";
    private static final String FACEBOOK_USER_INFO_PATTERN = "{0}?access_token={1}&fields=id,name,birthday,gender,hometown,email,devices,picture.width(400)";

    private static final String DOUYIN_AUTHORIZE_PATTERN = "{0}?client_key={1}&redirect_uri={2}&state={3}&response_type=code&scope=user_info";
    private static final String DOUYIN_ACCESS_TOKEN_PATTERN = "{0}?client_key={1}&client_secret={2}&code={3}&grant_type=authorization_code";
    private static final String DOUYIN_USER_INFO_PATTERN = "{0}?access_token={1}&open_id={2}";
    private static final String DOUYIN_REFRESH_TOKEN_PATTERN = "{0}?client_key={1}&refresh_token={2}&grant_type=refresh_token";

    private static final String LINKEDIN_AUTHORIZE_PATTERN = "{0}?client_id={1}&redirect_uri={2}&state={3}&response_type=code&scope=r_liteprofile%20r_emailaddress%20w_member_social";
    private static final String LINKEDIN_ACCESS_TOKEN_PATTERN = "{0}?client_id={1}&client_secret={2}&code={3}&redirect_uri={4}&grant_type=authorization_code";
    private static final String LINKEDIN_USER_INFO_PATTERN = "{0}?projection=(id,firstName,lastName,profilePicture(displayImage~:playableStreams))";
    private static final String LINKEDIN_REFRESH_TOKEN_PATTERN = "{0}?client_id={1}&client_secret={2}&refresh_token={3}&grant_type=refresh_token";

    private static final String MICROSOFT_AUTHORIZE_PATTERN = "{0}?client_id={1}&response_type=code&redirect_uri={2}&response_mode=query&scope=offline_access%20user.read%20mail.read&state={3}";
    private static final String MICROSOFT_ACCESS_TOKEN_PATTERN = "{0}?client_id={1}&client_secret={2}&scope=user.read%20mail.read&redirect_uri={3}&code={4}&grant_type=authorization_code";
    private static final String MICROSOFT_USER_INFO_PATTERN = "{0}";
    private static final String MICROSOFT_REFRESH_TOKEN_PATTERN = "{0}?client_id={1}&client_secret={2}&scope=user.read%20mail.read&redirect_uri={3}&refresh_token={4}&grant_type=refresh_token";

    private static final String MI_AUTHORIZE_PATTERN = "{0}?client_id={1}&redirect_uri={2}&response_type=code&scope=1%203%204%206&state={3}&skip_confirm=false";
    private static final String MI_ACCESS_TOKEN_PATTERN = "{0}?client_id={1}&client_secret={2}&redirect_uri={3}&code={4}&grant_type=authorization_code";
    private static final String MI_USER_INFO_PATTERN = "{0}?clientId={1}&token={2}";
    private static final String MI_REFRESH_TOKEN_PATTERN = "{0}?client_id={1}&client_secret={2}&redirect_uri={3}&refresh_token={4}&grant_type=refresh_token";

    private static final String TOUTIAO_ACCESS_TOKEN_PATTERN = "{0}?client_key={1}&client_secret={2}&code={3}&grant_type=authorize_code";
    private static final String TOUTIAO_USER_INFO_PATTERN = "{0}?client_key={1}&access_token={2}";
    private static final String TOUTIAO_AUTHORIZE_PATTERN = "{0}?client_key={1}&redirect_uri={2}&state={3}&response_type=code&auth_only=1&display=0";

    /**
     * 获取githubtoken的接口地址
     *
     * @param clientId     github 应用的Client ID
     * @param clientSecret github 应用的Client Secret
     * @param code         github 授权前的code，用来换token
     * @param redirectUri  待跳转的页面
     * @return full url
     */
    public static String getGithubAccessTokenUrl(String clientId, String clientSecret, String code, String redirectUri) {
        return MessageFormat.format(GITHUB_ACCESS_TOKEN_PATTERN, AuthSource.GITHUB.accessToken(), clientId, clientSecret, code, redirectUri);
    }

    /**
     * 获取github用户详情的接口地址
     *
     * @param token github 应用的token
     * @return full url
     */
    public static String getGithubUserInfoUrl(String token) {
        return MessageFormat.format(GITHUB_USER_INFO_PATTERN, AuthSource.GITHUB.userInfo(), token);
    }

    /**
     * 获取github授权地址
     *
     * @param clientId    github 应用的Client ID
     * @param redirectUrl github 应用授权成功后的回调地址
     * @return full url
     */
    public static String getGithubAuthorizeUrl(String clientId, String redirectUrl) {
        return MessageFormat.format(GITHUB_AUTHORIZE_PATTERN, AuthSource.GITHUB.authorize(), clientId, redirectUrl);
    }

    /**
     * 获取weibo token的接口地址
     *
     * @param clientId     weibo 应用的App Key
     * @param clientSecret weibo 应用的App Secret
     * @param code         weibo 授权前的code，用来换token
     * @param redirectUri  待跳转的页面
     * @return full url
     */
    public static String getWeiboAccessTokenUrl(String clientId, String clientSecret, String code, String redirectUri) {
        return MessageFormat.format(WEIBO_ACCESS_TOKEN_PATTERN, AuthSource.WEIBO.accessToken(), clientId, clientSecret, code, redirectUri);
    }

    /**
     * 获取weibo用户详情的接口地址
     *
     * @param token weibo 应用的token
     * @return full url
     */
    public static String getWeiboUserInfoUrl(String token) {
        return MessageFormat.format(WEIBO_USER_INFO_PATTERN, AuthSource.WEIBO.userInfo(), token);
    }

    /**
     * 获取weibo授权地址
     *
     * @param clientId    weibo 应用的Client ID
     * @param redirectUrl weibo 应用授权成功后的回调地址
     * @return full url
     */
    public static String getWeiboAuthorizeUrl(String clientId, String redirectUrl) {
        return MessageFormat.format(WEIBO_AUTHORIZE_PATTERN, AuthSource.WEIBO.authorize(), clientId, redirectUrl);
    }

    /**
     * 获取gitee token的接口地址
     *
     * @param clientId     gitee 应用的Client ID
     * @param clientSecret gitee 应用的Client Secret
     * @param code         gitee 授权前的code，用来换token
     * @param redirectUri  待跳转的页面
     * @return full url
     */
    public static String getGiteeAccessTokenUrl(String clientId, String clientSecret, String code, String redirectUri) {
        return MessageFormat.format(GITEE_ACCESS_TOKEN_PATTERN, AuthSource.GITEE.accessToken(), clientId, clientSecret, code, redirectUri);
    }

    /**
     * 获取gitee用户详情的接口地址
     *
     * @param token gitee 应用的token
     * @return full url
     */
    public static String getGiteeUserInfoUrl(String token) {
        return MessageFormat.format(GITEE_USER_INFO_PATTERN, AuthSource.GITEE.userInfo(), token);
    }

    /**
     * 获取gitee授权地址
     *
     * @param clientId    gitee 应用的Client ID
     * @param redirectUrl gitee 应用授权成功后的回调地址
     * @return json
     */
    public static String getGiteeAuthorizeUrl(String clientId, String redirectUrl) {
        return MessageFormat.format(GITEE_AUTHORIZE_PATTERN, AuthSource.GITEE.authorize(), clientId, redirectUrl);
    }

    /**
     * 获取钉钉登录二维码的地址
     *
     * @param clientId    钉钉 应用的App Id
     * @param redirectUrl 钉钉 应用授权成功后的回调地址
     * @return full url
     */
    public static String getDingTalkQrConnectUrl(String clientId, String redirectUrl) {
        return MessageFormat.format(DING_TALK_QRCONNECT_PATTERN, AuthSource.DINGTALK.authorize(), clientId, redirectUrl);
    }

    /**
     * 获取钉钉用户信息的地址
     *
     * @param signature 通过appSecret计算出来的签名值，签名计算方法：https://open-doc.dingtalk.com/microapp/faquestions/hxs5v9
     * @param timestamp 当前时间戳，单位是毫秒
     * @param accessKey 钉钉 应用的App Id
     * @return full url
     */
    public static String getDingTalkUserInfoUrl(String signature, String timestamp, String accessKey) {
        return MessageFormat.format(DING_TALK_USER_INFO_PATTERN, AuthSource.DINGTALK.userInfo(), signature, timestamp, accessKey);
    }

    /**
     * 获取baidu token的接口地址
     *
     * @param clientId     baidu 应用的API Key
     * @param clientSecret baidu 应用的Secret Key
     * @param code         baidu 授权前的code，用来换token
     * @param redirectUri  待跳转的页面
     * @return full url
     */
    public static String getBaiduAccessTokenUrl(String clientId, String clientSecret, String code, String redirectUri) {
        return MessageFormat.format(BAIDU_ACCESS_TOKEN_PATTERN, AuthSource.BAIDU.accessToken(), clientId, clientSecret, code, redirectUri);
    }

    /**
     * 获取baidu用户详情的接口地址
     *
     * @param token baidu 应用的token
     * @return full url
     */
    public static String getBaiduUserInfoUrl(String token) {
        return MessageFormat.format(BAIDU_USER_INFO_PATTERN, AuthSource.BAIDU.userInfo(), token);
    }

    /**
     * 获取baidu授权地址
     *
     * @param clientId    baidu 应用的API Key
     * @param redirectUrl baidu 应用授权成功后的回调地址
     * @return json
     */
    public static String getBaiduAuthorizeUrl(String clientId, String redirectUrl) {
        return MessageFormat.format(BAIDU_AUTHORIZE_PATTERN, AuthSource.BAIDU.authorize(), clientId, redirectUrl);
    }

    /**
     * 获取收回baidu授权的地址
     *
     * @param accessToken baidu 授权登录后的token
     * @return json
     */
    public static String getBaiduRevokeUrl(String accessToken) {
        return MessageFormat.format(BAIDU_REVOKE_PATTERN, AuthSource.BAIDU.revoke(), accessToken);
    }

    /**
     * 获取csdn token的接口地址
     *
     * @param clientId     csdn 应用的App Key
     * @param clientSecret csdn 应用的App Secret
     * @param code         csdn 授权前的code，用来换token
     * @param redirectUri  待跳转的页面
     * @return full url
     */
    public static String getCsdnAccessTokenUrl(String clientId, String clientSecret, String code, String redirectUri) {
        return MessageFormat.format(CSDN_ACCESS_TOKEN_PATTERN, AuthSource.CSDN.accessToken(), clientId, clientSecret, code, redirectUri);
    }

    /**
     * 获取csdn用户详情的接口地址
     *
     * @param token csdn 应用的token
     * @return full url
     */
    public static String getCsdnUserInfoUrl(String token) {
        return MessageFormat.format(CSDN_USER_INFO_PATTERN, AuthSource.CSDN.userInfo(), token);
    }

    /**
     * 获取csdn授权地址
     *
     * @param clientId    csdn 应用的Client ID
     * @param redirectUrl csdn 应用授权成功后的回调地址
     * @return full url
     */
    public static String getCsdnAuthorizeUrl(String clientId, String redirectUrl) {
        return MessageFormat.format(CSDN_AUTHORIZE_PATTERN, AuthSource.CSDN.authorize(), clientId, redirectUrl);
    }

    /**
     * 获取coding token的接口地址
     *
     * @param clientId     coding 应用的App Key
     * @param clientSecret coding 应用的App Secret
     * @param code         coding 授权前的code，用来换token
     * @return full url
     */
    public static String getCodingAccessTokenUrl(String clientId, String clientSecret, String code) {
        return MessageFormat.format(CODING_ACCESS_TOKEN_PATTERN, AuthSource.CODING.accessToken(), clientId, clientSecret, code);
    }

    /**
     * 获取coding用户详情的接口地址
     *
     * @param token coding 应用的token
     * @return full url
     */
    public static String getCodingUserInfoUrl(String token) {
        return MessageFormat.format(CODING_USER_INFO_PATTERN, AuthSource.CODING.userInfo(), token);
    }

    /**
     * 获取coding授权地址
     *
     * @param clientId    coding 应用的Client ID
     * @param redirectUrl coding 应用授权成功后的回调地址
     * @return full url
     */
    public static String getCodingAuthorizeUrl(String clientId, String redirectUrl) {
        return MessageFormat.format(CODING_AUTHORIZE_PATTERN, AuthSource.CODING.authorize(), clientId, redirectUrl);
    }

    /**
     * 获取腾讯云开发者平台 token的接口地址
     *
     * @param clientId     coding 应用的App Key
     * @param clientSecret coding 应用的App Secret
     * @param code         coding 授权前的code，用来换token
     * @return full url
     */
    public static String getTencentCloudAccessTokenUrl(String clientId, String clientSecret, String code) {
        return MessageFormat.format(TENCENT_ACCESS_TOKEN_PATTERN, AuthSource.TENCENT_CLOUD.accessToken(), clientId, clientSecret, code);
    }

    /**
     * 获取腾讯云开发者平台用户详情的接口地址
     *
     * @param token coding 应用的token
     * @return full url
     */
    public static String getTencentCloudUserInfoUrl(String token) {
        return MessageFormat.format(TENCENT_USER_INFO_PATTERN, AuthSource.TENCENT_CLOUD.userInfo(), token);
    }

    /**
     * 获取腾讯云开发者平台授权地址
     *
     * @param clientId    coding 应用的Client ID
     * @param redirectUrl coding 应用授权成功后的回调地址
     * @return full url
     */
    public static String getTencentCloudAuthorizeUrl(String clientId, String redirectUrl) {
        return MessageFormat.format(TENCENT_AUTHORIZE_PATTERN, AuthSource.TENCENT_CLOUD.authorize(), clientId, redirectUrl);
    }

    /**
     * 获取oschina token的接口地址
     *
     * @param clientId     oschina 应用的App Key
     * @param clientSecret oschina 应用的App Secret
     * @param code         oschina 授权前的code，用来换token
     * @param redirectUri  待跳转的页面
     * @return full url
     */
    public static String getOschinaAccessTokenUrl(String clientId, String clientSecret, String code, String redirectUri) {
        return MessageFormat.format(OSCHINA_ACCESS_TOKEN_PATTERN, AuthSource.OSCHINA.accessToken(), clientId, clientSecret, code, redirectUri);
    }

    /**
     * 获取oschina用户详情的接口地址
     *
     * @param token oschina 应用的token
     * @return full url
     */
    public static String getOschinaUserInfoUrl(String token) {
        return MessageFormat.format(OSCHINA_USER_INFO_PATTERN, AuthSource.OSCHINA.userInfo(), token);
    }

    /**
     * 获取oschina授权地址
     *
     * @param clientId    oschina 应用的Client ID
     * @param redirectUrl oschina 应用授权成功后的回调地址
     * @return full url
     */
    public static String getOschinaAuthorizeUrl(String clientId, String redirectUrl) {
        return MessageFormat.format(OSCHINA_AUTHORIZE_PATTERN, AuthSource.OSCHINA.authorize(), clientId, redirectUrl);
    }

    /**
     * 获取qq token的接口地址
     *
     * @param clientId     qq 应用的App Key
     * @param clientSecret qq 应用的App Secret
     * @param code         qq 授权前的code，用来换token
     * @param redirectUri  待跳转的页面
     * @return full url
     */
    public static String getQqAccessTokenUrl(String clientId, String clientSecret, String code, String redirectUri) {
        return MessageFormat.format(QQ_ACCESS_TOKEN_PATTERN, AuthSource.QQ.accessToken(), clientId, clientSecret, code, redirectUri);
    }

    /**
     * 获取qq用户详情的接口地址
     *
     * @param clientId qq 应用的clientId
     * @param token    qq 应用的token
     * @param openId   qq 应用的openId
     * @return full url
     */
    public static String getQqUserInfoUrl(String clientId, String token, String openId) {
        return MessageFormat.format(QQ_USER_INFO_PATTERN, AuthSource.QQ.userInfo(), clientId, token, openId);
    }

    /**
     * 获取qq授权地址
     *
     * @param clientId    qq 应用的Client ID
     * @param redirectUrl qq 应用授权成功后的回调地址
     * @return full url
     */
    public static String getQqAuthorizeUrl(String clientId, String redirectUrl) {
        return MessageFormat.format(QQ_AUTHORIZE_PATTERN, AuthSource.QQ.authorize(), clientId, redirectUrl, System.currentTimeMillis());
    }

    /**
     * 获取qq授权地址
     *
     * @param url   获取qqopenid的api接口地址
     * @param token qq 应用授权的token
     * @return full url
     */
    public static String getQqOpenidUrl(String url, String token) {
        return MessageFormat.format(QQ_OPENID_PATTERN, url, token);
    }

    /**
     * 获取alipay授权地址
     *
     * @param clientId    alipay 应用的Client ID
     * @param redirectUrl alipay 应用授权成功后的回调地址
     * @return full url
     */
    public static String getAlipayAuthorizeUrl(String clientId, String redirectUrl) {
        return MessageFormat.format(ALIPAY_AUTHORIZE_PATTERN, AuthSource.ALIPAY.authorize(), clientId, redirectUrl);
    }

    /**
     * 获取微信 授权地址
     *
     * @param clientId    微信 应用的appid
     * @param redirectUrl 微信 应用授权成功后的回调地址
     * @return full url
     */
    public static String getWeChatAuthorizeUrl(String clientId, String redirectUrl) {
        return MessageFormat.format(WECHAT_AUTHORIZE_PATTERN, AuthSource.WECHAT.authorize(), clientId, redirectUrl, System
                .currentTimeMillis());
    }

    /**
     * 获取微信 token的接口地址
     *
     * @param clientId     微信 应用的appid
     * @param clientSecret 微信 应用的secret
     * @param code         微信 授权前的code，用来换token
     * @return full url
     */
    public static String getWeChatAccessTokenUrl(String clientId, String clientSecret, String code) {
        return MessageFormat.format(WECHAT_ACCESS_TOKEN_PATTERN, AuthSource.WECHAT.accessToken(), clientId, clientSecret, code);
    }

    /**
     * 获取微信 用户详情的接口地址
     *
     * @param token  微信 应用返回的 access token
     * @param openId 微信 应用返回的openId
     * @return full url
     */
    public static String getWeChatUserInfoUrl(String token, String openId) {
        return MessageFormat.format(WECHAT_USER_INFO_PATTERN, AuthSource.WECHAT.userInfo(), token, openId);
    }

    /**
     * 获取微信 刷新令牌 地址
     *
     * @param clientId     微信 应用的appid
     * @param refreshToken 微信 应用返回的刷新token
     * @return full url
     */
    public static String getWeChatRefreshUrl(String clientId, String refreshToken) {
        return MessageFormat.format(WECHAT_REFRESH_TOKEN_PATTERN, AuthSource.WECHAT.refresh(), clientId, refreshToken);
    }

    /**
     * 获取Taobao token的接口地址: 淘宝的授权登录，在这一步就会返回用户信息
     *
     * @param clientId     taobao 应用的App Key
     * @param clientSecret taobao 应用的App Secret
     * @param code         taobao 授权前的code，用来换token
     * @param redirectUri  待跳转的页面
     * @return full url
     */
    public static String getTaobaoAccessTokenUrl(String clientId, String clientSecret, String code, String redirectUri) {
        return MessageFormat.format(TAOBAO_ACCESS_TOKEN_PATTERN, AuthSource.TAOBAO.accessToken(), clientId, clientSecret, code, redirectUri);
    }

    /**
     * 获取Taobao授权地址
     *
     * @param clientId    Taobao 应用的Client ID
     * @param redirectUrl Taobao 应用授权成功后的回调地址
     * @return full url
     */
    public static String getTaobaoAuthorizeUrl(String clientId, String redirectUrl) {
        return MessageFormat.format(TAOBAO_AUTHORIZE_PATTERN, AuthSource.TAOBAO.authorize(), clientId, redirectUrl, System
                .currentTimeMillis());
    }

    /**
     * 获取Google授权地址
     *
     * @param clientId    google 应用的Client ID
     * @param redirectUrl google 应用授权成功后的回调地址
     * @return full url
     */
    public static String getGoogleAuthorizeUrl(String clientId, String redirectUrl) {
        return MessageFormat.format(GOOGLE_AUTHORIZE_PATTERN, AuthSource.GOOGLE.authorize(), clientId, redirectUrl, System
                .currentTimeMillis());
    }

    /**
     * 获取Google token的接口地址
     *
     * @param clientId     google 应用的Client ID
     * @param clientSecret google 应用的Client Secret
     * @param code         google 授权前的code，用来换token
     * @param redirectUri  待跳转的页面
     * @return full url
     */
    public static String getGoogleAccessTokenUrl(String clientId, String clientSecret, String code, String redirectUri) {
        return MessageFormat.format(GOOGLE_ACCESS_TOKEN_PATTERN, AuthSource.GOOGLE.accessToken(), clientId, clientSecret, code, redirectUri);
    }

    /**
     * 获取Google用户详情的接口地址
     *
     * @param token google 应用的token
     * @return full url
     */
    public static String getGoogleUserInfoUrl(String token) {
        return MessageFormat.format(GOOGLE_USER_INFO_PATTERN, AuthSource.GOOGLE.userInfo(), token);
    }

    /**
     * 获取Facebook授权地址
     *
     * @param clientId    Facebook 应用的Client ID
     * @param redirectUrl Facebook 应用授权成功后的回调地址
     * @return full url
     */
    public static String getFacebookAuthorizeUrl(String clientId, String redirectUrl) {
        return MessageFormat.format(FACEBOOK_AUTHORIZE_PATTERN, AuthSource.FACEBOOK.authorize(), clientId, redirectUrl, System
                .currentTimeMillis());
    }

    /**
     * 获取Facebook token的接口地址
     *
     * @param clientId     Facebook 应用的Client ID
     * @param clientSecret Facebook 应用的Client Secret
     * @param code         Facebook 授权前的code，用来换token
     * @param redirectUri  待跳转的页面
     * @return full url
     */
    public static String getFacebookAccessTokenUrl(String clientId, String clientSecret, String code, String redirectUri) {
        return MessageFormat.format(FACEBOOK_ACCESS_TOKEN_PATTERN, AuthSource.FACEBOOK.accessToken(), clientId, clientSecret, code, redirectUri);
    }

    /**
     * 获取Facebook用户详情的接口地址
     *
     * @param token Facebook 应用的token
     * @return full url
     */
    public static String getFacebookUserInfoUrl(String token) {
        return MessageFormat.format(FACEBOOK_USER_INFO_PATTERN, AuthSource.FACEBOOK.userInfo(), token);
    }

    /**
     * 获取Douyin授权地址
     *
     * @param clientId    Douyin 应用的Client ID
     * @param redirectUrl Douyin 应用授权成功后的回调地址
     * @return full url
     */
    public static String getDouyinAuthorizeUrl(String clientId, String redirectUrl) {
        return MessageFormat.format(DOUYIN_AUTHORIZE_PATTERN, AuthSource.DOUYIN.authorize(), clientId, redirectUrl, System
                .currentTimeMillis());
    }

    /**
     * 获取Douyin token的接口地址
     *
     * @param clientId     Douyin 应用的Client ID
     * @param clientSecret Douyin 应用的Client Secret
     * @param code         Douyin 授权前的code，用来换token
     * @return full url
     */
    public static String getDouyinAccessTokenUrl(String clientId, String clientSecret, String code) {
        return MessageFormat.format(DOUYIN_ACCESS_TOKEN_PATTERN, AuthSource.DOUYIN.accessToken(), clientId, clientSecret, code);
    }

    /**
     * 获取Douyin用户详情的接口地址
     *
     * @param token  Douyin 应用的token
     * @param openId 用户在当前应用的唯一标识 通过token接口获取
     * @return full url
     */
    public static String getDouyinUserInfoUrl(String token, String openId) {
        return MessageFormat.format(DOUYIN_USER_INFO_PATTERN, AuthSource.DOUYIN.userInfo(), token, openId);
    }

    /**
     * 获取Douyin 刷新令牌 地址
     *
     * @param clientId     Douyin 应用的client_key
     * @param refreshToken Douyin 应用返回的refresh_token
     * @return full url
     */
    public static String getDouyinRefreshUrl(String clientId, String refreshToken) {
        return MessageFormat.format(DOUYIN_REFRESH_TOKEN_PATTERN, AuthSource.DOUYIN.refresh(), clientId, refreshToken);
    }

    /**
     * 获取Linkedin授权地址
     *
     * @param clientId    Linkedin 应用的Client ID
     * @param redirectUrl Linkedin 应用授权成功后的回调地址
     * @return full url
     */
    public static String getLinkedinAuthorizeUrl(String clientId, String redirectUrl) {
        return MessageFormat.format(LINKEDIN_AUTHORIZE_PATTERN, AuthSource.LINKEDIN.authorize(), clientId, redirectUrl, System
                .currentTimeMillis());
    }

    /**
     * 获取Linkedin token的接口地址
     *
     * @param clientId     Linkedin 应用的Client ID
     * @param clientSecret Linkedin 应用的Client Secret
     * @param code         Linkedin 授权前的code，用来换token
     * @param redirectUrl  Linkedin 应用授权成功后的回调地址
     * @return full url
     */
    public static String getLinkedinAccessTokenUrl(String clientId, String clientSecret, String code, String redirectUrl) {
        return MessageFormat.format(LINKEDIN_ACCESS_TOKEN_PATTERN, AuthSource.LINKEDIN.accessToken(), clientId, clientSecret, code, redirectUrl);
    }

    /**
     * 获取Linkedin用户详情的接口地址
     *
     * @return full url
     */
    public static String getLinkedinUserInfoUrl() {
        return MessageFormat.format(LINKEDIN_USER_INFO_PATTERN, AuthSource.LINKEDIN.userInfo());
    }

    /**
     * 获取Linkedin 刷新令牌 地址
     *
     * @param clientId     Linkedin 应用的client_key
     * @param clientSecret Linkedin 应用的Client Secret
     * @param refreshToken Linkedin 应用返回的refresh_token
     * @return full url
     */
    public static String getLinkedinRefreshUrl(String clientId, String clientSecret, String refreshToken) {
        return MessageFormat.format(LINKEDIN_REFRESH_TOKEN_PATTERN, AuthSource.LINKEDIN.refresh(), clientId, clientSecret, refreshToken);
    }

    /**
     * 获取微软授权地址
     *
     * @param clientId    微软 应用的Client ID
     * @param redirectUrl 微软 应用授权成功后的回调地址
     * @return full url
     */
    public static String getMicrosoftAuthorizeUrl(String clientId, String redirectUrl) {
        return MessageFormat.format(MICROSOFT_AUTHORIZE_PATTERN, AuthSource.MICROSOFT.authorize(), clientId, redirectUrl, System
                .currentTimeMillis());
    }

    /**
     * 获取微软 token的接口地址
     *
     * @param clientId     微软 应用的Client ID
     * @param clientSecret 微软 应用的Client Secret
     * @param redirectUrl  微软 应用授权成功后的回调地址
     * @param code         微软 授权前的code，用来换token
     * @return full url
     */
    public static String getMicrosoftAccessTokenUrl(String clientId, String clientSecret, String redirectUrl, String code) {
        return MessageFormat.format(MICROSOFT_ACCESS_TOKEN_PATTERN, AuthSource.MICROSOFT.accessToken(), clientId, clientSecret, redirectUrl, code);
    }

    /**
     * 获取微软用户详情的接口地址
     *
     * @return full url
     */
    public static String getMicrosoftUserInfoUrl() {
        return MessageFormat.format(MICROSOFT_USER_INFO_PATTERN, AuthSource.MICROSOFT.userInfo());
    }

    /**
     * 获取微软 刷新令牌 地址
     *
     * @param clientId     微软 应用的client_key
     * @param clientSecret 微软 应用的Client Secret
     * @param redirectUrl  微软 应用授权成功后的回调地址
     * @param refreshToken 微软 应用返回的refresh_token
     * @return full url
     */
    public static String getMicrosoftRefreshUrl(String clientId, String clientSecret, String redirectUrl, String refreshToken) {
        return MessageFormat.format(MICROSOFT_REFRESH_TOKEN_PATTERN, AuthSource.MICROSOFT.refresh(), clientId, clientSecret, redirectUrl, refreshToken);
    }

    /**
     * 获取小米授权地址
     *
     * @param clientId    小米 应用的Client ID
     * @param redirectUrl 小米 应用授权成功后的回调地址
     * @return full url
     */
    public static String getMiAuthorizeUrl(String clientId, String redirectUrl) {
        return MessageFormat.format(MI_AUTHORIZE_PATTERN, AuthSource.MI.authorize(), clientId, redirectUrl, System.currentTimeMillis());
    }

    /**
     * 获取小米 token的接口地址
     *
     * @param clientId     小米 应用的Client ID
     * @param clientSecret 小米 应用的Client Secret
     * @param redirectUrl  小米 应用授权成功后的回调地址
     * @param code         小米 授权前的code，用来换token
     * @return full url
     */
    public static String getMiAccessTokenUrl(String clientId, String clientSecret, String redirectUrl, String code) {
        return MessageFormat.format(MI_ACCESS_TOKEN_PATTERN, AuthSource.MI.accessToken(), clientId, clientSecret, redirectUrl, code);
    }

    /**
     * 获取小米用户详情的接口地址
     *
     * @param clientId 小米 应用的client_key
     * @param token    token
     * @return full url
     */
    public static String getMiUserInfoUrl(String clientId, String token) {
        return MessageFormat.format(MI_USER_INFO_PATTERN, AuthSource.MI.userInfo(), clientId, token);
    }

    /**
     * 获取小米 刷新令牌 地址
     *
     * @param clientId     小米 应用的client_key
     * @param clientSecret 小米 应用的Client Secret
     * @param redirectUrl  小米 应用授权成功后的回调地址
     * @param refreshToken 小米 应用返回的refresh_token
     * @return full url
     */
    public static String getMiRefreshUrl(String clientId, String clientSecret, String redirectUrl, String refreshToken) {
        return MessageFormat.format(MI_REFRESH_TOKEN_PATTERN, AuthSource.MI.refresh(), clientId, clientSecret, redirectUrl, refreshToken);
    }

    /**
     * 获取今日头条授权地址
     *
     * @param clientId    今日头条 应用的Client ID
     * @param redirectUrl 今日头条 应用授权成功后的回调地址
     * @return full url
     */
    public static String getToutiaoAuthorizeUrl(String clientId, String redirectUrl) {
        return MessageFormat.format(TOUTIAO_AUTHORIZE_PATTERN, AuthSource.TOUTIAO.authorize(), clientId, redirectUrl, System
                .currentTimeMillis());
    }

    /**
     * 获取今日头条 token的接口地址
     *
     * @param clientId     今日头条 应用的Client ID
     * @param clientSecret 今日头条 应用的Client Secret
     * @param code         今日头条 授权前的code，用来换token
     * @return full url
     */
    public static String getToutiaoAccessTokenUrl(String clientId, String clientSecret, String code) {
        return MessageFormat.format(TOUTIAO_ACCESS_TOKEN_PATTERN, AuthSource.TOUTIAO.accessToken(), clientId, clientSecret, code);
    }

    /**
     * 获取今日头条用户详情的接口地址
     *
     * @param clientId 今日头条 应用的client_key
     * @param token    token
     * @return full url
     */
    public static String getToutiaoUserInfoUrl(String clientId, String token) {
        return MessageFormat.format(TOUTIAO_USER_INFO_PATTERN, AuthSource.TOUTIAO.userInfo(), clientId, token);
    }
}
