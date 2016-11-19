package com.cart.service;

import java.util.List;

import com.cart.model.User;

public interface UserService {

	List<User> getAllUser();

	User getById(String id);

	void saveUser(User user);

	void updateUser(User user);
}
