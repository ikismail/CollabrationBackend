package com.cart.dao;

import java.util.List;

import com.cart.model.User;

public interface UserDao {

	List<User> getAllUser();
	
	User getById(String id);
	
	void saveUser(User user);
	
	void updateUser(User user);
}
