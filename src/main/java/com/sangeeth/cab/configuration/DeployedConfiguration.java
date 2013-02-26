package com.sangeeth.cab.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@Profile("deployed")
public class DeployedConfiguration {
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
