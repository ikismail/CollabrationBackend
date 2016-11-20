package com.cart.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cart.model.Blog;

@Repository
public class BlogDaoImpl implements BlogDao {

	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Transactional
	public List<Blog> getAllblogs() {
		Session session = sessionFactory.openSession();
		List<Blog> blogs = session.createQuery("from Blog").list();
		System.out.println("Ending Blogs method in DaoImpl");
		return blogs;
	}

	@Transactional
	public void saveBlog(Blog blog) {
		Session session = sessionFactory.openSession();
		System.out.println("Starting save Blog method in DaoImpl");
		session.save(blog);
		session.close();
	}

	@Transactional
	public void updateBlog(Blog blog) {
		Session session = sessionFactory.openSession();
		System.out.println("Starting update method in DaoImpl");
		session.update(blog);
		session.flush();
		session.close();

	}

	@Transactional
	public Blog getBlogById(String blogId) {
		Session session = sessionFactory.openSession();
		Blog blog = (Blog) session.get(Blog.class, blogId);
		session.close();
		return blog;
	}

}
