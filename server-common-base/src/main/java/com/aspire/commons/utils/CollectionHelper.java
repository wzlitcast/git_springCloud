package com.aspire.commons.utils;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 集合工具类
 *
 * @author cw
 * @create 2019-01-09 17:43
 */
public class CollectionHelper {

    /**
     * 去重，不改变原有的顺序
     *
     * @param list
     */
    public static List<String> distinctInOrder(List<String> list) {
        List<String> newList = new ArrayList<>();
        Set set = new HashSet();
        for (String str : list) {
            if (set.add(str)) {
                newList.add(str);
            }
        }
        return newList;
    }


    /**
     * 去重，按自然顺序排序
     *
     * @param list
     */
    public static List<String> distinctInNaturalOrder(List<String> list) {
        return new ArrayList<>(new TreeSet<String>(list));
    }

    public static String convertToSqlInStr(List<String> strlist) {
        StringBuilder sb = new StringBuilder();
        if (CollectionUtils.isNotEmpty(strlist)) {
            strlist.forEach(i -> {
                sb.append(",").append("'").append(i).append("'");
            });
            sb.delete(0, 1);
        }
        return sb.toString();
    }

    public static String convertToSqlInNumStr(List<String> strlist) {
        return strlist.stream().collect(Collectors.joining(","));
    }
}

