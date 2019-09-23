package com.aspire.commons.custom_head_annotation;

import java.lang.annotation.*;

/**
 * 获取请求头中的手机号码
 * 用于数据脱敏
 * 例：13800006789  脱敏后：138****6789
 * @author gaoqi 2019/04/23
 */
@Target({ElementType.METHOD, ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestPhoneMasking {

    int startIndex() default 3;
    int endIndex() default 7;
}
