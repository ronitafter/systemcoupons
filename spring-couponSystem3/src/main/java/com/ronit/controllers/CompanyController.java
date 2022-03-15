package com.ronit.controllers;

import java.util.List;



import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ronit.entities.Company;
import com.ronit.entities.Coupon;
import com.ronit.entities.LoginRequest;
import com.ronit.entities.ResponseDto;
import com.ronit.enums.ClientType;
import com.ronit.exceptions.AuthorizationException;
import com.ronit.exceptions.CouponSystemException;
import com.ronit.job.RemoveExpiredTokens;
import com.ronit.services.CompanyService;
import com.ronit.utils.LoginManager;
import com.ronit.utils.TokenManager;
import com.ronit.exceptions.LoginException;


@RestController
@RequestMapping("/company")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CompanyController extends ClientController {

	@Autowired
	private LoginManager loginManager;

	@Autowired
	private CompanyService companyService;

	private TokenManager tokenManager;

	private RemoveExpiredTokens removeExpiredTokens;



	@PostMapping("/login")
	//	public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest)
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest)
			throws AuthorizationException, LoginException {
		// return adminService.login(loggedin.getEmail(), loggedin.getPassword());
//		return loginManager.login(loginRequest.getEmail(), loginRequest.getPassword(), ClientType.ADMINISTRATOR);		//1- try to login
		try {
			
//			loginRequest.getClientType();
//			adminService = (AdminService) loginManager.login(loginRequest.getEmail(), loginRequest.getPassword(),
//					ClientType.ADMINISTRATOR);
			loginManager.login(loginRequest.getEmail(), loginRequest.getPassword(), ClientType.COMPANY);
//			String token = removeExpiredTokens.getNewToken();
			String token = tokenManager.generateToken(ClientType.COMPANY).toString();
			return new ResponseEntity<String>(token, HttpStatus.OK);
		} catch (Exception e) {
			// else -> return failure string "Fail to login"
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	
	@GetMapping("/logout")
	public void logout(@RequestHeader("authorization") String token) {
		loginManager.logout(token);
	}

	

//	@PostMapping("/login")
//	public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest)
//			throws AuthorizationException, CouponSystemException, LoginException {
//		// return adminService.login(loggedin.getEmail(), loggedin.getPassword());
////		return loginManager.login(loginRequest.getEmail(), loginRequest.getPassword(), ClientType.ADMINISTRATOR);		//1- try to login
//		try {
////			loginRequest.getClientType();
////			companyService = (CompanyService) loginManager.login(loginRequest.getEmail(), loginRequest.getPassword(),
////					ClientType.COMPANY);
//			loginManager.login(loginRequest.getEmail(), loginRequest.getPassword(), loginRequest.getClientType());
//			String token = removeExpiredTokens.getNewToken();
//			return new ResponseEntity<>(token, HttpStatus.OK);
//		} catch (Exception e) {
//			// else -> return failure string "Fail to login"
//			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
//		}
//	}
//
//	@GetMapping("/logout")
//	public void logout(@RequestHeader("authorization") String token) {
//		loginManager.logout(token);
//	}
// ----------------------addCoupon------------------
	@PostMapping("/coupon")
	public ResponseEntity<?> addCoupon(@RequestHeader("authorization") String token, @RequestBody Coupon coupon)
			throws AuthorizationException, CouponSystemException {
//		try {
//			companyService.addCoupon(coupon);
//			ResponseDto responsdto = new ResponseDto(true, "coupon added");
//			return new ResponseEntity<>(responsdto, HttpStatus.CREATED);
//
//		} catch (CouponSystemException e) {
//			ResponseDto responsdto = new ResponseDto(false, e.getMessage());
//			return new ResponseEntity<>(responsdto, HttpStatus.BAD_REQUEST);
////			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//		}

		if (tokenManager.isTokenExists(token)) {
			companyService.addCoupon(coupon, companyService.getCompanyId());
			ResponseDto responsdto = new ResponseDto(true, "coupon added");
			return new ResponseEntity<>(responsdto, HttpStatus.CREATED);
//			Integer id = testRepository.addCoupon(coupon);
//			return new ResponseEntity<Integer>(id, HttpStatus.OK);
		} else {
			throw new AuthorizationException("user not authorized");
//			ResponseDto responsdto = new ResponseDto(false, "coupon not added");
//			return new ResponseEntity<>(responsdto, HttpStatus.BAD_REQUEST);

		}
	}

// ----------------------UpdateCoupon------------------

	//@Valid 
	@PutMapping("/coupon")
	public ResponseEntity<?> UpdateCoupon(@RequestHeader("authorization") String token, @RequestBody Coupon coupon)
			throws CouponSystemException, AuthorizationException {
		if (tokenManager.isTokenExists(token)) {
			companyService.UpdateCoupon(coupon);
		} else {
			throw new AuthorizationException("company not authorized");

//		ResponseDto responsdto = new ResponseDto(false, "coupon not added");
//		return new ResponseEntity<>(responsdto, HttpStatus.BAD_REQUEST);
		}
		return ResponseEntity.ok(coupon);
	}
	
// ----------------------deleteCoupon------------------
	//@Valid
	@DeleteMapping("/coupon/{id}")
	public ResponseEntity<?> deleteCoupon(@RequestHeader("authorization") String token,
			@PathVariable("couponId") int couponId, @PathVariable("companyId") int companyId)
			throws CouponSystemException, AuthorizationException {

		if (tokenManager.isTokenExists(token)) {
			companyService.deleteCoupon(couponId, companyId);
			;
		} else {
			throw new AuthorizationException("company not authorized");
//			ResponseDto responsdto = new ResponseDto(false, "coupon not added");
//			return new ResponseEntity<>(responsdto, HttpStatus.BAD_REQUEST);
		}
		return ResponseEntity.ok().build();
	}
//		try {
//			companyService.deleteCoupon(couponId, companyId);
////				return ResponseEntity.ok().build();
//			return new ResponseEntity<>("coupon deleted", HttpStatus.OK);
//		} catch (Exception e) {
//			return new ResponseEntity<>("coupon not found", HttpStatus.NOT_FOUND);
//		}
//	}

// ----------------------getCompanyCouponsbycategory------------------
	//@Valid 
	@GetMapping("/coupon/category")
	//	public List<Coupon> getAllCompanies(@Valid @RequestParam Category category)
	public List<Coupon> getCompanyCoupons(@RequestHeader("authorization") String token, @PathVariable int categoryId)
			throws CouponSystemException, AuthorizationException {
		if (tokenManager.isTokenExists(token)) {
		return companyService.getCompanyCoupons(categoryId);
		}else {
			throw new AuthorizationException("company not authorized");
		}
	}

	
// ----------------------getCompanyCouponsByPrice------------------
	@GetMapping("/coupon/{maxPrice}")
	//	public ArrayList<Coupon> getAllCompanies(@Valid @PathVariable("maxPrice") int maxPrice)
	public List<Coupon> getCompanyCouponsByPrice(@RequestHeader("authorization") String token, double maxPrice)
			throws CouponSystemException, AuthorizationException {
		if (tokenManager.isTokenExists(token)) {
			return companyService.getCompanyCouponsByPrice(maxPrice);

		} else {
			throw new AuthorizationException("company not authorized");

		}
	}
// ----------------------getAllCompanyCoupons------------------
	@GetMapping("/company/coupons")
	public List<Coupon> getAllCompanyCoupons(@RequestHeader("authorization") String token)
			throws CouponSystemException, AuthorizationException {
		if (tokenManager.isTokenExists(token)) {
			return companyService.getAllCompanyCoupons();
		} else {
			throw new AuthorizationException("company not authorized");

		}

	}
	
// ----------------------getCompanyDetails------------------
	@GetMapping("/company")
	public Company getCompanyDetails(@RequestHeader("authorization") String token, int comanyId)
			throws CouponSystemException, AuthorizationException {
		if (tokenManager.isTokenExists(token)) {
			return companyService.getCompanyDetails(comanyId);
		} else {
			throw new AuthorizationException("company not authorized");

		}
	}
}
