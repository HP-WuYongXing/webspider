package com.oliver.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.oliver.dao.impl.ManagerDao;
import com.oliver.models.Manager;
import com.oliver.service.IManagerService;

@Service("managerService")
public class ManagerService implements IManagerService {

	@Resource(name="managerDao")
	private ManagerDao managerDao;
	
	@Override
	public void addManager(Manager m) {
		managerDao.insertManager(m);
	}

}
