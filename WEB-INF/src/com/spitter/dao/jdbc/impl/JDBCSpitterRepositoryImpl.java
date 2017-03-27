package com.spitter.dao.jdbc.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import com.spitter.dao.SpitterRepositoryDao;
import com.spitter.orm.domain.Spitter;

public class JDBCSpitterRepositoryImpl implements SpitterRepositoryDao {

	private static final String SELECT_SPITTER = "select id, username, password, firstname, lastName, email, role from Spitter where id=?";

	private static final String UPDATE_SPITTER = "update Spitter set username=?, password=?, firstname=?, lastName=?, email=?, role=? where id=?";

	private JdbcTemplate jdbcTemplate;

	public JDBCSpitterRepositoryImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public long count() {
		return jdbcTemplate.queryForObject("select count(id) from Spitter", Long.class);
	}

	@Override
	public Spitter save(Spitter spitter) {
		Long id = spitter.getId();
		if (id == null) {
			long spitterId = insertSpitterAndReturnId(spitter);
			return new Spitter(spitterId, spitter.getUsername(), spitter.getPassword(), spitter.getFirstname(),
					spitter.getLastName(), spitter.getEmail(), null);
		} else {
			jdbcTemplate.update(UPDATE_SPITTER, spitter.getUsername(), spitter.getPassword(), spitter.getFirstname(),
					spitter.getLastName(), spitter.getEmail(), spitter.getRole(), id);
		}
		return spitter;
	}

	@Override
	public Spitter findOne(long id) {
		return jdbcTemplate.queryForObject(SELECT_SPITTER, new SpitterRowMapper(), id);
	}

	@Override
	public Spitter findByUserName(String userName) {
		return jdbcTemplate.queryForObject(
				"select id, username, password, firstname, lastName, email, role from Spitter where username=?",
				new SpitterRowMapper(), userName);
	}

	@Override
	public List<Spitter> findAll() {
		return jdbcTemplate.query(
				"select id, username, password, firstname, lastName, email, role from Spitter order by id",
				new SpitterRowMapper());
	}

	@Override
	public void delete(long id) {
		jdbcTemplate.update("delete from spitter where id=?", id);
	}

	private long insertSpitterAndReturnId(Spitter spitter) {
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("Spitter");
		jdbcInsert.setGeneratedKeyName("id");
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("username", spitter.getUsername());
		args.put("password", spitter.getPassword());
		args.put("firstname", spitter.getFirstname());
		args.put("lastName", spitter.getLastName());
		args.put("email", spitter.getEmail());
		args.put("role", spitter.getRole());
		long spitterId = jdbcInsert.executeAndReturnKey(args).longValue();
		return spitterId;
	}

	private static final class SpitterRowMapper implements RowMapper<Spitter> {
		public Spitter mapRow(ResultSet rs, int rowNum) throws SQLException {
			long id = rs.getLong("id");
			String username = rs.getString("username");
			String password = rs.getString("password");
			String firstname = rs.getString("firstName");
			String lastName = rs.getString("lastName");
			String email = rs.getString("email");
			String role = rs.getString("role");
			return new Spitter(id, username, password, firstname, lastName, email, role);
		}
	}
}
