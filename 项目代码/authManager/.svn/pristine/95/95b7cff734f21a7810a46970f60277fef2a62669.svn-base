package com.hanb.filterJson.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
/**
 * 时间 得到方法执行时间
 * @author iceheartboy
 *
 */
@Aspect
@Component
public class TakeUpTimeAspect {
	private final Logger LOG = LoggerFactory.getLogger(TakeUpTimeAspect.class);
	
	@Pointcut("@annotation(com.hanb.filterJson.annotation.TakeUpTime)")
	public void takeUpTimePointCut() {
	}
	
	/**
	 * @param pjp
	 * @return
	 * @throws Throwable
	 */
	
	  @Around(value = "takeUpTimePointCut()")
	  public Object handleControllerMethod(ProceedingJoinPoint pjp) throws Throwable {    //pjp是一个 包含拦截方法信息的对象
		  //参数数组
		  long start = System.currentTimeMillis();
		  //调用被拦截的方法
		  Object object = pjp.proceed();
		  LOG.info(pjp.getTarget().getClass().getName()+"耗时："+(System.currentTimeMillis() - start));
		  return object;

	  }

}
