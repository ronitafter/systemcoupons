package com.ronit.test;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.ronit.exceptions.CouponSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.ronit.controllers.AdminController;
import com.ronit.entities.Company;
import com.ronit.entities.Coupon;
import com.ronit.enums.Category;
import com.ronit.services.AdminService;
//import com.sapir.beans.CustomerList;

@Component
public class AdminControllerTester {
// __________________________ TESTER __________________________________________
//	public static void main(String[] args) throws URISyntaxException {

	// Test RestTemplate to invoke the APIs.

	@Autowired
	private RestTemplate restTemplate = new RestTemplate();

	@Autowired
	private AdminService adminService;

	public void addOneCompany()  {
//
//********************** ADD Company ********************************
		Company company = createCompanyWithoutCoupons();

		// with coupons - does not work - need to fix it!!
		//Company company = createCompanyWithCoupons();

		System.out.println(company);
		try {
			adminService.addCompany(company);
		} catch (CouponSystemException e) {
			e.printStackTrace();
		}
	}

	private Company createCompanyWithoutCoupons() {
		List<Coupon> coupons = new ArrayList<Coupon>();
		Company company = new Company("aaa", "aaa@", "aaa123", coupons);
		return company;
	}

	private Company createCompanyWithCoupons() {
		List<Coupon> coupons = new ArrayList<Coupon>();
		coupons.add(new Coupon(Category.FOOD, "title5", "description5", Date.valueOf("2021-12-18"),
				Date.valueOf("2021-12-19"), 5, 100.00, "image5"));//
		Company company = new Company("aaa", "aaa@", "aaa123", coupons);
		return company;
	}

	public void testApi() {
//		
//********************** ADD Company ********************************
			List<Coupon> coupons = new ArrayList<Coupon>();
			coupons.add(new Coupon(Category.FOOD, "title5", "description5", Date.valueOf("2021-12-18"),
					Date.valueOf("2021-12-19"), 5, 100.00, "image5"));//			
			Company company = new Company("aaa", "aaa@","aaa123", coupons);
			System.out.println(company);
		ResponseEntity<Long> response = restTemplate.postForEntity(String.format("http://localhost:8080/admin/company"), company,Long.class);
		System.out.println("response statuse: " + response.getStatusCodeValue() );
		System.out.println("response body: " + response.getBody());
		System.out.println(company);
			
			
			
//			adminService.addCompany(b);
//			System.out.println("company added");
//			System.out.println(b);
//		}

//********************** GET CUSTOMER BY ID ********************************
//		resttemplate = new RestTemplate();
//		Customer customer = resttemplate.getForObject("http://localhost:8080/customer/1", Customer.class);
//		System.out.println(customer);

// ********************** GET CUSTOMERS BY NAME **********************
//				
//Customer customer = restTemplate.getForObject("http://localhost:8080/customer/ByFirstName?firstName=Rona", Customer.class);
//	customer);
//	CustomerList customers = restTemplate.getForObject("http://localhost:8080/customer/ByFirstName?firstName=Rona", CustomerList.class);	
//	System.out.println(String.format("received customers: %s", customers.getCustomers()));
//	System.out.println(customers.getCustomers());
//	customers.getCustomers().forEach(customer ->System.out.println (customer));

//	
	
//********************** UPDATE CUSTOMER ********************************
//		Customer customer = new Customer(1,"Daniella", 30);
//		customer.setAge(555);
//		customer.setFirstName("DDD");
//		resttemplate.put("http://localhost:8080/customer", customer);
//		System.out.println(customer);
//********************** GET ALL CUSTOMER ORDERS ********************************
		// 1. Wrapper class
		// 2. list using ParameterizedTypeReference and exchange method
		// 3. array
//		ResponseEntity<Order[]> res = resttemplate.getForEntity("http://localhost:8080/customer/orders/1",Order[].class);
//		Order[] arr = res.getBody();
//		List<Order> list = new ArrayList<>(Arrays.asList(arr));
//		System.out.println(list);
		
//********************** GET ALL CUSTOMERS ********************************
// WORKIN
//		CustomerList customers = resttemplate.getForObject(String.format("http://localhost:8080/customer"),
//				CustomerList.class);
//		System.out.println(String.format("received customers: %s", customers.getCustomers()));
// NOT WORKING
//	Customer[] customersArr = resttemplate.getForObject(String.format("http://localhost:8080/customer"), Customer[].class);
//		System.out.println(customersArr);
//********************** DELETE CUSTOMER ********************************	
//		System.out.println("checking method DeleteCustomer");
//	    resttemplate.delete("http://localhost:8080/customer/2");
//		System.out.println("customer deleted");
		
		
//	CustomersList customers = restTemplate.getForObject("http://localhost:8080/customer/ByFirstName?firstName=ranDanker", CustomersList.class);	
//	System.out.println(String.format("received customers: %s", customers.getCustomers()));
//    customers.getCustomers().forEach(customer -> System.out.println(customer));
//********************** ADD ORDER ********************************
		
//		?????
		
//********************** UPDATE ORDER ********************************

//????
				
// ********************** getAllUsersByAgeAndName **********************

//???
//********************** getAllCustomerByAge **********************
		// NOT WORKING
//	CustomerList customers = resttemplate.getForObject("http://localhost:8080/customer/ByAge?age=25", CustomerList.class);
//	System.out.println(String.format("received customers: %s", customers.getCustomers()));
//	System.err.println(customers.getCustomers());

//********************** GET ALL ORDERS ********************************
//		ResponseEntity<Order[]> res = resttemplate.getForEntity("http://localhost:8080/orders",Order[].class);
//		Order[] arr = res.getBody();
//		List<Order> list = new ArrayList<>(Arrays.asList(arr));
//		System.out.println(list);
//	
//			
//********************** ? ********************************	
//		List list = resttemplate.getForObject("http://localhost:8080/orders", List.class);
//		System.out.println(list);
//		Object orderAsMap =  list.get(0);
//		System.out.println(orderAsMap.getClass());

//		RequestEntity<?> req = new RequestEntity<>(HttpMethod.GET, new URI("http://localhost:8080/orders"));		
//		ParameterizedTypeReference<List<Order>> listOfOrder = new ParameterizedTypeReference<List<Order>>() {
//		};

//		List<Order> list2 = resttemplate.exchange(req, listOfOrder).getBody();
//		System.out.println(list2);
//		Order order = list2.get(0);		
				
// __________________________________ get the new customer __________________________________ 
//		
//		System.out.println("getting the new customer");
//		System.out.println(String.format("GET %s/customer/%d",SERVER_URL,response.getBody()));
////		Customer newCustomer = resttemplate.getForObject(String.format("%s/customer/%d", SERVER_URL,response.getBody()), Customer.class);
//		Customer newCustomer = resttemplate.getForObject("http://localhost:8080/customer/1", Customer.class);
//		System.out.println(String.format("new customer: %s", newCustomer.toString()));
//		System.out.println(newCustomer);

//	}
		

		}
	}

