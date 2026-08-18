package com.sangeeth.cab.employee;

import java.util.List;
import java.util.Optional;

public interface IEmployeeRepository {
    void create(Employee employee);

    Optional<Employee> read(EmployeeId employeeId);

    List<Employee> search(String name);
}
