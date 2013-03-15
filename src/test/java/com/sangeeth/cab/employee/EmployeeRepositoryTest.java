package com.sangeeth.cab.employee;


import javax.sql.DataSource;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.googlecode.flyway.core.Flyway;
import com.sangeeth.cab.configuration.RepositoryConfiguration;
import com.sangeeth.cab.employee.CostCenter;
import com.sangeeth.cab.employee.Email;
import com.sangeeth.cab.employee.Employee;
import com.sangeeth.cab.employee.EmployeeMapper;
import com.sangeeth.cab.employee.Manager;
import com.sangeeth.cab.employee.Name;
import com.sangeeth.cab.employee.Role;
import com.sangeeth.cab.repository.DbTestConfiguration;

public class EmployeeRepositoryTest {

	public static Flyway flyway;
	static AnnotationConfigApplicationContext context;
	private static JdbcTemplate dbtemplate;
	
	@BeforeClass
	public static void setup(){
		
		context = new AnnotationConfigApplicationContext(RepositoryConfiguration.class, DbTestConfiguration.class);
		context.getEnvironment().setActiveProfiles("integrated");
		

		context.start();

		flyway = context.getBean(Flyway.class);
		
		DataSource dataSource = context.getBean(DataSource.class);
		dbtemplate = new JdbcTemplate(dataSource);
		flyway.setInitOnMigrate(true);
		flyway.migrate();

	}
	
	@AfterClass
	public static void cleanup(){
		flyway.clean();
		context.close();
	}

	@Test
	public void shouldCreate() throws Exception{
		// Arrange
		
		// Act
		try{
			IEmployeeRepository repository = context.getBean(IEmployeeRepository.class);

			Name name = new Name("hugh", "jackman", null);
			
			Employee employee = new Employee(new EmployeeId("V2123"), name, Role.EMPLOYEE,new CostCenter("beggar_bowl"),"team42","9999999999", null, null, new Email("name@company.com"), null,Gender.MALE);
			repository.create(employee);
		
		
			String query = new StringBuilder("select ")
									.append(EmployeeMapper.QUERY_FIELD_PART)
									.append(" from employee where EMP_ID=?").toString(); 
			
		// Assert
		Employee persistedEmployee = dbtemplate.queryForObject(query, new Object[]{"V2123"}, new EmployeeMapper());
		assertNotNull(persistedEmployee);
		assertThat(employee, is(persistedEmployee));
		
		}finally{
			context.stop();
			context.close();
		}
	}
	
	@Test
	public void shouldRead(){
//	Arrange
		IEmployeeRepository repository = context.getBean(IEmployeeRepository.class);

		Name name = new Name("hugh", "jackman", null);
		
		Employee employee = new Employee(new EmployeeId("V2124"), name, Role.EMPLOYEE,new CostCenter("beggar_bowl"),"team42","9999999999", null, null, new Email("email@company.com"), null,Gender.MALE);
		repository.create(employee);

		
		// Act
		repository.read(new EmployeeId("V2124"));
	}
}
