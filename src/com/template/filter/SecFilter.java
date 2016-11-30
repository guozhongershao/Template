package com.template.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.template.entity.User;
import com.template.entity.Staff;
import com.template.common.TemplateConstants;
import com.template.common.util.BeanFactoryUtil;
import com.template.service.UserService;
import com.template.service.StaffService;

import sun.util.logging.resources.logging;

public class SecFilter implements Filter {

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		String uri = request.getRequestURI();

		if (request.getSession().getAttribute(TemplateConstants.SESSION_USER_ATTRIBUTE_ID) != null) {// 已登录,放行
			arg2.doFilter(arg0, arg1);
			return;
		} else {
			String userId = request.getParameter("account");

			String password = request.getParameter("password");
//监测点1
			System.out.println("监测点1" + userId + " " + password);
			
			
			if(userId != null && password != null) {
				try {
					UserService UserService = (UserService)BeanFactoryUtil.getBean("UserService");
					User user = UserService.getNormalUserById(userId, password);
					if(user!=null) {
						request.getSession().setAttribute(TemplateConstants.SESSION_USER_ATTRIBUTE_ID, user);
						response.sendRedirect(request.getContextPath() + "/pages/index.html");
						return;
					} else {
						response.sendRedirect(request.getContextPath() + "/pages/login.html");
						return;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		boolean exclude = false;
		if (uri.endsWith("login.jsp") || uri.endsWith("login.do")
				|| uri.endsWith(".css") || uri.endsWith(".js")
				|| uri.endsWith(".jpg") || uri.endsWith(".gif")
				|| uri.endsWith(".png") || uri.endsWith(".ico")
				|| uri.endsWith(".html")
				) {
			exclude = true;
		}
		if (exclude) {
			arg2.doFilter(arg0, arg1);
			return;
		} else {
			response.sendRedirect(request.getContextPath() + "/pages/login.html");
		}

		//arg2.doFilter(arg0, arg1);
		return;
	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
