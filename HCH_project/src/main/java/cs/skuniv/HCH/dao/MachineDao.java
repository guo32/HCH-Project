package cs.skuniv.HCH.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import cs.skuniv.HCH.dto.Machine;

public class MachineDao {
	
	private JdbcTemplate jdbcTemplate;
	
	public MachineDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	/* 제품(machine) 삽입 */
	public void insert(final Machine machine) {
		jdbcTemplate.update("insert into machine(id, category, name, brand, price, type, color, rating, ratingsum, review, regdate, registrant, filename)" + " values(?,?,?,?,?,?,?,?,?,?,?,?,?)",
				machine.getId(), machine.getCategory(), machine.getName(),
				machine.getBrand(), machine.getPrice(), machine.getType(),
				machine.getColor(), machine.getRating(), machine.getRatingsum(),
				machine.getReview(), machine.getRegdate(),
				machine.getRegistrant(), machine.getFilename());
	}
	
	/* 게시자(유저 아이디)별 게시물 검색*/
	public List<Machine> selectByRegistrant(String registrant) {
		List<Machine> results = jdbcTemplate.query("select * from machine where registrant = ?", (ResultSet rs, int rowNum)->{
			Machine machine = new Machine(rs.getInt("id"),
					rs.getString("category"),
					rs.getString("name"),
					rs.getString("brand"),
					rs.getDouble("rating"),
					rs.getString("registrant"),
					rs.getString("filename"),
					rs.getInt("favorite"));
			return machine;
		}, registrant);
		
		return results;
	}
	
	/* 제품 id 검색 */
	public Machine selectById(int id) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
		List<Machine> results = jdbcTemplate.query("select * from machine where id = ?", new RowMapper<Machine>() {
			public Machine mapRow(ResultSet rs, int rowNum) throws SQLException {
				Machine machine = new Machine(rs.getInt("id"),
						rs.getString("category"),
						rs.getString("name"),
						rs.getString("brand"),
						rs.getInt("price"),
						rs.getString("type"),
						rs.getString("color"),
						rs.getDouble("rating"),
						rs.getDouble("ratingsum"),
						rs.getString("review"),
						rs.getInt("favorite"),
						LocalDateTime.parse(rs.getString("regdate"), format),
						rs.getString("registrant"),
						rs.getString("filename"));
				return machine;
			}
		}, id);
		
		return results.isEmpty() ? null : results.get(0);
	}
	
	public List<Machine> selectAll() {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
		List<Machine> results = jdbcTemplate.query("select * from machine", new RowMapper<Machine>() {
			public Machine mapRow(ResultSet rs, int rowNum) throws SQLException {
				Machine machine = new Machine(rs.getInt("id"),
						rs.getString("category"),
						rs.getString("name"),
						rs.getString("brand"),
						rs.getInt("price"),
						rs.getString("type"),
						rs.getString("color"),
						rs.getDouble("rating"),
						rs.getDouble("ratingsum"),
						rs.getString("review"),
						rs.getInt("favorite"),
						LocalDateTime.parse(rs.getString("regdate"), format),
						rs.getString("registrant"),
						rs.getString("filename"));
				return machine;
			}
		});
		
		return results;
	}
	
	/* 제품(machine) 수정 */
	public void update(final Machine machine) {
		jdbcTemplate.update("update machine set name = ?, brand = ?, price = ?, type = ?, color = ?, review = ?, filename = ?" + " where id = ?",
				machine.getName(),
				machine.getBrand(),
				machine.getPrice(),
				machine.getType(),
				machine.getColor(),
				machine.getReview(),
				machine.getFilename(),
				machine.getId());
	}
	
	/* 평점 합계 수정 */
	public void updateRatingSum(final Machine machine) {
		jdbcTemplate.update("update machine set ratingsum = ? where id = ?", machine.getRatingsum(), machine.getId());
	}
	
	/* 좋아요 개수 수정 */
	public void updateFavorite(final Machine machine) {
		jdbcTemplate.update("update machine set favorite = ? where id = ?", machine.getFavorite(), machine.getId());
	}
	
	/* 제품(machine) 삭제 */
	public void delete(final Machine machine) {
		jdbcTemplate.update("delete from machine where id = ?", machine.getId());
	}

}
