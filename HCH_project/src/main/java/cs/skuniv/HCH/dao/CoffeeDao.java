package cs.skuniv.HCH.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import cs.skuniv.HCH.dto.Coffee;

public class CoffeeDao {
	
	private JdbcTemplate jdbcTemplate;
	
	public CoffeeDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	/* 제품(coffee) 삽입 */
	public void insert(final Coffee coffee) {
		jdbcTemplate.update("insert into coffee(num, category, name, manufacturer, price, roastlevel, taste, volume, rating, ratingsum, review, regdate, registrant, filename)" + " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
				coffee.getNum(), coffee.getCategory(), coffee.getName(),
				coffee.getManufacturer(), coffee.getPrice(), coffee.getRoastlevel(),
				coffee.getTaste(), coffee.getVolume(), coffee.getRating(), coffee.getRatingsum(), coffee.getReview(),
				coffee.getRegdate(), coffee.getRegistrant(), coffee.getFilename());
	}
	
	/* 유저 아이디별 등록 게시물(coffee) 검색 */
	public List<Coffee> selectByUserId(String registrant) {
		List<Coffee> results = jdbcTemplate.query("select * from coffee where registrant = ?", (ResultSet rs, int rowNum)->{
			Coffee coffee = new Coffee(rs.getInt("num"),
					rs.getString("name"),
					rs.getString("manufacturer"),
					rs.getDouble("rating"),
					rs.getString("registrant"),
					rs.getString("filename"),
					rs.getInt("favorite"));
			return coffee;
		}, registrant);
		
		return results;
	}

	/* num(게시물 번호) 검색 */
	public Coffee selectByNum(int num) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
		List<Coffee> results = jdbcTemplate.query("select * from coffee where num = ?", new RowMapper<Coffee>() {
			public Coffee mapRow(ResultSet rs, int rowNum) throws SQLException {
				Coffee coffee = new Coffee(rs.getInt("num"), rs.getString("category"), rs.getString("name"), 
						rs.getString("manufacturer"), rs.getInt("price"), rs.getString("roastlevel"),
						rs.getString("taste"), rs.getInt("volume"), rs.getDouble("rating"),
						rs.getDouble("ratingsum"), rs.getString("review"),
						LocalDateTime.parse(rs.getString("regdate"), format),
						rs.getString("registrant"), rs.getString("filename"), rs.getInt("favorite"));
				return coffee;
			}
		}, num);
		
		return results.isEmpty() ? null : results.get(0);
	}
	
	/* 전체 목록 */
	public List<Coffee> selectAll() {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
		List<Coffee> results = jdbcTemplate.query("select * from coffee", new RowMapper<Coffee>() {
			public Coffee mapRow(ResultSet rs, int rowNum) throws SQLException {
				Coffee coffee = new Coffee(rs.getInt("num"), rs.getString("category"), rs.getString("name"), 
						rs.getString("manufacturer"), rs.getInt("price"), rs.getString("roastlevel"),
						rs.getString("taste"), rs.getInt("volume"), rs.getDouble("rating"),
						rs.getDouble("ratingsum"), rs.getString("review"),
						LocalDateTime.parse(rs.getString("regdate"), format),
						rs.getString("registrant"), rs.getString("filename"), rs.getInt("favorite"));
				return coffee;
			}
		});
		
		return results;
	}
	
	/* 제조사별 검색 */
	public List<Coffee> selectManufacturer(String manufacturer) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
		List<Coffee> results = jdbcTemplate.query("select * from coffee where manufacturer = ?", new RowMapper<Coffee>() {
			public Coffee mapRow(ResultSet rs, int rowNum) throws SQLException {
				Coffee coffee = new Coffee(rs.getInt("num"), rs.getString("category"), rs.getString("name"), 
						rs.getString("manufacturer"), rs.getInt("price"), rs.getString("roastlevel"),
						rs.getString("taste"), rs.getInt("volume"), rs.getDouble("rating"),
						rs.getDouble("ratingsum"), rs.getString("review"),
						LocalDateTime.parse(rs.getString("regdate"), format),
						rs.getString("registrant"), rs.getString("filename"), rs.getInt("favorite"));
				return coffee;
			}
		}, manufacturer);
		
		return results;
	}
	
	public List<Coffee> selectSearchString(String search) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
		String searchData = "%" + search + "%";
		String sql = "select * from coffee where ";
		sql += "name like ?";
		
		List<Coffee> results = jdbcTemplate.query(sql, new RowMapper<Coffee>() {
			public Coffee mapRow(ResultSet rs, int rowNum) throws SQLException {
				Coffee coffee = new Coffee(rs.getInt("num"), rs.getString("category"), rs.getString("name"), 
						rs.getString("manufacturer"), rs.getInt("price"), rs.getString("roastlevel"),
						rs.getString("taste"), rs.getInt("volume"), rs.getDouble("rating"),
						rs.getDouble("ratingsum"), rs.getString("review"),
						LocalDateTime.parse(rs.getString("regdate"), format),
						rs.getString("registrant"), rs.getString("filename"), rs.getInt("favorite"));
				return coffee;
			}
		}, searchData);
		
		return results;
	}
		
	/* 제품(coffee) 수정 */
	public void update(final Coffee coffee) {
		jdbcTemplate.update("update coffee set name = ?, manufacturer = ?, price = ?, roastlevel = ?, taste = ?, volume = ?, review = ?, filename = ?" + " where num = ?",
				coffee.getName(), coffee.getManufacturer(), coffee.getPrice(), 
				coffee.getRoastlevel(),	coffee.getTaste(), coffee.getVolume(), 
				coffee.getReview(),	coffee.getFilename(), coffee.getNum());
	}
	
	/* 평점 합계 수정 */
	public void updateRatingSum(final Coffee coffee) {
		jdbcTemplate.update("update coffee set ratingsum = ? where num = ?", coffee.getRatingsum(), coffee.getNum());
	}
	
	/* 좋아요 개수 수정 */
	public void updateFavorite(final Coffee coffee) {
		jdbcTemplate.update("update coffee set favorite = ? where num = ?", coffee.getFavorite(), coffee.getNum());
	}
	
	/* 제품(coffee) 삭제 */
	public void delete(final Coffee coffee) {
		jdbcTemplate.update("delete from coffee where num = ?", coffee.getNum());
	}	

}
