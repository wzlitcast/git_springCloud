package com.aspire.commons.csv.util;

import com.aspire.commons.csv.constant.CsvConst;
import com.aspire.commons.csv.constant.CsvEscapeConst;
import com.github.houbb.heaven.constant.PunctuationConst;
import com.github.houbb.heaven.util.lang.CharUtil;
import com.github.houbb.heaven.util.lang.StringUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 内部工具类
 * @author binbin.hou
 * @since 0.0.5
 */
public final class CsvInnerUtil {

    private CsvInnerUtil(){}

    /**
     * 特殊符号与转移字符的映射关系
     * @since 0.1.6
     */
    private static final Map<String, String> SPECIAL_ESCAPE_MAP = new HashMap<>();

    /**
     * 特殊符号与转移字符的映射关系
     * @since 0.1.6
     */
    private static final Map<String, String> ESCAPE_SPECIAL_MAP = new HashMap<>();

    static {
        SPECIAL_ESCAPE_MAP.put(PunctuationConst.COMMA, CsvEscapeConst.COMMA);
        SPECIAL_ESCAPE_MAP.put(CsvConst.SPLIT_OR, CsvEscapeConst.OR);
        SPECIAL_ESCAPE_MAP.put(PunctuationConst.COLON, CsvEscapeConst.COLON);
        SPECIAL_ESCAPE_MAP.put(PunctuationConst.EQUAL, CsvEscapeConst.EQUAL);

        ESCAPE_SPECIAL_MAP.put(CsvEscapeConst.COMMA, PunctuationConst.COMMA);
        ESCAPE_SPECIAL_MAP.put(CsvEscapeConst.OR, CsvConst.SPLIT_OR);
        ESCAPE_SPECIAL_MAP.put(CsvEscapeConst.COLON, PunctuationConst.COLON);
        ESCAPE_SPECIAL_MAP.put(CsvEscapeConst.EQUAL, PunctuationConst.EQUAL);
    }

    /**
     * 替换所有特殊字符
     * @param special 特殊字符
     * @return 替换后的结果
     * @since 0.0.6
     */
    public static String replaceAllSpecial(final String special) {
        if (StringUtil.isEmpty(special)) {
            return special;
        }
        String result = special;
        for (Map.Entry<String, String> entry : SPECIAL_ESCAPE_MAP.entrySet()) {
            final String key = entry.getKey();
            final String value = entry.getValue();
            result = result.replaceAll(key, value);
        }
        return result;
    }

    /**
     * 替换所有转义字符
     * @param escape 转义字符
     * @return 替换后的结果
     * @since 0.0.6
     */
    public static String replaceAllEscape(final String escape) {
        if (StringUtil.isEmpty(escape)) {
            return escape;
        }
        String result = escape;
        for (Map.Entry<String, String> entry : ESCAPE_SPECIAL_MAP.entrySet()) {
            final String key = entry.getKey();
            final String value = entry.getValue();
            result = result.replaceAll(key, value);
        }
        return result;
    }

    /**
     * 获取下一个分隔符号
     * @param preSplit 原来的分隔符号
     * @return 下一个分隔符
     */
    public static String getNextSplit(final String preSplit) {
        if(CsvConst.COMMA.equals(preSplit)) {
            return CsvConst.ENTRY_SPLIT_UNIT;
        }
        if(preSplit.startsWith(CsvConst.ENTRY_SPLIT_UNIT)) {
            final int times = preSplit.length()+1;
            return CharUtil.repeat(CsvConst.ENTRY_SPLIT_UNIT_CHAR, times);
        }
        throw new UnsupportedOperationException("暂时不支持的分隔符!");
    }

}
