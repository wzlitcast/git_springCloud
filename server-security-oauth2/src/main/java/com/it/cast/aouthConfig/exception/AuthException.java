package com.it.cast.aouthConfig.exception;

import com.it.cast.aouthConfig.request.ResponseStatus;


/**
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0
 * @since 1.8
 */
public class AuthException extends RuntimeException {

    private int errorCode;
    private String errorMsg;

    public AuthException(String errorMsg) {
        this(ResponseStatus.FAILURE.getCode(), errorMsg);
    }

    public AuthException(int errorCode, String errorMsg) {
        super(errorCode + ":" + errorMsg);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public AuthException(ResponseStatus status) {
        super(status.getMsg());
    }

    public AuthException(String message, Throwable cause) {
        super(message, cause);
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
