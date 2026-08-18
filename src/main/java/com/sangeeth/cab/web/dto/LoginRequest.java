package com.sangeeth.cab.web.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(@NotBlank String employeeId, @NotBlank String password) {
}
