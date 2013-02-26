package com.sangeeth.cab.web.authentication;

import com.sangeeth.cab.contract.User;


public class Authentication {
	
	

	private final User user;

	public Authentication(User user) {
		this.user = user;
	}

	public User user() {
		return user;
	}

}
