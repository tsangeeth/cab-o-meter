package com.sangeeth.cab.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;

import com.sangeeth.cab.employee.EmployeeRepository;

@Configuration
@ComponentScan(basePackageClasses={EmployeeRepository.class})
public class ServiceConfiguration {
	@Autowired
	private DataSource datasource;

	@Bean
	@Scope("prototype")
	public JdbcTemplate jdbcTemplate(){
		return new JdbcTemplate(datasource);
	}
	

}
