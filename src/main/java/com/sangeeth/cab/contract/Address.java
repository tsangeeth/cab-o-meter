package com.sangeeth.cab.contract;

public class Address {

	private final String line1;
	private final String line2;
	private final String locality;
	private final String city;
	private final String state;
	private final String landmark;
	public Address(String line1, String line2, String locality, String city,
			String state, String landmark) {
		super();
		this.line1 = line1;
		this.line2 = line2;
		this.locality = locality;
		this.city = city;
		this.state = state;
		this.landmark = landmark;
	}
	public String getLine1() {
		return line1;
	}
	public String getLine2() {
		return line2;
	}
	public String getLocality() {
		return locality;
	}
	public String getCity() {
		return city;
	}
	public String getState() {
		return state;
	}
	public String getLandmark() {
		return landmark;
	}
	
	
}
