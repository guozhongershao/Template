package com.template.mapper;

import java.util.List;
import java.util.Map;

import com.template.entity.User;

import tk.mybatis.mapper.common.Mapper;

public interface UserMapper extends Mapper<User>{

	public List<User> getUserById(Map<String, Object> params) throws Exception;
    
}
