package com.cart.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cart.dao.UserDao;
import com.cart.model.User;
import com.cart.service.UserService;

@RestController
public class UserController {

	public static final Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;
	@Autowired
	private User user;
	@Autowired
	private UserDao userDao;

	@RequestMapping(value = "/getAllUsers", method = RequestMethod.GET)
	public ResponseEntity<List<User>> getAllUsers() {
		log.debug("Starting getAllUsers method in Controller");
		List<User> users = userService.getAllUser();

		if (users.isEmpty()) {
			user.setErrorCode("404");
			user.setErrorMessage("No Users are available");
		}
		log.debug("Ending getAllUsers method in Controller");
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);

	}

	@RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
	public ResponseEntity<User> getUserById(@PathVariable("userId") String userId) {
		log.debug("Starting getUserById method in controller");
		user = userService.getById(userId);
		if (user == null) {
			user = new User();
			user.setErrorCode("404"); // NLP NullPointerException
			user.setErrorMessage("User does not exist with this id:" + userId);
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@RequestMapping(value = "/user/register", method = RequestMethod.POST)
	public ResponseEntity<User> createUser(@RequestBody User user) {

		if (userService.getById(user.getUserId()) != null) {
			user.setErrorCode("404");
			user.setErrorMessage("With this is Id the record is already exist. Please Choose another id");

		} else {
			user.setIsOnline('N');
			if (userService.saveUser(user)) {
				user.setErrorCode("200");
				user.setErrorMessage("Your registration is Successfull");

			} else {
				user.setErrorCode("405");
				user.setErrorMessage("Unable to process your registration. Please Contact Admin");

			}
		}

		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@RequestMapping(value = "/user/validate/", method = RequestMethod.POST)
	public ResponseEntity<User> login(@RequestBody User user, HttpSession session) {
		String id = user.getEmailId();
		user = userService.validate(user.getEmailId(), user.getPassword());

		if (user == null) {
			user = new User();
			user.setErrorCode("404");
			user.setErrorMessage("User does not exist with this id:" + id);
		} else {
			user.setIsOnline('Y');
			userService.updateUser(user);
			session.setAttribute("loggedInUserId", user.getEmailId());
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);

	}

	@RequestMapping(value = "/user/logout/", method = RequestMethod.GET)
	public ResponseEntity<User> logout(HttpSession session) {

		String loggedInUserId = (String) session.getAttribute("LoggedInUserId");

		System.out.println("LoggedInUser" + loggedInUserId);

		user = userService.getById(loggedInUserId);

		System.out.println("User: " + user);
		user.setIsOnline('N');

		session.invalidate();

		if (userService.updateUser(user)) {
			user.setErrorCode("200");
			user.setErrorMessage("You have logged out successfully");
		} else {
			user.setErrorCode("404");
			user.setErrorMessage("You could not logged out please contact Admin");

		}

		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

}
