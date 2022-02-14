package com.ronit.repositories;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ronit.entities.Company;
import com.ronit.entities.Coupon;
import com.ronit.enums.Category;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {
	// find companies by email and password
 	boolean existsByEmailAndPassword(String email, String password);
 	Company findByEmailAndPassword(String email, String password);	
 	// find companies by name and email
	boolean existsByNameAndEmail(String name, String email);
	Company findByNameAndEmail(String name, String email);	
	boolean existsByNameOrEmail(String name, String email);
//	@Query("select c from Company c where c.name = :name AND c.password = :password")
//	public Optional<Company> login(@Param("email")String email, @Param("password")String password);



	
}
