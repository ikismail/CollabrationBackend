package com.cart.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cart.model.Job;
import com.cart.model.JobApplication;
import com.cart.service.JobAppService;
import com.cart.service.JobService;

@RestController
public class jobApplicationController {

	@Autowired
	private JobAppService jobAppService;

	@Autowired
	private JobApplication jobApp;

	@Autowired
	private Job job;

	@Autowired
	private JobService jobService;

	@RequestMapping(value = "/job/applyJob/{jobId}", method = RequestMethod.GET)
	public ResponseEntity<?> applyJob(@PathVariable("jobId") String jobId, HttpSession session) {
		System.out.println("---Applying for Job---");
		String loggedInUserId = (String) session.getAttribute("loggedInUserId");
		job = jobService.getJobById(jobId);
		System.out.println("Job: " + job.getTitle());
		jobApp.setJob(job);
		jobApp.setAppliedBy(loggedInUserId);
		jobApp.setStatus("N");
		jobAppService.applyJob(jobApp);
		return new ResponseEntity<JobApplication>(jobApp, HttpStatus.OK);

	}
}
