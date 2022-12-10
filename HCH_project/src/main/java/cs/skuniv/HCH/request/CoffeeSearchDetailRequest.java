package cs.skuniv.HCH.request;

/* 원두 상세 검색 시 사용되는 내용 */
public class CoffeeSearchDetailRequest {
	
	private String nation; // 22.11.16 원산지 검색을 위해 추가
	private String manufacturer;
	private String price;
	private String roastlevel;
	private String taste;
	private String tasteOther;
	private String volume;
	private String rating;
		
	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getManufacturer() {
		return manufacturer;
	}
	
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	
	public String getPrice() {
		return price;
	}
	
	public void setPrice(String price) {
		this.price = price;
	}
	
	public String getRoastlevel() {
		return roastlevel;
	}
	
	public void setRoastlevel(String roastlevel) {
		this.roastlevel = roastlevel;
	}
	
	public String getTaste() {
		return taste;
	}
	
	public void setTaste(String taste) {
		this.taste = taste;
	}
	
	public String getTasteOther() {
		return tasteOther;
	}
	
	public void setTasteOther(String tasteOther) {
		this.tasteOther = tasteOther;
	}
	
	public String getVolume() {
		return volume;
	}
	
	public void setVolume(String volume) {
		this.volume = volume;
	}
	
	public String getRating() {
		return rating;
	}
	
	public void setRating(String rating) {
		this.rating = rating;
	}

}
