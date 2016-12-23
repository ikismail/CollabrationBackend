package com.cart.controller;

import java.net.URI;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.cart.model.BlogComment;
import com.cart.service.BlogCommentService;

@RestController
public class BlogCommentController {

	@Autowired
	private BlogCommentService blogCommentService;

	@RequestMapping(value = "/getAllComments", method = RequestMethod.GET)
	public ResponseEntity<List<BlogComment>> getAllComments() {

		System.out.println(blogCommentService.commentList());

		List<BlogComment> comments = blogCommentService.commentList();

		if (comments.isEmpty())
			return new ResponseEntity<List<BlogComment>>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<List<BlogComment>>(comments, HttpStatus.OK);
	}

	@RequestMapping(value = "/blogComment/{blogId}", method = RequestMethod.GET)
	public ResponseEntity<List<BlogComment>> getCommentById(@PathVariable("blogId") String blogId) {
		List<BlogComment> comment = blogCommentService.getCommentById(blogId);
		// Comment Id [1] doesn't exist - 1
		if (comment == null)
			return new ResponseEntity<List<BlogComment>>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<List<BlogComment>>(comment, HttpStatus.OK);
	}

	@RequestMapping(value = "/comment", method = RequestMethod.POST)
	// RequestBody - to convert JSON data to java object
	// ResponseBody -> servet to client
	// RequestBody -> client to server
	public ResponseEntity<Void> createComment(@RequestBody BlogComment comment, UriComponentsBuilder build) {
		Date date = new Date();

		comment.setCommentedDate(date.toString());

		blogCommentService.saveComment(comment);
		HttpHeaders headers = new HttpHeaders();
		// http://localhost:8080/appname/Comment/210
		URI urilocation = build.path("/comment/").path(String.valueOf(comment.getCommentId())).build().toUri();
		headers.setLocation(urilocation);
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/comment/{commentId}", method = RequestMethod.PUT)
	public ResponseEntity<BlogComment> updateComment(@PathVariable("commentId") String commentId,
			@RequestBody BlogComment comment) {
		// Comment -> from client
		// updatedComment -> from database
		BlogComment updatedComment = blogCommentService.updateComment(commentId, comment);
		if (comment == null)
			return new ResponseEntity<BlogComment>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<BlogComment>(updatedComment, HttpStatus.OK);

	}

	@RequestMapping(value = "/comment/{commentId}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteComment(@PathVariable("commentId") String commentId) {
		BlogComment comment = blogCommentService.getById(commentId);
		if (comment == null)
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		blogCommentService.deleteComment(commentId);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
