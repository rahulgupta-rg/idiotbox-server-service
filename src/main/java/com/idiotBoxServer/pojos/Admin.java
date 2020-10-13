package com.idiotBoxServer.pojos;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@NamedStoredProcedureQuery(name = "validateAdmin", procedureName = "validateAdmin", resultClasses = {
		Admin.class }, parameters = {
				@StoredProcedureParameter(name = "email", type = String.class, mode = ParameterMode.IN) })

@Entity
@Table(name = "admin")
public class Admin {
	private Integer adminId;
	private String fName;
	private String lName;
	@Email
	private String email;
	@Email
	private String aEmail;
	private String phone;
	@NotEmpty
	private String password;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "admin_id")
	public Integer getAdminId() {
		return adminId;
	}

	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}

	@Column(name = "first_name")
	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	@Column(name = "last_name")
	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	@Column(name = "primary_email", unique = true)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "alternate_email")
	public String getaEmail() {
		return aEmail;
	}

	public void setaEmail(String aEmail) {
		this.aEmail = aEmail;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Admin() {
		super();
	}

	public Admin(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

	public Admin(String fName, String lName, String email, String aEmail, String phone, String password) {
		super();
		this.fName = fName;
		this.lName = lName;
		this.email = email;
		this.aEmail = aEmail;
		this.phone = phone;
		this.password = password;
	}

	@Override
	public String toString() {
		return "Admin [adminId=" + adminId + ", fName=" + fName + ", lName=" + lName + ", email=" + email + ", aEmail="
				+ aEmail + ", phone=" + phone + ", password=" + password + "]";
	}
}