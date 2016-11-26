package com.cart.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
	public void saveJob(Job job) {
		Session session = sessionFactory.openSession();
		session.save(job);
		System.out.println("-----Saving Job---" + job);
		session.close();
	}

	@Transactional
	public Job updateJob(String jobId, Job job) {
		Session session = sessionFactory.openSession();
		// emailid= ismail@gmail.com
		//select [before modification] 
		Job currentJob = (Job) session.get(Job.class, jobId); // persistent
		if (currentJob == null) // id doesn't in the database
			return null;
		// if i change the emailid as ismail@yahoo.com
		// update job set name=?,emailid=?.. where jobId=?
		
		//to modify update
		session.merge(job); // update query
		//select [after modification]
		Job updatedJob = (Job) session.get(Job.class, jobId); // select Query
		System.out.println("-----Updating Job----" + updatedJob);
		session.flush();
		session.close();
		return updatedJob;
	}

	@Transactional
	public void removeJob(String jobId) {
		Session session = sessionFactory.openSession();
		//make the object persistent - job
		Job job = (Job) session.get(Job.class, jobId);
		session.delete(job);
		//Transient - job
		System.out.println("-----Removing------" + job);
		session.flush();
		session.close();
	}

}
