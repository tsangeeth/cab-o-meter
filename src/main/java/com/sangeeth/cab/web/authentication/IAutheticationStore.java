package com.sangeeth.cab.web.authentication;


public interface IAutheticationStore {
	public Authentication get();
	public void store(Authentication authentication);
}
