package com.cart.service;

import java.util.List;

import com.cart.model.BlogComment;

public interface BlogCommentService {

	List<BlogComment> commentList();
	
	BlogComment getById(int blogId);

	List<BlogComment> getCommentById(int commentId);

	void saveComment(BlogComment blogComment);

	BlogComment updateComment(int commentId, BlogComment blogComment);

	void deleteComment(int CommentId);

}
