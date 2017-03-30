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

	public Job getJobById(int jobId) {
		return jobDao.getJobById(jobId);
	}

	public boolean saveJob(Job job) {
		return jobDao.saveJob(job);
	}

	public Job updateJob(int jobId, Job job) {
		return jobDao.updateJob(jobId, job);
	}

	public boolean removeJob(int jobId) {
		return jobDao.removeJob(jobId);
	}

}
