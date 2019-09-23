package com.aspire.commons.csv.support.convert.read.type.impl;

import com.aspire.commons.csv.support.convert.read.type.ITypeConverter;
import com.github.houbb.heaven.annotation.ThreadSafe;

/**
 * Short 读取转换器
 * @author binbin.hou
 * @since 0.0.1
 */
@ThreadSafe
public class ShortReadConverter implements ITypeConverter<Short> {

    @Override
    public Short convert(String value, final Class type) {
        return Short.valueOf(value);
    }

}
