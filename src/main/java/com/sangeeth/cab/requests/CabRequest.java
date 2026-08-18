package com.sangeeth.cab.requests;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record CabRequest(
        Integer id,
        Integer employeeDbId,
        String employeeId,
        String employeeName,
        String costCentre,
        String teamName,
        LocalDateTime createDate,
        LocalDateTime updateDate,
        String createdBy,
        LocalDate startDate,
        LocalTime loginTime,
        LocalDate endDate,
        LocalTime logoutTime,
        String reason,
        String status,
        String approverId,
        String approvalReason,
        String reoccurDays,
        boolean cancelled) {

    public static String statusFromFlag(String isApproved) {
        if (isApproved == null || isApproved.isBlank()) {
            return "pending";
        }
        return switch (isApproved) {
            case "Y" -> "approved";
            case "N" -> "declined";
            default -> "pending";
        };
    }
}
