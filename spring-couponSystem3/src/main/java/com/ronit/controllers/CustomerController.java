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
import com.ronit.exceptions.LoginException;
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

//---------------------------------- LOGIN -----------------------------------------------
	@PostMapping("/login")
	// public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest)
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest)
			throws AuthorizationException, LoginException {
		try {
			loginManager.login(loginRequest.getEmail(), loginRequest.getPassword(), ClientType.CUSTOMER);
			String token = tokenManager.generateToken(ClientType.CUSTOMER).toString();
			return new ResponseEntity<String>(token, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

//---------------------------------- LOGOUT -----------------------------------------------	
	@GetMapping("/logout")
	public void logout(@RequestHeader("authorization") String token) {
		loginManager.logout(token);
	}

//---------------------------------- PurchaseCoupon -----------------------------------------------	
	@GetMapping("/coupon")
//public ResponseEntity<?> PurchaseCoupon(@Valid @RequestBody Coupon coupon)
	public ResponseEntity<?> PurchaseCoupon(@RequestHeader("authorization") String token, @RequestBody int couponId)
			throws AuthorizationException, CouponSystemException {
		if (tokenManager.isTokenExists(token)) {
//			List<Customer> customers  = adminService.getAllCustomers();
			customerservice.PurchaseCoupon(couponId);
			ResponseDto responsdto = new ResponseDto(true, "Purchased Coupon");
			return new ResponseEntity<>(responsdto, HttpStatus.CREATED);
//		return adminService.getAllCustomers();
			// return new customerList(customers)
		}
		throw new AuthorizationException("Purchase not authorized");
//			ResponseDto responsdto = new ResponseDto(false, e.getMessage());
//			return new ResponseEntity<>(responsdto, HttpStatus.BAD_REQUEST);
//			
	}

//---------------------------------- getAllCustomerCoupons -----------------------------------------------			
	@GetMapping("/coupon")
	// public List<Coupon> getAllCustomerCoupons(int customerId) throws
	// CouponSystemException {
	public List<Coupon> getAllCustomerCoupons(@RequestHeader("authorization") String token, int customerId)
			throws CouponSystemException, AuthorizationException {
		if (tokenManager.isTokenExists(token)) {
//			List<Customer> customers  = adminService.getAllCustomers();
			return customerservice.getAllCustomerCoupons(customerId);
//				ResponseDto responsdto = new ResponseDto(true, "getAllCustomerCoupons Coupon");
//				 new ResponseEntity<>(responsdto, HttpStatus.CREATED);
//		return adminService.getAllCustomers();
			// return new customerList(customers)
		}
		throw new AuthorizationException("Purchase not authorized");
//			ResponseDto responsdto = new ResponseDto(false, e.getMessage());
//			return new ResponseEntity<>(responsdto, HttpStatus.BAD_REQUEST);
//			
	}

//---------------------------------- get Customer Coupons By Category -----------------------------------------------				
	@GetMapping("/coupon/category")
	public List<Coupon> getCustomerCoupons(@RequestHeader("authorization") String token,
			@RequestParam("customerId") int customerId, @RequestParam("category") int category)
			throws CouponSystemException, AuthorizationException {
		if (tokenManager.isTokenExists(token)) {
			return customerservice.getCustomerCoupons(customerId, category);
		}

		throw new AuthorizationException("Purchase not authorized");
	}

//---------------------------------- get Customer Coupons By Price -----------------------------------------------				
	@GetMapping("/coupon/{maxPrice}")
	public List<Coupon> getCustomerCouponsByPrice(@RequestHeader("authorization") String token,
			@RequestParam("customerId") int customerId, @RequestParam("maxPrice") double maxPrice)
			throws CouponSystemException, AuthorizationException {
		if (tokenManager.isTokenExists(token)) {
			return customerservice.getCustomerCouponsByPrice(customerId, maxPrice);
		}

		throw new AuthorizationException("Purchase not authorized");

	}

//---------------------------------- get Customer Details -----------------------------------------------				
	@GetMapping
	public Customer getAllCustomerDetails(@RequestHeader("authorization") String token, int customerId)
			throws CouponSystemException, AuthorizationException {
		if (tokenManager.isTokenExists(token)) {
			return customerservice.getAllCustomerDetails(customerId);
		}
		throw new AuthorizationException("Purchase not authorized");

	}
}
