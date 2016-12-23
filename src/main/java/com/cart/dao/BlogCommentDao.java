package com.cart.dao;

import java.util.List;

import com.cart.model.BlogComment;

public interface BlogCommentDao {

	List<BlogComment> commentList();
	
	BlogComment getById(String blogId);

	List<BlogComment> getCommentById(String blogId);

	void saveComment(BlogComment blogComment);

	BlogComment updateComment(String commentId, BlogComment blogComment);

	void deleteComment(String CommentId);
}
