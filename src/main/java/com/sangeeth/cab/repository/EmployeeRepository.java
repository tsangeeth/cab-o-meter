package com.sangeeth.cab.repository;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

public class EmployeeRepository implements IEmployeeRepository {

	private DataSource dataSource;
	private JdbcTemplate template;

	public EmployeeRepository(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void initialize() {
		this.template = new JdbcTemplate(dataSource);
	}

}
