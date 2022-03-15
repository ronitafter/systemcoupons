package com.ronit.test;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.ronit.controllers.AdminController;
import com.ronit.entities.Company;
import com.ronit.entities.Coupon;
import com.ronit.entities.Customer;
import com.ronit.enums.Category;
import com.ronit.enums.ClientType;
import com.ronit.exceptions.CouponSystemException;
import com.ronit.services.AdminService;
import com.ronit.utils.LoginManager;

@Component
public class AdminTester3 implements CommandLineRunner {
	int companyID;

	@Autowired
	private ApplicationContext context;

	@Override
	public void run(String... args) throws Exception {
		startAdminTest();
	}
	public void startAdminTest() {
		LoginManager loginManager = context.getBean(LoginManager.class);
		try {
			AdminService adminService = (AdminService) loginManager.login("admin@admin.com", "admin",
					ClientType.ADMINISTRATOR);
			
// -------------- COMPANY TESTING --------------
//deleteCompany
//			deleteCompany(adminService);			
//			printCompanies(adminService);
//createCompany:
//			createCompany(adminService);
//updateCompany:
//			updateCompany(adminService);
			
//getOneCompany
//			Company c = getOneCompany(adminService);
//			System.out.println(c);
//getAllCompanies:
//			List<Company> companies = adminService.getAllCompanies();
//			System.out.println(companies);
// -------------- CUSTOMER TESTING --------------
//ddCustomer:
//			addCustomer(adminService);
//updateCustomer:
//			updateCustomer(adminService);
//deleteCustomer:
//			deleteCustomer(adminService);
//getOneCustomer:
//			Customer c = getOneCustomer(adminService);
//			System.out.println(c);
//getAllCustomers:
//			List<Customer> customers = adminService.getAllCustomers();
//			System.out.println(customers);

		} catch (CouponSystemException e) {
			System.out.println("====== Error ========================");
			System.out.println(e.getMessage());
			System.out.println("====== ===== ========================");
//			e.printStackTrace();
		}

	}

// *************************** COMPANY ********************************************
	static void createCompany(AdminService adminService) throws CouponSystemException {
		List<Coupon> coupons = new ArrayList<Coupon>();
		coupons.add(new Coupon(Category.FOOD, "title5", "description5", Date.valueOf("2021-12-18"),
				Date.valueOf("2021-12-19"), 5, 100.00, "image5"));
		coupons.add(new Coupon(Category.RESTAURANT, "title6", "description6", Date.valueOf("2021-12-12"),
				Date.valueOf("2021-12-30"), 5, 10.0, "image6"));
		Company b = new Company("company1", "company1@", "company123", coupons);
		adminService.addCompany(b);
		System.out.println("company added");
		System.out.println(b);
	}

	static void printCompanies(AdminService adminService) {
		List<Company> companies = adminService.getAllCompanies();
		System.out.println(companies);
	}

	static void updateCompany(AdminService adminService) throws CouponSystemException {
		Company company = new Company(3, "CompanY", "CompanY@mail", "CompanY1123");
		adminService.updateCompany(company);
		System.out.println(company);
	}

// -------------------------- DELETE COMPANY -----------------------------------------
	public void deleteCompany(AdminService adminService) throws CouponSystemException {
		adminService.deleteCompany(1);
		System.out.println("company deleted");
	}
// -------------------------- getOneCompany -----------------------------------------

	public Company getOneCompany(AdminService adminService) throws CouponSystemException {
		return adminService.getOneCompany(3);
	}
// -------------------------- getAllCompanies -----------------------------------------

	public List<Company> getAllCompanies(AdminService adminService) {
		return adminService.getAllCompanies();
	}

// *************************** CUSTOMER ********************************************
// -------------------------- addCustomer -----------------------------------------
	public void addCustomer(AdminService adminService) throws CouponSystemException {
		Customer customer = new Customer("customer3", "customer", "customer3@", "customer323");
		adminService.addCustomer(customer);
		System.out.println(customer);

	}
// -------------------------- DELETE COMPANY -----------------------------------------
	public void deleteCustomer(AdminService adminService) throws CouponSystemException {
		adminService.deleteCustomer(2);
		System.out.println("customer deleted");

	}
// -------------------------- DELETE COMPANY -----------------------------------------
	public void updateCustomer(AdminService adminService) throws CouponSystemException {
		Customer customer = new Customer(3,"Customer3", "Customer", "Customer3@", "Customer323");
		adminService.updateCustomer(customer);
		System.out.println(customer);
	}
// -------------------------- DELETE COMPANY -----------------------------------------
	public Customer getOneCustomer(AdminService adminService) throws CouponSystemException {
		return adminService.getOneCustomer(2);
	}
// -------------------------- getOneCustomer -----------------------------------------
	public List<Customer> getAllCustomers(AdminService adminService) {
		return adminService.getAllCustomers();
	}


	}

