package com.sangeeth.cab.requests;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class CabRequestRepository {

    private static final String SELECT_FIELDS = """
            cr.id, cr.employee_id, e.emp_id, e.first_name, e.last_name, e.cost_centre, e.team_name,
            cr.create_date, cr.update_date, cr.created_by, cr.start_date, cr.login_time, cr.end_date, cr.logout_time,
            cr.req_reason, cr.isapproved, cr.approver_id, cr.appr_reason, cr.reocurr_days,
            EXISTS (SELECT 1 FROM request_cancellation rc WHERE rc.request_id = cr.id) AS cancelled
            """;

    private final JdbcClient jdbc;

    public CabRequestRepository(JdbcClient jdbc) {
        this.jdbc = jdbc;
    }

    public CabRequest create(Integer employeeDbId, String createdBy, LocalDate startDate, LocalTime loginTime,
            LocalDate endDate, LocalTime logoutTime, String reason, String reoccurDays) {
        Integer id = jdbc.sql("""
                INSERT INTO cab_request (employee_id, created_by, start_date, login_time, end_date, logout_time, req_reason, reocurr_days)
                VALUES (:employeeId, :createdBy, :startDate, :loginTime, :endDate, :logoutTime, :reason, :reoccurDays)
                RETURNING id
                """)
                .param("employeeId", employeeDbId)
                .param("createdBy", createdBy)
                .param("startDate", startDate)
                .param("loginTime", loginTime)
                .param("endDate", endDate)
                .param("logoutTime", logoutTime)
                .param("reason", reason)
                .param("reoccurDays", reoccurDays)
                .query(Integer.class)
                .single();
        return findById(id).orElseThrow();
    }

    public Optional<CabRequest> findById(int id) {
        return jdbc.sql("SELECT " + SELECT_FIELDS + " FROM cab_request cr JOIN employee e ON e.id = cr.employee_id WHERE cr.id = :id")
                .param("id", id)
                .query(CabRequestRepository::mapRow)
                .optional();
    }

    public List<CabRequest> findForEmployee(Integer employeeDbId, LocalDate start, LocalDate end) {
        return jdbc.sql("""
                        SELECT %s FROM cab_request cr
                        JOIN employee e ON e.id = cr.employee_id
                        WHERE cr.employee_id = :employeeId
                          AND (:startDate IS NULL OR cr.end_date >= :startDate)
                          AND (:endDate IS NULL OR cr.start_date <= :endDate)
                        ORDER BY cr.start_date DESC, cr.id DESC
                        """.formatted(SELECT_FIELDS))
                .param("employeeId", employeeDbId)
                .param("startDate", start)
                .param("endDate", end)
                .query(CabRequestRepository::mapRow)
                .list();
    }

    public List<CabRequest> findForManager(String managerEmpId, String status, LocalDate start, LocalDate end) {
        String approvalClause = switch (status == null ? "pending" : status) {
            case "approved" -> "cr.isapproved = 'Y'";
            case "declined" -> "cr.isapproved = 'N'";
            default -> "cr.isapproved IS NULL";
        };
        return jdbc.sql("""
                        SELECT %s FROM cab_request cr
                        JOIN employee e ON e.id = cr.employee_id
                        WHERE e.manager_id = :managerId
                          AND %s
                          AND (:startDate IS NULL OR cr.end_date >= :startDate)
                          AND (:endDate IS NULL OR cr.start_date <= :endDate)
                        ORDER BY cr.create_date DESC
                        """.formatted(SELECT_FIELDS, approvalClause))
                .param("managerId", managerEmpId)
                .param("startDate", start)
                .param("endDate", end)
                .query(CabRequestRepository::mapRow)
                .list();
    }

    public List<CabRequest> searchTrips(LocalDate start, LocalDate end, String employeeId, String department, String location) {
        return jdbc.sql("""
                        SELECT %s FROM cab_request cr
                        JOIN employee e ON e.id = cr.employee_id
                        LEFT JOIN address a ON a.employee_id = e.id
                        WHERE cr.isapproved = 'Y'
                          AND NOT EXISTS (SELECT 1 FROM request_cancellation rc WHERE rc.request_id = cr.id)
                          AND (:startDate IS NULL OR cr.end_date >= :startDate)
                          AND (:endDate IS NULL OR cr.start_date <= :endDate)
                          AND (:employeeId IS NULL OR e.emp_id ILIKE :employeeId)
                          AND (:department IS NULL OR e.team_name ILIKE :department OR e.cost_centre ILIKE :department)
                          AND (:location IS NULL OR COALESCE(a.locality, '') ILIKE :location OR COALESCE(a.city, '') ILIKE :location)
                        ORDER BY cr.start_date, e.last_name
                        """.formatted(SELECT_FIELDS))
                .param("startDate", start)
                .param("endDate", end)
                .param("employeeId", blankToLike(employeeId))
                .param("department", blankToLike(department))
                .param("location", blankToLike(location))
                .query(CabRequestRepository::mapRow)
                .list();
    }

    public void decide(int requestId, String approverId, boolean approved, String reason) {
        jdbc.sql("""
                UPDATE cab_request
                SET isapproved = :flag, approver_id = :approverId, appr_reason = :reason, update_date = NOW()
                WHERE id = :id
                """)
                .param("flag", approved ? "Y" : "N")
                .param("approverId", approverId)
                .param("reason", reason)
                .param("id", requestId)
                .update();
    }

    public void cancel(int requestId, Integer employeeDbId, String cancelledBy, String reason) {
        jdbc.sql("""
                INSERT INTO request_cancellation (request_id, employee_id, cancelled_by, canc_reason, iscomp_req_cancelled)
                VALUES (:requestId, :employeeId, :cancelledBy, :reason, 'Y')
                """)
                .param("requestId", requestId)
                .param("employeeId", employeeDbId)
                .param("cancelledBy", cancelledBy)
                .param("reason", reason)
                .update();
    }

    private static String blankToLike(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return "%" + value.strip() + "%";
    }

    static CabRequest mapRow(ResultSet rs, int rowNum) throws SQLException {
        Timestamp created = rs.getTimestamp("create_date");
        Timestamp updated = rs.getTimestamp("update_date");
        Date start = rs.getDate("start_date");
        Date end = rs.getDate("end_date");
        Time login = rs.getTime("login_time");
        Time logout = rs.getTime("logout_time");
        String first = rs.getString("first_name");
        String last = rs.getString("last_name");
        return new CabRequest(
                rs.getInt("id"),
                rs.getInt("employee_id"),
                rs.getString("emp_id"),
                (first + " " + last).strip(),
                rs.getString("cost_centre"),
                rs.getString("team_name"),
                created == null ? null : created.toLocalDateTime(),
                updated == null ? null : updated.toLocalDateTime(),
                rs.getString("created_by"),
                start.toLocalDate(),
                login.toLocalTime(),
                end.toLocalDate(),
                logout.toLocalTime(),
                rs.getString("req_reason"),
                CabRequest.statusFromFlag(rs.getString("isapproved")),
                rs.getString("approver_id"),
                rs.getString("appr_reason"),
                rs.getString("reocurr_days"),
                rs.getBoolean("cancelled"));
    }
}
