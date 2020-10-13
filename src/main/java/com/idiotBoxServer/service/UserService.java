 	package com.idiotBoxServer.service;

import com.idiotBoxServer.pojos.Feedback;
import com.idiotBoxServer.pojos.User;

public interface UserService {
	User validateUser(String mail, String pass);

	Integer registerUser(User user);

	String updateUser(User user);

	User getUser(String mail);
	
	String registerFeedback(Feedback feedback);
}