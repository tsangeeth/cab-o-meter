package com.sangeeth.cab.web.dto;

import java.util.List;

import com.sangeeth.cab.contract.Address;
import com.sangeeth.cab.employee.Employee;
import com.sangeeth.cab.employee.EmployeeRepository;

public record EmployeeResponse(
        Integer id,
        String employeeId,
        String firstName,
        String lastName,
        String middleName,
        String role,
        String costCentre,
        String teamName,
        String contactNumber,
        String alternateContactNumber,
        String landlineNumber,
        String email,
        String managerId,
        String managerName,
        String gender,
        Address address) {

    public static EmployeeResponse from(Employee employee, EmployeeRepository.AddressRecord address, String managerName) {
        return new EmployeeResponse(
                employee.dbId(),
                employee.employeeId(),
                employee.name().firstName(),
                employee.name().lastName(),
                employee.name().middleName(),
                employee.role().value(),
                employee.costCenter().value(),
                employee.teamName(),
                employee.contact(),
                employee.alternateContact(),
                employee.landlineNumber(),
                employee.email().value(),
                employee.managerId(),
                managerName,
                employee.gender().value(),
                address == null ? null : new Address(
                        address.line1(),
                        address.line2(),
                        address.locality(),
                        address.city(),
                        address.state(),
                        address.landmark()));
    }

    public static List<EmployeeResponse> listFrom(List<Employee> employees) {
        return employees.stream()
                .map(employee -> from(employee, null, null))
                .toList();
    }
}
