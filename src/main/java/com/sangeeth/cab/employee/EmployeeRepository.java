package com.sangeeth.cab.employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeRepository implements IEmployeeRepository {

    static final String QUERY_FIELD_PART = "id, emp_id, last_name, first_name, middle_name, role, cost_centre, team_name, contact_no, alternate_contact_no, landline_no, email, manager_id, gender";

    private static final String INSERT_STATEMENT = """
            INSERT INTO employee (emp_id, last_name, first_name, middle_name, role, cost_centre, team_name, contact_no, alternate_contact_no, landline_no, email, manager_id, gender)
            VALUES (:empId, :lastName, :firstName, :middleName, :role, :costCentre, :teamName, :contactNo, :alternateContactNo, :landlineNo, :email, :managerId, :gender)
            """;

    private final JdbcClient jdbc;

    public EmployeeRepository(JdbcClient jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public void create(Employee employee) {
        jdbc.sql(INSERT_STATEMENT)
                .param("empId", employee.getEmployeeId())
                .param("lastName", employee.getName().lastName())
                .param("firstName", employee.getName().firstName())
                .param("middleName", employee.getName().middleName())
                .param("role", employee.getRole().value())
                .param("costCentre", employee.getCostCenter().value())
                .param("teamName", employee.getTeamName())
                .param("contactNo", employee.getContactNumber())
                .param("alternateContactNo", employee.getAlternateContactNumber())
                .param("landlineNo", employee.getLandlineNumber())
                .param("email", employee.getEmail().value())
                .param("managerId", employee.getManagerId())
                .param("gender", employee.getGender().value())
                .update();
    }

    @Override
    public Optional<Employee> read(EmployeeId employeeId) {
        return jdbc.sql("SELECT " + QUERY_FIELD_PART + " FROM employee WHERE emp_id = :empId")
                .param("empId", employeeId.value())
                .query(EmployeeRepository::mapRow)
                .optional();
    }

    @Override
    public List<Employee> search(String name) {
        String term = "%" + (name == null ? "" : name.strip()) + "%";
        return jdbc.sql("""
                        SELECT %s FROM employee
                        WHERE last_name ILIKE :term
                           OR first_name ILIKE :term
                           OR COALESCE(middle_name, '') ILIKE :term
                           OR emp_id ILIKE :term
                        ORDER BY last_name, first_name
                        LIMIT 50
                        """.formatted(QUERY_FIELD_PART))
                .param("term", term)
                .query(EmployeeRepository::mapRow)
                .list();
    }

    public Optional<Employee> authenticate(String employeeId, String password) {
        return jdbc.sql("SELECT " + QUERY_FIELD_PART + " FROM employee WHERE emp_id = :empId AND password = :password")
                .param("empId", employeeId)
                .param("password", password)
                .query(EmployeeRepository::mapRow)
                .optional();
    }

    public Optional<AddressRecord> findAddress(Integer employeeDbId) {
        if (employeeDbId == null) {
            return Optional.empty();
        }
        return jdbc.sql("""
                        SELECT addr_line1, addr_line2, locality, city, state, landmark1
                        FROM address
                        WHERE employee_id = :id
                        ORDER BY id
                        LIMIT 1
                        """)
                .param("id", employeeDbId)
                .query((rs, rowNum) -> new AddressRecord(
                        rs.getString("addr_line1"),
                        rs.getString("addr_line2"),
                        rs.getString("locality"),
                        rs.getString("city"),
                        rs.getString("state"),
                        rs.getString("landmark1")))
                .optional();
    }

    public Optional<String> findNameByEmpId(String empId) {
        if (empId == null || empId.isBlank()) {
            return Optional.empty();
        }
        return jdbc.sql("SELECT first_name || ' ' || last_name AS full_name FROM employee WHERE emp_id = :empId")
                .param("empId", empId)
                .query(String.class)
                .optional();
    }

    static Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Employee(
                rs.getInt("id"),
                rs.getString("emp_id"),
                new Name(rs.getString("last_name"), rs.getString("first_name"), rs.getString("middle_name")),
                Role.convert(rs.getString("role")),
                new CostCenter(rs.getString("cost_centre")),
                rs.getString("team_name"),
                rs.getString("contact_no"),
                rs.getString("alternate_contact_no"),
                rs.getString("landline_no"),
                new Email(rs.getString("email")),
                rs.getString("manager_id"),
                Gender.convert(rs.getString("gender")));
    }

    public record AddressRecord(
            String line1,
            String line2,
            String locality,
            String city,
            String state,
            String landmark) {
    }
}
