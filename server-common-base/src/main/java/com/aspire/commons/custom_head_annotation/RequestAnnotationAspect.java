package com.aspire.commons.custom_head_annotation;

import com.aspire.commons.HeaderInformation;
import com.aspire.commons.ip_convert.IpConvert;
import com.aspire.commons.log.LogCommons;
import com.aspire.commons.valid_list.ValidList;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 自定义注解业务切面类
 *
 * @author gaoqi
 * 2019/05/01
 */
@Slf4j
@Aspect
@Component
@Order(3)
public class RequestAnnotationAspect {
    private static final String REFLECT_TYPE = "java.lang.reflect.TypeVariable";
    private static final String OVERLAY = "****";

    // 申明一个切点 里面是 execution表达式
    @Pointcut("@annotation(com.aspire.commons.custom_head_annotation.RequestIP)")
    private void headAspect() {
        //用作声明
    }


    // 请求method前打印内容
    @Before("headAspect()")
    public void methodBefore(JoinPoint joinPoint) throws ReflectiveOperationException {
        if (REFLECT_TYPE.equals(joinPoint.getClass().getTypeParameters().getClass().getComponentType().getName())) {
            //类型为List
            Object[] args = joinPoint.getArgs();
            for (Object obj : args) {
                if (obj instanceof ValidList) {
                    ValidList validList = (ValidList) obj;
                    for (int i = 0; i < validList.size(); i++) {
                        Field[] fields = validList.get(i).getClass().getDeclaredFields();
                        if (null != fields) {
                            judgmentType(validList.get(i), fields);
                        }
                    }
                } else if (obj instanceof Model) {
                    //类型为对象
                    Field[] fields = obj.getClass().getDeclaredFields();
                    if (null != fields) {
                        judgmentType(obj, fields);
                    }
                }
            }
        } else {
            //类型为对象
            Object[] objs = joinPoint.getArgs();
            for (Object obj : objs) {
                if (obj instanceof Model) {
                    Field[] fields = obj.getClass().getDeclaredFields();
                    if (null != fields) {
                        judgmentType(obj, fields);
                    }
                }
            }
        }
    }

    private void judgmentType(Object obj, Field[] fields) throws ReflectiveOperationException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        for (Field field : fields) {
            Annotation[] annotations = field.getAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation.toString().contains("RequestIP")) {
                    if (field.isAnnotationPresent(RequestIP.class)) {
                        RequestIP requestIP = field.getAnnotation(RequestIP.class);
                        String value = requestIP.value();
                        if (StringUtils.isEmpty(value)) {
                            Method method = setMethod(obj, field);
                            LogCommons.debug("ip:{}", request.getHeader(String.valueOf(IpConvert.ipToLong(request.getHeader("x-real-ip")))));
                            method.invoke(obj, Long.valueOf(IpConvert.ipToLong(request.getHeader("x-real-ip"))));
                        } else {
                            Method method = setMethod(obj, field);
                            LogCommons.debug("ip:{}", value);
                            method.invoke(obj, Long.valueOf(value));
                        }
                    }
                } else if (annotation.toString().contains("RequestCreator")) {
                    if (field.isAnnotationPresent(RequestCreator.class)) {
                        RequestCreator requestCreator = field.getAnnotation(RequestCreator.class);
                        String value = requestCreator.value();
                        if (StringUtils.isEmpty(value)) {
                            Method method = setMethod(obj, field);
                            LogCommons.debug("userId:{}", HeaderInformation.getUserId());
                            method.invoke(obj, HeaderInformation.getUserId());
                        } else {
                            Method method = setMethod(obj, field);
                            LogCommons.debug("userId:{}", value);
                            method.invoke(obj, value);
                        }
                    }
                } else if (annotation.toString().contains("RequestModifier")) {
                    if (field.isAnnotationPresent(RequestModifier.class)) {
                        RequestModifier requestModifier = field.getAnnotation(RequestModifier.class);
                        String value = requestModifier.value();
                        if (StringUtils.isEmpty(value)) {
                            Method method = setMethod(obj, field);
                            LogCommons.debug("modifierUserId:{}", HeaderInformation.getUserId());
                            method.invoke(obj,HeaderInformation.getUserId());
                        } else {
                            Method method = setMethod(obj, field);
                            LogCommons.debug("modifierUserId:{}", value);
                            method.invoke(obj, value);
                        }
                    }
                } else if (annotation.toString().contains("RequestPhoneMasking")) {
                    if (field.isAnnotationPresent(RequestPhoneMasking.class)) {
                        RequestPhoneMasking requestPhoneMasking = field.getAnnotation(RequestPhoneMasking.class);
                        int startIndex = requestPhoneMasking.startIndex();
                        int endIndex = requestPhoneMasking.endIndex();
                        if (startIndex < endIndex && startIndex > 0 && endIndex < Integer.MAX_VALUE) {
                            Method methodGet = getMethod(obj, field);
                            Object invoke = methodGet.invoke(obj, null);
                            if (!StringUtils.isEmpty(invoke)) {
                                //取VO类信息对数据脱敏
                                LogCommons.debug("getInvoke的值为:{}", invoke);
                                String overlay = org.apache.commons.lang3.StringUtils.overlay((String) invoke, OVERLAY, startIndex, endIndex);
                                Method method = setMethod(obj, field);
                                method.invoke(obj, overlay);
                            }
                        }
                    }
                }
            }
        }
    }


    private Method getMethod(Object obj, Field field) throws ReflectiveOperationException {
        try {
            return obj.getClass().getMethod("get" + captureName(field.getName()), null);
        } catch (NoSuchMethodException e) {
            LogCommons.error("AOP切面field反射异常,obj：{},name:{}", obj, field.getName(), e);
            throw new ReflectiveOperationException("切面类获取get方法异常", e);
        }
    }

    private Method setMethod(Object obj, Field field) throws ReflectiveOperationException {
        try {
            return obj.getClass().getMethod("set" + captureName(field.getName()), Class.forName(field.getGenericType().getTypeName()));
        } catch (NoSuchMethodException e) {
            LogCommons.error("AOP切面field反射异常,obj：{},name:{}", obj, field.getName(), e);
            throw new ReflectiveOperationException("切面类获取set方法异常", e);
        }
    }


    /**
     * 将字符串的首字母转大写
     *
     * @param str 需要转换的字符串
     * @return
     */
    private String captureName(String str) {
        // 进行字母的ascii编码前移，效率要高于截取字符串进行转换的操作
        char[] cs = str.toCharArray();
        cs[0] -= 32;
        return String.valueOf(cs);
    }


    @After("headAspect()")
    public void methodAfterReturing() {
        //方法执行完后处理的业务逻辑
    }
}
