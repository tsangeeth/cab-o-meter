package com.sangeeth.cab.web;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.sangeeth.cab.contract.User;
import com.sangeeth.cab.employee.Employee;
import com.sangeeth.cab.employee.EmployeeId;
import com.sangeeth.cab.employee.EmployeeRepository;
import com.sangeeth.cab.requests.CabRequest;
import com.sangeeth.cab.requests.CabRequestRepository;
import com.sangeeth.cab.web.dto.CancelRequestCommand;
import com.sangeeth.cab.web.dto.CreateCabRequestCommand;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class CabRequestController {

    private final CabRequestRepository repository;
    private final EmployeeRepository employees;

    public CabRequestController(CabRequestRepository repository, EmployeeRepository employees) {
        this.repository = repository;
        this.employees = employees;
    }

    @PostMapping("/cab-requests")
    @ResponseStatus(HttpStatus.CREATED)
    public CabRequest create(@Valid @RequestBody CreateCabRequestCommand command, HttpSession session) {
        Employee employee = currentEmployee(session);
        String days = command.reoccurDays() == null ? null : String.join(",", command.reoccurDays());
        return repository.create(
                employee.dbId(),
                employee.employeeId(),
                command.startDate(),
                command.loginTime(),
                command.endDate(),
                command.logoutTime(),
                command.reason(),
                days);
    }

    @GetMapping("/cab-requests")
    public List<CabRequest> list(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            HttpSession session) {
        Employee employee = currentEmployee(session);
        return repository.findForEmployee(employee.dbId(), startDate, endDate);
    }

    @PostMapping("/cab-requests/{id}/cancel")
    public CabRequest cancel(@PathVariable int id, @RequestBody(required = false) CancelRequestCommand command, HttpSession session) {
        Employee employee = currentEmployee(session);
        CabRequest request = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found"));
        if (!request.employeeDbId().equals(employee.dbId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cannot cancel another employee's request");
        }
        if (request.cancelled()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Request is already cancelled");
        }
        String reason = command == null ? null : command.reason();
        repository.cancel(id, employee.dbId(), employee.employeeId(), reason);
        return repository.findById(id).orElseThrow();
    }

    private Employee currentEmployee(HttpSession session) {
        User user = CurrentUser.require(session);
        return employees.read(new EmployeeId(user.employeeId()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Employee not found"));
    }
}
