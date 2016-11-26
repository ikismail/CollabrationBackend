package com.cart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cart.dao.FriendDao;
import com.cart.model.Friend;

@Service
public class FriendServiceImpl implements FriendService {

	@Autowired
	private FriendDao friendDao;

	public List<Friend> getMyFriend(String userId) {
		return friendDao.getMyFriend(userId);
	}

	public Friend getBId(String userId, String friendId) {
		return friendDao.getBId(userId, friendId);
	}

	public boolean saveFriend(Friend friend) {
		return friendDao.saveFriend(friend);
	}

	public boolean updateFriend(Friend friend) {
		// TODO Auto-generated method stub
		return friendDao.updateFriend(friend);
	}

	public void deleteFriend(String userId, String friendId) {
		friendDao.deleteFriend(userId, friendId);
	}

	public List<Friend> getNewFriendRequest(String userId) {
		// TODO Auto-generated method stub
		return friendDao.getNewFriendRequest(userId);
	}

	public void setOnline(String userId) {
		friendDao.setOnline(userId);
	}

	public void setOffLine(String userId) {
		// TODO Auto-generated method stub
		friendDao.setOffLine(userId);

	}

}
