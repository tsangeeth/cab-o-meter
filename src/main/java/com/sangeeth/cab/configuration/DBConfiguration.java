package com.sangeeth.cab.configuration;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.sangeeth.cab.repository.EmployeeRepository;
import com.sangeeth.cab.repository.IEmployeeRepository;

@Configuration
public class DBConfiguration {

	@Bean
	public IEmployeeRepository employeeRepository(){
		EmployeeRepository repository = new EmployeeRepository(dataSource());
		repository.initialize();
		return repository;
	}
	
	@Bean
	public DataSource dataSource(){
		DriverManagerDataSource dataSource = new DriverManagerDataSource(); 
		dataSource.setDriverClassName("org.hsqldb.jdbcDriver"); 
		dataSource.setUrl("jdbc:hsqldb:hsql://localhost:"); 
		dataSource.setUsername("sa");
		dataSource.setPassword("");
		return dataSource;
	}
}
