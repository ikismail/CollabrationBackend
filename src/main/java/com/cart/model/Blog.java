package com.cart.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "blog")
public class Blog extends BaseDomain {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String blogId;
	private String title;
	private String description;
	private String blogCreatedDate;
	private String status;
	private String postLike;
	

	public String getPostLike() {
		return postLike;
	}

	public void setPostLike(String postLike) {
		this.postLike = postLike;
	}

	public String getBlogId() {
		return blogId;
	}

	public void setBlogId(String blogId) {
		this.blogId = blogId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBlogCreatedDate() {
		return blogCreatedDate;
	}

	public void setBlogCreatedDate(String blogCreatedDate) {
		this.blogCreatedDate = blogCreatedDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
