package com.template.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.template.bean.StaffBean;
import com.template.common.DataSource;
import com.template.entity.Staff;
import com.template.mapper.StaffMapper;

@Service("staffService")
public class StaffService extends BaseService<Staff>{

	private final static Logger logger = Logger.getLogger(StaffService.class);
	
	@Autowired
	private StaffMapper staffMapper;

	//使用此方式切换数据源
    @DataSource(name = DataSource.tempalte)
	public Staff login(String staffId,String password){
		try {
			Staff staff = new Staff();
			staff.setStaffId(staffId);
			staff.setStaffPwd(password);

//监测点2
			System.out.println("监测点2" + staffId + " " + password);
			
			List<Staff> staffList = this.queryListByWhere(staff);
			if(staffList == null || staffList.isEmpty()) {
				return null;
			} else {
				return staffList.get(0);
			}
			
		} catch (Exception e) {
			logger.error(e);
		}
		return null;
	}

	public List<Staff> getAllUser() {
		try {
			return this.queryAll();
		} catch (Exception e) {
			logger.error(e);
		}
		return null;
	}

	public List<StaffBean> getAUser() {
		try {
			return staffMapper.getAUser();
		} catch (Exception e) {
			logger.error(e);
		}
		return null;
	}
	
}
