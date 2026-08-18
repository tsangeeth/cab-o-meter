package com.sangeeth.cab.web;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.sangeeth.cab.contract.User;
import com.sangeeth.cab.employee.Employee;
import com.sangeeth.cab.employee.EmployeeRepository;
import com.sangeeth.cab.web.authentication.Authentication;
import com.sangeeth.cab.web.authentication.AuthenticationStore;
import com.sangeeth.cab.web.dto.LoginRequest;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final EmployeeRepository employees;

    public AuthController(EmployeeRepository employees) {
        this.employees = employees;
    }

    @PostMapping("/login")
    public User login(@Valid @RequestBody LoginRequest request, HttpSession session) {
        Employee employee = employees.authenticate(request.employeeId().strip(), request.password())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid employee ID or password"));
        User user = CurrentUser.toUser(employee, employees);
        AuthenticationStore.store(session, new Authentication(user));
        return user;
    }

    @PostMapping("/logout")
    public Map<String, String> logout(HttpSession session) {
        AuthenticationStore.clear(session);
        return Map.of("status", "signed-out");
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "ok");
    }
}
