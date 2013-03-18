package com.sangeeth.cab.configuration;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class DatabaseConfiguration {

	@Bean
	public DataSource dataSource(){
		DriverManagerDataSource dataSource = new DriverManagerDataSource(); 
		dataSource.setDriverClassName("com.mysql.jdbc.Driver"); 
		dataSource.setUrl("jdbc:mysql://mysql.cabometer.com:3306/cab_o_meter"); 
		dataSource.setUsername("cabwala");
		dataSource.setPassword("secret");
		return dataSource;
	}
}
