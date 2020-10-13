package com.idiotBoxServer.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.StoredProcedureQuery;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.idiotBoxServer.pojos.Admin;
import com.idiotBoxServer.pojos.Feedback;
import com.idiotBoxServer.util.PasswordHasher;

@Repository
public class AdminDaoImpl implements AdminDao {

	@Autowired
	private EntityManager manager;

	@Autowired
	PasswordHasher hasher;

	private Session getSession() {
		return manager.unwrap(Session.class);
	}

	@Override
	public Admin validateAdmin(String email, String password) {
		Admin currentAdmin = null;
		StoredProcedureQuery getAdmin = manager.createNamedStoredProcedureQuery("validateAdmin").setParameter("email",
				email);
		try {
			currentAdmin = (Admin) getAdmin.getSingleResult();
			if (hasher.matchHashedString(password, currentAdmin.getPassword())) {
				return currentAdmin;
			}
			currentAdmin = null;
			return currentAdmin;
		} catch (Exception e) {
			return currentAdmin;
		}
	}

	@Override
	public String registerAdmin(Admin admin) {
		try {
			Session session = getSession();
			admin.setPassword(hasher.getHashedString(admin.getPassword()));
			session.save(admin);
			return "Admin " + admin.getfName() + " Added successfully";
		} catch (Exception e) {
			return "Unsuccesfull attempt to add Admin";
		}
	}

	@Override
	public String changePassword(String email) {
		return null;
	}

	@Override
	public String blockUser(int userId) {
		return null;
	}

	@Override
	public List<Admin> getAllAdmins(Integer id) {
		String jpql = "select a from Admin a where a.adminId!= :id";
		try {
			Session session = getSession();
			List<Admin> adminList = session.createQuery(jpql, Admin.class).setParameter("id", id).getResultList();
			return adminList;
		} catch (Exception e) {
			List<Admin> adminList = null;
			return adminList;
		}
	}

	@Override
	public String deleteAdmin(Integer id) {
		try {
			Admin admin = getSession().get(Admin.class, id);
			Session session = getSession();
			session.delete(admin);
			return "Admin " + admin.getfName() + " deleted successfully";
		} catch (Exception e) {
			return "Admin can't be deleted";
		}
	}

	@Override
	public List<Feedback> getFeedbackList() {
		try {
			Session session = getSession();
			String flag = "unread";
			String jpql = "select f from Feedback f where f.flag= :fl";
			List<Feedback> feedbackList = session.createQuery(jpql, Feedback.class).setParameter("fl", flag)
					.getResultList();
			return feedbackList;
		} catch (Exception e) {
			List<Feedback> feedbackList = null;
			return feedbackList;
		}
	}
	
	@Override
	public String updateAdmin(Admin admin)
	{
		Session session = getSession();
		session.update(admin);
		session.close();
		return "Request updated";
	}
	 
}