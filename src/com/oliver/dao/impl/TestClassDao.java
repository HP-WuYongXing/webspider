package com.oliver.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.oliver.models.TestClass;
import com.oliver.dao.inter.ITestClassDao;
import com.oliver.mapper.inter.ITestClassMapper;

@Repository("testClassDao")
public class TestClassDao implements ITestClassDao {
    
	
	@Resource(name="userMapper")
	private ITestClassMapper userMapper;
	
	public void setUserMapper(@Qualifier("userMapper")ITestClassMapper userMapper) {
		this.userMapper = userMapper;
	}

	@Override
	public TestClass getUser(int id) {
		//return (TestClass)this.sqlSession.selectOne(packageName+".getUser", id);
		return userMapper.getUser(id);
	}

	@Override
	public void addTestClass(TestClass tc) {
		
	}

	@Override
	public List<TestClass> queryAll() {
		
		return userMapper.queryAll();
	}

	@Override
	public void deleteAll() {
		userMapper.deleteAll();
	}

}
