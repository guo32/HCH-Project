package cs.skuniv.HCH.dao;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import cs.skuniv.HCH.dto.Coffee;
import cs.skuniv.HCH.dto.Comment;

public class CommentDao {
	
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private CoffeeDao coffeeDao;
	
	public CommentDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
		
	/* 댓글 삽입 */
	public void insert(final Comment comment) {
		if(comment.getCategory().equals("cb")) {
			Coffee coffee = coffeeDao.selectByNum(comment.getPosting());
			coffee.setRatingsum(coffee.getRatingsum() + comment.getRating());
			coffeeDao.updateRatingSum(coffee);
		}
		
		jdbcTemplate.update("insert into comment(id, registrant, posting, category, content, rating, regdate)" + " values(?,?,?,?,?,?,?)",
				comment.getId(), comment.getRegistrant(), comment.getPosting(),
				comment.getCategory(), comment.getContent(), comment.getRating(),
				comment.getRegdate());
	}
	
	/* 댓글 삭제 */
	public void delete(final Comment comment) {
		if(comment.getCategory().equals("cb")) {
			Coffee coffee = coffeeDao.selectByNum(comment.getPosting());
			coffee.setRatingsum(coffee.getRatingsum() - comment.getRating());
			coffeeDao.updateRatingSum(coffee);
		}
		
		jdbcTemplate.update("delete from comment where id = ?", comment.getId());
	}
	
	/* 댓글 검색 */
	public Comment selectById(int id) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
		List<Comment> results = jdbcTemplate.query("select * from comment where id = ?", (ResultSet rs, int rowNum)->{
			Comment comment = new Comment(
					rs.getInt("id"),
					rs.getString("registrant"),
					rs.getInt("posting"),
					rs.getString("category"),
					rs.getString("content"),
					rs.getDouble("rating"),
					LocalDateTime.parse(rs.getString("regdate"), format));
			return comment;
		}, id);
		
		return results.isEmpty() ? null : results.get(0);
	}
	
	/* 게시물별 댓글 검색 */
	public List<Comment> selectByPost(int post, String category) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
		List<Comment> results = jdbcTemplate.query("select * from comment where posting = ? and category = ?", (ResultSet rs, int rowNum)->{
			Comment comment = new Comment(
					rs.getInt("id"),
					rs.getString("registrant"),
					rs.getInt("posting"),
					rs.getString("category"),
					rs.getString("content"),
					rs.getDouble("rating"),
					LocalDateTime.parse(rs.getString("regdate"), format));
			return comment;
		}, post, category);
		
		return results;
	}

}
