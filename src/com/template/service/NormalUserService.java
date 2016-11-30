package com.template.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.template.entity.NormalUser;
import com.template.mapper.NormalUserMapper;

@Service("normalUserService")
public class NormalUserService extends BaseService<NormalUser>{

	private final static Logger logger = Logger.getLogger(NormalUserService.class);
	
	@Autowired
	private NormalUserMapper normalUserMapper;
	
	public NormalUser getNormalUserById(String userId, String password){
		try {
			NormalUser normalUser = new NormalUser();
			normalUser.setUserId(userId);
			normalUser.setPassword(password);
			Map<String, Object> params = new HashMap<String,Object>();
			params.put("userId", userId);
			params.put("password", password);
			List<NormalUser> normalUserList = normalUserMapper.getNormalUserById(params);
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
