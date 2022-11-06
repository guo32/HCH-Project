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

import cs.skuniv.HCH.dto.Machine;

public class MachineDao {
	
	private JdbcTemplate jdbcTemplate;
	private DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	private RowMapper<Machine> machineRowMapper = new RowMapper<Machine>() {
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
					rs.getString("filename"),
					rs.getInt("comment"));
			return machine;
		}
	};
	
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
		List<Machine> results = jdbcTemplate.query("select * from machine where id = ?", machineRowMapper, id);
		
		return results.isEmpty() ? null : results.get(0);
	}
	
	public List<Machine> selectAll() {
		List<Machine> results = jdbcTemplate.query("select * from machine", machineRowMapper);
		
		return results;
	}
	
	public List<Machine> selectBrand(String brand) {
		List<Machine> results = jdbcTemplate.query("select * from machine where brand = ?", machineRowMapper, brand);
		
		return results;
	}
	
	public List<Machine> selectSearchString(String search) {
		String searchData = "%" + search + "%";
		String sql = "select * from machine where ";
		sql += "name like ? or brand like ? or review like ?";
		
		List<Machine> results = jdbcTemplate.query(sql, machineRowMapper, searchData, searchData, searchData);
		
		return results;
	}
	
	/* 상세 검색 */
	public List<Machine> selectSearchDetail(String sqlData, List<String> data) {
		String sql = "select * from machine where" + sqlData;
		
		List<Machine> results = new ArrayList<>();
		
		/* list 내 아이템 개수 별로 분리 */
		if(data.size() == 1)
			results = jdbcTemplate.query(sql, machineRowMapper, data.get(0));
		
		else if(data.size() == 2)
			results = jdbcTemplate.query(sql, machineRowMapper, data.get(0), data.get(1));
		
		else if(data.size() == 3)
			results = jdbcTemplate.query(sql, machineRowMapper, data.get(0), data.get(1), data.get(2));
		
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
	public void updateRatingSumAndCommentCount(final Machine machine, int vary) {
		jdbcTemplate.update("update machine set ratingsum = ?, comment = ? where id = ?", machine.getRatingsum(), machine.getComment() + vary,  machine.getId());
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
