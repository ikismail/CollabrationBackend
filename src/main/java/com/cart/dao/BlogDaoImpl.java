package com.cart.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.hibernate.Query;
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
	public boolean saveBlog(Blog blog) {
		Session session = sessionFactory.openSession();
		System.out.println("Starting save Blog method in DaoImpl");
		try {
			session.save(blog);
			session.close();
			return true;
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Transactional
	public boolean updateStatus(Blog blog) {
		Session session = sessionFactory.openSession();
		System.out.println("Starting update method in DaoImpl");
		try {
			session.update(blog);
			session.flush();
			session.close();
			return true;
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}

	@Transactional
	public void deleteBlog(String blogId) {
		Session session = sessionFactory.openSession();
		Blog blog = (Blog) session.get(Blog.class, blogId);
		session.delete(blog);
		session.flush();
		session.close();
	};

	@Transactional
	public Blog getBlogById(String blogId) {
		Session session = sessionFactory.openSession();
		Blog blog = (Blog) session.get(Blog.class, blogId);
		session.close();
		return blog;
	}

	public Blog updateBlog(String blogId, Blog blog) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Blog currentBlog = (Blog) session.get(Blog.class, blogId);
		if (currentBlog == null) {
			return null;
		}
		session.merge(blog);
		Blog updatedBlog = (Blog) session.get(Blog.class, blogId);
		session.flush();
		session.close();
		return updatedBlog;
	}

	public void increaseLikes(String blogId) {
		Session session = sessionFactory.openSession();
		System.out.println("---starting likes ");
		Blog blog = getBlogById(blogId);
		System.out.println("Before Like: "+ blog.getLikes());
		blog.setLikes(blog.getLikes() + 1);
		System.out.println("After Like: "+ blog.getLikes());
		session.update(blog);
		session.flush();
		session.close();
	}

	public void increaseDislikes(String blogId) {
		Session session = sessionFactory.openSession();
		System.out.println("---starting likes ");
		Blog blog = getBlogById(blogId);
		System.out.println("Before Dislike: "+ blog.getDislikes());
		blog.setDislikes(blog.getDislikes() + 1);
		System.out.println("After Dislike: "+ blog.getDislikes());
		session.update(blog);
		session.flush();
		session.close();
	}

}
