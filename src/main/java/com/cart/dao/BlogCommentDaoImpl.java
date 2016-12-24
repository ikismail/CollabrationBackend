package com.cart.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.metamodel.source.annotations.entity.ConfiguredClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cart.model.BlogComment;
import com.cart.model.User;

@Repository
public class BlogCommentDaoImpl implements BlogCommentDao {

	
	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Transactional
	public List<BlogComment> commentList() {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from BlogComment");
		List<BlogComment> comments = query.list();
		session.close();
		return comments;
	}

	@Transactional
	public List<BlogComment> getCommentById(String blogId) {

		Session session = sessionFactory.openSession();
		String hql = "from BlogComment where blogId ='" + blogId + "'";
		Query query = session.createQuery(hql);
		List<BlogComment> comments = query.list();
		return comments;
	}

	@Transactional
	public BlogComment getById(String blogId) {
		System.out.println("Starting of the getById method in DaoImpl");
		Session session = sessionFactory.openSession();
		BlogComment comment = (BlogComment) session.get(BlogComment.class, blogId);
		/*
		 * String hql = "from User where id = '" + id + "'";
		 * 
		 * Query query = sessionFactory.getCurrentSession().createQuery(hql);
		 * user = (User) query.uniqueResult();
		 */
		return comment;
	}

	@Transactional
	public void saveComment(BlogComment blogComment) {
		Session session = sessionFactory.openSession();
		session.save(blogComment);
		session.flush();
		session.close();

	}

	@Transactional
	public BlogComment updateComment(String commentId, BlogComment blogComment) {

		Session session = sessionFactory.openSession();
		if (session.get(BlogComment.class, commentId) == null)
			return null;
		session.merge(blogComment);
		BlogComment updatedComment = (BlogComment) session.get(BlogComment.class, commentId);
		session.flush();
		session.close();
		return updatedComment;
	}

	@Transactional
	public void deleteComment(String CommentId) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		BlogComment comment = (BlogComment) session.get(BlogComment.class, CommentId);
		session.delete(comment);
		session.flush();
		session.close();
	}

}
