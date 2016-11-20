package com.cart.dao;

import java.util.List;

import com.cart.model.User;

public interface UserDao {

	List<User> getAllUser();
	
	User getById(String id);
	
	boolean saveUser(User user);
	
	boolean updateUser(User user);
	
	User validate(String id, String password);
}
