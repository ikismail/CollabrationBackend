package com.cart.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cart.model.Blog;
import com.cart.model.Job;
import com.cart.service.JobService;

@RestController
public class JobController {

	@Autowired
	private JobService jobService;
	@Autowired
	private Job job;

	@RequestMapping(value = "/job/getAllJobs", method = RequestMethod.GET)
	public ResponseEntity<?> getAllJobs(HttpSession session) {
		System.out.println("----Starting getAllJobs in JobController");
		String loggedInUserId = (String) session.getAttribute("loggedInUserId");
		List<Job> jobs = jobService.getAllJobs();
		if (loggedInUserId != null) {
			if (jobs.isEmpty()) {
				return new ResponseEntity<List<Job>>(jobs, HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<List<Job>>(jobs, HttpStatus.OK);
		} else {
			Job job = new Job();
			job.setErrorCode("401");
			job.setErrorMessage("Please LogIn");
			return new ResponseEntity<Job>(job, HttpStatus.UNAUTHORIZED);
		}
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
	public ResponseEntity<?> saveJob(@RequestBody Job job, HttpSession session) {
		System.out.println("---Saving Jobs---");
		String loggedInUserId = (String) session.getAttribute("loggedInUserId");
		if (loggedInUserId != null) {
			Date date = new Date();
			job.setPostDate(date.toString());
			jobService.saveJob(job);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} else {
			job = new Job();
			job.setErrorCode("401");
			job.setErrorMessage("Please LogIn");
			return new ResponseEntity<Job>(job, HttpStatus.UNAUTHORIZED);
		}

	}

	@RequestMapping(value = "/job/deleteJob/{jobId}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteJob(@PathVariable("jobId") String jobId) {
		System.out.println("----Starting delete in Jobcontroller");
		Job job = jobService.getJobById(jobId);
		if (job == null)
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		jobService.removeJob(jobId);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@RequestMapping(value = "/job/updateJob/{jobId}", method = RequestMethod.PUT)
	public ResponseEntity<Job> updateJob(@PathVariable("jobId") String jobId, @RequestBody Job job) {
		Date date = new Date();
		job.setPostDate(date.toString());
		Job updatedJob = jobService.updateJob(jobId, job);
		job.setErrorCode("200");
		job.setErrorMessage("Updated Successfully");
		return new ResponseEntity<Job>(updatedJob, HttpStatus.OK);

	}

}
