package cs.skuniv.HCH.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import cs.skuniv.HCH.dto.Note;

public class NoteDao {
	
	private JdbcTemplate jdbcTemplate;
	private RowMapper<Note> noteRowMapper = new RowMapper<Note>() {
		public Note mapRow(ResultSet rs, int rowNum) throws SQLException {
			Note note = new Note(rs.getInt("noteid"), rs.getString("major"), rs.getString("middle"), rs.getString("minor"));
			return note;
		}
	};
	private RowMapper<String> majorRowMapper = new RowMapper<String>() {
		public String mapRow(ResultSet rs, int rowNum) throws SQLException {
			String major = rs.getString("major");
			return major;
		}
	};
	
	public NoteDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	/* 전체 */
	public List<Note> selectAll() {
		List<Note> results = jdbcTemplate.query("select * from note", noteRowMapper);
		
		return results;
	}
	
	public List<String> selectAllMajor() {
		List<String> data = jdbcTemplate.query("select major from note", majorRowMapper);
		Set<String> setData = new HashSet<>(data);
		List<String> results = new ArrayList<>(setData);
		
		return results;
	}
	
	public List<Note> selectByMajor(String major) {
		List<Note> results = jdbcTemplate.query("select * from note where major = ?", noteRowMapper, major);
		
		return results;
	}
	
	public List<Note> selectByMiddle(String middle) {
		List<Note> results = jdbcTemplate.query("select * from note where middle = ?", noteRowMapper, middle);
		
		return results;
	}
	
	public Note selectById(int noteid) {
		List<Note> results = jdbcTemplate.query("select * from note where noteid = ?", noteRowMapper, noteid);
		
		return results.isEmpty() ? null : results.get(0);
	}

}
