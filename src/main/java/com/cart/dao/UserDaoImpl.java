package com.cart.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cart.model.User;
import com.sun.org.apache.regexp.internal.recompile;

@Repository
public class UserDaoImpl implements UserDao {

	public static final Logger log = LoggerFactory.getLogger(UserDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public List<User> getAllUser() {
		log.debug("Starting getAllUsers DaoImpl");
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from User");
		List<User> users = query.list();
		session.close();
		return users;
	}

	public User getById(String id) {
		log.debug("Starting of the getById method in DaoImpl");
		Session session = sessionFactory.openSession();
		User user = (User) session.get(User.class, id);
		session.close();
		return user;
	}

	public void saveUser(User user) {
		log.debug("starting save method in daoimpl");
		Session session = sessionFactory.openSession();
		session.save(user);
		session.flush();
		session.close();
	}

	public void updateUser(User user) {
		log.debug("starting update method in daoimpl");
		Session session = sessionFactory.openSession();
		session.update(user);
		session.close();
	}

}
