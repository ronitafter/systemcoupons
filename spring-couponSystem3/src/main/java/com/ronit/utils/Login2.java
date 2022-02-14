package com.ronit.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.ronit.enums.ClientType;
import com.ronit.exceptions.AuthorizationException;
import com.ronit.exceptions.CouponSystemException;
import com.ronit.job.RemoveExpiredTokens;
import com.ronit.services.AdminService;
import com.ronit.services.ClientService;
import com.ronit.services.CompanyService;
import com.ronit.services.CustomerService;
import com.ronit.services.CustomerServie;

public class Login2 {

	@Autowired
	TokenManager tokenManager;
	private RemoveExpiredTokens removeExpiredTokens;

	@Autowired
	private ApplicationContext context;
//  clientService = applicationcontext.getBean(adminService.classe)

	  private  AdminService adminService;
	    private  CompanyService companyService;
	    private CustomerService customerService;

//	public String Login(String email, String passwaord, ClientType type ) throws AuthorizationException{
//		if(email.equals("admin") && passwaord.equals("this.password")) {
//			return tokenManager.generateToken(type);
//			
//		}
//		throw new AuthorizationException("user not authorized to log in");				
//	}
//	
	public void logout(String token) {
		tokenManager.removeToken(token);
	}
	
	public String login(String email, String passwaord, ClientType clientType) throws CouponSystemException, AuthorizationException {
		ClientService clientService = null;
		switch (clientType) {
		case COMPANY:
			if(companyService.login(email, passwaord)) {
				return tokenManager.generateToken(clientType);		
			}else {
				throw new AuthorizationException("company not Authorized");
			}
		case CUSTOMER:
			if(customerService.login(email, passwaord)) {
				return tokenManager.generateToken(clientType);		
			}else {
				throw new AuthorizationException("customer not Authorized");
			}
		case ADMINISTRATOR:
			if(adminService.login(email, passwaord)) {
				return tokenManager.generateToken(clientType);		
			}else {
				throw new AuthorizationException("admin not Authorized");
			}
		}
		// ????
//		if (login(email, passwaord, clientType) != null) {
			throw new CouponSystemException("login failed");
		}
//		return passwaord;
	}
//}

