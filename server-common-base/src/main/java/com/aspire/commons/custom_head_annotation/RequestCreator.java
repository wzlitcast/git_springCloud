package com.aspire.commons.custom_head_annotation;

import java.lang.annotation.*;
/**
 * 获取请求头中的创建人
 * @author gaoqi 2019/04/23
 */
@Target({ElementType.METHOD, ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestCreator {
    String value() default "";
}
