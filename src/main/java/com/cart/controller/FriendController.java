package com.cart.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cart.model.Friend;
import com.cart.service.FriendService;

@RestController
public class FriendController {

	@Autowired
	private FriendService friendService;
	@Autowired(required = true)
	private Friend friend;

	@RequestMapping(value = "/myfriends", method = RequestMethod.GET)
	public ResponseEntity<List<Friend>> getMyFriends(HttpSession session) {
		System.out.println("-----Starting getMyfriend method");
		String loggedInUserId = (String) session.getAttribute("loggedInUserId");
		System.out.println("getting friends of: " + loggedInUserId);
		List<Friend> myFriends = friendService.getMyFriend(loggedInUserId);

		if (myFriends.isEmpty()) {
			System.out.println("Friends does not exist for the user : " + loggedInUserId);
			friend.setErrorCode("404");
			friend.setErrorMessage("You does not have any friends");
			myFriends.add(friend);
		}
		System.out.println("Send the friends List: ");
		return new ResponseEntity<List<Friend>>(myFriends, HttpStatus.OK);
	}

	@RequestMapping(value = "/sendFriendRequest/{friendId}", method = RequestMethod.GET)
	public ResponseEntity<Friend> sendFriendRequest(@PathVariable("friendId") String friendId, HttpSession session) {
		System.out.println("-----Calling sendFriendRequest Method");
		String loggedInUserId = (String) session.getAttribute("loggedInUserId");
		friend.setUserID(loggedInUserId);
		friend.setFriendId(friendId);
		friend.setStatus('N');
		System.out.println("sending friendrequest Id: " + friend.getFriendId());
		friendService.saveFriend(friend);
		System.out.println("-----Saving and Ending sendFriendRequestMethod");
		return new ResponseEntity<Friend>(friend, HttpStatus.OK);

	}

	@RequestMapping(value = "/getMyFriendRequests/", method = RequestMethod.GET)
	public ResponseEntity<List<Friend>> getMyFriendRequestList(HttpSession session) {
		System.out.println("----calling method getMyFriendRequest");
		String loggedInUserId = (String) session.getAttribute("loggedInUserId");
		List<Friend> myFriendRequests = friendService.getNewFriendRequest(loggedInUserId);
		return new ResponseEntity<List<Friend>>(myFriendRequests, HttpStatus.OK);
	}

	// Method for updating Request
	private void updateRequest(String friendId, char status, HttpSession session) {
		System.out.println("----calling method UpdateRequestJavaMethod");
		String loggedInUserId = (String) session.getAttribute("loggedInUserId");
		friend = friendService.getBId(friendId, loggedInUserId);
		System.out.println("In Friend Controller: " + friend.getUserID());
		friend.setUserID(loggedInUserId);
		friend.setFriendId(friendId);
		friend.setStatus(status);
		friendService.updateFriend(friend);

	}

	@RequestMapping(value = "/unfriend/{friendId}", method = RequestMethod.GET)
	public ResponseEntity<Friend> unFriend(@PathVariable("friendId") String friendId, HttpSession session) {
		System.out.println("-----Calling unFriend Method in Controller");
		String loggedInUserId = (String) session.getAttribute("loggedInUserId");
		friend = friendService.getBId(loggedInUserId, friendId);
		System.out.println("In Friend Controller: " + friend.getUserID());
		friend.setUserID(loggedInUserId);
		friend.setFriendId(friendId);
		friend.setStatus('U');
		friendService.updateFriend(friend);
		return new ResponseEntity<Friend>(friend, HttpStatus.OK);
	}

	@RequestMapping(value = "/rejectfriend/{friendId}", method = RequestMethod.GET)
	public ResponseEntity<Friend> rejectFriend(@PathVariable("friendId") String friendId, HttpSession session) {
		System.out.println("-----Calling rejectFriend Method in Controller");
		updateRequest(friendId, 'R', session);
		return new ResponseEntity<Friend>(friend, HttpStatus.OK);
	}

	@RequestMapping(value = "/acceptfriend/{friendId}", method = RequestMethod.GET)
	public ResponseEntity<Friend> acceptFriend(@PathVariable("friendId") String friendId, HttpSession session) {
		System.out.println("-----Calling acceptFriend Method in Controller");
		updateRequest(friendId, 'A', session);
		return new ResponseEntity<Friend>(friend, HttpStatus.OK);
	}

}
