package com.sangeeth.cab.employee;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


final class EmployeeMapper implements RowMapper<Employee> {
	
	final static String QUERY_FIELD_PART = "ID, EMP_ID,LAST_NAME,FIRST_NAME,MIDDLE_NAME,ROLE,COST_CENTRE,TEAM_NAME,CONTACT_NO,ALTERNATE_CONTACT_NO,LANDLINE_NO,EMAIL,MANAGER_ID,GENDER";


	@Override
	public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
		Integer dbId = rs.getInt("ID");
		String employeeId = rs.getString("EMP_ID");
		String lastName = rs.getString("LAST_NAME");
		String firstName = rs.getString("FIRST_NAME");
		String middleName = rs.getString("MIDDLE_NAME");
		Role role = Role.convert(rs.getString("ROLE"));
		CostCenter costCenter = new CostCenter(rs.getString("COST_CENTRE"));
		String teamName = rs.getString("TEAM_NAME");
		String contactNumber = rs.getString("CONTACT_NO");
		String alternateContactNumber = rs.getString("ALTERNATE_CONTACT_NO");
		String landlineNumber = rs.getString("LANDLINE_NO");
		Email emailId = new Email(rs.getString("EMAIL"));
		String genderValue = rs.getString("GENDER");
		System.out.println("GenderVALUE:"+ genderValue);
		Gender gender = Gender.convert(genderValue);

	  	return new Employee(dbId, employeeId, new Name(lastName, firstName, middleName), role, costCenter, teamName, contactNumber, alternateContactNumber, landlineNumber, emailId, null, gender);
	}
}