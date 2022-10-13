package cs.skuniv.HCH.dto;

import java.time.LocalDateTime;

public class Product {

	private int id;
	private String category;
	private String name;
	private int price;
	private LocalDateTime regdate;
	
	public Product(String category, String name, int price) {
		this.category = category;
		this.name = name;
		this.price = price;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}	
	
}
