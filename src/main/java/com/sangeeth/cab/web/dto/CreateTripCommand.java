package com.sangeeth.cab.web.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateTripCommand(
        @NotNull LocalDate tripDate,
        @NotNull LocalTime tripTime,
        @NotBlank String routeName,
        Integer approxTripKms,
        @NotNull Double approxTripCost,
        Double tollCharges,
        String pickupOrDrop,
        String cabRegistration,
        Integer driverId,
        List<PassengerCommand> passengers) {
}
