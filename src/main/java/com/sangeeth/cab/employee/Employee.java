package com.sangeeth.cab.employee;

public record Employee(
        Integer dbId,
        String employeeId,
        Name name,
        Role role,
        CostCenter costCenter,
        String teamName,
        String contact,
        String alternateContact,
        String landlineNumber,
        Email email,
        String managerId,
        Gender gender
) {
    public Employee(
            String employeeId,
            Name name,
            Role role,
            CostCenter costCenter,
            String teamName,
            String contact,
            String alternateContact,
            String landlineNumber,
            Email email,
            String managerId,
            Gender gender) {
        this(null, employeeId, name, role, costCenter, teamName, contact, alternateContact, landlineNumber, email, managerId, gender);
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public Name getName() {
        return name;
    }

    public Role getRole() {
        return role;
    }

    public CostCenter getCostCenter() {
        return costCenter;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getContactNumber() {
        return contact;
    }

    public String getAlternateContactNumber() {
        return alternateContact;
    }

    public String getLandlineNumber() {
        return landlineNumber;
    }

    public Email getEmail() {
        return email;
    }

    public String getManagerId() {
        return managerId;
    }

    public Gender getGender() {
        return gender;
    }

    Integer getDbId() {
        return dbId;
    }
}
