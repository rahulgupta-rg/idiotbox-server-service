package com.idiotBoxServer.dao;

import java.util.List;
import com.idiotBoxServer.pojos.Admin;
import com.idiotBoxServer.pojos.Feedback;

public interface AdminDao {
	Admin validateAdmin(String email, String password);

	String registerAdmin(Admin admin);

	String changePassword(String email);

	String blockUser(int userId);

	List<Admin> getAllAdmins(Integer id);

	String deleteAdmin(Integer id);
	
	List<Feedback> getFeedbackList();
	
	String updateAdmin(Admin admin);
}