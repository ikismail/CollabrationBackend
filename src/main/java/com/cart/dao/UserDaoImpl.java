package com.cart.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cart.model.User;

@Repository
public class UserDaoImpl implements UserDao {

	public static final Logger log = LoggerFactory.getLogger(UserDaoImpl.class);

	@Autowired
	private User user;

	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Transactional
	public List<User> getAllUser() {
		System.out.println("Starting getAllUsers DaoImpl");
		String hql = "from User";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		System.out.println("Ending UserDao getAllUser");
		return query.list();
	}

	@Transactional
	public User getById(String id) {
		System.out.println("Starting of the getById method in DaoImpl");
		Session session = sessionFactory.openSession();
		User user = (User) session.get(User.class, id);
		/*
		 * String hql = "from User where id = '" + id + "'";
		 * 
		 * Query query = sessionFactory.getCurrentSession().createQuery(hql);
		 * user = (User) query.uniqueResult();
		 */
		return user;
	}

	@Transactional
	public User getByemailId(String emailId) {
		System.out.println("Starting getby emailid method");
		String hql = "from User where emailId='" + emailId + "'";
		System.out.println("------getByemailID query : " + hql);
		Query query = sessionFactory.openSession().createQuery(hql);
		return (User) query.uniqueResult();
	}

	@Transactional
	public boolean saveUser(User user) {
		System.out.println("starting save method in daoimpl");
		try {
			sessionFactory.openSession().save(user);
			return true;
		} catch (HibernateException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Transactional
	public boolean updateUser(User user) {
		System.out.println("starting update method in daoimpl");
		System.out.println("ISONLINE VALUE IS [BEFORE UPDATE]" + user.getIsOnline());
		User existingUser;
		try {
			Session session = sessionFactory.openSession();
			existingUser = (User) session.get(User.class, user.getUserId());
			existingUser.setIsOnline(user.getIsOnline());
			session.update(existingUser);
			session.flush();
			session.close();
			System.out.println("ISONLINE VALUE IS [AFTER UPDATE] " + existingUser.getIsOnline());
			return true;
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}

	@Transactional
	public User validate(User user) {

		System.out.println("Starting vlidate method in daoImpl");
		String hql = "from User where emailId='" + user.getEmailId() + "' and password='" + user.getPassword() + "'";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		System.out.println("Ending of the Update Method in DaoImpl");
		User validUser = (User) query.uniqueResult();
		return validUser;
		
	}

}
