package cs.skuniv.HCH.dto;

public class Favorite {
	
	private String user;
	private int posting;
	private String category;
	
	public Favorite(String user, int posting, String category) {
		this.user = user;
		this.posting = posting;
		this.category = category;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
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

}
