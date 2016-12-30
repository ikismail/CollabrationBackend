package com.cart.controller;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.util.SystemPropertyUtils;

public class MailController {

	private MailSender mailSender;

	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void sendMail(String from, String to, String subject, String message) {
		System.out.println("********Send Mail");
		SimpleMailMessage sMessage = new SimpleMailMessage();

		System.out.println("From: " + from + "/n To: " + to + "Subject: " + subject + "Message: " + message);
		sMessage.setFrom(from);
		sMessage.setTo(to);
		sMessage.setSubject(subject);
		sMessage.setText(message);
		mailSender.send(sMessage);
	}
}
