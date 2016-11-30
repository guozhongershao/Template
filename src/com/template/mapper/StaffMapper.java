package com.template.mapper;

import java.util.List;

import com.template.bean.StaffBean;
import com.template.entity.Staff;

import tk.mybatis.mapper.common.Mapper;

public interface StaffMapper extends Mapper<Staff>{

	public List<StaffBean> getAUser() throws Exception;
    
}
