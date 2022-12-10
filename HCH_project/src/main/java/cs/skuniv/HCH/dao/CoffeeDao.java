package cs.skuniv.HCH.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import cs.skuniv.HCH.dto.Coffee;

public class CoffeeDao {
	
	private JdbcTemplate jdbcTemplate;
	private DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	private RowMapper<Coffee> coffeeRowMapper = new RowMapper<Coffee>() {
		public Coffee mapRow(ResultSet rs, int rowNum) throws SQLException {
			Coffee coffee = new Coffee(rs.getInt("num"), rs.getString("category"), rs.getString("name"), 
					rs.getString("manufacturer"), rs.getInt("nation"), rs.getInt("price"), rs.getString("roastlevel"),
					rs.getInt("taste"), rs.getInt("volume"), rs.getDouble("rating"),
					rs.getDouble("ratingsum"), rs.getString("review"),
					LocalDateTime.parse(rs.getString("regdate"), format),
					rs.getString("registrant"), rs.getString("filename"), rs.getInt("favorite"), rs.getInt("comment"));
			return coffee;
		}
	};
	
	public CoffeeDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	/* 제품(coffee) 삽입 */
	public void insert(final Coffee coffee) {
		jdbcTemplate.update("insert into coffee(num, category, name, manufacturer, nation, price, roastlevel, taste, volume, rating, ratingsum, review, regdate, registrant, filename)" + " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
				coffee.getNum(), coffee.getCategory(), coffee.getName(),
				coffee.getManufacturer(), coffee.getNation(), coffee.getPrice(), coffee.getRoastlevel(),
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
		List<Coffee> results = jdbcTemplate.query("select * from coffee where num = ?", coffeeRowMapper, num);
		
		return results.isEmpty() ? null : results.get(0);
	}
	
	/* 전체 목록 */
	public List<Coffee> selectAll() {
		List<Coffee> results = jdbcTemplate.query("select * from coffee", coffeeRowMapper);
		
		return results;
	}
	
	/* 제조사별 검색 */
	public List<Coffee> selectManufacturer(String manufacturer) {
		List<Coffee> results = jdbcTemplate.query("select * from coffee where manufacturer = ?", coffeeRowMapper, manufacturer);
		
		return results;
	}
	
	/* 원산지별 검색 */
	public List<Coffee> selectNation(int nation) {
		List<Coffee> results = jdbcTemplate.query("select * from coffee where nation = ?", coffeeRowMapper, nation);
		
		return results;
	}
	
	/* 일반 검색 */
	public List<Coffee> selectSearchString(String search) {
		String searchData = "%" + search + "%";
		String sql = "select * from coffee where ";
		sql += "name like ? or manufacturer like ? or review like ?";
		
		List<Coffee> results = jdbcTemplate.query(sql, coffeeRowMapper, searchData, searchData, searchData);
		
		return results;
	}
	
	/* 상세 검색 */
	public List<Coffee> selectSearchDetail(String sqlData, List<String> data) {
		String sql = "select * from coffee where" + sqlData;
		
		List<Coffee> results = new ArrayList<>();
		
		/* list 내 아이템 개수 별로 분리 */
		if(data.size() == 1)
			results = jdbcTemplate.query(sql, coffeeRowMapper, data.get(0));
		
		if(data.size() == 2)
			results = jdbcTemplate.query(sql, coffeeRowMapper, data.get(0), data.get(1));
		
		if(data.size() == 3)
			results = jdbcTemplate.query(sql, coffeeRowMapper, data.get(0), data.get(1), data.get(2));
		
		return results;
	}
		
	/* 제품(coffee) 수정 */
	public void update(final Coffee coffee) {
		jdbcTemplate.update("update coffee set name = ?, manufacturer = ?, nation = ?, price = ?, roastlevel = ?, taste = ?, volume = ?, review = ?, filename = ?" + " where num = ?",
				coffee.getName(), coffee.getManufacturer(), coffee.getNation(), coffee.getPrice(), 
				coffee.getRoastlevel(),	coffee.getTaste(), coffee.getVolume(), 
				coffee.getReview(),	coffee.getFilename(), coffee.getNum());
	}
	
	/* 평점 합계 수정 */
	public void updateRatingSumAndCommentCount(final Coffee coffee, int vary) {
		jdbcTemplate.update("update coffee set ratingsum = ?, comment = ? where num = ?", coffee.getRatingsum(), coffee.getComment() + vary, coffee.getNum());
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
