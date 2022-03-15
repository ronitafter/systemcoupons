package com.ronit.utils;

public class AuthenticationRequest {

/*
	The JSON input request provided by the user, will be 
	unmarshalled to Java Object using this class
  
  */

	private String username;
	private String password;

	public AuthenticationRequest(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public AuthenticationRequest() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


}
