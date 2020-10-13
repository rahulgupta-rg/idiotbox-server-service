package com.idiotBoxServer.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.idiotBoxServer.dao.AdminDao;
import com.idiotBoxServer.pojos.Admin;
import com.idiotBoxServer.pojos.Feedback;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminDao dao;

	@Override
	public Admin validateAdmin(String email, String password) {
		return dao.validateAdmin(email, password);
	}

	@Override
	public String registerAdmin(Admin admin) {
		return dao.registerAdmin(admin);
	}

	@Override
	public String changePassword(String email) {
		return dao.changePassword(email);
	}

	@Override
	public String blockUser(int userId) {
		return dao.blockUser(userId);
	}

	@Override
	public List<Admin> getAllAdmins(Integer id) {
		return dao.getAllAdmins(id);
	}

	@Override
	public String deleteAdmin(Integer id) {
		return dao.deleteAdmin(id);
	}

	@Override
	public List<Feedback> getFeedbackList() {
		return dao.getFeedbackList();
	}
	@Override
	public String updateAdmin(Admin admin) {
		// TODO Auto-generated method stub
		return dao.updateAdmin(admin);
	}
}