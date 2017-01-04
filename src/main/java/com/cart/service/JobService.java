package com.cart.service;

import java.util.List;

import com.cart.model.Job;

public interface JobService {

	List<Job> getAllJobs();

	Job getJobById(int jobId);

	boolean saveJob(Job job);

	Job updateJob(int jobId, Job job);

	boolean removeJob(int jobId);
}
