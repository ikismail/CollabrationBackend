package com.cart.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cart.dao.BlogDao;
import com.cart.model.Blog;

@Service
public class BlogServiceImpl implements BlogService {

	@Autowired
	private BlogDao blogDao;

	@Transactional
	public List<Blog> getAllblogs() {
		return blogDao.getAllblogs();
	}

	
	public void saveBlog(Blog blog) {
		blogDao.saveBlog(blog);
	}

	public void updateBlog(Blog blog) {
		blogDao.updateBlog(blog);
	}

	public Blog getBlogById(String blogId) {
		return blogDao.getBlogById(blogId);
	}

}
