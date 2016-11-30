package com.template.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.template.entity.Staff;
import com.template.bean.StaffBean;
import com.template.common.TemplateConstants;
import com.template.common.util.MiscUtil;
import com.template.service.StaffService;

@Controller
@RequestMapping("/staff")
public class StaffController {
	
	@Autowired
	private StaffService staffService;
	
	@RequestMapping("/login")
	public String login(String staffId,String password,HttpServletRequest request,HttpServletResponse response) {		
		
		Staff staff = staffService.login(staffId,password);

		if(staff == null) {
			return "/login";
		} else {
			request.getSession().setAttribute(TemplateConstants.SESSION_USER_ATTRIBUTE_ID, staff);
			return "/index";
		}
		
	}
	
	@RequestMapping("/getAllUser")
	public void getAllUser(HttpServletRequest request,HttpServletResponse response) {
		List<Staff> staffList = staffService.getAllUser();
		if(staffList == null) {
			staffList = new ArrayList<Staff>();
		}
		JSONArray ja = JSONArray.fromObject(staffList,MiscUtil.getJsonConfig());
		MiscUtil.writeJsonToResponse(ja.toString(),response);
	}
	
	@RequestMapping("/getAUser")
	public void getAIKFUser(HttpServletRequest request,HttpServletResponse response) {
		List<StaffBean> staffList = staffService.getAUser();
		if(staffList == null) {
			staffList = new ArrayList<StaffBean>();
		}
		JSONArray ja = JSONArray.fromObject(staffList,MiscUtil.getJsonConfig());
		MiscUtil.writeJsonToResponse(ja.toString(),response);
	}
	
}
