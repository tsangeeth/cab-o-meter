package com.sangeeth.cab.trip;

public record CabInfo(
        String registrationNumber,
        String agencyName,
        String modelName,
        String make,
        int capacity,
        Integer makeYear,
        Double ratePerHour,
        String defaultDriverName) {
}
