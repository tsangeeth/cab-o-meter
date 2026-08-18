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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.sangeeth.cab.contract.User;
import com.sangeeth.cab.employee.Role;
import com.sangeeth.cab.requests.CabRequest;
import com.sangeeth.cab.requests.CabRequestRepository;
import com.sangeeth.cab.web.dto.DecisionCommand;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/manager")
public class ManagerController {

    private final CabRequestRepository repository;

    public ManagerController(CabRequestRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/requests")
    public List<CabRequest> list(
            @RequestParam(defaultValue = "pending") String status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
            HttpSession session) {
        User user = requireManager(session);
        return repository.findForManager(user.employeeId(), status, fromDate, toDate);
    }

    @PostMapping("/requests/{id}/approve")
    public CabRequest approve(@PathVariable int id, @RequestBody(required = false) DecisionCommand command, HttpSession session) {
        return decide(id, command, true, session);
    }

    @PostMapping("/requests/{id}/reject")
    public CabRequest reject(@PathVariable int id, @RequestBody(required = false) DecisionCommand command, HttpSession session) {
        return decide(id, command, false, session);
    }

    private CabRequest decide(int id, DecisionCommand command, boolean approved, HttpSession session) {
        User user = requireManager(session);
        CabRequest request = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found"));
        repository.decide(id, user.employeeId(), approved, command == null ? null : command.comments());
        return repository.findById(request.id()).orElseThrow();
    }

    private User requireManager(HttpSession session) {
        User user = CurrentUser.require(session);
        Role role = Role.convert(user.role());
        if (role != Role.MANAGER && role != Role.ADMIN && role != Role.TRIP_MANAGER) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Manager role required");
        }
        return user;
    }
}
