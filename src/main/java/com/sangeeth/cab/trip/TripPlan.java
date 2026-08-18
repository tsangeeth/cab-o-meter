package com.sangeeth.cab.trip;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record TripPlan(
        Integer id,
        LocalDate tripDate,
        LocalTime tripTime,
        String routeName,
        Integer approxTripKms,
        Double approxTripCost,
        Double tollCharges,
        String pickupOrDrop,
        String cabRegistration,
        Integer driverId,
        String driverName,
        List<TripPassenger> passengers) {

    public TripPlan withPassengers(List<TripPassenger> nextPassengers) {
        return new TripPlan(id, tripDate, tripTime, routeName, approxTripKms, approxTripCost, tollCharges,
                pickupOrDrop, cabRegistration, driverId, driverName, nextPassengers);
    }
}
