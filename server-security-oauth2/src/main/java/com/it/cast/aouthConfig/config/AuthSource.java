package com.it.cast.aouthConfig.config;

import com.it.cast.aouthConfig.exception.AuthException;
import com.it.cast.aouthConfig.request.ResponseStatus;


/**
 * 各api需要的url， 用枚举类分平台类型管理
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0
 * @since 1.0
 */
public enum AuthSource {
    /**
     * Github
     */
    GITHUB {
        @Override
        public String authorize() {
            return "https://github.com/login/oauth/authorize";
        }

        @Override
        public String accessToken() {
            return "https://github.com/login/oauth/access_token";
        }

        @Override
        public String userInfo() {
            return "https://api.github.com/user";
        }
    },
    /**
     * 新浪微博
     */
    WEIBO {
        @Override
        public String authorize() {
            return "https://api.weibo.com/oauth2/authorize";
        }

        @Override
        public String accessToken() {
            return "https://api.weibo.com/oauth2/access_token";
        }

        @Override
        public String userInfo() {
            return "https://api.weibo.com/2/users/show.json";
        }
    },
    /**
     * gitee
     */
    GITEE {
        @Override
        public String authorize() {
            return "https://gitee.com/oauth/authorize";
        }

        @Override
        public String accessToken() {
            return "https://gitee.com/oauth/token";
        }

        @Override
        public String userInfo() {
            return "https://gitee.com/api/v5/user";
        }
    },
    /**
     * 钉钉
     */
    DINGTALK {
        @Override
        public String authorize() {
            return "https://oapi.dingtalk.com/connect/qrconnect";
        }

        @Override
        public String accessToken() {
            throw new AuthException(ResponseStatus.UNSUPPORTED);
        }

        @Override
        public String userInfo() {
            return "https://oapi.dingtalk.com/sns/getuserinfo_bycode";
        }
    },
    /**
     * 百度
     */
    BAIDU {
        @Override
        public String authorize() {
            return "https://openapi.baidu.com/oauth/2.0/authorize";
        }

        @Override
        public String accessToken() {
            return "https://openapi.baidu.com/oauth/2.0/token";
        }

        @Override
        public String userInfo() {
            return "https://openapi.baidu.com/rest/2.0/passport/users/getInfo";
        }

        @Override
        public String revoke() {
            return "https://openapi.baidu.com/rest/2.0/passport/auth/revokeAuthorization";
        }
    },
    /**
     * csdn
     */
    CSDN {
        @Override
        public String authorize() {
            return "https://api.csdn.net/oauth2/authorize";
        }

        @Override
        public String accessToken() {
            return "https://api.csdn.net/oauth2/access_token";
        }

        @Override
        public String userInfo() {
            return "https://api.csdn.net/user/getinfo";
        }
    },
    /**
     * Coding
     */
    CODING {
        @Override
        public String authorize() {
            return "https://coding.net/oauth_authorize.html";
        }

        @Override
        public String accessToken() {
            return "https://coding.net/api/oauth/access_token";
        }

        @Override
        public String userInfo() {
            return "https://coding.net/api/account/current_user";
        }
    },
    /**
     * 腾讯云开发者平台（coding升级后就变成腾讯云开发者平台了）
     */
    TENCENT_CLOUD {
        @Override
        public String authorize() {
            return "https://dev.tencent.com/oauth_authorize.html";
        }

        @Override
        public String accessToken() {
            return "https://dev.tencent.com/api/oauth/access_token";
        }

        @Override
        public String userInfo() {
            return "https://dev.tencent.com/api/account/current_user";
        }
    },
    /**
     * oschina 开源中国
     */
    OSCHINA {
        @Override
        public String authorize() {
            return "https://www.oschina.net/action/oauth2/authorize";
        }

        @Override
        public String accessToken() {
            return "https://www.oschina.net/action/openapi/token";
        }

        @Override
        public String userInfo() {
            return "https://www.oschina.net/action/openapi/user";
        }
    },
    /**
     * 支付宝
     */
    ALIPAY {
        @Override
        public String authorize() {
            return "https://openauth.alipay.com/oauth2/publicAppAuthorize.htm";
        }

        @Override
        public String accessToken() {
            return "https://openapi.alipay.com/gateway.do";
        }

        @Override
        public String userInfo() {
            return "https://openapi.alipay.com/gateway.do";
        }
    },
    /**
     * QQ
     */
    QQ {
        @Override
        public String authorize() {
            return "https://graph.qq.com/oauth2.0/authorize";
        }

        @Override
        public String accessToken() {
            return "https://graph.qq.com/oauth2.0/token";
        }

        @Override
        public String userInfo() {
            return "https://graph.qq.com/user/get_user_info";
        }
    },
    /**
     * 微信
     */
    WECHAT {
        @Override
        public String authorize() {
            return "https://open.weixin.qq.com/connect/qrconnect";
        }

        @Override
        public String accessToken() {
            return "https://api.weixin.qq.com/sns/oauth2/access_token";
        }

        @Override
        public String userInfo() {
            return "https://api.weixin.qq.com/sns/userinfo";
        }

        @Override
        public String refresh() {
            return "https://api.weixin.qq.com/sns/oauth2/refresh_token";
        }
    },
    /**
     * 淘宝
     */
    TAOBAO {
        @Override
        public String authorize() {
            return "https://oauth.taobao.com/authorize";
        }

        @Override
        public String accessToken() {
            return "https://oauth.taobao.com/token";
        }

        @Override
        public String userInfo() {
            throw new AuthException(ResponseStatus.UNSUPPORTED);
        }
    },
    /**
     * Google
     */
    GOOGLE {
        @Override
        public String authorize() {
            return "https://accounts.google.com/o/oauth2/v2/auth";
        }

        @Override
        public String accessToken() {
            return "https://www.googleapis.com/oauth2/v4/token";
        }

        @Override
        public String userInfo() {
            return "https://oauth2.googleapis.com/tokeninfo";
        }
    },
    /**
     * Facebook
     */
    FACEBOOK {
        @Override
        public String authorize() {
            return "https://www.facebook.com/v3.3/dialog/oauth";
        }

        @Override
        public String accessToken() {
            return "https://graph.facebook.com/v3.3/oauth/access_token";
        }

        @Override
        public String userInfo() {
            return "https://graph.facebook.com/v3.3/me";
        }
    },
    /**
     * 抖音
     */
    DOUYIN {
        @Override
        public String authorize() {
            return "https://open.douyin.com/platform/oauth/connect";
        }

        @Override
        public String accessToken() {
            return "https://open.douyin.com/oauth/access_token/";
        }

        @Override
        public String userInfo() {
            return "https://open.douyin.com/oauth/userinfo/";
        }

        @Override
        public String refresh() {
            return "https://open.douyin.com/oauth/refresh_token/";
        }
    },
    /**
     * 领英
     */
    LINKEDIN {
        @Override
        public String authorize() {
            return "https://www.linkedin.com/oauth/v2/authorization";
        }

        @Override
        public String accessToken() {
            return "https://www.linkedin.com/oauth/v2/accessToken";
        }

        @Override
        public String userInfo() {
            return "https://api.linkedin.com/v2/me";
        }

        @Override
        public String refresh() {
            return "https://www.linkedin.com/oauth/v2/accessToken";
        }
    },
    /**
     * 微软
     */
    MICROSOFT {
        @Override
        public String authorize() {
            return "https://login.microsoftonline.com/common/oauth2/v2.0/authorize";
        }

        @Override
        public String accessToken() {
            return "https://login.microsoftonline.com/common/oauth2/v2.0/token";
        }

        @Override
        public String userInfo() {
            return "https://graph.microsoft.com/v1.0/me";
        }

        @Override
        public String refresh() {
            return "https://login.microsoftonline.com/common/oauth2/v2.0/token";
        }
    },
    /**
     * 小米
     */
    MI {
        @Override
        public String authorize() {
            return "https://account.xiaomi.com/oauth2/authorize";
        }

        @Override
        public String accessToken() {
            return "https://account.xiaomi.com/oauth2/token";
        }

        @Override
        public String userInfo() {
            return "https://open.account.xiaomi.com/user/profile";
        }

        @Override
        public String refresh() {
            return "https://account.xiaomi.com/oauth2/token";
        }
    },
    /**
     * 今日头条
     */
    TOUTIAO {
        @Override
        public String authorize() {
            return "https://open.snssdk.com/auth/authorize";
        }

        @Override
        public String accessToken() {
            return "https://open.snssdk.com/auth/token";
        }

        @Override
        public String userInfo() {
            return "https://open.snssdk.com/data/user_profile";
        }
    };

    /**
     * 授权的api
     *
     * @return url
     */
    public abstract String authorize();

    /**
     * 获取accessToken的api
     *
     * @return url
     */
    public abstract String accessToken();

    /**
     * 获取用户信息的api
     *
     * @return url
     */
    public abstract String userInfo();

    /**
     * 取消授权的api
     *
     * @return url
     */
    public String revoke() {
        throw new AuthException(ResponseStatus.UNSUPPORTED);
    }

    /**
     * 刷新授权的api
     *
     * @return url
     */
    public String refresh() {
        throw new AuthException(ResponseStatus.UNSUPPORTED);
    }

}