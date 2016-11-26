package com.cart.controller;

import java.util.Date;
import java.util.List;

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
import com.cart.service.UserService;

@Controller
public class BlogController {

	@Autowired
	private BlogService blogService;
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/blog/getAllblogs", method = RequestMethod.GET)
	public ResponseEntity<List<Blog>> getAllBlogs() {
		List<Blog> blogs = blogService.getAllblogs();
		if (blogs.isEmpty()) {
			return new ResponseEntity<List<Blog>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Blog>>(blogs, HttpStatus.OK);
	}

	@RequestMapping(value = "/blog/create/", method = RequestMethod.POST)
	public ResponseEntity<Void> createBlog(@RequestBody Blog blog) {
		Date date = new Date();
		blog.setBlogCreatedDate(date.toString());
		blogService.saveBlog(blog);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@RequestMapping(value="/blog/getBlog/{blogId}",method=RequestMethod.GET)
	public ResponseEntity<Blog> getBlogById(@PathVariable("blogId") String blogId){
		Blog  blog = blogService.getBlogById(blogId);
		return new ResponseEntity<Blog>(blog,HttpStatus.OK);
	}
	
}