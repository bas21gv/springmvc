package com.cts.springmvc.repository;

import java.util.List;

import com.cts.springmvc.model.User;

public interface UserRepository {

	public User getById(int id);
	public List<User> getAll();
	public void save(User user);
	public void update(User user);
	public void delete(int id);
}
