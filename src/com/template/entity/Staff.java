package com.template.entity;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "staff")
public class Staff implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 3797498064549922822L;

	@Id
    private String staffId;

    private String staffPwd;


    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId == null ? null : staffId.trim();
    }


    public String getStaffPwd() {
        return staffPwd;
    }

    public void setStaffPwd(String staffPwd) {
        this.staffPwd = staffPwd == null ? null : staffPwd.trim();
    }

    public String toString() {
        return "staff [staffId=" + staffId + ", staffPwd=" + staffPwd + "]";
    }
    
    
}