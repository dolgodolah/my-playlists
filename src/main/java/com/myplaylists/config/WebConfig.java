package com.myplaylists.config;

import java.util.List;

import com.myplaylists.crypto.DecryptedArgumentResolver;
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
	private final LoginUserArgumentResolver loginUserArgumentResolver;
	private final DecryptedArgumentResolver decryptedArgumentResolver;
	
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(decryptedArgumentResolver);
		argumentResolvers.add(loginUserArgumentResolver);
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LogInterceptor())
				.order(1)
				.addPathPatterns("/**")
				.excludePathPatterns("/error", "/api-docs/**", "/swagger-ui/**", "/*.ico", "/_next/**");

		registry.addInterceptor(new AuthInterceptor())
				.order(2)
				.addPathPatterns("/**")
				.excludePathPatterns("/", "/signup", "/login", "/logout", "/login/kakao", "/login/google", "/error", "/api-docs/**", "/swagger-ui/**", "/*.ico", "/_next/**");
	}
}
