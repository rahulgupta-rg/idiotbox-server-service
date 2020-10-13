package com.idiotBoxServer;

import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class SMTPEmailSender {
	@Autowired
	private JavaMailSender javaMailSender;
	private String emailSubject;
	private String body;
	private String password;

	public void setValues() {
		
		System.out.println("Setting the values for sending email");
		emailSubject = "Registration Successfull";
		body = "Dear User, Your OTP is ";
	}

	public void send(String to, String subject, String body) throws MessagingException {
		
		System.out.println("Sending the mail");
		password = getOtp();
		body = body + password;
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper;
		helper = new MimeMessageHelper(message, true);
		helper.setSubject(subject);
		helper.setTo(to);
		helper.setText(body, true);
		System.out.println("Just before sending");
		javaMailSender.send(message);
		System.out.println("Mail sent");
	}

	public void sendToAdmin(String from, String subject, String body) throws MessagingException {
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper;
		helper = new MimeMessageHelper(message, true);
		helper.setSubject(subject);
		helper.setTo("idiotboxstreamingservices@gmail.com");
		helper.setFrom(from);
		helper.setText(body, true);
		javaMailSender.send(message);
	}

	public String getOtp() {
		String numbers = "0123456789";
		Random random = new Random();
		char[] otpChar = new char[4];
		for (int i = 0; i < otpChar.length; i++) {
			otpChar[i] = numbers.charAt(random.nextInt(numbers.length()));
		}
		String otp = new String(otpChar);
		password = otp;
		return otp;
	}

	public String getEmailSubject() {
		return emailSubject;
	}

	public String getBody() {
		return body;
	}

	public String getPassword() {
		return password;
	}

	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}