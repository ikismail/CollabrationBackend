package com.cart.dao;

import java.util.List;

import com.cart.model.Job;

public interface JobDao {

	List<Job> getAllJobs();
	
	Job getJobById(int jobId);
	
	boolean saveJob(Job job);
	
	Job updateJob(int jobId,Job job);
	
	boolean removeJob(int jobId);
	
	
	
 }
