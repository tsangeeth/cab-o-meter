package com.sangeeth.cab.web;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sangeeth.cab.employee.EmployeeRepository;
import com.sangeeth.cab.web.dto.EmployeeResponse;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    private final EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping("/employees")
    public List<EmployeeResponse> search(@RequestParam(name = "name", required = false, defaultValue = "") String name) {
        return EmployeeResponse.listFrom(employeeRepository.search(name));
    }
}
