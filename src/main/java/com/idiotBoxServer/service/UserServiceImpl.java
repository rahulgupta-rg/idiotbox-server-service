package com.idiotBoxServer.service;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.idiotBoxServer.dao.UserDao;
import com.idiotBoxServer.pojos.Feedback;
import com.idiotBoxServer.pojos.User;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao dao;

	@Override
	public User validateUser(String email, String password) {
		return dao.validateUser(email, password);
	}

	@Override
	public Integer registerUser(User newUser) {
		return dao.registerUser(newUser);
	}

	@Override
	public String updateUser(User user) {
		return dao.updateUser(user);
	}

	@Override
	public User getUser(String email) {
		return dao.getUser(email);
	}
	
	@Override
	public String registerFeedback(Feedback feedback) {
		
		return dao.registerFeedback(feedback);
	}
}