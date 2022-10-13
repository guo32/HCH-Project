package cs.skuniv.HCH.request;

public class CommentRegisterRequest {
	
	private String registrant;
	private String posting;
	private String category;
	private String content;
	private String rating;
	
	public String getRegistrant() {
		return registrant;
	}
	
	public void setRegistrant(String registrant) {
		this.registrant = registrant;
	}
	
	public String getPosting() {
		return posting;
	}
	
	public void setPosting(String posting) {
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
	
	public String getRating() {
		return rating;
	}
	
	public void setRating(String rating) {
		this.rating = rating;
	}	

}
