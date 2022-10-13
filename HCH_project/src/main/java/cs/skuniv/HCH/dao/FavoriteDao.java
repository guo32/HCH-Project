package cs.skuniv.HCH.dao;

import java.sql.ResultSet;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import cs.skuniv.HCH.dto.Favorite;

public class FavoriteDao {
	
	private JdbcTemplate jdbcTemplate;
	
	public FavoriteDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	/* 관심 추가 */
	public void insert(Favorite favorite) {
		jdbcTemplate.update("insert into favorite(user, posting, category)" + " values(?,?,?)",
				favorite.getUser(), favorite.getPosting(), favorite.getCategory());
	}
	
	/* 관심 해제 */
	public void delete(Favorite favorite) {
		jdbcTemplate.update("delete from favorite where user = ? and posting = ? and category = ?",
				favorite.getUser(), favorite.getPosting(), favorite.getCategory());
	}
	
	/* 사용자별 관심 목록 검색 */
	public List<Favorite> selectUser(String user) {
		List<Favorite> results = jdbcTemplate.query("select * from favorite where user = ?", (ResultSet rs, int rowNum)->{
			Favorite favorite = new Favorite(
					rs.getString("user"),
					rs.getInt("posting"),
					rs.getString("category"));
			return favorite;
		}, user);
		
		return results;
	}
	
	public Favorite selectFavorite(String user, int posting, String category) {
		List<Favorite> results = jdbcTemplate.query("select * from favorite where user = ? and posting = ? and category = ?", (ResultSet rs, int rowNum)->{
			Favorite favorite = new Favorite(
					rs.getString("user"),
					rs.getInt("posting"),
					rs.getString("category"));
			return favorite;
		}, user, posting, category);
		
		return results.isEmpty() ? null : results.get(0);
	}

}
