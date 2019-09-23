package com.aspire.commons.csv.api;

import java.util.List;

/**
 * CSV 读写接口
 * @author binbin.hou
 * @since 0.0.1
 * @param <T> 泛型
 */
public interface ICsv<T> {

    /**
     * 写入
     * @param context 上下文
     */
    void write(IWriteContext<T> context);

    /**
     * 读取
     * @param context 上下文
     * @return 列表
     */
    List<T> read(IReadContext<T> context);

}
