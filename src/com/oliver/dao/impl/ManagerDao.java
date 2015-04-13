package com.oliver.dao.impl;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import com.oliver.models.Manager;
import com.oliver.dao.inter.IManagerDao;
import com.oliver.db.DBHelper;
import com.oliver.mapper.inter.IManagerMapper;

@Repository("managerDao")
public class ManagerDao implements IManagerDao{

	@Resource(name="managerMapper")
	private IManagerMapper mapper;
	
	@Override
	public void insertManager(Manager m) {
		mapper.insertManager(m);
	}

	
}
