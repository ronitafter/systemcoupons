package com.ronit.repositories;

import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.ronit.entities.Company;
import com.ronit.entities.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	boolean existsByEmailAndPassword(String email, String password);
	Customer findByEmailAndPassword(String email, String password);
	boolean existsByFirstNameAndLastNameAndEmail(String firstName, String lastName, String email);
	Company findByFirstNameAndLastNameAndEmail(String firstName, String lastName, String email);
//	@Query("select c from Customer c where c.custName = :name and c.password = :password")
//	public Optional<Customer> login(@Param("email") String email, @Param("password") String password);

	

}
