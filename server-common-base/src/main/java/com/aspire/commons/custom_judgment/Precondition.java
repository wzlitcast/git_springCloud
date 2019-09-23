package com.aspire.commons.custom_judgment;

import com.aspire.commons.ConstantsCode;
import com.aspire.commons.ConstantsCodeCommon;
import com.aspire.commons.HttpCodeException;
import org.springframework.http.HttpStatus;

/**
 * 自定义判断类
 */
public class Precondition {

    /**
     * 判断原子层查询的结果如果为null，则抛出改自定义异常，HTTP状态404
     * @param reference
     * @param <T>
     * @return
     */
    public static <T> T checkNotNull(T reference) {
        if (reference == null) {
            throw new HttpCodeException(HttpStatus.NOT_FOUND,ConstantsCodeCommon.DATA_IS_EMPTY.getMsg());
        }
        return reference;
    }

    /**
     *判断原子层查询的结果list<T>.isEmpty(),如果为null，则抛出改自定义异常，HTTP状态404
     * @param expression
     */
    public static void checkIsEmpty(boolean expression) {
        if (expression) {
            throw new HttpCodeException(HttpStatus.NOT_FOUND,ConstantsCodeCommon.DATA_IS_EMPTY.getMsg());
        }
    }

    /**
     * 原子层校验请求参数如果不合法，则抛出改自定义异常，HTTP状态400
     * @param expression 参数
     */
    public static void checkArgument(boolean expression) {
        if (!expression) {
            throw new HttpCodeException(HttpStatus.BAD_REQUEST,ConstantsCodeCommon.PARAMETER_IS_INVALID_ERROR.getMsg());
        }
    }


    /**
     * 判断原子层请求参数是否为null，则抛出改自定义异常，HTTP状态403
     * @param reference
     * @param <T>
     * @return
     */
    public static <T> T parameterNotNull(T reference) {
        if (reference == null) {
            throw new HttpCodeException(HttpStatus.FORBIDDEN,ConstantsCodeCommon.PARAMETER_IS_EMPTY.getMsg());
        }
        return reference;
    }


    public static void parametercheckIsEmpty(boolean expression) {
        if (expression) {
            throw new HttpCodeException(HttpStatus.FORBIDDEN,ConstantsCodeCommon.PARAMETER_IS_EMPTY.getMsg());
        }
    }


}
