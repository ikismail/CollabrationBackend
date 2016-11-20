package com.cart.service;

import java.util.List;

import com.cart.model.Blog;

public interface BlogService {

	List<Blog> getAllblogs();

	void saveBlog(Blog blog);

	void updateBlog(Blog blog);

	Blog getBlogById(String blogId);
}
