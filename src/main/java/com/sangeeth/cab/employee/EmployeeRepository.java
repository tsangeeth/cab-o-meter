package com.sangeeth.cab.employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
@Repository
public class EmployeeRepository implements IEmployeeRepository {

	private static final String Q_SEARCH_BY_NAME = "select ID, EMP_ID,FIRST_NAME,LAST_NAME,MIDDLE_NAME,ROLE,COST_CENTRE,TEAM_NAME,CONTACT_NO,ALTERNATE_CONTACT_NO,LANDLINE_NO,EMAIL,MANAGER_ID,GENDER from employee where LAST_NAME like ?";
	private final JdbcTemplate template;
	private final String INSERT_STATEMENT = "INSERT INTO employee (EMP_ID,LAST_NAME,FIRST_NAME,MIDDLE_NAME,ROLE,COST_CENTRE,TEAM_NAME,CONTACT_NO,ALTERNATE_CONTACT_NO,LANDLINE_NO,EMAIL,MANAGER_ID,GENDER) VALUES ( ?,?,?,?,?,?,?,?,?,?,?,?,?)";

	@Autowired
	public EmployeeRepository(JdbcTemplate template) {
		this.template = template;
	}

	@Override
	public void create(Employee employee) {

		String employeeId = employee.getEmployeeId();
		Name name = employee.getName();
		Role role = employee.getRole();
		CostCenter costCenter = employee.getCostCenter();
		String teamName = employee.getTeamName();
		String contactNumber = employee.getContactNumber();
		String alternateContactNumber = employee.getAlternateContactNumber();
		String landlineNumber = employee.getLandlineNumber();
		Email email = employee.getEmail();
		// Manager manager = employee.getManager();
		Gender gender = employee.getGender();
		System.out.println("gender:" + gender.value());
		template.update(INSERT_STATEMENT, employeeId, name.getLastName(),
				name.getFirstName(), name.getMiddleName(), role.value(),
				costCenter.getValue(), teamName, contactNumber,
				alternateContactNumber, landlineNumber, email.getValue(), null,
				gender.value());
	}

	@Override
	public void read(EmployeeId employeeId) {

	}

	public List<Employee> search(String name) {
		return this.template.query(
				Q_SEARCH_BY_NAME,
				new RowMapper<Employee>() {
					public Employee mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						Integer id = rs.getInt("ID");
						String employeeId = rs.getString("EMP_ID");
						String firstName = rs.getString("FIRST_NAME");
						String lastName = rs.getString("LAST_NAME");
						String middleName = rs.getString("MIDDLE_NAME");
						Role role = Role.convert(rs.getString("ROLE"));
						String costCentre = rs.getString("COST_CENTRE");
						String teamName = rs.getString("TEAM_NAME");
						String contactNumber = rs.getString("CONTACT_NO");
						String alternateContactNumber = rs.getString("ALTERNATE_CONTACT_NO");
						String landlineNumber = rs.getString("LANDLINE_NO");
						Email emailId = new Email(rs.getString("EMAIL"));
						String managerId = rs.getString("MANAGER_ID");
						Gender gender = Gender.convert(rs.getString("GENDER"));
						Name name = new Name(lastName, firstName, middleName);
						
						return new Employee(id, employeeId, name, role, new CostCenter(costCentre), teamName, contactNumber,
								alternateContactNumber, landlineNumber, emailId, (Manager)null, gender);
					}
				}, "%" + name + "%");
	}

}
