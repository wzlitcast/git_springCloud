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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
/**
 * <p>
 *  业务日志配置类
 * </p>
 *
 * @author 
 * @since 2019-06-11
 */
//申明是个切面
@Aspect
//申明是个spring管理的bean
@Component
@Order(1)
public class AccessAspectConfig {

	private static Logger logger = LoggerFactory.getLogger("businessMonitoring");
	
	private long startTime = 0;
	
	private long endTime = 0;

	// 申明一个切点 里面是 execution表达式
	@Pointcut("execution(public * com.it.cast.service.*.service.*.*(..))")
	private void controllerAspect() {
		//用作声明
	}

	// 请求method前打印内容
	@Before(value = "controllerAspect()")
	public void methodBefore(JoinPoint joinPoint) {
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = requestAttributes.getRequest();
		// 打印请求内容
		String requestURL = request.getRequestURL().toString();
		String requestMethod = request.getMethod();
		String methodParms = Arrays.toString(joinPoint.getArgs());
		logger.info("requestURL: {},requestMethod: {}",requestURL,requestMethod);
		logger.info("class: {}",joinPoint.getSignature());
		logger.info("methodParms: {}",methodParms);
		startTime = System.currentTimeMillis();
	}

	// 在方法执行完结后打印返回内容
	@AfterReturning(returning = "o", pointcut = "controllerAspect()")
	public void methodAfterReturing(Object o) {
		endTime = System.currentTimeMillis();
		String time = String.valueOf(endTime - startTime);
		logger.info("timeConsuming: {}, Response: {}",time,o);
	}
}

