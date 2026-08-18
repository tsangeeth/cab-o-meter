package com.sangeeth.cab.contract;

public record Address(
        String line1,
        String line2,
        String locality,
        String city,
        String state,
        String landmark) {
}
