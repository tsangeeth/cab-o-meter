package com.sangeeth.cab.trip;

public record TripPassenger(
        String employeeId,
        String employeeName,
        String location,
        String address,
        Integer distanceInKms,
        Integer tripSequence,
        String costCentre,
        String teamName) {
}
