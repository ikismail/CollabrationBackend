package com.cart.dao;

import java.util.List;

import com.cart.model.Blog;

public interface BlogDao {

	List<Blog> getAllblogs();

	boolean saveBlog(Blog blog);

	boolean updateStatus(Blog blog);
	
	void deleteBlog(String blogId);

	Blog getBlogById(String blogId);
	
	Blog updateBlog(String blogId,Blog blog);
	
	void increaseLikes(String blogId);
	
	void increaseDislikes(String blogId);
}
