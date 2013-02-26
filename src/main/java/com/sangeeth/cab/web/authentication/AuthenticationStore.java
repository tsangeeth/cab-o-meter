package com.sangeeth.cab.web.authentication;

public class AuthenticationStore implements IAutheticationStore {
	

	private Authentication authentication;

	@Override
	public Authentication get() {
		return authentication;
	}

	@Override
	public void store(Authentication authentication) {
		this.authentication = authentication;
	}

}
