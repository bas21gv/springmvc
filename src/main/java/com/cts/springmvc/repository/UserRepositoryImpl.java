package com.cts.springmvc.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.cts.springmvc.model.User;

@Repository
public class UserRepositoryImpl implements UserRepository{
	
	private NamedParameterJdbcTemplate npJdbcTemplate;
	
	@Autowired
	DataSource dataSource;

	@Override
	public User getById(int id) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		String query = "SELECT * from users WHERE id=:id";
		Map<String, Object> params = new HashMap<>();
		params.put("id", id);
		User user = null;
		try{
			user = npJdbcTemplate.queryForObject(query, params,new UserMapper());
		}catch (EmptyResultDataAccessException e) {
			return null;
		}		
		return user;
	}

	@Override
	public List<User> getAll() {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		String query = "SELECT * from users";
		List<User> userList = npJdbcTemplate.query(query, new UserMapper());
		return userList;
	}

	@Override
	public void save(User user) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		String query = "INSERT INTO users(name,age,salary) VALUES (:name,:age,:salary)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		npJdbcTemplate.update(query, getSqlParameterByModel(user),keyHolder);
		user.setId(keyHolder.getKey().intValue());
	}

	@Override
	public void update(User user) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		String query = "UPDATE users set name=:name,age=:age,salary=:salary WHERE id=:id";
		npJdbcTemplate.update(query, getSqlParameterByModel(user));
	}
	
	@Override
	public void delete(int id) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		String query = "DELETE FROM users WHERE id=:id";
		npJdbcTemplate.update(query, new MapSqlParameterSource("id", id));
	}
	
	private SqlParameterSource getSqlParameterByModel(User user) {

		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("id", user.getId());
		paramSource.addValue("name", user.getName());
		paramSource.addValue("age", user.getAge());
		paramSource.addValue("salary", user.getSalary());
		return paramSource;
	}
	
	private static final class UserMapper implements RowMapper<User>{
		@Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();
			user.setId(rs.getInt("id"));
			user.setName(rs.getString("name"));
			user.setAge(rs.getInt("age"));
			user.setSalary(rs.getDouble("salary"));
			return user;
		}
	}


}
