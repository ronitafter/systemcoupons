package com.ronit.repositories;

import java.sql.Date;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ronit.entities.Coupon;
import com.ronit.enums.Category;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Integer> {

// ************************ COMPANY ************************
	@Modifying
	@Transactional
	@Query(value = "delete from coupons where end_date<now()", nativeQuery = true)
	void deleteExpiredCoupons();

	@Query(value = "select coupons.* from coupons where company_id = ?1" + " and price =?2 ", nativeQuery = true)
	List<Coupon> findByCompanyIdAndPrice(int companyId, double maxPrice);

	@Query(value = "select coupons.* from coupons where company_id = ?1" + " and category_id =?2 ", nativeQuery = true)
	List<Coupon> findByCompanyIdAndCategory(int companyId, int categoryId);

	boolean existsByCompanyIdAndTitle(int companyId, String title);

	List<Coupon> findCouponsByCompanyId(int companyId);

// ************************ CUSTOMER ************************
	@Query(value = "select coupons.* from coupons join coupons_customers on coupon_id = id "
			+ "where customer_id = ? and price <=?", nativeQuery = true)
	List<Coupon> findCustomerCouponsByMaxPrice(int customerId, double maxPrice);

	@Query(value = "select coupon. * from coupons join customers_coupons on"
			+ "coupon.id=customers_coupons.coupon_id where customer_id=? and category_id=categoryId", nativeQuery = true)
	List<Coupon> getCustomersCoupons(@Param("customerId") int customerId, @Param("categoryId") int categoryId);

	List<Coupon> findByCustomersId(int customerId);

	boolean existsByIdAndCustomersId(int couponId, int customerId);

	boolean existsByIdAndCompanyId(int couponId, int companyId);

	@Query(value = "select coupons.* from coupons join coupons_customers on coupon_id = id where customer_id = ?1"
			+ " and category_id =?2 ", nativeQuery = true)
	List<Coupon> findByCustomerIdAndCategory(int customerId, int categoryId);

}
