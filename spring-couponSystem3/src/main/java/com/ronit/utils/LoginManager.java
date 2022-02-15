package com.ronit.utils;

import org.springframework.beans.factory.annotation.Autowired;



import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.ronit.enums.ClientType;
import com.ronit.exceptions.AuthorizationException;
import com.ronit.exceptions.CouponSystemException;
import com.ronit.job.RemoveExpiredTokens;
import com.ronit.services.AdminService;
import com.ronit.services.ClientService;
import com.ronit.services.CompanyService;
import com.ronit.services.CustomerService;

@Component
public class LoginManager {
	
	@Autowired
	TokenManager tokenManager;
	private RemoveExpiredTokens removeExpiredTokens;

	@Autowired
	private ApplicationContext context;
//  clientService = applicationcontext.getBean(adminService.classe)

	

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
	
	public ClientService login(String email, String passwaord, ClientType clientType) throws CouponSystemException {
		ClientService clientService = null;
		switch (clientType) {
		case COMPANY:
			 clientService = context.getBean(CompanyService.class);
//			 return tokenManager.generateToken(clientType);
//			String token = tokenManager.generateToken(clientType);

			 break;
		case CUSTOMER:
			clientService = context.getBean(CustomerService.class);
			String token2 = tokenManager.generateToken(clientType);

			break;
		case ADMINISTRATOR:
			clientService = context.getBean(AdminService.class);
			String token3 = tokenManager.generateToken(clientType);
			break;
		}

		if (clientService.login(email, passwaord)) {
			return clientService;
		} else {
			throw new CouponSystemException("login failed");
		}
	}

}
