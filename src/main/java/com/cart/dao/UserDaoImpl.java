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
		log.debug("Starting getAllUsers DaoImpl");
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from User");
		List<User> users = query.list();
		session.close();
		return users;
	}

	@Transactional
	public User getById(String id) {
		log.debug("Starting of the getById method in DaoImpl");
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
	public boolean saveUser(User user) {
		log.debug("starting save method in daoimpl");
		try {
			sessionFactory.getCurrentSession().save(user);
			return true;
		} catch (HibernateException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Transactional
	public boolean updateUser(User user) {
		System.out.println("starting update method in daoimpl");
		try {
			sessionFactory.getCurrentSession().update(user);
			return true;
		} catch (HibernateException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Transactional
	public User validate(String emailId, String password) {
		log.debug("Starting vlidate method in daoImpl");
		String hql = "from User where emailId='" + emailId + "' and password='" + password + "'";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		log.debug("Ending of the Update Method in DaoImpl");
		return (User) query.uniqueResult();

	}

}
