package com.aspire.commons.csv.bs;


import com.aspire.commons.csv.api.ICsv;
import com.aspire.commons.csv.support.context.DefaultWriteContext;
import com.aspire.commons.csv.support.csv.DefaultCsv;
import com.github.houbb.heaven.constant.CharsetConst;
import com.github.houbb.heaven.support.sort.ISort;
import com.github.houbb.heaven.support.sort.impl.NoSort;

import java.util.List;

/**
 * csv 写入引导类
 * @author binbin.hou
 * @since 0.0.1
 */
public class CsvWriteBs {
    /**
     * 是否写入 head 头信息
     */
    private boolean writeHead = true;

    /**
     * 是否写入 bom
     */
    private boolean writeBom = true;

    /**
     * 指定文件编码
     */
    private String charset = CharsetConst.UTF8;

    /**
     * 指定排序方式
     */
    private ISort sort = new NoSort();

    /**
     * 文件路径
     */
    private String path;

    /**
     * 特殊字符转换
     * @see com.github.houbb.csv.constant.CsvEscapeConst 特殊信息
     */
    private boolean escape = false;

    /**
     * 私有化构造器
     */
    private CsvWriteBs(){}

    public static CsvWriteBs newInstance(final String path) {
        CsvWriteBs csvBs = new CsvWriteBs();
        csvBs.path(path);
        return csvBs;
    }

    public CsvWriteBs writeHead(boolean writeHead) {
        this.writeHead = writeHead;
        return this;
    }

    public CsvWriteBs writeBom(boolean writeBom) {
        this.writeBom = writeBom;
        return this;
    }

    public CsvWriteBs charset(String charset) {
        this.charset = charset;
        return this;
    }

    public CsvWriteBs sort(ISort sort) {
        this.sort = sort;
        return this;
    }

    public CsvWriteBs path(String path) {
        this.path = path;
        return this;
    }

    public CsvWriteBs escape(boolean escape) {
        this.escape = escape;
        return this;
    }

    /**
     * 将指定列表的内容写入到文件中
     * @param list 列表
     * @param <T> 泛型
     */
    public <T> void write(List<T> list) {
        DefaultWriteContext<T> context = new DefaultWriteContext<>();
        context.charset(charset)
                .list(list)
                .writeHead(writeHead)
                .writeBom(writeBom)
                .path(path)
                .sort(sort)
                .escape(escape)
                ;

        ICsv<T> csv = new DefaultCsv<>();
        csv.write(context);
    }

}
