package edu.hfu.auth.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import edu.hfu.auth.util.CacheData;
import edu.hfu.auth.util.DesEncrypt;

@Component
public class LoginInterceptor implements HandlerInterceptor{
	private final Logger LOG = LoggerFactory.getLogger(LoginInterceptor.class);

	/**
     * 在请求被处理之前调用
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		LOG.debug("拦截路径:"+request.getRequestURI());
		String authori=request.getHeader("Authorization");
		if (null==authori) {
			 // 检查每个到来的请求对应的session域中是否有登录标识
	        Object loginName = request.getSession().getAttribute("userCode");
	        if (null == loginName || !(loginName instanceof String)) {
	        	LOG.debug("登录超时...");
	            // 未登录，重定向到登录页
	        	request.getRequestDispatcher("/timeout").forward(request,response);
	        	return false;
	        }else {
		        String userName = (String) loginName;
		        LOG.debug("当前用户已登录，登录的用户名为： " + userName);
		        return true;
	        }
		}else {
			authori=DesEncrypt.getDesString(authori);
			String[] access=authori.split("@");
			if (access.length==2) {
				String userCode=access[0];//
				String accessCode=access[1];
				if("student".equals(userCode)) {
					if ("Mx04z12l+ckVeHAwSPUbgnHUm2gcW0oVy4oEO7rQQkM=".equals(accessCode)) {
						return true;
					}else {
						LOG.debug("无效的学生访问授权");
						throw new java.lang.RuntimeException("无效的学生访问授权");
					}
				}else {
					String authRes=CacheData.checkAuth(userCode, accessCode);
					if ("succ".equals(authRes)) {
						return true;
					}else {
						LOG.debug(authRes);
						throw new java.lang.RuntimeException(authRes);
					}
				}
			}else {
				throw new java.lang.RuntimeException("未找到授权码");
			}
		}
		
		
		
	}
	/**
     * 在请求被处理后，视图渲染之前调用
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}
	 /**
     * 在整个请求结束后调用
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}

}
