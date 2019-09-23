package com.aspire.commons.csv.bs;

import com.aspire.commons.csv.api.ICsv;
import com.aspire.commons.csv.support.context.DefaultReadContext;
import com.aspire.commons.csv.support.csv.DefaultCsv;
import com.github.houbb.heaven.constant.CharsetConst;
import com.github.houbb.heaven.support.sort.ISort;
import com.github.houbb.heaven.support.sort.impl.NoSort;

import java.util.List;

/**
 * csv 读取引导类
 * @author binbin.hou
 * @since 0.0.1
 */
public class CsvReadBs {

    /**
     * 指定文件编码
     */
    private String charset = CharsetConst.UTF8;

    /**
     * 开始下标
     * 1. 跳过第一行的 head
     */
    private int startIndex = 1;

    /**
     * 结束下标
     */
    private int endIndex = Integer.MAX_VALUE;

    /**
     * 指定排序方式
     */
    private ISort sort = new NoSort();

    /**
     * 文件路径
     */
    private String path;

    /**
     * 执行转意
     * @since 0.0.6
     */
    private boolean escape = false;

    /**
     * 私有化构造器
     */
    private CsvReadBs(){}

    public static CsvReadBs newInstance(final String path) {
        CsvReadBs csvBs = new CsvReadBs();
        csvBs.path(path);
        return csvBs;
    }

    public CsvReadBs charset(String charset) {
        this.charset = charset;
        return this;
    }

    public CsvReadBs sort(ISort sort) {
        this.sort = sort;
        return this;
    }

    public CsvReadBs path(String path) {
        this.path = path;
        return this;
    }

    public CsvReadBs startIndex(int startIndex) {
        this.startIndex = startIndex;
        return this;
    }

    public CsvReadBs endIndex(int endIndex) {
        this.endIndex = endIndex;
        return this;
    }

    public CsvReadBs escape(boolean escape) {
        this.escape = escape;
        return this;
    }

    /**
     * 将指定文件的内容读取到列表中
     * @param tClass 类型
     * @param <T> 泛型
     * @return 列表
     */
    public <T> List<T> read(Class<T> tClass) {
        DefaultReadContext<T> context = new DefaultReadContext<>();
        context.charset(charset)
                .path(path)
                .startIndex(startIndex)
                .endIndex(endIndex)
                .sort(sort)
                .readClass(tClass)
                .escape(escape)
                ;

        final ICsv<T> csv = new DefaultCsv<>();
        return csv.read(context);
    }

}
