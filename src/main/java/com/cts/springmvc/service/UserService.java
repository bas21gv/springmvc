package com.cts.springmvc.service;

import java.util.List;

import com.cts.springmvc.model.User;


public interface UserService {

	public User getUser(int id);
	public List<User> getAll();
	public void saveOrUpdate(User user);
	public void deleteUser(int id);
}
