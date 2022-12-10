package cs.skuniv.HCH.dto;

import java.time.LocalDateTime;

public class Coffee {
	
	private int num;				// 1. 등록 번호
	private String category;		// 2. 카테고리
	private String name;			// 3. 제품명(원두명)
	private String manufacturer;	// 4. 제조사
	private int nation;
	private int price;				// 5. 가격
	private String roastlevel;		// 6. 로스팅
	private int taste;			// 7. 맛
	private int volume;				// 8. 용량
	private double rating;			// 9. 평점
	private double ratingsum;		// 10. 평점 합계 : 평점 평균 계산에 사용
	private String review;			// 11. 후기
	private LocalDateTime regdate;	// 12. 등록일
	private String registrant;		// 13. 게시자
	private String filename;		// 14. 사진 파일
	private int favorite;			// 15. 좋아요 개수
	private int comment;			// 16. 댓글 개수 + 1 : 평점 평균 계산에 사용
	
	public Coffee(String category, String name, String manufacturer, int nation,
			int price, String roastlevel, int taste,
			int volume, double rating, double ratingsum,
			String review, LocalDateTime regdate,
			String registrant, String filename) {
		this.category = category;
		this.name = name;
		this.manufacturer = manufacturer;
		this.nation = nation;
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
			double rating, String registrant, String filename, int favorite) {
		this.num = num;
		this.name = name;
		this.manufacturer = manufacturer;
		this.rating = rating;
		this.registrant = registrant;
		this.filename = filename;
		this.favorite = favorite;
	}
	
	/* 게시물 */
	public Coffee(int num, String category, String name, 
			String manufacturer, int nation, int price, String roastlevel, 
			int taste, int volume, double rating, 
			double ratingsum, String review, LocalDateTime regdate, 
			String registrant, String filename, int favorite, int comment) {
		this(category, name, manufacturer, nation, price, roastlevel, taste, volume, rating, ratingsum, review, regdate, registrant, filename);
		this.num = num;		
		this.favorite = favorite;
		this.comment = comment;
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

	public int getTaste() {
		return taste;
	}

	public void setTaste(int taste) {
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

	public int getFavorite() {
		return favorite;
	}

	public void setFavorite(int favorite) {
		this.favorite = favorite;
	}

	public int getComment() {
		return comment;
	}

	public void setComment(int comment) {
		this.comment = comment;
	}

	public int getNation() {
		return nation;
	}

	public void setNation(int nation) {
		this.nation = nation;
	}	

}
