package com.sangeeth.cab.web;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.sangeeth.cab.contract.User;
import com.sangeeth.cab.employee.Role;
import com.sangeeth.cab.requests.CabRequest;
import com.sangeeth.cab.requests.CabRequestRepository;
import com.sangeeth.cab.trip.CabInfo;
import com.sangeeth.cab.trip.DriverInfo;
import com.sangeeth.cab.trip.TripPassenger;
import com.sangeeth.cab.trip.TripPlan;
import com.sangeeth.cab.trip.TripRepository;
import com.sangeeth.cab.web.dto.CreateTripCommand;
import com.sangeeth.cab.web.dto.PassengerCommand;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class TripController {

    private final CabRequestRepository cabRequests;
    private final TripRepository trips;

    public TripController(CabRequestRepository cabRequests, TripRepository trips) {
        this.cabRequests = cabRequests;
        this.trips = trips;
    }

    @GetMapping("/trip-requests")
    public List<CabRequest> searchRequests(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) String employeeId,
            @RequestParam(required = false) String department,
            @RequestParam(required = false) String location,
            HttpSession session) {
        requireTripPlanner(session);
        return cabRequests.searchTrips(startDate, endDate, employeeId, department, location);
    }

    @GetMapping("/cabs")
    public List<CabInfo> cabs(HttpSession session) {
        requireTripPlanner(session);
        return trips.listCabs();
    }

    @GetMapping("/drivers")
    public List<DriverInfo> drivers(HttpSession session) {
        requireTripPlanner(session);
        return trips.listDrivers();
    }

    @GetMapping("/trips")
    public List<TripPlan> trips(HttpSession session) {
        requireTripPlanner(session);
        return trips.listPlans();
    }

    @PostMapping("/trips")
    @ResponseStatus(HttpStatus.CREATED)
    public TripPlan create(@Valid @RequestBody CreateTripCommand command, HttpSession session) {
        requireTripPlanner(session);
        List<PassengerCommand> passengers = command.passengers() == null ? List.of() : command.passengers();
        return trips.createPlan(
                command.tripDate(),
                command.tripTime(),
                command.routeName(),
                command.approxTripKms(),
                command.approxTripCost(),
                command.tollCharges(),
                command.pickupOrDrop(),
                command.cabRegistration(),
                command.driverId(),
                passengers.stream()
                        .map(item -> new TripPassenger(
                                item.employeeId(),
                                item.employeeName(),
                                item.location(),
                                item.address(),
                                item.distanceInKms(),
                                null,
                                item.costCentre(),
                                item.teamName()))
                        .toList());
    }

    private void requireTripPlanner(HttpSession session) {
        User user = CurrentUser.require(session);
        Role role = Role.convert(user.role());
        if (role != Role.TRIP_MANAGER && role != Role.ADMIN) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Trip manager role required");
        }
    }
}
