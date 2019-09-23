package com.aspire.commons.csv.support.convert.read.type;

/**
 * 根据类型进行转换
 * @author binbin.hou
 * @since 0.0.3
 * @param <T> 泛型
 */
public interface ITypeConverter<T> {

    /**
     * 执行转换
     * @param value 字符串值
     * @param type 字段类型
     * @return 结果
     */
    T convert(final String value, final Class type);

}
