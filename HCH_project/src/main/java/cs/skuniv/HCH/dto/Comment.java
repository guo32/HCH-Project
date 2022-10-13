package cs.skuniv.HCH.dto;

import java.time.LocalDateTime;

public class Comment {

	private int id;
	private String registrant;
	private int posting;
	private String category;
	private String content;
	private double rating;
	private LocalDateTime regdate;
	
	public Comment(String registrant, int posting, String category, 
			String content, double rating, LocalDateTime regdate) {
		
		this.registrant = registrant;
		this.posting = posting;
		this.category = category;
		this.content = content;
		this.rating = rating;
		this.regdate = regdate;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getRegistrant() {
		return registrant;
	}
	
	public void setRegistrant(String registrant) {
		this.registrant = registrant;
	}
	
	public int getPosting() {
		return posting;
	}
	
	public void setPosting(int posting) {
		this.posting = posting;
	}
	
	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public double getRating() {
		return rating;
	}
	
	public void setRating(double rating) {
		this.rating = rating;
	}
	
	public LocalDateTime getRegdate() {
		return regdate;
	}
	
	public void setRegdate(LocalDateTime regdate) {
		this.regdate = regdate;
	}	
	
}
