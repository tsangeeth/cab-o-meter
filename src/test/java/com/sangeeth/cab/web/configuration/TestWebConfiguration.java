package com.sangeeth.cab.web.configuration;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.googlecode.flyway.core.Flyway;
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
	
	@Bean
	public DataSource datasource(){
		DriverManagerDataSource dataSource = new DriverManagerDataSource(); 
		dataSource.setDriverClassName("com.mysql.jdbc.Driver"); 
		dataSource.setUrl("jdbc:mysql://mysql.cabometer.com:3306/test_cab_o_meter"); 
		dataSource.setUsername("cabwala");
		dataSource.setPassword("secret");
		return dataSource;
	}
	
	@Bean
	public Flyway flyway(){
		Flyway flyway = new Flyway();
		flyway.setDataSource(datasource());
		flyway.setLocations("db/definitions");
		flyway.setSchemas("test_cab_o_meter");
		return flyway;
	}
}
