package com.ronit.services;




import java.util.ArrayList;




import java.util.Date;

import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ronit.entities.Company;
import com.ronit.entities.Coupon;
import com.ronit.entities.Customer;
import com.ronit.enums.Category;
import com.ronit.enums.ClientType;
import com.ronit.exceptions.CouponSystemException;
import com.ronit.repositories.CouponRepository;
import com.ronit.repositories.CustomerRepository;

import jdk.jfr.DataAmount;

/*
 * @Transactional(rollbackFor):
 * the metadata that specifies the semantics of the transactions on a method. 
 * 
 * 
 */


@Service
@Scope("prototype")
@Transactional(rollbackFor = CouponSystemException.class)
public class CustomerService extends ClientService {

	private int customerId;
	
//	@Autowired
//	private CustomerRepository customerRipository;
//	@Autowired
//	private CouponRepository couponRipository;
// -------------------------- login -----------------------------------------
	public boolean login(String email, String passwaord) {
		if (customerRepository.existsByEmailAndPassword(email, passwaord)) {
			Customer customer = customerRepository.findByEmailAndPassword(email, passwaord);
			customerId = customer.getId();
			return true;
		}

		return false;

	}	
// -------------------------- Contractor -----------------------------------------
	@Autowired
	public CustomerService(CouponRepository couponrepository, CustomerRepository customerrepository) {
		this.couponRepository = couponrepository;
		this.customerRepository = customerrepository;

	}
// -------------------------- PurchaseCoupon -----------------------------------------
	public void PurchaseCoupon(int couponId) throws CouponSystemException {
		Optional<Coupon> opt = this.couponRepository.findById(couponId);
		if (opt.isEmpty()) {
			throw new CouponSystemException("PurchaseCoupon faild - coupon with this Id not found");
		}
		Coupon coupon = opt.get();
		if (coupon.getAmount() <= 0) {
			throw new CouponSystemException("PurchaseCoupon faild - amount for this coupo is 0");		
		}
		 if(couponRepository.existsByIdAndCustomersId(couponId, customerId)){
		 throw new CouponSystemException("PurchaseCoupon faild - coupon exists for this CustomerId");		
		}
	
			if (coupon.getEndDate().before(new Date())) {
				throw new CouponSystemException("could not  Purchase coupon - coupon  is expired");
		} else {
			Customer customer = customerRepository.getById(customerId);
			customer.addCoupon(coupon);
			coupon.setAmount(coupon.getAmount() - 1);
			System.out.println("coupon Purchased successfully");
			System.out.println("coupon Id: " + couponId + " customer Id: " + customerId);
		
		}

	}
// -------------------------- getAllCustomerCoupons -----------------------------------------
	public List<Coupon> getAllCustomerCoupons(int customerId) throws CouponSystemException {
		if (couponRepository.findByCustomersId(customerId).isEmpty()) {
			throw new CouponSystemException(
					"getAllCustomerCoupons faild - Coupons not found in this category for this customer");
		} else {
			return new ArrayList<Coupon>(couponRepository.findByCustomersId(customerId));

		}

	}
// -------------------------- getCustomerCoupons -----------------------------------------
	public List<Coupon> getCustomerCoupons(int customerId, int category) throws CouponSystemException {
		if (couponRepository.findByCustomerIdAndCategory(customerId, category).isEmpty()) {
			throw new CouponSystemException(
					"getCustomerCoupons faild - Coupons not found in this category for this customer");
		} else {

			return new ArrayList<Coupon>(couponRepository.findByCustomerIdAndCategory(customerId, category));
		}

	}
// -------------------------- getCustomerCouponsByPrice -----------------------------------------
	public List<Coupon> getCustomerCouponsByPrice(int customerId, double maxPrice) throws CouponSystemException {
		if (couponRepository.findCustomerCouponsByMaxPrice(customerId, maxPrice).isEmpty()) {
			throw new CouponSystemException("getCustomerCoupons faild - customerId not found");
		} else {
			return new ArrayList<Coupon>(couponRepository.findCustomerCouponsByMaxPrice(customerId, maxPrice));
		}
	}
// -------------------------- getAllCustomerDetails -----------------------------------------
	public Customer getAllCustomerDetails(int customerId) throws CouponSystemException {
		Optional<Customer> opt = this.customerRepository.findById(customerId);
		if (opt.isEmpty()) {
			throw new CouponSystemException("getAllCustomerDetails faild - Customer not found");
		}

		System.out.println("getAllCustomerDetails");
		return opt.get();
	}
	
	


}
