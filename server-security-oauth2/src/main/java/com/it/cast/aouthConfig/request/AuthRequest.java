package com.it.cast.aouthConfig.request;

import com.it.cast.aouthConfig.exception.AuthException;
import com.it.cast.aouthConfig.model.AuthResponse;
import com.it.cast.aouthConfig.model.AuthToken;

/**
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0
 * @since 1.8
 */
public interface AuthRequest {

    /**
     * 返回认证url，可自行跳转页面
     *
     * @return 返回授权地址
     */
    default String authorize() {
        throw new AuthException(ResponseStatus.NOT_IMPLEMENTED);
    }

    /**
     * 第三方登录
     *
     * @param code 通过authorize换回的code
     * @return 返回登录成功后的用户信息
     */
    default AuthResponse login(String code) {
        throw new AuthException(ResponseStatus.NOT_IMPLEMENTED);
    }

    /**
     * 撤销授权
     *
     * @param authToken 登录成功后返回的Token信息
     * @return AuthResponse
     */
    default AuthResponse revoke(AuthToken authToken) {
        throw new AuthException(ResponseStatus.NOT_IMPLEMENTED);
    }

    /**
     * 刷新access token （续期）
     *
     * @param authToken 登录成功后返回的Token信息
     * @return AuthResponse
     */
    default AuthResponse refresh(AuthToken authToken) {
        throw new AuthException(ResponseStatus.NOT_IMPLEMENTED);
    }
}
