package com.cart.dao;

import java.util.List;

import com.cart.model.Job;

public interface JobDao {

	List<Job> getAllJobs();
	
	Job getJobById(String jobId);
	
	boolean saveJob(Job job);
	
	Job updateJob(String jobId,Job job);
	
	boolean removeJob(String jobId);
	
	
	
 }
