package com.sangeeth.cab.contract;


public class UserInformation {
	private final String employeeId;
	private final String firstName;
	private final String lastName;
	private final String middleName;
	private final String role;
	private final String costCentre;
	private final Address address;
	public UserInformation(String employeeId, String firstName,
			String lastName, String middleName, String role, String costCentre, Address address) {
		super();
		this.employeeId = employeeId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.middleName = middleName;
		this.role = role;
		this.costCentre = costCentre;
		this.address = address;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public String getRole() {
		return role;
	}
	public String getCostCentre() {
		return costCentre;
	}
	public Address getAddress() {
		return address;
	}
	
	
}
