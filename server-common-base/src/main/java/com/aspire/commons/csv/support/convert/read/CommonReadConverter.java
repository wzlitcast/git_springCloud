package com.aspire.commons.csv.support.convert.read;


import com.aspire.commons.csv.api.IReadConverter;
import com.aspire.commons.csv.support.context.SingleReadContext;
import com.aspire.commons.csv.support.convert.read.collection.ArrayReadConverter;
import com.aspire.commons.csv.support.convert.read.collection.CollectionReadConverter;
import com.aspire.commons.csv.support.convert.read.collection.MapReadConverter;
import com.aspire.commons.csv.support.convert.read.entry.EntryReadConverter;
import com.aspire.commons.csv.support.convert.read.type.ITypeConverter;
import com.aspire.commons.csv.support.convert.read.type.impl.*;
import com.aspire.commons.csv.util.CsvFieldUtil;
import com.aspire.commons.csv.util.CsvInnerUtil;
import com.github.houbb.heaven.annotation.ThreadSafe;
import com.github.houbb.heaven.support.instance.impl.InstanceFactory;
import com.github.houbb.heaven.support.instance.impl.Instances;
import com.github.houbb.heaven.support.sort.ISort;
import com.github.houbb.heaven.util.lang.ObjectUtil;
import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.heaven.util.lang.reflect.ClassTypeUtil;
import com.github.houbb.heaven.util.lang.reflect.PrimitiveUtil;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 通用读取转换器
 * @author binbin.hou
 * @since 0.0.1
 */
@ThreadSafe
public class CommonReadConverter implements IReadConverter<Object> {

    /**
     * 转换器映射关系
     */
    private static final Map<Class, ITypeConverter> CONVERTER_MAP = new HashMap<>();

    static {
        CONVERTER_MAP.put(String.class, InstanceFactory.getInstance().singleton(StringReadConverter.class));
        CONVERTER_MAP.put(Byte.class, InstanceFactory.getInstance().singleton(ByteReadConverter.class));
        CONVERTER_MAP.put(Boolean.class, InstanceFactory.getInstance().singleton(BooleanReadConverter.class));
        CONVERTER_MAP.put(Short.class, InstanceFactory.getInstance().singleton(ShortReadConverter.class));
        CONVERTER_MAP.put(Integer.class, InstanceFactory.getInstance().singleton(IntegerReadConverter.class));
        CONVERTER_MAP.put(Long.class, InstanceFactory.getInstance().singleton(LongReadConverter.class));
        CONVERTER_MAP.put(Float.class, InstanceFactory.getInstance().singleton(FloatReadConverter.class));
        CONVERTER_MAP.put(Double.class, InstanceFactory.getInstance().singleton(DoubleReadConverter.class));
        CONVERTER_MAP.put(Character.class, InstanceFactory.getInstance().singleton(CharacterReadConverter.class));
    }

    @Override
    public Object convert(final SingleReadContext context) {
        final String value = context.value();
        final Field field = context.field();
        final String split = context.split();
        final ISort sort = context.sort();

        //1. 为空判断
        if(StringUtil.isEmpty(value)) {
            return null;
        }

        // 获取当前字段类型
        Class refType = field.getType();

        //2 特殊集合的处理
        // 2.1 数组
        if(ClassTypeUtil.isArray(refType)) {
            return Instances.singletion(ArrayReadConverter.class).convert(context);
        }
        // 2.2 map
        if(ClassTypeUtil.isMap(refType)) {
            return Instances.singletion(MapReadConverter.class).convert(context);
        }
        // 2.3 collection
        if(ClassTypeUtil.isCollection(refType)) {
            return Instances.singletion(CollectionReadConverter.class).convert(context);
        }
        // 2.4 对象
        // 当前字段指定为 @CsvEntry 且为对象
        if(CsvFieldUtil.isEntryAble(field)) {
            final String nextSplit = CsvInnerUtil.getNextSplit(split);
            SingleReadContext singleReadContext = new SingleReadContext();
            singleReadContext.sort(sort)
                    .value(value)
                    .split(nextSplit)
                    .classType(refType)
                    .field(field)
                    .escape(context.escape())
                    ;
            return Instances.singletion(EntryReadConverter.class).convert(singleReadContext);
        }

        // 3. 基本类型
        final boolean escape = context.escape();
        return this.convert(value, refType, escape);
    }

    /**
     * 根据类型进行转换
     * @param value 字符串的值
     * @param type 字段的类型
     * @param escape 是否进行特殊字符转义 since 0.0.6
     * @return 转换的结果
     */
    public Object convert(String value, final Class type,
                          final boolean escape) {
        //1. 快速返回
        if(StringUtil.isEmpty(value)
            || ObjectUtil.isNull(type)) {
            return null;
        }

        // 2. 根据类型处理
        Class actualType = type;
        if(actualType.isPrimitive()) {
            actualType = PrimitiveUtil.getReferenceType(actualType);
        }
        ITypeConverter readConverter = CONVERTER_MAP.get(actualType);
        if(ObjectUtil.isNotNull(readConverter)) {
            if(escape) {
                value = CsvInnerUtil.replaceAllEscape(value);
            }
            return readConverter.convert(value, actualType);
        }

        //3. 不属于基本类型，则直接返回 null
        return null;
    }

}
