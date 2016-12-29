package com.cart.service;

import java.util.List;

import com.cart.model.JobApplication;

public interface JobAppService {

	boolean applyJob(JobApplication jobApp);
	
	List<JobApplication> getAppliedJobs(String userId);
}
