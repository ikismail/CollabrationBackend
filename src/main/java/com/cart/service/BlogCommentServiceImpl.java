package com.cart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cart.dao.BlogCommentDao;
import com.cart.model.BlogComment;

@Service
public class BlogCommentServiceImpl implements BlogCommentService {

	@Autowired
	private BlogCommentDao blogCommentDao;
	
	
	public List<BlogComment> commentList() {
		// TODO Auto-generated method stub
		return blogCommentDao.commentList();
	}

	public List<BlogComment> getCommentById(int commentId) {
		// TODO Auto-generated method stub
		return blogCommentDao.getCommentById(commentId);
	}

	public void saveComment(BlogComment blogComment) {
		// TODO Auto-generated method stub
		blogCommentDao.saveComment(blogComment);
	}

	public BlogComment updateComment(int commentId, BlogComment blogComment) {
		// TODO Auto-generated method stub
		return blogCommentDao.updateComment(commentId, blogComment);
	}

	public void deleteComment(int CommentId) {
		// TODO Auto-generated method stub
		blogCommentDao.deleteComment(CommentId);
	}

	public BlogComment getById(int blogId) {
		// TODO Auto-generated method stub
		return blogCommentDao.getById(blogId);
	}

}
