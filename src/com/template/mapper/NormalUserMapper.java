package com.template.mapper;

import java.util.List;
import java.util.Map;

import com.template.entity.NormalUser;

import tk.mybatis.mapper.common.Mapper;

public interface NormalUserMapper extends Mapper<NormalUser>{

	public List<NormalUser> getNormalUserById(Map<String, Object> params) throws Exception;
    
}
