package com.sangeeth.cab.employee;

public enum Role {
	
	EMPLOYEE("Employee"), MANAGER("Manager"), TRIP_MANAGER("TripManager");

	private final String value;

	private Role(String value){
		this.value = value;
	}
	
	public String value(){
		return value;
	}
	
	@Override
	public String toString() {
		return value;
	}

	public static Role convert(String value) {
		switch(value){
		case "Employee": return EMPLOYEE;
		case "Manager": return MANAGER;
		case "TripManager": return TRIP_MANAGER;
		default: throw new RuntimeException("Invalid Role:" + value);
		}
	}
	
	
	
}
