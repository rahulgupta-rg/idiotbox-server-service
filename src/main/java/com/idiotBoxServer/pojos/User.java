package com.idiotBoxServer.pojos;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

@NamedStoredProcedureQuery(name = "validateUser", procedureName = "validateUser", resultClasses = {
		User.class }, parameters = {
				@StoredProcedureParameter(name = "mail", type = String.class, mode = ParameterMode.IN) })

@NamedStoredProcedureQuery(name = "getUser", procedureName = "getUser", resultClasses = { User.class }, parameters = {
		@StoredProcedureParameter(name = "email", type = String.class, mode = ParameterMode.IN), })

@Entity
@Table
public class User {

	private Integer userId;
	private String fName;
	private String lName;
	@Email
	private String email;
	@Email
	private String aEmail;
	@NotEmpty
	private String password;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birthDate;
	private String gender;
	private String phone;
	private String twoFactAuthStatus;

	public User() {
		super();
	}

	public User(String fName, String lName, String email, String aEmail, String password, Date birthDate, String gender,
			String phone) {
		super();
		this.fName = fName;
		this.lName = lName;
		this.email = email;
		this.aEmail = aEmail;
		this.password = password;
		this.birthDate = birthDate;
		this.gender = gender;
		this.phone = phone;
	}

	public User(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name = "first_name")
	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	@Column(name = "primary_email", unique = true)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "alternate_email")
	public String getAEmail() {
		return aEmail;
	}

	public void setAEmail(String aEmail) {
		this.aEmail = aEmail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Temporal(TemporalType.DATE)
	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "last_name")
	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	@Column(name = "two_fact_auth_status")
	public String getTwoFactAuthStatus() {
		return twoFactAuthStatus;
	}

	public void setTwoFactAuthStatus(String twoFactAuthStatus) {
		this.twoFactAuthStatus = twoFactAuthStatus;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", fName=" + fName + ", lName=" + lName + ", email=" + email + ", aEmail="
				+ aEmail + ", password=" + password + ", birthDate=" + birthDate + ", gender=" + gender + ", phone="
				+ phone + "]";
	}
}