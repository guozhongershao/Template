package com.template.bean;

import java.io.Serializable;

public class StaffBean implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 3599776564012964053L;
	
	private String staffId;
	private String password;
	
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
