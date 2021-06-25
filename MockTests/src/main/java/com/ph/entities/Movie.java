package com.ph.entities;

public class Movie {

	private String name;
	private Integer stock;
	private Double rentPrice;
	
	public Movie() {}
	
	public Movie(String name, Integer stock, Double rentPrice) {
		this.name = name;
		this.stock = stock;
		this.rentPrice = rentPrice;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	public Double getRentPrice() {
		return rentPrice;
	}
	public void setRentPrice(Double rentPrice) {
		this.rentPrice = rentPrice;
	}
}