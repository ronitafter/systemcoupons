package com.ronit.entities;

import javax.persistence.Entity;

import com.ronit.enums.ClientType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//@Data
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@ToString
//@Entity
public class LoginRequest {

	private int id;
	private String email;
	private String password;
	
	//do i need another constructor?
	public LoginRequest(int id, String email) {
		this.id = id;
		this.email = email;
	}



	public LoginRequest() {
		super();
	}

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}



	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}



	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}



	@Override
	public String toString() {
		return "LoginRequest [id=" + id + ",  email=" + email + ", password=" + password
				+ "]";
	}
	
	
	


}
