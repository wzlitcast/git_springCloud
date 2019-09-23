package com.it.cast.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import java.util.Arrays;
/**
 * <p>
 *  业务日志自动对比配置类
 * </p>
 *
 * @author 
 * @since 2019-06-11
 */
@Component
@Aspect
@Order(2)
public class TraceIdMDCSpringConfig {
    private Logger log1 = LoggerFactory.getLogger("beforeMonitoring");
    private Logger log2 = LoggerFactory.getLogger("afterMonitoring");
    private static final String FLAG = "1";

    @Pointcut("execution(public * com.it.cast.service.*.service.*.*(..))")
    public void logAspect() {
        //用作声明
    }

    @Before("logAspect()")
    public void doBefore(JoinPoint joinPoint){
        String classType = joinPoint.getTarget().getClass().getName();
        try {
            Class<?> clazz = Class.forName(classType);
            String clazzName = clazz.getName();
            String methodName = joinPoint.getSignature().getName();
            if("1".equals(FLAG)){
                String value = Arrays.toString( joinPoint.getArgs());
                log1.info("|{},|{},|{}",clazzName,methodName,value);
            }else {
                log1.info("|{},|{}",clazzName,methodName);
            }
        } catch (ClassNotFoundException e) {
            log1.error("doBefore切面日志异常",e);
        }
    }

    @AfterReturning(pointcut = "logAspect()", returning = "returnValue")
    public void doAfterReturning(JoinPoint joinPoint, Object returnValue) throws Throwable  {
        String classType = joinPoint.getTarget().getClass().getName();
        try {
            Class<?> clazz = Class.forName(classType);
            String clazzName = clazz.getName();
            String methodName = joinPoint.getSignature().getName();
            if("1".equals(FLAG)){
                log2.info("|{},|{},|{}",clazzName,methodName,returnValue);
            }else {
                log2.info("|{},|{}",clazzName,methodName);
            }
        } catch (ClassNotFoundException e) {
            log2.error("doAfterReturning切面日志异常",e);
        }
    }

}


