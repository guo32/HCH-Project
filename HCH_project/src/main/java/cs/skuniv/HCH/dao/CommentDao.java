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
import cs.skuniv.HCH.dto.Etc;
import cs.skuniv.HCH.dto.Machine;

public class CommentDao {
	
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private CoffeeDao coffeeDao;
	@Autowired
	private MachineDao machineDao;
	@Autowired
	private EtcDao etcDao;
	
	private DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
	public CommentDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
		
	/* 댓글 삽입 */
	public void insert(final Comment comment) {
		/* 원두 게시물 평점 수정 */
		if(comment.getCategory().equals("cb")) {
			Coffee coffee = coffeeDao.selectByNum(comment.getPosting());
			coffee.setRatingsum(coffee.getRatingsum() + comment.getRating());
			coffeeDao.updateRatingSumAndCommentCount(coffee, 1);
		}
		/* 가전 게시물 평점 수정 */
		if(comment.getCategory().equals("cm")) {
			Machine machine = machineDao.selectById(comment.getPosting());
			machine.setRatingsum(machine.getRatingsum() + comment.getRating());
			machineDao.updateRatingSumAndCommentCount(machine, 1);
		}
		/* 기타 게시물 평점 수정 */
		if(comment.getCategory().equals("ce")) {
			Etc etc = etcDao.selectById(comment.getPosting());
			etc.setRatingsum(etc.getRatingsum() + comment.getRating());
			etcDao.updateRatingSumAndCommentCount(etc, 1);
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
			coffeeDao.updateRatingSumAndCommentCount(coffee, -1);
		}
		if(comment.getCategory().equals("cm")) {
			Machine machine = machineDao.selectById(comment.getPosting());
			machine.setRatingsum(machine.getRatingsum() - comment.getRating());
			machineDao.updateRatingSumAndCommentCount(machine, -1);
		}
		if(comment.getCategory().equals("ce")) {
			Etc etc = etcDao.selectById(comment.getPosting());
			etc.setRatingsum(etc.getRatingsum() - comment.getRating());
			etcDao.updateRatingSumAndCommentCount(etc, -1);
		}
		
		jdbcTemplate.update("delete from comment where id = ?", comment.getId());
	}
	
	/* 댓글 검색 */
	public Comment selectById(int id) {
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
		// DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
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
	
	/* 게시물별 댓글 수(평균 계산 시 사용) */
	public int selectByPostCommentCount(int post, String category) {		
		int count = jdbcTemplate.queryForObject("select count(*) from comment where posting = ? and category = ?", Integer.class , post, category);
		
		return count;
	}

}
