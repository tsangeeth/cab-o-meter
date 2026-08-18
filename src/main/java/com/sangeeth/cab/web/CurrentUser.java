package com.sangeeth.cab.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.sangeeth.cab.contract.Address;
import com.sangeeth.cab.contract.User;
import com.sangeeth.cab.employee.Employee;
import com.sangeeth.cab.employee.EmployeeRepository;
import com.sangeeth.cab.web.authentication.Authentication;
import com.sangeeth.cab.web.authentication.AuthenticationStore;

import jakarta.servlet.http.HttpSession;

final class CurrentUser {

    private CurrentUser() {
    }

    static User require(HttpSession session) {
        Authentication authentication = AuthenticationStore.get(session);
        if (authentication == null || authentication.user() == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Not signed in");
        }
        return authentication.user();
    }

    static User toUser(Employee employee, EmployeeRepository employees) {
        EmployeeRepository.AddressRecord address = employees.findAddress(employee.dbId()).orElse(null);
        String managerName = employees.findNameByEmpId(employee.managerId()).orElse(null);
        return new User(
                employee.employeeId(),
                employee.name().firstName(),
                employee.name().lastName(),
                employee.name().middleName(),
                employee.role().value(),
                employee.costCenter().value(),
                employee.teamName(),
                employee.managerId(),
                managerName,
                address == null ? null : new Address(
                        address.line1(),
                        address.line2(),
                        address.locality(),
                        address.city(),
                        address.state(),
                        address.landmark()));
    }
}
