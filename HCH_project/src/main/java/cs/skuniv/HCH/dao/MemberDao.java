package cs.skuniv.HCH.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import cs.skuniv.HCH.dto.Member;

public class MemberDao {
	
	private JdbcTemplate jdbcTemplate;
	
	public MemberDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	/* id 검색 */
	public Member selectById(String id) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
		List<Member> results = jdbcTemplate.query("select * from member where id = ?", new RowMapper<Member>() {
			public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
				Member member = new Member(rs.getString("id"), 
						rs.getString("password"), 
						rs.getString("email"),
						rs.getString("name"),
						rs.getDate("birth"),
						LocalDateTime.parse(rs.getString("regdate"), format));
				return member;
			}
		}, id);
		
		return results.isEmpty() ? null : results.get(0);
	}
	
	/* email 검색 */
	public String selectByEmail(String email) {
		List<String> results = jdbcTemplate.query("select id from member where email = ?", new RowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("id");
			}
		}, email);
		
		return results.isEmpty() ? null : results.get(0);
	}
	
	/* 멤버 삽입 */
	public void insert(final Member member) {
		jdbcTemplate.update("insert into member(id, password, email, name, birth, regdate)" + " values(?,?,?,?,?,?)",
				member.getId(), member.getPassword(), member.getEmail(), 
				member.getName(), member.getBirth(), member.getRegdate());
	}
	
	/* 멤버 정보 수정 */
	public void update(final Member member) {
		jdbcTemplate.update("update member set password = ?, email = ?, name = ?, birth = ?" + " where id = ?", 
				member.getPassword(), member.getEmail(), member.getName(),
				member.getBirth(), member.getId());
	}
	
	/* 멤버 삭제 */
	public void delete(final Member member) {
		jdbcTemplate.update("delete from member where id = ?", member.getId());
	}

}