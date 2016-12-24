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
		System.out.println("-----Starting getAllUsers method in Controller");
		List<User> users = userService.getAllUser();

		if (users.isEmpty()) {
			user.setErrorCode("404");
			user.setErrorMessage("No Users are available");
		}
		System.out.println("------Ending getAllUsers method in Controller");
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);

	}

	@RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
	public ResponseEntity<User> getUserById(@PathVariable("userId") String userId) {
		System.out.println("------Starting getUserById method in controller");
		user = userService.getById(userId);
		if (user == null) {
			user = new User(); // to avoid NLP
			user.setErrorCode("404"); // NLP NullPointerException
			user.setErrorMessage("User does not exist with this id:" + userId);
			return new ResponseEntity<User>(user, HttpStatus.NOT_FOUND);
		}
		System.out.println("-----UserName : " + user.getfName());
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@RequestMapping(value = "/user/register", method = RequestMethod.POST)
	public ResponseEntity<User> createUser(@RequestBody User user) {
		System.out.println("-----Starting the method register");

		if (userService.getById(user.getUserId()) != null) {
			user.setErrorCode("404");
			user.setErrorMessage("With this is Id the record is already exist. Please Choose another id");

		} else {
			user.setStatus('N');
			user.setIsOnline('N');
			System.out.println("------saving user in controller");
			if (userService.saveUser(user)) {
				user.setErrorCode("200");
				user.setErrorMessage("Your registration is Successfull");

			} else {
				user.setErrorCode("405");
				user.setErrorMessage("Unable to process your registration. Please Contact Admin");

			}
		}
		System.out.println("------Ending of the method createuser");
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@RequestMapping(value = "/user/validate/", method = RequestMethod.POST)
	public ResponseEntity<User> login(@RequestBody User user, HttpSession session) {
		System.out.println("------Starting the method login");
		String id = user.getEmailId();
		user = userService.validate(user);

		if (user == null) {
			user = new User();
			user.setErrorCode("404");
			user.setErrorMessage("Please Check your credential or user doesn't exist with this id:" + id);
			return new ResponseEntity<User>(user,HttpStatus.UNAUTHORIZED);//401
		} else {
			user.setIsOnline('Y');
			user.setErrorCode("200");
			user.setErrorMessage("You have successfully logged");
			userService.updateUser(user);
			session.setAttribute("loggedInUserId", user.getEmailId());
			System.out.println("-----Logg in Id :" + user.getEmailId());
		}
		System.out.println("------Ending of the method");
		return new ResponseEntity<User>(user, HttpStatus.OK);

	}

	@RequestMapping(value = "/user/logout/", method = RequestMethod.GET)
	public ResponseEntity<User> logout(HttpSession session) {

		String loggedInUserId = (String) session.getAttribute("loggedInUserId");

		System.out.println("------LogOut Id :" + loggedInUserId);

		user = userService.getByemailId(loggedInUserId);

		System.out.println("User: " + user.getfName());
		user.setIsOnline('N');
		session.removeAttribute(loggedInUserId);
		session.invalidate();

		if (userService.updateUser(user)) {
			user.setErrorCode("200");
			user.setErrorMessage("You have logged out successfully");
		} else {
			user.setErrorCode("404");
			user.setErrorMessage("You could not logged out please contact Admin");

		}
		System.out.println("------Ending the method logout");
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@RequestMapping(value = "/user/accept/{userId}", method = RequestMethod.GET)
	public ResponseEntity<User> accept(@PathVariable("userId") String userId) {
		System.out.println("------Starting of the method accept");
		user = updateStatus(userId, 'A', "");
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@RequestMapping(value = "/user/accept/{userId}/{reason}", method = RequestMethod.GET)
	public ResponseEntity<User> reject(@PathVariable("userId") String userId, @PathVariable("reason") String reason) {
		System.out.println("------Starting of the method reject");

		user = updateStatus(userId, 'R', reason);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	// method for accept and reject
	private User updateStatus(String userId, char status, String reason) {
		System.out.println("------Starting the method updateStatus");
		System.out.println("Status: " + status);
		user = userService.getById(userId);
		if (user == null) {
			user = new User();
			user.setErrorCode("404");
			user.setErrorMessage("Could not update the status");
		} else {
			user.setStatus(status);
			user.setReason(reason);
			userService.updateUser(user);
		}
		System.out.println("------Ending update Status");
		return user;

	}

}
