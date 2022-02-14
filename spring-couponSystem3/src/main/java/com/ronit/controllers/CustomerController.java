package com.ronit.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ronit.entities.Coupon;
import com.ronit.entities.Customer;
import com.ronit.entities.LoginRequest;
import com.ronit.entities.ResponseDto;
import com.ronit.enums.ClientType;
import com.ronit.exceptions.AuthorizationException;
import com.ronit.exceptions.CouponSystemException;
import com.ronit.job.RemoveExpiredTokens;
import com.ronit.services.CompanyService;
import com.ronit.services.CustomerService;
import com.ronit.utils.TokenManager;

@RestController
@RequestMapping("/customer")
public class CustomerController extends ClientController {
	

	@Autowired
	private CustomerService customerservice;
	
	private TokenManager tokenManager;
	private RemoveExpiredTokens removeExpiredTokens;


	@Autowired
	public CustomerController(CustomerService customerService) {
		this.customerservice = customerService;
	}
	
	

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest)
			throws AuthorizationException, CouponSystemException {
		// return adminService.login(loggedin.getEmail(), loggedin.getPassword());
//		return loginManager.login(loginRequest.getEmail(), loginRequest.getPassword(), ClientType.ADMINISTRATOR);		//1- try to login
		try {
			loginRequest.getClientType();
			customerservice = (CustomerService) loginManager.login(loginRequest.getEmail(), loginRequest.getPassword(),
					ClientType.CUSTOMER);
			String token = removeExpiredTokens.getNewToken();
			return new ResponseEntity<String>(token, HttpStatus.OK);
		} catch (Exception e) {
			// else -> return failure string "Fail to login"
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		// if login succeed -> return TOKEN
		// generate token here!
		// TokenManager.getNewToken()

	}

	@GetMapping("/logout")
	public void logout(@RequestHeader("authorization") String token) {
		loginManager.logout(token);
	}
	
	@PostMapping
	//void
	public  ResponseEntity<?> PurchaseCoupon(@RequestHeader("authorization") String token, @RequestBody int couponId) throws AuthorizationException, CouponSystemException {
		if (tokenManager.isTokenExists(token)) {
//			List<Customer> customers  = adminService.getAllCustomers();
				customerservice.PurchaseCoupon(couponId);			
				ResponseDto responsdto = new ResponseDto(true, "Purchased Coupon");
				return new ResponseEntity<>(responsdto, HttpStatus.CREATED);
//		return adminService.getAllCustomers();
			//return new customerList(customers)		
		}
		throw new AuthorizationException("Purchase not authorized");		
//			ResponseDto responsdto = new ResponseDto(false, e.getMessage());
//			return new ResponseEntity<>(responsdto, HttpStatus.BAD_REQUEST);
//			
		}	
		
	@GetMapping
	//	public List<Coupon> getAllCustomerCoupons(int customerId) throws CouponSystemException {
	public List<Coupon> getAllCustomerCoupons(@RequestHeader("authorization")String token, int customerId) throws CouponSystemException, AuthorizationException {
		if (tokenManager.isTokenExists(token)) {
//			List<Customer> customers  = adminService.getAllCustomers();
			return customerservice.getAllCustomerCoupons(customerId);
//				ResponseDto responsdto = new ResponseDto(true, "getAllCustomerCoupons Coupon");
//				 new ResponseEntity<>(responsdto, HttpStatus.CREATED);
//		return adminService.getAllCustomers();
			//return new customerList(customers)		
		}
		throw new AuthorizationException("Purchase not authorized");		
//			ResponseDto responsdto = new ResponseDto(false, e.getMessage());
//			return new ResponseEntity<>(responsdto, HttpStatus.BAD_REQUEST);
//			
		}	
	
	
	@GetMapping
	public List<Coupon> getCustomerCoupons(@RequestHeader("authorization")String token, @RequestParam("customerId") int customerId, @RequestParam("category") int category) throws CouponSystemException, AuthorizationException {
		if (tokenManager.isTokenExists(token)) {
		return customerservice.getCustomerCoupons(customerId, category);
	}
	
		throw new AuthorizationException("Purchase not authorized");	
	}

	@GetMapping
	public List<Coupon> getCustomerCouponsByPrice(@RequestHeader("authorization")String token, @RequestParam("customerId") int customerId, @RequestParam("maxPrice") double maxPrice) throws CouponSystemException, AuthorizationException {
		if (tokenManager.isTokenExists(token)) {
		return customerservice.getCustomerCouponsByPrice(customerId, maxPrice);
	}
		
		throw new AuthorizationException("Purchase not authorized");	

	}
	
	@GetMapping
	public Customer getAllCustomerDetails(@RequestHeader("authorization")String token, int customerId) throws CouponSystemException, AuthorizationException {
		if (tokenManager.isTokenExists(token)) {
		return customerservice.getAllCustomerDetails(customerId);
}
		throw new AuthorizationException("Purchase not authorized");	

}
}

