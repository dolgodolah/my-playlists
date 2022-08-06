package com.myplaylists.config;

import java.util.List;

import com.myplaylists.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.myplaylists.config.auth.LoginUserArgumentResolver;

import lombok.NoArgsConstructor;
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
		registry.addInterceptor(new LoginInterceptor())
				.order(1)
				.addPathPatterns("/**")
				.excludePathPatterns("/login/kakao", "/logout");
	}
}
