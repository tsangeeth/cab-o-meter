package com.sangeeth.cab.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;

import com.sangeeth.cab.employee.EmployeeRepository;
import com.sangeeth.cab.employee.IEmployeeRepository;

@Configuration
public class RepositoryConfiguration {

	@Autowired
	private DataSource dataSource;
	
	@Bean
	public IEmployeeRepository employeeRepository(){
		EmployeeRepository repository = new EmployeeRepository(new JdbcTemplate(dataSource));
		return repository;
	}
	
	
}
