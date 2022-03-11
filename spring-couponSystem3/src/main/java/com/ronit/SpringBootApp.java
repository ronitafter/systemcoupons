package com.ronit;

import org.springframework.boot.SpringApplication;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.ronit.services.CompanyService;
import com.ronit.test.AdminTester3;

//@ServletComponentScan
@EnableScheduling
@SpringBootApplication
public class SpringBootApp {

	public static void main(String[] args) {
		
//		ApplicationContext context = SpringApplication.run(Main.class);
//		CompanyService com = context.getBean(CompanyService.class);
//		AdminTester3 tester = context.getBean(AdminTester3.class);
//		tester.startAdminTest();
	
//Testing by using CommandLineRunner - a Spring Boot interface with a run method. 
//Spring Boot will automatically call the run method of all beans implementing this 
//interface, after the application context has been load
	
// ************ for testing without CouponExpirationDailyIob2 ************
//		1. uncomment the @Component in the wanted tester(AdminTester,
//		CompanyTester,CustomerTester);
//		2.uncomment the wanted method for testing
//		3.uncomment:
	SpringApplication.run(SpringBootApp.class);
//	ApplicationContext context = SpringApplication.run(SpringBootApp.class);
////	initCustomers(context.getBean(StoreService.class));
//	AdminTester3 tester = context.getBean(AdminTester3.class);
//	tester.startAdminTest();
////}
////		
		 
// ************ for testing with CouponExpirationDailyIob2 ************
//		1.uncomment the CouponExpirationDailyIob2 Component
//		2. uncomment:
//		ApplicationContext context = SpringApplication.run(SpringBootApp.class);
//	CouponRepository couponRepository = context.getBean(CouponRepository.class);
		 //	CouponExpirationDailyIob2 newJob = context.getBean(CouponExpirationDailyIob2.class);
//	 newJob.deleteExpiredJob();
//		3. uncomment the @Component in the wanted tester(AdminTester,
//		CompanyTester,CustomerTester);
//		4.uncomment the wanted method for testing

	}

}
