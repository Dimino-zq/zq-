package com.hanb.filterJson.annotation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Documented
@Retention(RUNTIME)
@Target(value = {ElementType.METHOD})
@Repeatable(value=PowerJsonFilters.class)
public @interface PowerJsonFilter {

	Class<?> clazz();

	/**
	 * include为对象需要包含的字段，默认使用include，如果include为空，则使用exclude字段
	 * 
	 * @Title: include
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param param
	 * @return String[]
	 */
	String[] include() default {};

	/**
	 * exclude 要排除的字段
	 * 
	 * @Title: exclude
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param param
	 * @return String[]
	 */
	String[] exclude() default {};

}
