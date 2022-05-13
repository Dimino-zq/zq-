package com.hanb.filterJson.aspect;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.hanb.filterJson.annotation.PowerJsonFilter;
import com.hanb.filterJson.annotation.PowerJsonFilters;
import com.hanb.filterJson.utils.FilterCache;

@Component
@Aspect
public class PowerFiltersAspect {

	private final Logger LOG = LoggerFactory.getLogger(PowerFiltersAspect.class);
	/**
	 * @Title: jacksonFilterPointCut
	 * 定义切入点
	 * @param param
	 * @return void
	 */
	@Pointcut("@annotation(com.hanb.filterJson.annotation.PowerJsonFilter) "
			+ "|| @annotation(com.hanb.filterJson.annotation.PowerJsonFilters)")
	public void powerFiltersPointCut() {
	}

	/**
	 * @Title: powerjacksonFilter
	 * @param param
	 * @return void
	 * @throws Throwable
	 */
	@Around(value = "powerFiltersPointCut()")
	public Object jacksonFiltersAroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		Method method = methodSignature.getMethod();
		// 获取注解
		PowerJsonFilter powerjsonFilterElem = method.getAnnotation(PowerJsonFilter.class);
		PowerJsonFilter[] powerFiterArray = null;
		PowerJsonFilters powerFilters = null;
		if (powerjsonFilterElem != null) {
			powerFiterArray = new PowerJsonFilter[1];
			powerFiterArray[0] = powerjsonFilterElem;
		} else if ((powerFilters = method.getAnnotation(PowerJsonFilters.class)) != null) {
			powerFiterArray = powerFilters.value();
		}
		if (powerFiterArray == null || powerFiterArray.length <= 0) {
			return joinPoint.proceed();
		}else {
			if (LOG.isDebugEnabled()) {
				for(PowerJsonFilter filter:powerFiterArray) {
					LOG.debug(filter.clazz().getName()+":"+filter.include());
				}
			}
			// 方法执行
			Object result = joinPoint.proceed();
			LOG.debug("方法名："+method.getName()+"，结果名:"+result.getClass().getName()+"，hash值:"+result.hashCode()+",powerfilters size:"+powerFiterArray.length);
			FilterCache.putFilterByObjKey(result.hashCode(), powerFiterArray);
			return result;
		}
	}
}
