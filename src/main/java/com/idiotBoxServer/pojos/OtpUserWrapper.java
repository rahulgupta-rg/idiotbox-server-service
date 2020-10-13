package com.idiotBoxServer.pojos;

public class OtpUserWrapper {
	private String otp;
	private User user;

	public OtpUserWrapper() {
		super();
	}

	public OtpUserWrapper(User user) {
		super();
		this.user = user;
	}

	public OtpUserWrapper(String otp, User user) {
		super();
		this.otp = otp;
		this.user = user;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}