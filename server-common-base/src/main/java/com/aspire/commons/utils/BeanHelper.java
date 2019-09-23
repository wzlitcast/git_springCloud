package com.aspire.commons.utils;

import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * @author cw
 * @create 2019-01-07 20:31
 */
public class BeanHelper {
    /**
     * 将Map中的key由下划线转换为驼峰
     *
     * @param map
     * @return
     */
    public static Map<String, Object> formatColName(Map<String, Object> map) {
        if (null == map || 0 == map.size()) return null;
        Map<String, Object> newMap = new HashMap<String, Object>();
        Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> entry = it.next();
            String key = entry.getKey();
            String newKey = ((StringUtils.isEmpty(key)) ? key : toFormatCol(key));
            newMap.put(newKey, entry.getValue());
        }
        return newMap;
    }

    /**
     * 下划线转换为驼峰
     *
     * @param colName
     * @return
     */
    public static String toFormatCol(String colName) {
        StringBuilder sb = new StringBuilder();
        String[] str = colName.toLowerCase().split("_");
        int i = 0;
        for (String s : str) {
            if (1 == s.length()) {
                s = s.toUpperCase();
            }
            i++;
            if (1 == i) {
                sb.append(s);
                continue;
            }
            if (s.length() > 0) {
                sb.append(s.substring(0, 1).toUpperCase());
                sb.append(s.substring(1));
            }
        }
        return sb.toString();
    }

    /**
     * 将List中map的key值命名方式格式化为驼峰
     *
     * @param
     * @return
     */
    public static List<Map<String, Object>> formatColNameForList(List<Map<String, Object>> list) {
        List<Map<String, Object>> newList = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> o : list) {
            newList.add(formatColName(o));
        }
        return newList;
    }

    /**
     * 实体对象转成Map(所有非null非final非static非空字段)
     *
     * @param entity 实体对象
     * @return
     */
    public static Map<String, Object> entity2Map(Object entity) throws IllegalAccessException {
        Map<String, Object> map = new HashMap<>();
        if (null == entity) {
            return map;
        }
        Class<? extends Object> clazz = entity.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            int mod = field.getModifiers();
            if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                continue;
            }
            field.setAccessible(true);
            if (StringUtils.isEmpty(field.get(entity))) {
                continue;
            }
            map.put(field.getName(), field.get(entity));
        }
        return map;
    }

    /**
     * 实体对象转成Map(所有非final非static字段)
     *
     * @param entity 实体对象
     * @return
     */
    public static Map<String, Object> entity2MapAllField(Object entity) throws IllegalAccessException {
        Map<String, Object> map = new HashMap<>();
        if (null == entity) {
            return map;
        }
        Class<? extends Object> clazz = entity.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            int mod = field.getModifiers();
            if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                continue;
            }
            field.setAccessible(true);
            map.put(field.getName(), field.get(entity));
        }
        return map;
    }

    /**
     * Map转成实体对象
     *
     * @param map   map实体对象包含属性
     * @param clazz 实体对象类型
     * @return
     */
    public static Object map2Entity(Map<String, Object> map, Class<?> clazz) throws IllegalAccessException, InstantiationException {
        if (null == map) {
            return null;
        }
        Object obj = null;
        obj = clazz.newInstance();

        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            int mod = field.getModifiers();
            if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                continue;
            }
            field.setAccessible(true);
            field.set(obj, map.get(field.getName()));
        }
        return obj;
    }
}
