package com.sangeeth.cab.web.dto;

public record PassengerCommand(
        String employeeId,
        String employeeName,
        String location,
        String address,
        Integer distanceInKms,
        String costCentre,
        String teamName) {
}
