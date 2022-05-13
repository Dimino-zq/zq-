package edu.hfu.train.config;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import edu.hfu.train.bean.SysDepartTrainCycle;
import edu.hfu.train.bean.SysStaff;
import edu.hfu.train.bean.SysStudent;
import edu.hfu.train.bean.SysTrainCycle;
import edu.hfu.train.service.security.CustomUserDetailsAuthenticationProvider;
import edu.hfu.train.service.security.CustomUserDetailsService;
import edu.hfu.train.service.security.MyUserDetails;
import edu.hfu.train.service.security.UserPassEncoder;
import edu.hfu.train.service.sysset.SysTrainCycleService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	private final Logger LOG = LoggerFactory.getLogger(WebSecurityConfig.class);
	@Resource
	SysTrainCycleService sysTrainCycleService;
	@Autowired
	CustomUserDetailsService userDetailsService;
	@Autowired
	AuthenticationAccessDeniedHandler authenticationAccessDeniedHandler;

	@Autowired
	private AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> authenticationDetailsSource;

	@Bean
	public PasswordEncoder myPasswordEncoder() {
		return new UserPassEncoder();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/druid/**", "/login", 
				"/static/**", "/", "/goBlank",
				"/invalidSession","/testupfile");
	}

	public AuthenticationProvider authProvider() {
		CustomUserDetailsAuthenticationProvider provider = new CustomUserDetailsAuthenticationProvider(
				myPasswordEncoder(), userDetailsService);
		return provider;
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.anyRequest()
        .authenticated()
		.and().formLogin().loginPage("/").loginProcessingUrl("/userLogin")
				.usernameParameter("userCode").passwordParameter("userPass")
				.authenticationDetailsSource(authenticationDetailsSource).permitAll()
				.failureHandler(new AuthenticationFailureHandler() {
					public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
							AuthenticationException e) throws IOException, ServletException {
						String msg = e.toString().replace(
								"org.springframework.security.authentication.InternalAuthenticationServiceException:",
								"");
						if (msg.indexOf("Bad credentials") > -1) {
							msg = "账号或密码错误";
						}
						String path = "/?mess=" + msg;
						request.getRequestDispatcher(path).forward(request, response);
					}
				}).successHandler(new AuthenticationSuccessHandler() {
					public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
							Authentication auth) throws IOException, ServletException {
						try {
							LOG.debug("登录成功");
							// 登录成功，将必要信息保存到session
							HttpSession session = request.getSession();
							Object principal = auth.getPrincipal();
							if (principal != null && principal instanceof MyUserDetails) {
								MyUserDetails usd = (MyUserDetails) principal;
								session.setAttribute("userType", usd.getUserType());
								session.setAttribute("userCode", usd.getUsername());
								SysTrainCycle cycle = new SysTrainCycle();
								cycle.setStatus("进行中");
								List<SysTrainCycle> lsCologe = sysTrainCycleService.getSysTrainCycleByCon(cycle);
								
								if (null != lsCologe && lsCologe.size() != 0) {
									cycle = lsCologe.get(0);
									// 保存当前实训周期
									session.setAttribute("cycleCologe", cycle);
								} else {
									cycle = null;
								}
								if ("student".equals(usd.getUserType())) {
									if(null == cycle) {
										throw new Exception("实训周期未开启，禁止登录系统！");
									}
									if(Integer.parseInt(((SysStudent) usd.getCustomObj()).getStudentGrade()) != (Integer.parseInt(cycle.getStartschoolyear())-3)) {
										throw new Exception("你当前不在实习时间范围内，禁止登录系统！");
									}
									SysStudent stu=((SysStudent) usd.getCustomObj());
									session.setAttribute("userName", stu.getStudentName());
									session.setAttribute("depart", stu.getStudentName());
									session.setAttribute("major", stu.getStudentName());
									SysDepartTrainCycle sysDepartTrainCycle = new SysDepartTrainCycle();
									sysDepartTrainCycle.setMajorName(((SysStudent) usd.getCustomObj()).getMajor());
									if (null != cycle.getCycleId()) {
										List<SysDepartTrainCycle> ls = sysTrainCycleService
												.getMajorSysTrainCycleByCon(cycle, sysDepartTrainCycle);
										if (null != ls && ls.size() > 0) {
											session.setAttribute("cycleDepart", ls.get(0));
										}
									}

								} else if ("teacher".equals(usd.getUserType())) {

									session.setAttribute("userName", ((SysStaff) usd.getCustomObj()).getStaffName());
								}
								session.setAttribute("user", usd.getCustomObj());

							}
							request.getRequestDispatcher("/goIndex").forward(request, response);
						} catch (Exception e) {
							e.printStackTrace();
							String path = "/?mess=" + e.toString().replace(
									"org.springframework.web.util.NestedServletException: Request processing failed; nested exception is org.springframework.security.access.AccessDeniedException: ",
									"");
							request.getRequestDispatcher(path).forward(request, response);
						}

					}
				}).and().logout().permitAll()
				// and().headers().frameOptions().sameOrigin()
				// 可以解决页面不允许在iframe打开的问题
				.and().headers().frameOptions().sameOrigin().and().csrf().disable().exceptionHandling()
				.accessDeniedHandler(authenticationAccessDeniedHandler).and().sessionManagement()
				.invalidSessionUrl("/invalidSession").maximumSessions(1)
				// 当达到最大值时，是否保留已经登录的用户 为true，新用户无法登录；为 false，旧用户被踢出
				.maxSessionsPreventsLogin(false).expiredSessionStrategy(new SessionInformationExpiredStrategy() {
					@Override
					public void onExpiredSessionDetected(SessionInformationExpiredEvent event)
							throws IOException, ServletException {
						System.out.println("expiredSessionStrategy");
					}
				});
		;
	}
}
