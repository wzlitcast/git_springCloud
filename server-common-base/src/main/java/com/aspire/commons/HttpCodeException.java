package com.aspire.commons;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

/**
 * 原子层自定义httpcode异常类
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class HttpCodeException extends RuntimeException {
    private final HttpStatus status;


    public HttpCodeException(HttpStatus status, String msg){
        super(msg);
        this.status = status;
    }

}
