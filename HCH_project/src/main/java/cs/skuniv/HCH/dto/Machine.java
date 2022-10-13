package cs.skuniv.HCH.dto;

import java.time.LocalDateTime;

public class Machine {
	
	private int id;
	private String category;
	private String name;
	private String brand;
	private int price;
	private String type;
	private String color;
	private double rating;
	private double ratingsum;
	private String review;
	private int favorite;
	private LocalDateTime regdate;
	private String registrant;
	private String filename;
	
	public Machine(String category, String name,
			String brand, int price, String type, String color,
			double rating, double ratingsum, String review,
			LocalDateTime regdate, String registrant, String filename) {
		this.category = category;
		this.name = name;
		this.brand = brand;
		this.price = price;
		this.type = type;
		this.color = color;
		this.rating = rating;
		this.ratingsum = ratingsum;
		this.review = review;
		this.regdate = regdate;
		this.registrant = registrant;
		this.filename = filename;
	}
	
	/* 게시물 */
	public Machine(int id, String category, String name,
			String brand, int price, String type, String color,
			double rating, double ratingsum, String review, int favorite,
			LocalDateTime regdate, String registrant, String filename) {
		this.id = id;
		this.category = category;
		this.name = name;
		this.brand = brand;
		this.price = price;
		this.type = type;
		this.color = color;
		this.rating = rating;
		this.ratingsum = ratingsum;
		this.review = review;
		this.favorite = favorite;
		this.regdate = regdate;
		this.registrant = registrant;
		this.filename = filename;
	}
	
	/* 게시물 미리보기 */
	public Machine(int id, String category, String name, 
			String brand, double rating, String registrant,
			String filename, int favorite) {
		this.id = id;
		this.category = category;
		this.name = name;
		this.brand = brand;
		this.rating = rating;
		this.registrant = registrant;
		this.filename = filename;
		this.favorite = favorite;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public double getRatingsum() {
		return ratingsum;
	}

	public void setRatingsum(double ratingsum) {
		this.ratingsum = ratingsum;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public int getFavorite() {
		return favorite;
	}

	public void setFavorite(int favorite) {
		this.favorite = favorite;
	}

	public LocalDateTime getRegdate() {
		return regdate;
	}

	public void setRegdate(LocalDateTime regdate) {
		this.regdate = regdate;
	}

	public String getRegistrant() {
		return registrant;
	}

	public void setRegistrant(String registrant) {
		this.registrant = registrant;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}	

}
