package com.cart.dao;

import java.util.List;

import com.cart.model.JobApplication;

public interface JobAppDao {

	boolean applyJob(JobApplication jobapp);

	List<JobApplication> getAppliedJobs(String userId);
}
