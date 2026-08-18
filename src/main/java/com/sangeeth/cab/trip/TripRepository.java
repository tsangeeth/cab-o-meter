package com.sangeeth.cab.trip;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class TripRepository {

    private final JdbcClient jdbc;

    public TripRepository(JdbcClient jdbc) {
        this.jdbc = jdbc;
    }

    public List<CabInfo> listCabs() {
        return jdbc.sql("""
                SELECT c.registration_number, c.agency_name, c.model_name, c.make, c.capacity, c.make_year, c.rateperhr,
                       d.driver_name
                FROM cab_info c
                LEFT JOIN driver_info d ON d.id = c.default_driver_id
                ORDER BY c.registration_number
                """)
                .query((rs, rowNum) -> new CabInfo(
                        rs.getString("registration_number"),
                        rs.getString("agency_name"),
                        rs.getString("model_name"),
                        rs.getString("make"),
                        rs.getInt("capacity"),
                        (Integer) rs.getObject("make_year"),
                        (Double) rs.getObject("rateperhr"),
                        rs.getString("driver_name")))
                .list();
    }

    public List<DriverInfo> listDrivers() {
        return jdbc.sql("""
                SELECT id, driver_name, agency_name, license_no, contact_no, city
                FROM driver_info
                ORDER BY driver_name
                """)
                .query((rs, rowNum) -> new DriverInfo(
                        rs.getInt("id"),
                        rs.getString("driver_name"),
                        rs.getString("agency_name"),
                        rs.getString("license_no"),
                        rs.getString("contact_no"),
                        rs.getString("city")))
                .list();
    }

    public List<TripPlan> listPlans() {
        List<TripPlan> plans = jdbc.sql("""
                SELECT tp.id, tp.trip_date, tp.trip_time, tp.route_name, tp.approx_trip_kms, tp.approx_trip_cost,
                       tp.toll_charges, tp.pickup_drop, tp.cab_regd_number, tp.driver_id, d.driver_name
                FROM trip_plan tp
                LEFT JOIN driver_info d ON d.id = tp.driver_id
                ORDER BY tp.trip_date, tp.trip_time
                """)
                .query((rs, rowNum) -> new TripPlan(
                        rs.getInt("id"),
                        toDate(rs.getDate("trip_date")),
                        toTime(rs.getTime("trip_time")),
                        rs.getString("route_name"),
                        (Integer) rs.getObject("approx_trip_kms"),
                        rs.getDouble("approx_trip_cost"),
                        (Double) rs.getObject("toll_charges"),
                        rs.getString("pickup_drop"),
                        rs.getString("cab_regd_number"),
                        (Integer) rs.getObject("driver_id"),
                        rs.getString("driver_name"),
                        List.of()))
                .list();
        return plans.stream().map(plan -> plan.withPassengers(passengersFor(plan.id()))).toList();
    }

    public TripPlan createPlan(LocalDate tripDate, LocalTime tripTime, String routeName, Integer kms, Double cost,
            Double toll, String pickupOrDrop, String cabRegistration, Integer driverId, List<TripPassenger> passengers) {
        Integer id = jdbc.sql("""
                INSERT INTO trip_plan (trip_date, trip_time, route_name, approx_trip_kms, approx_trip_cost, toll_charges, pickup_drop, cab_regd_number, driver_id)
                VALUES (:tripDate, :tripTime, :routeName, :kms, :cost, :toll, :pickupDrop, :cab, :driverId)
                RETURNING id
                """)
                .param("tripDate", tripDate)
                .param("tripTime", tripTime)
                .param("routeName", routeName)
                .param("kms", kms)
                .param("cost", cost)
                .param("toll", toll)
                .param("pickupDrop", pickupOrDrop)
                .param("cab", cabRegistration)
                .param("driverId", driverId)
                .query(Integer.class)
                .single();

        int sequence = 1;
        for (TripPassenger passenger : passengers) {
            jdbc.sql("""
                    INSERT INTO trip_passengers (trip_plan_id, employee_id, employee_name, location, address, distance_in_kms,
                        trip_sequence, approved_manager_id, approved_manager_name, cost_centre, split_cost, team_name)
                    VALUES (:planId, :employeeId, :employeeName, :location, :address, :kms, :seq, :managerId, :managerName, :costCentre, :split, :teamName)
                    """)
                    .param("planId", id)
                    .param("employeeId", passenger.employeeId())
                    .param("employeeName", passenger.employeeName())
                    .param("location", passenger.location())
                    .param("address", passenger.address())
                    .param("kms", passenger.distanceInKms())
                    .param("seq", sequence++)
                    .param("managerId", "PC0001")
                    .param("managerName", "Rahim Charles")
                    .param("costCentre", passenger.costCentre())
                    .param("split", cost == null || passengers.isEmpty() ? null : cost / passengers.size())
                    .param("teamName", passenger.teamName())
                    .update();
        }
        return listPlans().stream().filter(plan -> plan.id().equals(id)).findFirst().orElseThrow();
    }

    private List<TripPassenger> passengersFor(Integer planId) {
        return jdbc.sql("""
                SELECT employee_id, employee_name, location, address, distance_in_kms, trip_sequence, cost_centre, team_name
                FROM trip_passengers
                WHERE trip_plan_id = :id
                ORDER BY trip_sequence NULLS LAST, employee_name
                """)
                .param("id", planId)
                .query((rs, rowNum) -> new TripPassenger(
                        rs.getString("employee_id"),
                        rs.getString("employee_name"),
                        rs.getString("location"),
                        rs.getString("address"),
                        (Integer) rs.getObject("distance_in_kms"),
                        (Integer) rs.getObject("trip_sequence"),
                        rs.getString("cost_centre"),
                        rs.getString("team_name")))
                .list();
    }

    private static LocalDate toDate(Date date) {
        return date == null ? null : date.toLocalDate();
    }

    private static LocalTime toTime(Time time) {
        return time == null ? null : time.toLocalTime();
    }
}
