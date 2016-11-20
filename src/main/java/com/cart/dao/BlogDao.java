package com.cart.dao;

import java.util.List;

import com.cart.model.Blog;

public interface BlogDao {

	List<Blog> getAllblogs();

	void saveBlog(Blog blog);

	void updateBlog(Blog blog);

	Blog getBlogById(String blogId);
}
