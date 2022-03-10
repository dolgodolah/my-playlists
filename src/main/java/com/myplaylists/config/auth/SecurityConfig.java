package com.myplaylists.config.auth;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	private final CustomOAuth2UserService customOAuth2UserService;

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui",
									"/swagger-resources", "/configuration/security",
									"/swagger-ui.html", "/webjars/**","/swagger/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
				.headers().frameOptions().disable()
				.and()
					.authorizeRequests()
					.antMatchers("/login", "/assets/**", "/swagger-resources/**", "/swagger-ui/**").permitAll()
					.anyRequest().authenticated()
				.and()
					.logout()
					.logoutSuccessUrl("/")
				.and()
					.oauth2Login()
					.loginPage("/login")
					.userInfoEndpoint()
					.userService(customOAuth2UserService);
   }
}
