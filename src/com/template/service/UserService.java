package com.template.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.template.entity.User;
import com.template.mapper.UserMapper;

@Service("UserService")
public class UserService extends BaseService<User>{

	private final static Logger logger = Logger.getLogger(UserService.class);
	
	@Autowired
	private UserMapper userMapper;
	
	public User getNormalUserById(String userId, String password){
		try {
			User user = new User();
			user.setUserId(userId);
			user.setPassword(password);
			Map<String, Object> params = new HashMap<String,Object>();
			params.put("userId", userId);
			params.put("password", password);
			List<User> normalUserList = userMapper.getUserById(params);
			if(normalUserList.isEmpty()){
				return null;
			}else {
				return normalUserList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
}
