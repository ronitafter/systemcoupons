package com.ronit.services;

import java.sql.Date;



import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ronit.entities.Company;
import com.ronit.entities.Coupon;
import com.ronit.enums.Category;
import com.ronit.enums.ClientType;
import com.ronit.exceptions.CouponSystemException;
import com.ronit.repositories.CompanyRepository;

@Service
@Scope("prototype")
@Transactional(rollbackFor = CouponSystemException.class)
public class CompanyService extends ClientService {

	
	
	@Autowired
	private CompanyRepository companyrepository;

	private int companyId;
// -------------------------- login -----------------------------------------
	public boolean login(String email, String passwaord) {

		if (companyrepository.existsByEmailAndPassword(email, passwaord)) {
			Company company = companyrepository.findByEmailAndPassword(email, passwaord);
			companyId = company.getId();
			return true;
		}
		return false;
//
	}
	
// -------------------------- addCoupon -----------------------------------------
	public void addCoupon(Coupon coupon) throws CouponSystemException {
		if (this.couponRepository.existsByCompanyIdAndTitle(companyId, coupon.getTitle())) {
			throw new CouponSystemException("addCoupon faild - coupon already exist for this company ");
		}

		Company company = companyrepository.findById(companyId).get();
		company.addCoupon(coupon);

	}
// -------------------------- UpdateCoupon -----------------------------------------
	public void UpdateCoupon(Coupon coupon) throws CouponSystemException {
		Optional<Coupon> opt = this.couponRepository.findById(coupon.getId());
		if (opt.isEmpty()) {
			throw new CouponSystemException("UpdateCoupon faild - coupon not found");
		}
		Coupon couponFromDb = opt.get();
		couponFromDb.setAmount(coupon.getAmount());
		couponFromDb.setCategory(coupon.getCategory());
		couponFromDb.setDescription(coupon.getDescription());
		couponFromDb.setStartDate(coupon.getStartDate());
		couponFromDb.setEndDate(coupon.getEndDate());
		couponFromDb.setImage(coupon.getImage());
		couponFromDb.setPrice(coupon.getPrice());
		couponFromDb.setTitle(coupon.getTitle());

	}
// -------------------------- deleteCoupon -----------------------------------------
	public void deleteCoupon(int couponId, int companyId) throws CouponSystemException {
		Optional<Coupon> opt = this.couponRepository.findById(couponId);
		if (opt.isEmpty()) {
			throw new CouponSystemException("deleteCoupon faild - coupon not found");
		}
		Coupon couponFromDb = opt.get();
		if (!couponRepository.existsByIdAndCompanyId(couponId, companyId)) {
			throw new CouponSystemException("eleteCoupon faild - coupon not belong to this company");
		}
		couponRepository.deleteById(couponId);
		System.out.println("coupon deleted");
		System.out.println(couponRepository.findById(couponId));

	}
// -------------------------- getCompanyCoupons -----------------------------------------
	public List<Coupon> getCompanyCoupons(int categoryId) throws CouponSystemException {
		if (couponRepository.findByCompanyIdAndCategory(companyId, categoryId).isEmpty()) {
			throw new CouponSystemException(
					"getCompanyCoupons faild - Coupons not found in this category for this company");
		} else {
			return new ArrayList<Coupon>(couponRepository.findByCompanyIdAndCategory(companyId, categoryId));
		}
	}
// -------------------------- getCompanyCouponsByPrice -----------------------------------------
	public List<Coupon> getCompanyCouponsByPrice(double maxPrice) throws CouponSystemException {
		if (couponRepository.findByCompanyIdAndPrice(companyId, maxPrice).isEmpty()) {
			throw new CouponSystemException("getCompanyCoupons faild - company not found");
		} else {
			return new ArrayList<Coupon>(couponRepository.findByCompanyIdAndPrice(companyId, maxPrice));
		}
	}
// -------------------------- getAllCompanyCoupons -----------------------------------------
	public List<Coupon> getAllCompanyCoupons() throws CouponSystemException {
		if (couponRepository.findCouponsByCompanyId(companyId).isEmpty()) {
			throw new CouponSystemException("getAllCompanyCoupons faild - no coupons found for this company");

		} else {

		}
		return new ArrayList<Coupon>(couponRepository.findCouponsByCompanyId(companyId));
	}
// -------------------------- getCompanyDetails -----------------------------------------
	public Company getCompanyDetails(int comanyId) throws CouponSystemException {
		Optional<Company> opt = this.companyrepository.findById(comanyId);
		if (opt.isEmpty()) {
			throw new CouponSystemException("getCompanyDetails faild - company with this id not found");
		}

		return opt.get();

	}

}
