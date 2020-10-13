package com.idiotBoxServer.dao;

import com.idiotBoxServer.pojos.Feedback;
import com.idiotBoxServer.pojos.User;

public interface UserDao {
	User validateUser(String mail, String pass);

	Integer registerUser(User newUser);

	String updateUser(User user);

	User getUser(String mail);
	
	String registerFeedback(Feedback feedback);
}