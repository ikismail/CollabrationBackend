package com.cart.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cart.model.Job;
import com.cart.service.JobService;

@RestController
public class JobController {

	@Autowired
	private JobService jobService;

	@RequestMapping(value = "/job/getAllJobs", method = RequestMethod.GET)
	public ResponseEntity<List<Job>> getAllJobs() {
		System.out.println("----Starting getAllJobs in JobController");
		List<Job> jobs = jobService.getAllJobs();
		if (jobs.isEmpty()) {
			return new ResponseEntity<List<Job>>(jobs, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Job>>(jobs,HttpStatus.OK);
	}

	@RequestMapping(value = "/job/getJobById/{jobId}", method = RequestMethod.GET)
	public ResponseEntity<Job> getJobById(@PathVariable("jobId") String jobId) {
		System.out.println("----Starting getBId in JobController----");
		Job job = jobService.getJobById(jobId);
		if (job == null) {
			job = new Job();
			return new ResponseEntity<Job>(job, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Job>(job, HttpStatus.OK);
	}

	@RequestMapping(value = "/job/addJob/", method = RequestMethod.POST)
	public ResponseEntity<Void> saveJob(@RequestBody Job job) {
		System.out.println("---Saving Jobs---");
		Date date = new Date();
		job.setPostDate(date.toString());
		jobService.saveJob(job);
		return new ResponseEntity<Void>(HttpStatus.OK);

	}

	@RequestMapping(value = "/job/deleteJob/{jobId}")
	public ResponseEntity<Void> deleteJob(@PathVariable("jobId") String jobId) {
		System.out.println("----Starting delete in Jobcontroller");
		jobService.removeJob(jobId);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

}
