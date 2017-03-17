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

	private JdbcTemplate jdbcTemplate;

	public JDBCSpitterRepositoryImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public long count() {
		return jdbcTemplate.queryForLong("select count(id) from Spitter");
	}

	public Spitter save(Spitter spitter) {
		Long id = spitter.getId();
		if (id == null) {
			long spitterId = insertSpitterAndReturnId(spitter);
			return new Spitter(spitterId, spitter.getUsername(), spitter.getPassword(), spitter.getFirstname(),
					spitter.getLastName(), spitter.getEmail(), null);
		} else {
			jdbcTemplate.update(
					"update Spitter set username=?, password=?, fullname=?, email=?, updateByEmail=? where id=?",
					spitter.getUsername(), spitter.getPassword(), spitter.getUsername(), spitter.getEmail(), id);
		}
		return spitter;
	}

	/**
	 * Inserts a spitter using SimpleJdbcInsert. Involves no direct SQL and is
	 * able to return the ID of the newly created Spitter.
	 * 
	 * @param spitter
	 *            a Spitter to insert into the databse
	 * @return the ID of the newly inserted Spitter
	 */
	private long insertSpitterAndReturnId(Spitter spitter) {
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("Spitter");
		jdbcInsert.setGeneratedKeyName("id");
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("username", spitter.getUsername());
		args.put("password", spitter.getPassword());
		args.put("fullname", spitter.getUsername());
		args.put("email", spitter.getEmail());
		long spitterId = jdbcInsert.executeAndReturnKey(args).longValue();
		return spitterId;
	}

	/**
	 * Inserts a spitter using a simple JdbcTemplate update() call. Does not
	 * return the ID of the newly created Spitter.
	 * 
	 * @param spitter
	 *            a Spitter to insert into the database
	 */
	@SuppressWarnings("unused")
	private void insertSpitter(Spitter spitter) {
		jdbcTemplate.update(INSERT_SPITTER, spitter.getUsername(), spitter.getPassword(), spitter.getUsername(),
				spitter.getEmail());
	}

	public Spitter findOne(long id) {
		return jdbcTemplate.queryForObject(SELECT_SPITTER + " where id=?", new SpitterRowMapper(), id);
	}

	public Spitter findByUsername(String username) {
		return jdbcTemplate.queryForObject(
				"select id, username, password, fullname, email, updateByEmail from Spitter where username=?",
				new SpitterRowMapper(), username);
	}

	public List<Spitter> findAll() {
		return jdbcTemplate.query(
				"select id, username, password, fullname, email, updateByEmail from Spitter order by id",
				new SpitterRowMapper());
	}

	private static final class SpitterRowMapper implements RowMapper<Spitter> {
		public Spitter mapRow(ResultSet rs, int rowNum) throws SQLException {
			long id = rs.getLong("id");
			String username = rs.getString("username");
			String password = rs.getString("password");
			String fullName = rs.getString("firstName");
			String lastName = rs.getString("lastName");
			String email = rs.getString("email");
			boolean updateByEmail = rs.getBoolean("updateByEmail");
			return new Spitter(id, username, password, fullName, lastName, email, null);
		}
	}

	private static final String INSERT_SPITTER = "insert into Spitter (username, password, fullname, email, updateByEmail) values (?, ?, ?, ?, ?)";

	private static final String SELECT_SPITTER = "select id, username, password, fullname, email, updateByEmail from Spitter";

	@Override
	public Spitter findByUserName(String userName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(long id) {
		// TODO Auto-generated method stub

	}

}
