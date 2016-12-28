package com.cart.dao;

import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cart.model.JobApplication;

@Repository
public class JobAppDaoImpl implements JobAppDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
	public boolean applyJob(JobApplication jobapp) {
		try {
			Session session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			session.save(jobapp);
			tx.commit();
			System.out.println("Applied by: " + jobapp.getAppliedBy());
			return true;
		} catch (HibernateException e) {
			e.printStackTrace();
			return false;
		}
	}
}
