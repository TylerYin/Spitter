package com.spitter.dao.jdbc.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import com.spitter.dao.SpittleRepositoryDao;
import com.spitter.orm.domain.Spitter;
import com.spitter.orm.domain.Spittle;

public class JDBCSpittleRepositoryImpl implements SpittleRepositoryDao {

	private static final String SELECT_SPITTLE = "select sp.id, s.id as spitterId, s.username, s.password, s.firstname, s.lastName, s.email, s.role, sp.message, sp.postedTime from Spittle sp, Spitter s where sp.spitter = s.id";
	private static final String SELECT_SPITTLES_BY_SPITTER_ID = SELECT_SPITTLE
			+ " and s.id=? order by sp.postedTime desc";
	private static final String SELECT_RECENT_SPITTLES = SELECT_SPITTLE + " order by sp.postedTime desc limit ?";

	private JdbcTemplate jdbcTemplate;

	public JDBCSpittleRepositoryImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public long count() {
		return jdbcTemplate.queryForObject("select count(id) from Spittle", Long.class);
	}

	@Override
	public List<Spittle> findRecent() {
		return findRecent(10);
	}

	@Override
	public List<Spittle> findRecent(int count) {
		return jdbcTemplate.query(SELECT_RECENT_SPITTLES, new SpittleRowMapper(), count);
	}

	@Override
	public Spittle findOne(long id) {
		return jdbcTemplate.queryForObject(SELECT_SPITTLES_BY_SPITTER_ID, new SpittleRowMapper(), id);
	}

	@Override
	public Spittle save(Spittle spittle) {
		long spittleId = insertSpittleAndReturnId(spittle);
		return new Spittle(spittleId, spittle.getSpitter(), spittle.getMessage(), spittle.getPostedTime());
	}

	@Override
	public List<Spittle> findBySpitterId(long spitterId) {
		return jdbcTemplate.query(SELECT_SPITTLES_BY_SPITTER_ID, new SpittleRowMapper(), spitterId);
	}

	private long insertSpittleAndReturnId(Spittle spittle) {
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("Spittle");
		jdbcInsert.setGeneratedKeyName("id");
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("spitter", spittle.getSpitter().getId());
		args.put("message", spittle.getMessage());
		args.put("postedTime", spittle.getPostedTime());
		long spittleId = jdbcInsert.executeAndReturnKey(args).longValue();
		return spittleId;
	}

	@Override
	public void delete(long id) {
		jdbcTemplate.update("delete from Spittle where id=?", id);
	}

	@Override
	public List<Spittle> findAll() {
		return jdbcTemplate.query(
				"select id, username, password, firstname, lastName, email, role from Spitter order by id",
				new SpittleRowMapper());
	}

	private static final class SpittleRowMapper implements RowMapper<Spittle> {
		public Spittle mapRow(ResultSet rs, int rowNum) throws SQLException {
			long id = rs.getLong("id");
			String message = rs.getString("message");
			Date postedTime = rs.getTimestamp("postedTime");
			long spitterId = rs.getLong("spitterId");
			String username = rs.getString("username");
			String password = rs.getString("password");
			String firstname = rs.getString("firstname");
			String lastName = rs.getString("lastName");
			String email = rs.getString("email");
			String role = rs.getString("role");
			Spitter spitter = new Spitter(spitterId, username, password, firstname, lastName, email, role);
			return new Spittle(id, spitter, message, postedTime);
		}
	}
}
