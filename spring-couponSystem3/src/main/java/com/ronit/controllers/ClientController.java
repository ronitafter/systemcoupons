package com.ronit.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.ronit.services.AdminService;
import com.ronit.services.CompanyService;
import com.ronit.services.CustomerService;
import com.ronit.utils.LoginManager;

@RestController
public class ClientController {

	// TODO Auto-generated constructor stub
	@Autowired
	protected LoginManager loginManager;
//
//	@Autowired
//	protected TokenManager TokenManager;
@Autowired
private AdminService adminService;
private CompanyService companyService;
private CustomerService customerService;


public boolean login() {
	return false;
}
}
	