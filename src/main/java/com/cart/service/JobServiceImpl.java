package com.cart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cart.dao.JobDao;
import com.cart.model.Job;

@Service
public class JobServiceImpl implements JobService {

	@Autowired
	private JobDao jobDao;

	public JobDao getJobDao() {
		return jobDao;
	}

	public void setJobDao(JobDao jobDao) {
		this.jobDao = jobDao;
	}

	public List<Job> getAllJobs() {
		return jobDao.getAllJobs();
	}

	public Job getJobById(String jobId) {
		return jobDao.getJobById(jobId);
	}

	public void saveJob(Job job) {
		jobDao.saveJob(job);
	}

	public void updateJob(Job job) {
		jobDao.updateJob(job);
	}

	public void removeJob(String jobId) {
		jobDao.removeJob(jobId);
	}
}
