package com.cart.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.cart.model.BaseDomain;
import com.cart.model.Blog;
import com.cart.model.BlogComment;
import com.cart.model.Friend;
import com.cart.model.Job;
import com.cart.model.JobApplication;
import com.cart.model.User;

@Configuration
@EnableTransactionManagement
public class DBConfig {

	@Bean
	public SessionFactory sessionFactory() {
		LocalSessionFactoryBuilder lsf = new LocalSessionFactoryBuilder(getDataSource());
		Properties hibernateProperties = new Properties();
		hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
		hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "update");
		hibernateProperties.setProperty("hibernate.show_sql", "true");
		lsf.addProperties(hibernateProperties);
		return lsf.addAnnotatedClass(User.class).addAnnotatedClass(BaseDomain.class).addAnnotatedClass(Blog.class)
				.addAnnotatedClass(Job.class).addAnnotatedClass(Friend.class).addAnnotatedClass(BlogComment.class)
				.addAnnotatedClass(JobApplication.class).buildSessionFactory();

	}

	@Bean
	public DataSource getDataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("org.h2.Driver");
		dataSource.setUrl("jdbc:h2:tcp://localhost/~/collabration");
		dataSource.setUsername("sa");
		dataSource.setPassword("sa");
		return dataSource;
	}

	@Bean
	public HibernateTransactionManager hibTransManager() {
		return new HibernateTransactionManager(sessionFactory());
	}
}
