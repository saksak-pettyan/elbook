package com.kaneko.elbook.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Interceptor登録（ログインチェックの共通化）。
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	private final AdminLoginInterceptor adminLoginInterceptor;
	private final UserLoginInterceptor userLoginInterceptor;

	public WebMvcConfig(AdminLoginInterceptor adminLoginInterceptor, UserLoginInterceptor userLoginInterceptor) {
		this.adminLoginInterceptor = adminLoginInterceptor;
		this.userLoginInterceptor = userLoginInterceptor;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		// 管理者：/admin/** はログイン必須。ログイン画面だけ除外。
		registry.addInterceptor(adminLoginInterceptor).addPathPatterns("/admin/**").excludePathPatterns("/admin/login");
		
		 // 利用者：/user/** はログイン必須。ログイン画面だけ除外。
		registry.addInterceptor(userLoginInterceptor).addPathPatterns("/user/**").excludePathPatterns("/user/login");
	}
}
