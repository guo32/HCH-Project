package cs.skuniv.HCH.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import cs.skuniv.HCH.dto.Favorite;

public class FavoriteDao {
	
	private JdbcTemplate jdbcTemplate;
	private RowMapper<Favorite> favoriteRowMapper = new RowMapper<Favorite>() {
		public Favorite mapRow(ResultSet rs, int rowNum) throws SQLException {
			Favorite favorite = new Favorite(
					rs.getString("user"),
					rs.getInt("posting"),
					rs.getString("category"));
			return favorite;
		}
	};
	
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
		List<Favorite> results = jdbcTemplate.query("select * from favorite where user = ?", favoriteRowMapper, user);
		
		return results;
	}
	
	/* 관심 검색 */
	public Favorite selectFavorite(String user, int posting, String category) {
		List<Favorite> results = jdbcTemplate.query("select * from favorite where user = ? and posting = ? and category = ?", favoriteRowMapper, user, posting, category);
		
		return results.isEmpty() ? null : results.get(0);
	}
	
	/* 게시물별 관심 검색 */
	public List<Favorite> selectPosting(int posting, String category) {
		List<Favorite> results = jdbcTemplate.query("select * from favorite where posting = ? and category = ?", favoriteRowMapper, posting, category);
		
		return results;
	}

}
