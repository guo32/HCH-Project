package cs.skuniv.HCH.dto;

import java.time.LocalDateTime;

public class Coffee {
	
	private int num;				// 1. 등록 번호
	private String category;		// 2. 카테고리
	private String name;			// 3. 제품명(원두명)
	private String manufacturer;	// 4. 제조사
	private int price;				// 5. 가격
	private String roastlevel;		// 6. 로스팅
	private String taste;			// 7. 맛
	private int volume;				// 8. 용량
	private double rating;			// 9. 평점
	private double ratingsum;
	private String review;			// 10. 후기
	private LocalDateTime regdate;	// 11. 등록일
	private String registrant;		// 12. 게시자
	private String filename;		// 13. 사진 파일
	private int like;				// 14. 좋아요 개수
	
	public Coffee(String category, String name, String manufacturer,
			int price, String roastlevel, String taste,
			int volume, double rating, double ratingsum,
			String review, LocalDateTime regdate,
			String registrant, String filename) {
		this.category = category;
		this.name = name;
		this.manufacturer = manufacturer;
		this.price = price;
		this.roastlevel = roastlevel;
		this.taste = taste;
		this.volume = volume;
		this.rating = rating;
		this.ratingsum = ratingsum;
		this.review = review;
		this.regdate = regdate;
		this.registrant = registrant;
		this.filename = filename;
	}
	
	/* 게시물 미리보기 */
	public Coffee(int num, String name, String manufacturer, 
			double rating, String registrant, String filename, int like) {
		this.num = num;
		this.name = name;
		this.manufacturer = manufacturer;
		this.rating = rating;
		this.registrant = registrant;
		this.filename = filename;
		this.like = like;
	}
	
	/* 게시물 */
	public Coffee(int num, String category, String name, 
			String manufacturer, int price, String roastlevel, 
			String taste, int volume, double rating, 
			double ratingsum, String review, LocalDateTime regdate, 
			String registrant, String filename, int like) {
		this.num = num;
		this.category = category;
		this.name = name;
		this.manufacturer = manufacturer;
		this.price = price;
		this.roastlevel = roastlevel;
		this.taste = taste;
		this.volume = volume;
		this.rating = rating;
		this.ratingsum = ratingsum;
		this.review = review;
		this.regdate = regdate;
		this.registrant = registrant;
		this.filename = filename;
		this.like = like;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
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

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
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

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
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

	public int getLike() {
		return like;
	}

	public void setLike(int like) {
		this.like = like;
	}	

}
