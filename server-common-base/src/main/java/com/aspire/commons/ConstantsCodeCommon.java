package com.aspire.commons;


/**
 * 自定义状态码返回类
 */
public enum ConstantsCodeCommon implements ConstantsCode {

    SUCCESS_CODE("success", "0"),
    PARAMETER_IS_EMPTY("请求参数为空", "4000"),
    DATA_IS_EMPTY("数据为空", "41000"),
    PARAMETER_IS_INVALID_ERROR("请求参数类型异常", "42000"),
    DATABASE_EXCEPTION("内部服务器错误", "50000"),
    METHOD_NOT_ALLOWED("请求方法异常", "51000"),
    JSON_PARSING_EXCEPTION("JSON序列化异常", "52000"),
    SERVER_EXCEPTION("系统维护","53000"),
    SERVICE_TIME_OUT("服务层超时","54000"),
    REQUEST_TYPES_EXCEPTION("请求类型错误","415"),
    REQUEST_PARAMETER_EXCEPTION("请求请求参数错误","416"),
    REQUEST_PARAMETER_FORMAT_EXCEPTION("请求格式错误","412"),
    //以下是封装层业务返回码
    VERIFICATION_FAILED("verification failed","0009"),

    FAILED_SAVE_PRICE("Failed save price","10001");
    ConstantsCodeCommon(String msg, String code) {
        this.msg = msg;
        this.code = code;
    }

    private String msg;

    public String getMsg() {
        return this.msg;
    }

    public String getCode() {
        return this.code;
    }

    private String code;

}
