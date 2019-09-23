package com.aspire.commons;

import com.aspire.commons.response_json.CommonJsonNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

/**
 * 接口统一返回消息类
 *
 * @param <T>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Slf4j
public class AjaxResult<T> {
    private String code;
    private String msg;
    private T data;

    public AjaxResult<T> fail(String code, String msg) {
        return new AjaxResult<T>(code, msg, data);
    }

    public AjaxResult<T> fail(String msg, T data) {
        return new AjaxResult<T>("-1", msg, data);
    }

    public AjaxResult<T> fail(String code, String msg, T data) {
        return new AjaxResult<T>(code, msg, data);
    }

    public AjaxResult<T> fail(ConstantsCode code) {
        return new AjaxResult<T>(code.getCode(), code.getMsg(), (T) CommonJsonNode.results(new HashMap<>(1)));
    }

    public AjaxResult<T> fail(ConstantsCode code, T data) {
        return new AjaxResult<T>(code.getCode(), code.getMsg(), data);
    }

    public AjaxResult<T> success() {
        return new AjaxResult<T>(ConstantsCodeCommon.SUCCESS_CODE.getCode(), ConstantsCodeCommon.SUCCESS_CODE.getMsg(), (T) CommonJsonNode.results(new HashMap<>(1)));
    }

    public AjaxResult<T> success(String code, String msg) {
        return new AjaxResult<T>(code, msg, data);
    }

    public AjaxResult<T> success(T data) {
        return new AjaxResult<T>(ConstantsCodeCommon.SUCCESS_CODE.getCode(), ConstantsCodeCommon.SUCCESS_CODE.getMsg(), data);
    }

    public AjaxResult<T> success(String msg) {
        return new AjaxResult<T>("0", msg, null);
    }

    public AjaxResult<T> success(String code, String msg, T data) {
        return new AjaxResult<T>(ConstantsCodeCommon.SUCCESS_CODE.getCode(), ConstantsCodeCommon.SUCCESS_CODE.getMsg(), data);
    }

}


