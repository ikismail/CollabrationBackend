package com.cart.service;

import java.util.List;

import com.cart.model.Job;

public interface JobService {

	List<Job> getAllJobs();

	Job getJobById(String jobId);

	void saveJob(Job job);

	Job updateJob(String jobId,Job job);

	void removeJob(String jobId);
}
