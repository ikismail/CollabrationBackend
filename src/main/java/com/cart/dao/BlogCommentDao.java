package com.cart.dao;

import java.util.List;

import com.cart.model.BlogComment;

public interface BlogCommentDao {

	List<BlogComment> commentList();
	
	BlogComment getById(int blogId);

	List<BlogComment> getCommentById(int blogId);

	void saveComment(BlogComment blogComment);

	BlogComment updateComment(int commentId, BlogComment blogComment);

	void deleteComment(int CommentId);
}
