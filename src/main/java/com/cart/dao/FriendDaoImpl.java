package com.cart.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cart.model.Friend;

@Repository
public class FriendDaoImpl implements FriendDao {

	@Autowired(required=true)
	private SessionFactory sessionFactory;
	
	public FriendDaoImpl(){
		
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	//creating Auto generation in Own Way
	private Integer getMaxId() {
		System.out.println("-----starting method getMaxId");
		String hql = "select max(id) from Friend";
		Query query = sessionFactory.openSession().createQuery(hql);
		Integer maxId;
		try {
			maxId = (Integer) query.uniqueResult();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 100;
		}
		System.out.println("-----MaxId : " + maxId);
		return maxId;
	}

	@Transactional
	public List<Friend> getMyFriend(String userId) {
		String hql = "from Friend where userId=" + "'" + userId + "' and status = '" + "Accepted'";
		Query query = sessionFactory.openSession().createQuery(hql);
		List<Friend> list = (List<Friend>) query.list();
		return list;
	}

	@Transactional
	public Friend getBId(String userId, String friendId) {
		String hql = "from Friend where userId=" + "'" + userId + "' and friendId= '" + friendId + "'";
		System.out.println("hql :" + hql);
		Query query = sessionFactory.openSession().createQuery(hql);

		return (Friend) query.uniqueResult();
	}

	@Transactional
	public boolean saveFriend(Friend friend) {
		try {
			System.out.println("---setPrevious id: " + getMaxId());
			friend.setId(getMaxId() + 1);
			System.out.println("---generated id: " + getMaxId());
			sessionFactory.getCurrentSession().save(friend);
			return true;
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Transactional
	public boolean updateFriend(Friend friend) {
		try {
			sessionFactory.openSession().update(friend);
			return true;
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Transactional
	public void deleteFriend(String userId, String friendId) {
		Friend friend = new Friend();
		friend.setFriendId(friendId);
		friend.setUserID(userId);
		sessionFactory.openSession().delete(friend);
	}

	@Transactional
	public List<Friend> getNewFriendRequest(String userId) {
		String hql = "from Friend where friendId=" + "'" + userId + "' and status = '" + "New'";
		Query query = sessionFactory.openSession().createQuery(hql);
		List<Friend> list = (List<Friend>) query.list();
		return list;
	}

	@Transactional
	public void setOnline(String userId) {
		System.out.println("-----staring setnline method in frienddao");
		String hql = "UPDATE Friend SET isOnline = 'Y' where userId='" + userId + "'";
		System.out.println("-----hql : " + hql);
		Query query = sessionFactory.openSession().createQuery(hql);
		query.executeUpdate();
		System.out.println("-----Ending the method setOnline");

	}

	@Transactional
	public void setOffLine(String userId) {
		System.out.println("-----starting the mehod setOffline");
		String hql = "UPDATE Friend SET isOnline = 'N' where userId='" + userId + "'";
		System.out.println("-----hql : " + hql);
		Query query = sessionFactory.openSession().createQuery(hql);
		query.executeUpdate();
		System.out.println("-----Ending the method setOnline");

	}

}
