package com.idiotBoxServer.dao;

import javax.persistence.EntityManager;
import javax.persistence.StoredProcedureQuery;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.idiotBoxServer.pojos.Feedback;
import com.idiotBoxServer.pojos.User;
import com.idiotBoxServer.util.PasswordHasher;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	private EntityManager manager;

	@Autowired
	private PasswordHasher hasher;
	
	private Session getSession() {
		return manager.unwrap(Session.class);
	}

	@Override
	public User validateUser(String mail, String pass) {
		User currentUser = null;
		System.out.println(1);
		StoredProcedureQuery getUser = manager.createNamedStoredProcedureQuery("validateUser")
				.setParameter("mail", mail);
		System.out.println(2);
		try {
			currentUser = (User) getUser.getSingleResult();
			System.out.println(currentUser);
			if (hasher.matchHashedString(pass, currentUser.getPassword())) {
				System.out.println(4);
				return currentUser;
			}
			System.out.println(5);
			currentUser = null;
			return currentUser;
		} catch (Exception e) 
		{
			System.out.println(6);
			return currentUser;
		}
	}

	@Override
	public Integer registerUser(User newUser) {
		try {
			Session session = getSession();
			newUser.setPassword(hasher.getHashedString(newUser.getPassword()));
			session.save(newUser);
			return newUser.getUserId();
		} catch (Exception e) {
			return -1;
		}
	}

	@Override
	public String updateUser(User user) {
		try {
			Session session = getSession();
			session.update(user);
			return "Request Updated";
		} catch (Exception e) {
			return "User can't be upadated";
		}
	}

	@Override
	public User getUser(String email) {
		User currentUser = null;
		StoredProcedureQuery getUser = manager.createNamedStoredProcedureQuery("getUser").setParameter("email", email);
		try {
			currentUser = (User) getUser.getSingleResult();
			return currentUser;
		} catch (Exception e) {
			return currentUser;
		}
	}
	
	@Override
	public String registerFeedback(Feedback feedback) 
	{	
		Session session = getSession();
		Integer id = (Integer)session.save(feedback);
		System.out.println("F/B id" +id);
		session.close();
		return "Your feedback has been received , Feedback Id is : "+id;
	}
}