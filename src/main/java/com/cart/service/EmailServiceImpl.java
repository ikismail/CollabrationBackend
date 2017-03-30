package com.cart.service;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

	public void mail(String mailId, String userName, String mess, String subject) {

		try {
			String result;

			final String to = mailId;
			System.out.println("Mail To: " + mailId);
			System.out.println("userName: " + userName);

			String mes1 = "Dear " + userName + ", <br/><br/> <i>Greetings from MailBook Team!.</i> ";

			String mes2 = mes1.concat(mess);

			final String from = "dignxtismail@gmail.com";
			final String pass = "project12";

			// defining gmail host
			String host = "smtp.gmail.com";

			// creating properties
			Properties pros = new Properties();
			pros.put("mail.smtp.host", host);
			pros.put("mail.transport.protocol", "smtp");
			pros.put("mail.smtp.auth", "true");
			pros.put("mail.smtp.starttls.enable", "true");
			pros.put("mail.user", from);
			pros.put("mail.password", pass);
			pros.put("mail.port", "465");

			// Authorized the Session Object
			Session mailSession = Session.getInstance(pros, new javax.mail.Authenticator() {

				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(from, pass);
				}

			});

			try {
				// Create a default MimeMessage object.
				MimeMessage message = new MimeMessage(mailSession);
				// Set From: header field of the header.
				message.setFrom(new InternetAddress(from));
				// Set To: header field of the header.
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
				// Set Subject: header field
				message.setSubject(subject);
				// Now set the actual message
				message.setContent(mes2, "text/html; charset=utf-8");
				// Send message
				Transport.send(message);
			} catch (MessagingException mex) {
				result = "Error: unable to send mail....";
				System.out.println(result);
				mex.printStackTrace();

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
