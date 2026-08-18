package com.sangeeth.cab.employee;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Role {
    EMPLOYEE("Employee"),
    MANAGER("Manager"),
    TRIP_MANAGER("TripManager"),
    ADMIN("Admin");

    private final String value;

    Role(String value) {
        this.value = value;
    }

    @JsonValue
    public String value() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    public static Role convert(String value) {
        return switch (value) {
            case "Employee" -> EMPLOYEE;
            case "Manager" -> MANAGER;
            case "TripManager" -> TRIP_MANAGER;
            case "Admin" -> ADMIN;
            default -> throw new IllegalArgumentException("Invalid Role: " + value);
        };
    }
}
