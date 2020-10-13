package com.idiotBoxServer.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.idiotBoxServer.SMTPEmailSender;
import com.idiotBoxServer.pojos.Feedback;
import com.idiotBoxServer.pojos.OtpUserWrapper;
import com.idiotBoxServer.pojos.User;
import com.idiotBoxServer.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService service;

	@Autowired
	private SMTPEmailSender smtpEmailSender;

	@PostMapping("/login")
	public ResponseEntity<?> postLogin(@RequestBody User user) {
		try {
			System.out.println("Hello user1");
			user = service.validateUser(user.getEmail(), user.getPassword());
			System.out.println("Hello user2" + user);
			OtpUserWrapper wrapper = null;
			
			if (user != null) {
				System.out.println("Again Here" + user.getTwoFactAuthStatus());
				if (user.getTwoFactAuthStatus().equals("Active")) {
					System.out.println("I am Active");
					smtpEmailSender.setValues();
					smtpEmailSender.send(user.getEmail(), smtpEmailSender.getEmailSubject(), smtpEmailSender.getBody());
					wrapper = new OtpUserWrapper(smtpEmailSender.getPassword(), user);
					ResponseEntity<OtpUserWrapper> entity = new ResponseEntity<OtpUserWrapper>(wrapper, HttpStatus.OK);
					System.out.println("Hello user");
					return entity;
				} else {
					System.out.println("Kya Yaar");
					wrapper = new OtpUserWrapper(user);
					ResponseEntity<OtpUserWrapper> entity = new ResponseEntity<OtpUserWrapper>(wrapper, HttpStatus.OK);

					return entity;
				}
			} else {
				ResponseEntity<String> entity = new ResponseEntity<>("Invalid user id or password",
						HttpStatus.NOT_FOUND);
				return entity;
			}
		} catch (Exception e) {
			user = null;
			ResponseEntity<User> entity = new ResponseEntity<User>(user, HttpStatus.NOT_FOUND);
			return entity;
		}
	}

	@PostMapping("/verifyOtp")
	public ResponseEntity<?> postRegistration(@RequestBody User user) {
		System.out.println(user);
		try {
			int id = service.registerUser(user);
			ResponseEntity<?> entity = new ResponseEntity<Integer>(id, HttpStatus.OK);
			return entity;
		} catch (Exception e) {
			ResponseEntity<?> entity = new ResponseEntity<Integer>(0, HttpStatus.NOT_FOUND);
			return entity;
		}
	}

	@PostMapping("/register")
	public ResponseEntity<String> postRegisterForm(@RequestBody User user, HttpSession hs) 
	{
		System.out.println("In Rest register user form" + user);
		try 
		{
		System.out.println(user);
		smtpEmailSender.setValues();
		smtpEmailSender.send(user.getEmail(), smtpEmailSender.getEmailSubject(), smtpEmailSender.getBody());
		System.out.println("Email sent");
		String password = smtpEmailSender.getPassword();
		hs.setAttribute("requestedUserAccount", user);
		hs.setAttribute("otpSent", password);
		System.out.println("otp sent "+password+" Requested User account "+user);	
		ResponseEntity<String> entity = new ResponseEntity<String>(password, HttpStatus.OK);
		System.out.println(user.getEmail()+ " "+smtpEmailSender.getEmailSubject());
		return entity;
	} 
	catch (Exception e)
	{
		ResponseEntity<String> entity = new ResponseEntity<String>("Error in Registration", HttpStatus.NOT_FOUND);
		System.out.println("Error in catch");
		return entity;
	}
	}
	
	@GetMapping("/index")
	public String showIndex() {
		return "/user/index";
	}

	@PostMapping("/sendEmail")
	public ResponseEntity<String> sendEmailToAdmin(@RequestBody List<String> list) {
		ResponseEntity<String> entity = null;
		try {
			User user = service.getUser(list.get(0));
			Feedback feedback = new Feedback(user.getUserId(), list.get(2), "unread", list.get(1), user.getEmail());
			String feedbackResponse = service.registerFeedback(feedback);
			entity = new ResponseEntity<String>(feedbackResponse, HttpStatus.OK);
			System.out.println("All good on rest");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Exception in rest");
		}
		return entity;
	}
	@PostMapping("/twoWayAuthStatus")
	public ResponseEntity<String> toggleStatus(@RequestBody User user)
	{
		System.out.println(user);
		String updateStatus = service.updateUser(user);
		ResponseEntity<String> entity = new ResponseEntity<String>(updateStatus,HttpStatus.OK);
		System.out.println("Sending entity after update");
		return entity;
	}
	
	@PostMapping("/changePassword")
	public ResponseEntity<String> changePassword(@RequestBody User user)
	{
		System.out.println("Received user details : "+user);
		String updateStatus = service.updateUser(user);
		ResponseEntity<String> entity = new ResponseEntity<String>(updateStatus,HttpStatus.OK);
		System.out.println("Sending entity after update");
		return entity;
	}
	@PostMapping("/forgetPassword")
	public ResponseEntity<OtpUserWrapper> changePassword(@RequestBody String email)
	{
		smtpEmailSender.setValues();
		User user = service.getUser(email);
		System.out.println("Received user details : "+user);
		
		try
		{
			smtpEmailSender.send(user.getEmail(), smtpEmailSender.getEmailSubject(), smtpEmailSender.getBody());
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		String password = smtpEmailSender.getPassword();
		OtpUserWrapper wrapper = new OtpUserWrapper(password, user);
		ResponseEntity<OtpUserWrapper> entity = new ResponseEntity<OtpUserWrapper>(wrapper,HttpStatus.OK);
		System.out.println(entity.getBody().getOtp()+" "+entity.getBody().getUser().getPassword());
		return entity;
	}
	@PostMapping("/sendOtpToEmail")
	public ResponseEntity<String> sendOtpToEmail(@RequestBody User user)
	{
		ResponseEntity<String> entity = null;
		System.out.println("Rest user "+user);
		try
		{
			smtpEmailSender.setValues();
			smtpEmailSender.send(user.getEmail(), smtpEmailSender.getEmailSubject(), user.getPassword());
			entity = new ResponseEntity<String>("Otp sent successfully",HttpStatus.OK);
			System.out.println("All good on rest");
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			System.out.println("Exception in rest");
		}
		return entity;
		
	}
	
}