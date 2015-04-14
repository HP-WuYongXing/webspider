package com.oliver.dao.inter;

import java.util.List;

import com.oliver.models.TestClass;

public interface ITestClassDao {
	   public void addTestClass(TestClass tc);
	   public List<TestClass> queryAll();
	   public void deleteAll();
	   public TestClass getUser(int id);
}
