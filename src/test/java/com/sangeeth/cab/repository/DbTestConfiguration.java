package com.sangeeth.cab.repository;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.googlecode.flyway.core.Flyway;

@Configuration
public class DbTestConfiguration {

	@Bean
	public Flyway flyway(){
		Flyway flyway = new Flyway();
		flyway.setDataSource(datasource());
		flyway.setLocations("db/migration");
		flyway.setSchemas("cab_o_meter");
		return flyway;
	}
	
	@Bean
	public DataSource datasource(){
		DriverManagerDataSource dataSource = new DriverManagerDataSource(); 
		dataSource.setDriverClassName("com.mysql.jdbc.Driver"); 
		dataSource.setUrl("jdbc:mysql://172.16.0.10:3306/cab_o_meter"); 
		dataSource.setUsername("cabwala");
		dataSource.setPassword("secret");
		return dataSource;
	}
}
