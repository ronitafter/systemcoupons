package com.ronit.utils;

public class AuthenticationResponse {

	/*
	 * 
	  this class which is a model class to return the token 
	  on successful authentication	  
	 
	 * */
	private String token;

	public AuthenticationResponse(String token) {
		this.token = token;
	}

	public AuthenticationResponse() {
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}


}
