package com.sangeeth.cab;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.sangeeth.cab.employee.CostCenter;
import com.sangeeth.cab.employee.Email;
import com.sangeeth.cab.employee.Employee;
import com.sangeeth.cab.employee.EmployeeId;
import com.sangeeth.cab.employee.EmployeeRepository;
import com.sangeeth.cab.employee.Gender;
import com.sangeeth.cab.employee.IEmployeeRepository;
import com.sangeeth.cab.employee.Name;
import com.sangeeth.cab.employee.Role;

@SpringBootTest
@Transactional
class EmployeeRepositoryTest {

    @Autowired
    private IEmployeeRepository repository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void shouldCreate() {
        Employee employee = new Employee(
                "V2123",
                new Name("hugh", "jackman", null),
                Role.EMPLOYEE,
                new CostCenter("beggar_bowl"),
                "team42",
                "9999999999",
                null,
                null,
                new Email("name@company.com"),
                null,
                Gender.MALE);
        repository.create(employee);

        Employee persisted = repository.read(new EmployeeId("V2123")).orElseThrow();
        assertThat(persisted.employeeId()).isEqualTo("V2123");
        assertThat(persisted.name()).isEqualTo(employee.name());
        assertThat(persisted.role()).isEqualTo(Role.EMPLOYEE);
        assertThat(persisted.email()).isEqualTo(employee.email());
        assertThat(persisted.gender()).isEqualTo(Gender.MALE);
        assertThat(jdbcTemplate.queryForObject("SELECT COUNT(*) FROM employee WHERE emp_id = 'V2123'", Integer.class)).isEqualTo(1);
    }

    @Test
    void shouldRead() {
        Employee employee = new Employee(
                "V2124",
                new Name("hugh", "jackman", null),
                Role.EMPLOYEE,
                new CostCenter("beggar_bowl"),
                "team42",
                "9999999999",
                null,
                null,
                new Email("email@company.com"),
                null,
                Gender.MALE);
        repository.create(employee);
        assertThat(repository.read(new EmployeeId("V2124"))).isPresent();
    }

    @Test
    void shouldSearchByLastName() {
        assertThat(repository.search("Howell"))
                .extracting(Employee::employeeId)
                .contains("PC0282");
    }
}
