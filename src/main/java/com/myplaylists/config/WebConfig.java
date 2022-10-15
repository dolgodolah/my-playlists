package com.myplaylists.config;

import java.util.List;

import com.myplaylists.interceptor.AuthInterceptor;
import com.myplaylists.interceptor.LogInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.myplaylists.config.auth.LoginUserArgumentResolver;

import lombok.RequiredArgsConstructor;


@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer{
	/*
	 * LoginUserArgumentResolver를 스프링에서 인식할 수 있도록 WebMvcConfigurer에 추가
	 */
	private final LoginUserArgumentResolver loginUserArgumentResolver;
	
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(loginUserArgumentResolver);
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LogInterceptor())
				.order(1)
				.addPathPatterns("/**")
				.excludePathPatterns("/error", "/api-docs/**", "/swagger-ui/**", "/*.ico");

		registry.addInterceptor(new AuthInterceptor())
				.order(2)
				.addPathPatterns("/**")
				.excludePathPatterns("/login", "/logout", "/login/kakao", "/login/google", "/error", "/api-docs/**", "/swagger-ui/**", "/*.ico");
	}
}
