
package com.yl.springbootsecurity.config;



import com.yl.springbootsecurity.filter.CustomizeAbstractSecurityInterceptor;
import com.yl.springbootsecurity.filter.CustomizeAccessDecisionManager;
import com.yl.springbootsecurity.filter.CustomizeAuthenticationFilter;
import com.yl.springbootsecurity.filter.CustomizeFilterInvocationSecurityMetadataSource;
import com.yl.springbootsecurity.service.impl.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * security核心配置类，要继承WebSecurityConfigurerAdapter
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	@Bean
	public UserDetailsService getUserDetailsService() {
		return new UserDetailServiceImpl();
	}

	@Bean
	public PasswordEncoder getPasswordEncoderBean() {
		return new BCryptPasswordEncoder();
	}

//	@Bean
//	public JwtAuthenticationTokenFilter getauthenticationTokenFilterBean() {
//		return new JwtAuthenticationTokenFilter();
//	}

	//注入登录成功的处理器
	@Bean
	public LoginSuccessHandler getLoginSuccessHandler() {
		return new LoginSuccessHandler();
	}

	//注入登录失败的处理器
	@Bean
	public LoginFailHandler getLoginFailHandler() {
		return new LoginFailHandler();
	}

	//注入未登录的异常处理器
	@Bean
	public CustomizeAuthenticationEntryPoint getCustomizeAuthenticationEntryPoint(){
		return new CustomizeAuthenticationEntryPoint();
	}

	//注入登出成功的处理器
	@Bean
	public CustomizeLogoutSuccessHandler getCustomizeLogOutSuccessHandler() {
		return new CustomizeLogoutSuccessHandler();
	}

	//注册自定义的UsernamePasswordAuthenticationFilter
	@Bean
	public CustomizeAuthenticationFilter getCustomizeAuthenticationFilter() throws Exception {
		CustomizeAuthenticationFilter filter = new CustomizeAuthenticationFilter();
		filter.setAuthenticationSuccessHandler(getLoginSuccessHandler());
		filter.setAuthenticationFailureHandler(getLoginFailHandler());
//		filter.setFilterProcessesUrl("/login/self");
		filter.setFilterProcessesUrl("/authentication/form");

		//这句很关键，重用WebSecurityConfigurerAdapter配置的AuthenticationManager，不然要自己组装AuthenticationManager
		filter.setAuthenticationManager(authenticationManagerBean());
		return filter;
	}

	//访问决策管理器
	@Autowired
	CustomizeAccessDecisionManager accessDecisionManager;

	//实现权限拦截
	@Autowired
	CustomizeFilterInvocationSecurityMetadataSource securityMetadataSource;

	@Autowired
	private CustomizeAbstractSecurityInterceptor securityInterceptor;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//1.配置授权方式，这个configure方法里面主要是配置一些
		//http的相关配置，包括登入，登出，异常处理，会话管理等
		http.authorizeRequests().
				withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
					@Override
					public <O extends FilterSecurityInterceptor> O postProcess(O o) {
						o.setAccessDecisionManager(accessDecisionManager);//访问决策管理器
						o.setSecurityMetadataSource(securityMetadataSource);//安全元数据源
						return o;
					}
				})
//				.antMatchers("/user/getUsers").hasAuthority("query_user") //设置指定的角色才能访问得到的请求资源
//				.antMatchers("/user/deleteUserById").hasAuthority("delete_user")
				.antMatchers("/authentication/form").permitAll() //允许所有人
				.anyRequest().authenticated()//其他所有请求都要认证
				//登入
				.and().formLogin()
				.permitAll() //允许所有人访问
				.loginProcessingUrl("/authentication/form") //登录成功后，处理登录的url
				.successHandler(getLoginSuccessHandler()) //登录成功后，调用成功处理器
				.failureHandler(getLoginFailHandler()) //登录失败，//调用失败处理器
				//登出
				.and().logout()
				.permitAll()
				.logoutSuccessHandler(getCustomizeLogOutSuccessHandler()) //登出成功后，调用登出成功处理器
				.deleteCookies("JSESSIONID")//登出之后删除cookie
				//异常处理（权限拒绝，登录失效）
				.and().exceptionHandling()
				//匿名用户访问无权限资源时的异常处理
				.authenticationEntryPoint(getCustomizeAuthenticationEntryPoint()); //异常处理器

		http.addFilterBefore(securityInterceptor, FilterSecurityInterceptor.class);//增加到默认拦截链中

		//用重写的Filter替换掉原有的UsernamePasswordAuthenticationFilter
//		http.addFilterAt(getCustomizeAuthenticationFilter(),
//				UsernamePasswordAuthenticationFilter.class);

		http.csrf().disable();
//		http.cors().and().csrf().disable();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//2.配置认证方式
		//从数据库查该用户拥有的权限
		auth.userDetailsService(getUserDetailsService());
	}


}
