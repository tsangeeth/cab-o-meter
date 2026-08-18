package com.sangeeth.cab.web.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import jakarta.validation.constraints.NotNull;

public record CreateCabRequestCommand(
        @NotNull LocalDate startDate,
        @NotNull LocalDate endDate,
        @NotNull LocalTime loginTime,
        @NotNull LocalTime logoutTime,
        List<String> reoccurDays,
        String reason) {
}
