package com.aspire.commons.analysis_excel.annotation;

import java.lang.annotation.*;

/**
 * 
 * 映射注解类
 *
 */
@Documented
@Target(value = {ElementType.FIELD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Mapping {
	public String key();
	
	public int length() default -1;
	
	public String rex() default "";
	
	public boolean delNull() default false;

	public boolean distinct() default false;
}
