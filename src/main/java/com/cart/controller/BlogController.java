package com.cart.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cart.model.Blog;
import com.cart.service.BlogService;

@Controller
public class BlogController {

	@Autowired
	private BlogService blogService;
	@Autowired
	private Blog blog;

	@RequestMapping(value = "/blog/getAllblogs", method = RequestMethod.GET)
	public ResponseEntity<?> getAllBlogs(HttpSession session) {

		String loggedInUserId = (String) session.getAttribute("loggedInUserId");
		List<Blog> blogs = blogService.getAllblogs();
		if (loggedInUserId == null) {
			Blog blog = new Blog();
			blog.setErrorCode("401");
			blog.setErrorMessage("Please LogIn");
			return new ResponseEntity<Blog>(blog, HttpStatus.UNAUTHORIZED);
		} else {

			if (blogs.isEmpty()) {
				blog.setErrorCode("404");
				blog.setErrorMessage("There is no blogs please add");
			}
			blog.setErrorCode("200");
			blog.setErrorMessage("List of blogs");
			return new ResponseEntity<List<Blog>>(blogs, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/blog/create/", method = RequestMethod.POST)
	public ResponseEntity<?> createBlog(@RequestBody Blog blog, HttpSession session) {
		String loggedInUserId = (String) session.getAttribute("loggedInUserId");
		if (loggedInUserId != null) {
			blog.setUserId(loggedInUserId);
			blog.setStatus("New");
			blog.setLikes(0);
			blog.setDislikes(0);
			Date date = new Date();
			blog.setBlogCreatedDate(date.toString());
			System.out.println("----saving create blog");
			if (blogService.saveBlog(blog)) {
				blog.setErrorCode("200");
				blog.setErrorMessage("Your Blog has been saved Successfully");
			} else {
				blog.setErrorCode("405");
				blog.setErrorMessage("Unable to process your request");
			}
			System.out.println("----Ending of the method saveBlog");
			return new ResponseEntity<Blog>(blog, HttpStatus.OK);
		} else {
			blog = new Blog();
			blog.setErrorCode("401");
			blog.setErrorMessage("Please LogIn");
			return new ResponseEntity<Blog>(blog, HttpStatus.UNAUTHORIZED);
		}

	}

	@RequestMapping(value = "/blog/getBlog/{blogId}", method = RequestMethod.GET)
	public ResponseEntity<Blog> getBlogById(@PathVariable("blogId") String blogId) {
		Blog blog = blogService.getBlogById(blogId);
		return new ResponseEntity<Blog>(blog, HttpStatus.OK);
	}

	@RequestMapping(value = "/blog/updateBlog/{blogId}", method = RequestMethod.PUT)
	public ResponseEntity<Blog> updateBlog(@PathVariable("blogId") String blogId, @RequestBody Blog blog) {
		System.out.println("---Starting Update method in BlogController");
		Blog updatedBlog = blogService.updateBlog(blogId, blog);
		if (blog == null) {
			return new ResponseEntity<Blog>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Blog>(updatedBlog, HttpStatus.OK);
	}

	@RequestMapping(value = "/blog/{blogId}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteBlog(@PathVariable String blogId) {
		Blog blog = blogService.getBlogById(blogId);
		if (blog == null)
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		blogService.deleteBlog(blogId);
		return new ResponseEntity<Void>(HttpStatus.OK);

	}

	@RequestMapping(value = "/blog/upvote/{blogId}", method = RequestMethod.GET)
	public ResponseEntity<Blog> upvote(@PathVariable("blogId") String blogId) {
		System.out.println("Starting upvote method in controller");
		blogService.increaseLikes(blogId);
		return new ResponseEntity<Blog>(HttpStatus.OK);
	}

	@RequestMapping(value = "/blog/downvote/{blogId}", method = RequestMethod.GET)
	public ResponseEntity<Blog> downvote(@PathVariable("blogId") String blogId) {
		System.out.println("Starting upvote method in controller");
		blogService.increaseDislikes(blogId);
		return new ResponseEntity<Blog>(HttpStatus.OK);
	}

	@RequestMapping(value = "/blog/accept/{blogId}/", method = RequestMethod.GET)
	public ResponseEntity<Blog> acceptBlog(@PathVariable("blogId") String blogId) {
		System.out.println("---Starting acceptBlog method");
		blog = UpdateStatus(blogId, "Accepted", "");
		return new ResponseEntity<Blog>(blog, HttpStatus.OK);
	}

	@RequestMapping(value = "/blog/reject/{blogId}/{reason}", method = RequestMethod.GET)
	public ResponseEntity<Blog> rejectBlog(@PathVariable("blogId") String blogId,
			@PathVariable("reason") String reason) {
		System.out.println("---Starting RejectBlog method");
		blog = UpdateStatus(blogId, "Rejected", reason);
		return new ResponseEntity<Blog>(blog, HttpStatus.OK);
	}

	// method for accept and reject
	private Blog UpdateStatus(String blogId, String status, String reason) {
		System.out.println("---starting update method in blog");
		System.out.println("Status : " + status);
		blog = blogService.getBlogById(blogId);
		if (blog == null) {
			blog = new Blog();
			blog.setErrorCode("404");
			blog.setErrorMessage("Could not update the status");
		} else {
			blog.setStatus(status);
			blog.setReason(reason);
			blogService.updateStatus(blog);
		}
		System.out.println("---Ending update status");
		return blog;
	}

}