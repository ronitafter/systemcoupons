package com.ronit.entities;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "company")
public class Company {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String email;
	private String password;
	@JsonManagedReference
	@OneToMany(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH
			,CascadeType.ALL}, fetch = FetchType.LAZY, mappedBy = "company")
	private List<Coupon> coupons;

	public Company() {
	}

	public Company(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
	}

	public Company(int id, String name, String email, String password) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
	}

	public Company(String name, String email, String password, List<Coupon> coupons) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.coupons = coupons;
		for (Coupon coupon : coupons) {
			coupon.setCompany(this);
		}
	}
	
	public Company(int id, String name, String email, String password, List<Coupon> coupons) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.coupons = coupons;
		for (Coupon coupon : coupons) {
			coupon.setCompany(this);

		}
	}

	public int getId() {
		return id;
	}

	public void seId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Coupon> getCoupons() {
		return coupons;
	}

	public void setCoupons(List<Coupon> coupons) {
		this.coupons = coupons;
	}

	public void addCoupon(Coupon coupon) {
		if (this.coupons == null) {
			this.coupons = new ArrayList<Coupon>();
		}
		coupon.setCompany(this);
		this.coupons.add(coupon);
	}

	@Override
	public String toString() {
		return "Company [id =" + id + ", name=" + name + ", email=" + email + ", password=" + password + "]";

	}
}
