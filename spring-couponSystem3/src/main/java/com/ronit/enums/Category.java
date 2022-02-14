package com.ronit.enums;

public enum Category {

	NULL, FOOD, ELECTRICITY, RESTAURANT, VACATION;

	private int id;

	private Category() {
		
	}
	private Category(int id) {
		this.id = id;

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
