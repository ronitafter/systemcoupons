package com.ronit.utils;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.ronit.enums.ClientType;
import com.ronit.exceptions.LoginException;
import com.ronit.services.AdminService;
import com.ronit.services.ClientService;
import com.ronit.services.CompanyService;
import com.ronit.services.CustomerService;

@Component
public class LoginManager2 {

//	@Autowired
//	TokenManager tokenManager;
//	private RemoveExpiredTokens removeExpiredTokens;

//	@Autowired
//	private ApplicationContext context;
//  clientService = applicationcontext.getBean(adminService.classe)

//	    private  CompanyService companyService;
//	    private CustomerService customerService;

	private final ApplicationContext context;
	private final AdminService adminService;
	private TokenManager tokenManager;
	private ClientService clientService;

	@Autowired

	public LoginManager2(ApplicationContext context, AdminService adminService, TokenManager tokenManager) {
		this.context = context;
		this.adminService = adminService;
		this.tokenManager = tokenManager;
	}
	
	public ClientService login(String email, String password, ClientType clientType) throws LoginException{
		
		switch(clientType) {
		case ADMINISTRATOR:
			clientService=(ClientService)adminService;
			
			break;
		case COMPANY:
			clientService=(ClientService)context.getBean(CompanyService.class);
			
			break;
		case CUSTOMER:
			clientService=(ClientService)context.getBean(CustomerService.class);
			
			break;
		}
		if (!clientService.login(email, password)) {
			throw new LoginException(clientType.name().toString() + " Unauthorized");
	}

		return clientService;
	}
	
	public void logout(String token) {
		tokenManager.removeToken(token);
	}
}


