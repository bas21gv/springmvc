package com.cts.springmvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.springmvc.model.User;
import com.cts.springmvc.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserRepository userRepository;
	
	@Override
	public User getUser(int id) {
		return userRepository.getById(id);
	}

	@Override
	public List<User> getAll() {
		return userRepository.getAll();
	}

	@Override
	public void saveOrUpdate(User user) {
		if(getUser(user.getId()) == null){
			userRepository.save(user);
		}else{
			userRepository.update(user);
		}
	}

	@Override
	public void deleteUser(int id) {
		userRepository.delete(id);
	}

}
