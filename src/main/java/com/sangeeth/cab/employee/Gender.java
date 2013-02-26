package com.sangeeth.cab.employee;

public enum Gender {
	MALE('M'), FEMALE('F');
	
	private final Character value;

	private Gender(Character value){
		this.value = value;
	}
	
	public String value(){
		return value.toString();
	}

	@Override
	public String toString() {
		return value.toString();
	}
	
	public static Gender convert(String value){
		switch(value.charAt(0)){
		case 'M': return MALE;
		case 'F': return FEMALE;
		default:throw new RuntimeException("invalid gender:" + value);
		}
	}
}
