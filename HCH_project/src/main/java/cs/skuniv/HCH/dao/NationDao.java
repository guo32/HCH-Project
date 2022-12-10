package cs.skuniv.HCH.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import cs.skuniv.HCH.dto.Nation;

public class NationDao {
	
	private JdbcTemplate jdbcTemplate;
	private RowMapper<Nation> nationRowMapper = new RowMapper<Nation>() {
		public Nation mapRow(ResultSet rs, int rowNum) throws SQLException {
			Nation nation = new Nation(rs.getInt("nationid"), rs.getString("country"), rs.getString("group"));
			return nation;
		}
	};	
	
	public NationDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public List<Nation> selectAll() {
		List<Nation> results = jdbcTemplate.query("select * from nation", nationRowMapper);
		
		return results;
	}
	
	public List<Nation> selectByGroup(String group) {
		List<Nation> results = jdbcTemplate.query("select * from nation where group = ?", nationRowMapper, group);
		
		return results;
	}
	
	public Nation selectById(int nationid) {
		List<Nation> results = jdbcTemplate.query("select * from nation where nationid = ?", nationRowMapper, nationid);
		
		return results.isEmpty() ? null : results.get(0);
	}

}
