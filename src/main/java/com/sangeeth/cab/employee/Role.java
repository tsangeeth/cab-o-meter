package com.sangeeth.cab.employee;

public enum Role {
	
	EMPLOYEE("employee"), MANAGER("manager"), TRIP_MANAGER("trip_manager");

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
		case "employee": return EMPLOYEE;
		case "manager": return MANAGER;
		case "trip_manager": return TRIP_MANAGER;
		default: throw new RuntimeException("Invalid Role:" + value);
		}
	}
	
	
	
}
