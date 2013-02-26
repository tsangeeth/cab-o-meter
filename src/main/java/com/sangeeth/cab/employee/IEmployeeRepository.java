package com.sangeeth.cab.employee;


public interface IEmployeeRepository {

	void create(Employee employee);

	void read(EmployeeId employeeId);

}
