package com.ronit.services;

import java.util.List;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ronit.entities.Company;
import com.ronit.entities.Customer;
import com.ronit.enums.ClientType;
import com.ronit.exceptions.CouponSystemException;

@Service
@Transactional(rollbackFor = CouponSystemException.class)
public class AdminService extends ClientService {

	@Value("${admin.email}")
	private static final String email = "admin@admin.com";
	@Value("${admin.password}")
	private static final String password = "admin";

	public boolean login(String email, String passwaord) {
		System.out.println(AdminService.email);
		System.out.println(AdminService.password);
		boolean b = email.equals(AdminService.email) && passwaord.equals(AdminService.password);
		return b;
	}

// ************************************ COMPANY *****************************************************************
// ___________________________________ Add Company _____________________________________________________________
	public void addCompany(Company company) throws CouponSystemException {
		if (companyRepository.existsByNameAndEmail(company.getName(), company.getEmail())) {
			throw new CouponSystemException("addCompany faild - company with this name and email already exist ");
		} 
		companyRepository.save(company);
	}

// ___________________________________ Update Company ___________________________________________________________
	public void updateCompany(Company company) throws CouponSystemException {
		Optional<Company> opt = this.companyRepository.findById(company.getId());
		if (companyRepository.existsByNameAndEmail(company.getName(), company.getEmail())) {
			// ompanyrepository.ExistsByNameOrEmail(company.getName(), company.getEmail()))
			throw new CouponSystemException("addCompany faild - company with this name and email already exist ");
		}
		if (opt.isPresent()) {
			Company companyFromDB = opt.get();
			companyFromDB.setName(company.getName());
			companyFromDB.setEmail(company.getEmail());
			companyFromDB.setPassword(company.getPassword());
		} else {
			throw new CouponSystemException("updateCompany faild - company not found ");
		}

	}

// ___________________________________ Delete Company ____________________________________________________________
	public void deleteCompany(int companyID) throws CouponSystemException {
		Optional<Company> opt = this.companyRepository.findById(companyID);

		if (opt.isEmpty()) {
			throw new CouponSystemException("deleteCompany faild - Company with this id not found");
		}
//		Company CompanyFromDb = opt.get();
		opt.get();
		companyRepository.deleteById(companyID);
//		companyrepository.delete(CompanyFromDb);

	}

// ___________________________________ Get One Company ___________________________________________________________
	public Company getOneCompany(int companyID) throws CouponSystemException {
		Optional<Company> opt = this.companyRepository.findById(companyID);
		if (opt.isPresent()) {
			return opt.get();
		} else {
			throw new CouponSystemException("getOneCompany faild - not found");
		}
	}

// ___________________________________ Get All Companies _________________________________________________________
	public List<Company> getAllCompanies() {
		return companyRepository.findAll();
	}

// ************************************ CUSTOMER *****************************************************************

// ___________________________________ Add Customer _____________________________________________________________
	public void addCustomer(Customer customer) throws CouponSystemException {
		if (this.customerRepository.existsByFirstNameAndLastNameAndEmail(customer.getFirstName(),
				customer.getLastName(), customer.getEmail())) {
			throw new CouponSystemException("addCustomer faild - customer with this name and email already exist ");
		}
		customerRepository.save(customer);
	}

// ___________________________________ Update Customer ____________________________________________________________
	public void updateCustomer(Customer customer) throws CouponSystemException {
		Optional<Customer> opt = this.customerRepository.findById(customer.getId());
		if (opt.isEmpty()) {
			throw new CouponSystemException("updateCustomer faild - customer not found");
		}

		Customer customerFromDb = opt.get();
		customerFromDb.setFirstName(customer.getFirstName());
		customerFromDb.setLastName(customer.getLastName());
		customerFromDb.setEmail(customer.getEmail());
		customerFromDb.setPassword(customer.getPassword());
		customerFromDb.setCoupons(customer.getCoupons());
	}

// ___________________________________ Delete Customer ____________________________________________________________
	public void deleteCustomer(int customerId) throws CouponSystemException {
		Optional<Customer> opt = this.customerRepository.findById(customerId);
		if (opt.isEmpty()) {
			throw new CouponSystemException("deleteCustomer faild - Customer with this id not found");
		}

		opt.get();
		customerRepository.deleteById(customerId);
	}

// ___________________________________ getAllCustomers ____________________________________________________________
	public List<Customer> getAllCustomers() {
		return customerRepository.findAll();
	}

// ___________________________________ Get One Customer ___________________________________________________________
	public Customer getOneCustomer(int CustomerID) throws CouponSystemException {
		Optional<Customer> opt = this.customerRepository.findById(CustomerID);
		if (opt.isEmpty()) {
			throw new CouponSystemException("getOneCustomer faild - customer not found");
		}
		return opt.get();
	}

}
