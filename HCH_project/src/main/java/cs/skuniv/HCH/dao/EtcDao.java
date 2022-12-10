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

import cs.skuniv.HCH.dto.Etc;

public class EtcDao {
	
	private JdbcTemplate jdbcTemplate;
	DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	private RowMapper<Etc> etcRowMapper = new RowMapper<Etc>() {
		public Etc mapRow(ResultSet rs, int rowNum) throws SQLException {
			Etc etc = new Etc(rs.getInt("id"),
					rs.getString("category"),
					rs.getString("name"),
					rs.getString("brand"),
					rs.getInt("price"),
					rs.getString("type"),
					rs.getDouble("rating"),
					rs.getDouble("ratingsum"),
					rs.getString("review"),
					rs.getInt("favorite"),
					LocalDateTime.parse(rs.getString("regdate"), format),
					rs.getString("registrant"),
					rs.getString("filename"),
					rs.getInt("comment"));
			return etc;
		}
	};
	
	public EtcDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public void insert(final Etc etc) {
		jdbcTemplate.update("insert into etc(id, category, name, brand, price, type, rating, ratingsum, review, regdate, registrant, filename)" + " values(?,?,?,?,?,?,?,?,?,?,?,?)",
				etc.getId(), etc.getCategory(), etc.getName(),
				etc.getBrand(), etc.getPrice(), etc.getType(),
				etc.getRating(), etc.getRatingsum(),
				etc.getReview(), etc.getRegdate(),
				etc.getRegistrant(), etc.getFilename());
	}
	
	/* 게시자(유저 아이디)별 게시물 검색*/
	public List<Etc> selectByRegistrant(String registrant) {
		List<Etc> results = jdbcTemplate.query("select * from etc where registrant = ?", (ResultSet rs, int rowNum)->{
			Etc etc = new Etc(rs.getInt("id"),
					rs.getString("category"),
					rs.getString("name"),
					rs.getString("brand"),
					rs.getDouble("rating"),
					rs.getString("registrant"),
					rs.getString("filename"),
					rs.getInt("favorite"));
			return etc;
		}, registrant);
		
		return results;
	}
	
	/* 제품 id 검색 */
	public Etc selectById(int id) {		
		List<Etc> results = jdbcTemplate.query("select * from etc where id = ?", etcRowMapper, id);
		
		return results.isEmpty() ? null : results.get(0);
	}
	
	public List<Etc> selectAll() {
		List<Etc> results = jdbcTemplate.query("select * from etc", etcRowMapper);
		
		return results;
	}
	
	public List<Etc> selectBrand(String brand) {
		List<Etc> results = jdbcTemplate.query("select * from etc where brand = ?", etcRowMapper, brand);
		
		return results;
	}
	
	public List<Etc> selectSearchString(String search) {
		String searchData = "%" + search + "%";
		String sql = "select * from etc where ";
		sql += "name like ? or brand like ? or review like ?";
		
		List<Etc> results = jdbcTemplate.query(sql, etcRowMapper, searchData, searchData, searchData);
		
		return results;
	}
	
	/* 상세 검색 */
	public List<Etc> selectSearchDetail(String sqlData, List<String> data) {
		String sql = "select * from etc where" + sqlData;
		
		List<Etc> results = new ArrayList<>();
		
		/* list 내 아이템 개수 별로 분리 */
		if(data.size() == 1)
			results = jdbcTemplate.query(sql, etcRowMapper, data.get(0));
		
		else if(data.size() == 2)
			results = jdbcTemplate.query(sql, etcRowMapper, data.get(0), data.get(1));
				
		return results;
	}
	
	/* 제품(Etc) 수정 */
	public void update(final Etc etc) {
		jdbcTemplate.update("update etc set name = ?, brand = ?, price = ?, type = ?, review = ?, filename = ?" + " where id = ?",
				etc.getName(),
				etc.getBrand(),
				etc.getPrice(),
				etc.getType(),
				etc.getReview(),
				etc.getFilename(),
				etc.getId());
	}
	
	/* 평점 합계 수정 */
	public void updateRatingSumAndCommentCount(final Etc etc, int vary) {
		jdbcTemplate.update("update etc set ratingsum = ?, comment = ? where id = ?", etc.getRatingsum(), etc.getComment() + vary, etc.getId());
	}
	
	/* 좋아요 개수 수정 */
	public void updateFavorite(final Etc etc) {
		jdbcTemplate.update("update etc set favorite = ? where id = ?", etc.getFavorite(), etc.getId());
	}
	
	public void delete(final Etc etc) {
		jdbcTemplate.update("delete from etc where id = ?", etc.getId());
	}

}
