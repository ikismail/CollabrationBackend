package com.cart.dao;

import java.util.List;

import com.cart.model.Friend;

public interface FriendDao {
	//friendList using userId
	public List<Friend> getMyFriend(String userId);

	//to accept or reject purpose
	public Friend getBId(String userId, String friendId);

	//New Friend request
	public boolean saveFriend(Friend friend);

	//accept or reject purpose
	public boolean updateFriend(Friend friend);

	//unFriend 
	public void deleteFriend(String userId, String friendId);

	//to list all the new friend request
	public List<Friend> getNewFriendRequest(String userId);

	public void setOnline(String userId);

	public void setOffLine(String userId);
}
