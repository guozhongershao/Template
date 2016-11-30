package com.template.entity;

import java.io.Serializable;

public class NormalUser implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 398902069774019320L;
	private String userId;
	private String password;
	
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
