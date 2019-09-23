package com.it.cast.aouthConfig.model;

import com.it.cast.aouthConfig.request.ResponseStatus;
import lombok.Builder;
import lombok.Data;


/**
 * JustAuth统一授权响应类
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0
 * @since 1.8
 */
@Builder
@Data
public class AuthResponse<T> {
    /**
     * 授权响应状态码
     */
    private int code;

    /**
     * 授权响应信息
     */
    private String msg;

    /**
     * 授权响应数据，当且仅当 code = 2000 时返回
     */
    private T data;

    /**
     * 是否请求成功
     *
     * @return true or false
     */
    public boolean ok() {
        return this.code == ResponseStatus.SUCCESS.getCode();
    }
}
