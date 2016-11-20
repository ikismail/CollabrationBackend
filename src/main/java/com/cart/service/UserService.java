package com.cart.service;

import java.util.List;

import com.cart.model.User;

public interface UserService {

	List<User> getAllUser();

	User getById(String id);

	boolean saveUser(User user);

	boolean updateUser(User user);
	
	User validate(String id,String password);
}
