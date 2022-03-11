package com.ronit.test;

import java.sql.Date;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.ronit.entities.Company;
import com.ronit.entities.Coupon;
import com.ronit.enums.Category;
import com.ronit.enums.ClientType;
import com.ronit.exceptions.CouponSystemException;
import com.ronit.services.CompanyService;
import com.ronit.utils.LoginManager;

//@Component
public class CompanyTester implements CommandLineRunner {

	int companyID;

	@Autowired
	private ApplicationContext context;

	@Override
	public void run(String... args) throws Exception {
		startCompanyTester();
	}

	public void startCompanyTester() {

		LoginManager loginManager = context.getBean(LoginManager.class);
		try {
			CompanyService companyService = (CompanyService) 
					loginManager.login("company1@", "company123",
					ClientType.COMPANY);
			System.out.println("loged in as company");
// -------------- COMPANY SERVICE TESTING --------------
//			printCompanies(companyService);
//getAllCompanies:
//			getAllCompanies(companyService);
//getCompanyCouponsByPrice:			
//			getCompanyCouponsByPrice(companyService);
//deleteCoupon:		
//			deleteCoupon(companyService);
//UpdateCoupon:			
//			UpdateCoupon(companyService);
//addCoupon:
//			addCoupon(companyService);
//getCompanyDetails:			
//			getCompanyDetails(companyService);
//getCompanyCoupons:			
//			getCompanyCouponsByCategory(companyService);
//getAllCompanyCoupons:			
//			getAllCompanyCoupons(companyService);

		} catch (CouponSystemException e) {
			System.out.println("====== Error ========================");
			System.out.println(e.getMessage());
			System.out.println("====== ===== ========================");
			e.printStackTrace();
		}
	}

	public void addCoupon(CompanyService companyService) throws CouponSystemException {
		
		Coupon coupon = new Coupon( Category.VACATION, "HFHFHF", "gfgf", Date.valueOf("2022-12-17"),
				Date.valueOf("2021-12-18"), 5, 100.00, "ffff");
		System.out.println(coupon.getCompany());
		companyService.addCoupon(coupon);
		System.out.println(coupon.getCompany());
		
	}
	
	public void UpdateCoupon(CompanyService companyService) throws CouponSystemException {
		Coupon coupon = new Coupon(15,Category.VACATION, "HFHFHFHF", "vvvv", Date.valueOf("2022-12-17"),
				Date.valueOf("2021-12-20"), 5, 100.00, "vvvv");
		companyService.UpdateCoupon(coupon);
	}

	public void deleteCoupon(CompanyService companyService) throws CouponSystemException {
		System.out.println();
		companyService.deleteCoupon(17, 8);
	}
	
	public List<Coupon> getCompanyCouponsByCategory(CompanyService companyService) throws CouponSystemException {
		for (Coupon coupon : companyService.getCompanyCoupons(1)) {
			System.out.println(coupon);
			
		}
		return null;
		
	}
	
	public List<Coupon> getCompanyCouponsByPrice(CompanyService companyService) throws CouponSystemException {
		for (Coupon coupon : companyService.getCompanyCouponsByPrice(10)) {
			 System.out.println(coupon);
		}
		return null;
		
	}

	public List<Coupon> getAllCompanyCoupons(CompanyService companyService) throws CouponSystemException{
		for (Coupon coupon : companyService.getAllCompanyCoupons()) {
			 System.out.println(coupon);
		}
		return null;
		
	}
	
	public Company getCompanyDetails(CompanyService companyService) throws CouponSystemException {
		System.out.println(companyService.getCompanyDetails(2));
		return null;

		
	}

}