package com.sangeeth.cab.trip;

public record DriverInfo(
        Integer id,
        String driverName,
        String agencyName,
        String licenseNo,
        String contactNo,
        String city) {
}
