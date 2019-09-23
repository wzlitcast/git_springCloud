package com.aspire.commons.csv.annotation;

import com.aspire.commons.csv.api.IReadConverter;
import com.aspire.commons.csv.api.IWriteConverter;
import com.aspire.commons.csv.support.convert.read.CommonReadConverter;
import com.aspire.commons.csv.support.convert.write.CommonWriteConverter;

import java.lang.annotation.*;

/**
 * CSV 注解
 * @author binbin.hou
 * @since 0.0.1
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Target(ElementType.FIELD)
public @interface Csv {

    /**
     * 字段显示名称
     * 1. 默认使用 field.name
     * @return 显示名称
     */
    String label() default "";

    /**
     * 字段格式
     * 1. 默认使用 field.name
     * @return 显示名称
     */
    boolean textStyle()  default false;

    /**
     * 读取是否需要
     * @return 是
     */
    boolean readRequire() default true;

    /**
     * 写入是否需要
     * @return 是
     */
    boolean writeRequire() default true;

    /**
     * 读取转换
     * @return 处理实现类
     */
    Class<? extends IReadConverter> readConverter() default CommonReadConverter.class;

    /**
     * 写入转换
     * @return 处理实现类
     */
    Class<? extends IWriteConverter> writeConverter() default CommonWriteConverter.class;

}
