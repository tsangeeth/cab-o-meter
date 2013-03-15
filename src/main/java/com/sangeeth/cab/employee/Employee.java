package com.sangeeth.cab.employee;

public class Employee {



	private final Integer dbId;
	private final EmployeeId employeeId;
	private final Name name;
	private final Role role;
	private final CostCenter costCenter;
	private final String teamName;
	private final String contact;
	private final String alternateContact;
	private final String landlineNumber;
	private final Email email;
	private final Manager manager;
	private final Gender gender;

	Employee(Integer dbId, EmployeeId employeeId, Name name, Role role,
			CostCenter costCenter, String teamName, String contact,
			String alternateContact, String landlineNumber, Email email,
			Manager manager, Gender gender) {
	
		super();
		this.dbId = dbId;
		this.employeeId = employeeId;
		this.name = name;
		this.role = role;
		this.costCenter = costCenter;
		this.teamName = teamName;
		this.contact = contact;
		this.alternateContact = alternateContact;
		this.landlineNumber = landlineNumber;
		this.email = email;
		this.manager = manager;
		this.gender = gender;
	}

	public Employee(EmployeeId employeeId, Name name, Role role,
			CostCenter costCenter, String teamName, String contact,
			String alternateContact, String landlineNumber, Email email,
			Manager manager, Gender gender) {
		this(null, employeeId, name, role,costCenter, teamName, contact, alternateContact, landlineNumber, email, manager, gender);
	}
	


	public EmployeeId getEmployeeId() {
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

	public Manager getManager() {
		return manager;
	}

	public Gender getGender() {
		return gender;
	}

	Integer getDbId() {
		return dbId;
	}

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((alternateContact == null) ? 0 : alternateContact.hashCode());
		result = prime * result + ((contact == null) ? 0 : contact.hashCode());
		result = prime * result
				+ ((costCenter == null) ? 0 : costCenter.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result
				+ ((employeeId == null) ? 0 : employeeId.hashCode());
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result
				+ ((landlineNumber == null) ? 0 : landlineNumber.hashCode());
		result = prime * result + ((manager == null) ? 0 : manager.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		result = prime * result
				+ ((teamName == null) ? 0 : teamName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		if (alternateContact == null) {
			if (other.alternateContact != null)
				return false;
		} else if (!alternateContact.equals(other.alternateContact))
			return false;
		if (contact == null) {
			if (other.contact != null)
				return false;
		} else if (!contact.equals(other.contact))
			return false;
		if (costCenter == null) {
			if (other.costCenter != null)
				return false;
		} else if (!costCenter.equals(other.costCenter))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (employeeId == null) {
			if (other.employeeId != null)
				return false;
		} else if (!employeeId.equals(other.employeeId))
			return false;
		if (gender != other.gender)
			return false;
		if (landlineNumber == null) {
			if (other.landlineNumber != null)
				return false;
		} else if (!landlineNumber.equals(other.landlineNumber))
			return false;
		if (manager == null) {
			if (other.manager != null)
				return false;
		} else if (!manager.equals(other.manager))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (role != other.role)
			return false;
		if (teamName == null) {
			if (other.teamName != null)
				return false;
		} else if (!teamName.equals(other.teamName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Employee [dbId=" + dbId + ", employeeId=" + employeeId
				+ ", name=" + name + ", role=" + role + ", costCenter="
				+ costCenter + ", teamName=" + teamName + ", contact="
				+ contact + ", alternateContact=" + alternateContact
				+ ", landlineNumber=" + landlineNumber + ", email=" + email
				+ ", manager=" + manager + ", gender=" + gender + "]";
	}
	
	
}
