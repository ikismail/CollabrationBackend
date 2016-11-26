package com.cart.service;

import java.util.List;

import com.cart.model.Friend;

public interface FriendService {

	public List<Friend> getMyFriend(String userId);

	public Friend getBId(String userId, String friendId);

	public boolean saveFriend(Friend friend);

	public boolean updateFriend(Friend friend);

	public void deleteFriend(String userId, String friendId);

	public List<Friend> getNewFriendRequest(String userId);

	public void setOnline(String userId);

	public void setOffLine(String userId);
}
