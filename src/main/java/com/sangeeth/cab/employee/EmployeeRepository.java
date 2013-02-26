package com.sangeeth.cab.employee;

import org.springframework.jdbc.core.JdbcTemplate;


public class EmployeeRepository implements IEmployeeRepository {

	private final JdbcTemplate template;
	private final String INSERT_STATEMENT="INSERT INTO employee (EMP_ID,LAST_NAME,FIRST_NAME,MIDDLE_NAME,ROLE,COST_CENTRE,TEAM_NAME,CONTACT_NO,ALTERNATE_CONTACT_NO,LANDLINE_NO,EMAIL,MANAGER_ID,GENDER) VALUES ( ?,?,?,?,?,?,?,?,?,?,?,?,?)";

	public EmployeeRepository(JdbcTemplate template) {
		this.template = template;
	}

	@Override
	public void create(Employee employee) {
		
		String employeeId = employee.getEmployeeId().getValue();
		Name name = employee.getName();
		Role role = employee.getRole();
		CostCenter costCenter = employee.getCostCenter();
		String teamName = employee.getTeamName();
		String contactNumber = employee.getContactNumber();
		String alternateContactNumber = employee.getAlternateContactNumber();
		String landlineNumber = employee.getLandlineNumber();
		Email email = employee.getEmail();
//		Manager manager = employee.getManager();
		Gender gender = employee.getGender();
		System.out.println("gender:" + gender.value());
		template.update(INSERT_STATEMENT, employeeId, name.getLastName(), name.getFirstName(), 
				name.getMiddleName(), role.value(), costCenter.getValue(), teamName, 
				contactNumber,alternateContactNumber, landlineNumber, email.getValue(),null, gender.value());		
	}

	@Override
	public void read(EmployeeId employeeId) {
		
		
	}

	

}
