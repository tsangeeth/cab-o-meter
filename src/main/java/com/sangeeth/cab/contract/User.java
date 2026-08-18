package com.sangeeth.cab.contract;

public record User(
        String employeeId,
        String firstName,
        String lastName,
        String middleName,
        String role,
        String costCentre,
        String teamName,
        String managerId,
        String managerName,
        Address address) {
}
