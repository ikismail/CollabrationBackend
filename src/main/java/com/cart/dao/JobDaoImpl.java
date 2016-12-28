package com.cart.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cart.model.Friend;
import com.cart.model.Job;

@Repository
public class JobDaoImpl implements JobDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
	public List<Job> getAllJobs() {
		Session session = sessionFactory.openSession();
		List<Job> jobs = session.createQuery("from Job").list();
		System.out.println("----List of Jobs-----");
		System.out.println(jobs);
		session.flush();
		session.close();
		return jobs;
	}

	@Transactional
	public Job getJobById(String jobId) {
		Session session = sessionFactory.openSession();
		Job job = (Job) session.get(Job.class, jobId);
		System.out.println("----getJobById : " + job);
		session.close();
		return job;
	}

	@Transactional
	public boolean saveJob(Job job) {
		Session session = sessionFactory.openSession();
		try {
			session.save(job);
			System.out.println("-----Saving Job---" + job);
			session.close();
			return true;
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Transactional
	public Job updateJob(String jobId, Job job) {
		System.out.println("-----Starting update job in daoimpl");
		Session session = sessionFactory.openSession();

		Job currentJob = (Job) session.get(Job.class, jobId);
		if (currentJob == null)
			return null;
		session.merge(job);
		Job updatedJob = (Job) session.get(Job.class, jobId);
		session.flush();
		session.close();
		System.out.println("Ending Update method in DaoImpl");
		return updatedJob;
	}
	
	@Transactional
	public boolean removeJob(String jobId) {
		Session session = sessionFactory.openSession();
		// make the object persistent - job
		try {
			Job job = (Job) session.get(Job.class, jobId);
			session.delete(job);
			// Transient - job
			System.out.println("-----Removing------" + job);
			session.flush();
			session.close();
			return true;
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

}
