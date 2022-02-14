package com.ronit.services;

import org.springframework.beans.factory.annotation.Autowired;
import com.ronit.repositories.CompanyRepository;
import com.ronit.repositories.CouponRepository;
import com.ronit.repositories.CustomerRepository;

public abstract class ClientService {
	
	@Autowired
	protected CompanyRepository companyRepository;
	@Autowired
	protected CouponRepository couponRepository;
	@Autowired
	protected CustomerRepository customerRepository;
	
	public abstract boolean login(String email, String passwaord);
	

}
