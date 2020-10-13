package com.idiotBoxServer.pojos;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "Comments")

public class Comment {

	Integer cid;
	String text;
	Integer userid;
	String username;
	int pcomid;
	Date dt;

	public Comment(String text, Integer userid, String username, int pcomid, Date dt) {
		super();
		this.text = text;
		this.userid = userid;
		this.username = username;
		this.pcomid = pcomid;
		this.dt = dt;
	}

	public Comment(String text, Integer userid, String username, Date dt) {
		super();
		this.text = text;
		this.userid = userid;
		this.username = username;
		this.dt = dt;
	}

	public Comment() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	@Column(nullable = true)
	public Integer getPcomid() {
		return pcomid;
	}

	public void setPcomid(Integer pcomid) {
		this.pcomid = pcomid;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	@Column(name = "created", updatable = false)
	public Date getDt() {
		return dt;
	}

	public void setDt(Date dt) {
		this.dt = dt;
	}

	@Override
	public String toString() {
		return "Comment [text=" + text + ", userid=" + userid + ", username=" + username + ", pcomid=" + pcomid
				+ ", dt=" + dt + "]\n";
	}
}