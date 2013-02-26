package com.sangeeth.cab.web.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.sangeeth.cab.web.authentication.AutoLoginInterceptor;

@Configuration
@EnableWebMvc
public class TestWebConfiguration extends WebMvcConfigurerAdapter {
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(autoLoginInteceptor());
	}

	@Bean
	public AutoLoginInterceptor autoLoginInteceptor(){
		return new AutoLoginInterceptor();
	}
}
