package com.rbac.framework.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.filter.CorsFilter;

import com.rbac.framework.security.filter.JwtAuthenticationTokenFilter;
import com.rbac.framework.security.handle.AuthenticationErrorEntryPointImpl;
import com.rbac.framework.security.handle.LogoutSuccessHandlerImpl;

/**
 * spring security配置<br>
 * 添加本配置后会启动spring security功能
 * 
 * @author wlfei
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	UserDetailsService userDetailsService;

	/**
	 * 跨域过滤器
	 */
	@Autowired
	private CorsFilter corsFilter;

	/**
	 * 认证失败处理类
	 */
	@Autowired
	private AuthenticationErrorEntryPointImpl unauthorizedHandler;

	/**
	 * 退出处理类
	 */
	@Autowired
	private LogoutSuccessHandlerImpl logoutSuccessHandler;

	/**
	 * token认证过滤器
	 */
	@Autowired
	private JwtAuthenticationTokenFilter authenticationTokenFilter;

	/**
	 * 解决 无法直接注入 AuthenticationManager
	 *
	 * @return
	 * @throws Exception
	 */
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	/**
	 * anyRequest | 匹配所有请求路径<br>
	 * access | SpringEl表达式结果为true时可以访问<br>
	 * anonymous | 匿名可以访问 <br>
	 * denyAll | 用户不能访问 <br>
	 * fullyAuthenticated | 用户完全认证可以访问（非remember-me下自动登录） <br>
	 * hasAnyAuthority | 如果有参数，参数表示权限，则其中任何一个权限可以访问<br>
	 * hasAnyRole | 如果有参数，参数表示角色，则其中任何一个角色可以访问<br>
	 * hasAuthority | 如果有参数，参数表示权限，则其权限可以访问<br>
	 * hasIpAddress | 如果有参数，参数表示IP地址，如果用户IP和参数匹配，则可以访问<br>
	 * hasRole | 如果有参数，参数表示角色，则其角色可以访问<br>
	 * permitAll | 用户可以任意访问 <br>
	 * rememberMe | 允许通过remember-me登录的用户访问 <br>
	 * authenticated | 用户登录后可访问
	 */
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
				// CRSF禁用，因为不使用session
				.csrf().disable()
				// 认证失败处理类
				.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
				// 基于token，所以不需要session
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				// 过滤请求
				.authorizeRequests()
				// 对于登录login 验证码captcha 注册register 允许匿名访问
				.antMatchers("/personal/login", "/captcha").anonymous()
				.antMatchers(HttpMethod.GET, "/*.html", "/**/*.html", "/**/*.css", "/**/*.js").permitAll()
				.antMatchers("/profile/**").anonymous().antMatchers("/common/download**").anonymous()
				.antMatchers("/common/download/resource**").anonymous().antMatchers("/swagger-ui.html").anonymous()
				.antMatchers("/swagger-resources/**").anonymous().antMatchers("/webjars/**").anonymous()
				.antMatchers("/*/api-docs").anonymous().antMatchers("/druid/**").anonymous()
				// 除上面外的所有请求全部需要鉴权认证
				.anyRequest().authenticated().and().headers().frameOptions().disable();
		httpSecurity.logout().logoutUrl("/personal/logout").logoutSuccessHandler(logoutSuccessHandler);
		// 添加JWT filter
		httpSecurity.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
		// 添加CORS filter
		httpSecurity.addFilterBefore(corsFilter, JwtAuthenticationTokenFilter.class);
		httpSecurity.addFilterBefore(corsFilter, LogoutFilter.class);
	}

	/**
	 * 强散列哈希加密实现
	 */
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * 身份认证接口
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}

}
