package com.ronit.controllers;

import java.util.List;



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
import com.ronit.entities.Customer;
import com.ronit.entities.LoginRequest;
import com.ronit.entities.ResponseDto;
import com.ronit.enums.ClientType;
import com.ronit.exceptions.AuthorizationException;
import com.ronit.exceptions.CouponSystemException;
import com.ronit.exceptions.InvalidOperationException;
import com.ronit.exceptions.LoginException;
import com.ronit.job.RemoveExpiredTokens;
import com.ronit.services.AdminService;
import com.ronit.utils.LoginManager;
import com.ronit.utils.TokenManager;


@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AdminController extends ClientController {

	
	@Autowired
	private AdminService adminService;
	@Autowired
	private LoginManager loginManager;
	@Autowired
	private TokenManager tokenManager;	

//	@Autowired
//	private ResponseDto ResponseDto;

	private RemoveExpiredTokens removeExpiredTokens;


	@Autowired
	public AdminController(AdminService adminService) {
		this.adminService = adminService;

	}

	// do i need to add @Valid?
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
			loginManager.login(loginRequest.getEmail(), loginRequest.getPassword(), ClientType.ADMINISTRATOR);
//			String token = removeExpiredTokens.getNewToken();
			String token = tokenManager.generateToken(ClientType.ADMINISTRATOR).toString();
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

	


//
//	}
// ************************************ COMPANY *****************************************************************

// ___________________________________ Add Company _____________________________________________________________

	 /**
     * add a company to the system
     * @param token for validation
     * @param company
     * @return ResponseEntity
     * @throws CouponSystemException
     */
	
	
	// add company
//	@PostMapping(path = "/company", 
//			// method= RequestMethod.POST,
////		    produces = MediaType.APPLICATION_JSON_VALUE,
//		    consumes = "application/json")
//	public ResponseEntity<?> addCompany(@Valid @RequestBody Company company) {
//		try {
//			adminService.addCompany(company);
//		} catch (AddCompanyException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return new ResponseEntity(HttpStatus.CREATED);
//	}
//	
//add company - Company company
	@PostMapping("/company")
	public ResponseEntity<?> addCompany(@RequestHeader("authorization") String token, @RequestBody Company company)
			throws InvalidOperationException, CouponSystemException {
		
		try {
			adminService.addCompany(company);
//			adminService.save(company).getId();
			ResponseDto responsdto = new ResponseDto(true, "company added");
			return new ResponseEntity<>(responsdto, HttpStatus.CREATED);
			// catch (InvalidOperationException e) {
		} catch (Exception e) {
			ResponseDto responsdto = new ResponseDto(false, e.getMessage());
			return new ResponseEntity<>(responsdto, HttpStatus.BAD_REQUEST);
		}

	}
//		return new ResponseEntity<>(new ResponseDto(true. adminService
//				.addCompany(company).toString(), (HttpStatus.CREATED));
//		
//		try {
//			adminService.addCompany(company);
//			return new ResponseEntity<>(HttpStatus.CREATED);
//
//		} catch (CouponSystemException e) {
//			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//
//		}

// ___________________________________ Update Company ___________________________________________________________
	

	// @Valid?
	@PutMapping("/company")
	public ResponseEntity<?> updateCompany(@RequestHeader("authorization") String token, @RequestBody Company company)
			throws InvalidOperationException, CouponSystemException {
		try {
		adminService.updateCompany(company);
		}catch (CouponSystemException e) {
			e.printStackTrace();
		}
//		return ResponseEntity.ok(company);
		ResponseDto responseDto = new ResponseDto(true, "updated Company successfully");
		return new ResponseEntity<>(responseDto, HttpStatus.OK);
		// return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);


	}

// ___________________________________ Delete Company ____________________________________________________________	
	@DeleteMapping("/company/{id}")
	public ResponseEntity<?> deleteCompany(@RequestHeader("authorization") String token, @PathVariable("id") int id)
			throws CouponSystemException {

		try {
			adminService.deleteCompany(id);
//			return ResponseEntity.ok().build();
			return new ResponseEntity<>("company deleted", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("company not found", HttpStatus.NOT_FOUND);
		}
	}

// ___________________________________ Get All Companies _________________________________________________________
	@GetMapping("/company")
	public List<Company> getAllCompanies(@RequestHeader("authorization") String token) {
		return adminService.getAllCompanies();
//			return new CustomerList(storeService.getAllCustomers());

	}

// ___________________________________ Get One Company ___________________________________________________________

	// get one company
	@GetMapping("/company/{companyId}")
	public ResponseEntity<?> getCompanyById(@RequestHeader("authorization") String token, @PathVariable int companyId) {
		try {
			Company company = adminService.getOneCompany(companyId);
			// Company company = adminService.getCompanyById(companyId);
			return new ResponseEntity<>(company, HttpStatus.OK);
		} catch (CouponSystemException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

// ************************************ CUSTOMER *****************************************************************

// ___________________________________ Add Customer _____________________________________________________________
	//@Valid?
	@PostMapping("/customer")
	// public Long addCustomer(@RequestBody Customer customer) {
	public ResponseEntity<?> addCustomer(@RequestHeader("authorization") String token, @RequestBody Customer customer) {
		try {
			adminService.addCustomer(customer);
		} catch (CouponSystemException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

// ___________________________________ Update Customer ____________________________________________________________	
	//@Valid 
	@PutMapping("/customer")
	public ResponseEntity<?> updateCustomer(@RequestHeader("authorization") String token,
			@RequestBody Customer customer) throws CouponSystemException {
		adminService.updateCustomer(customer);
		return ResponseEntity.ok(customer);
	}

// ___________________________________ Delete Customer ____________________________________________________________	
	//@Valid 
	@DeleteMapping("/customers/{customerId}")
	public ResponseEntity<?> deleteCustomer(@RequestHeader("authorization") String token,
			@PathVariable int customerId) {
		try {
			adminService.deleteCustomer(customerId);
//			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (CouponSystemException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}		
		return new ResponseEntity<>(HttpStatus.ACCEPTED);

	}

// ___________________________________ getAllCustomers ____________________________________________________________

	@GetMapping("/customer")
	public List<Customer> getAllCustomers(@RequestHeader("authorization") String token) throws AuthorizationException {
		if (tokenManager.isTokenExists(token)) {
			List<Customer> customers = adminService.getAllCustomers();
//		return adminServic e.getAllCustomers();
			return customers;
			// return new customerList(customers)
		}

		throw new AuthorizationException("user not authorized");
	}

// ___________________________________ Get One Customer ___________________________________________________________
	@GetMapping("/customer/{id}")
	//	public ResponseEntity<?> getOneCustomer(@RequestHeader("authorization") String token,
	public ResponseEntity<?> getOneCustomer(@PathVariable("id") Integer id) throws CouponSystemException {
		try {
			Customer customer = adminService.getOneCustomer(id);
			return new ResponseEntity<>(customer, HttpStatus.OK);
		} catch (CouponSystemException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}

	}

}
