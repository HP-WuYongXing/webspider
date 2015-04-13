package com.oliver.mapper.inter;

import java.util.List;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.SelectProvider;

import com.oliver.db.sql.TestClassProvider;
import com.oliver.models.TestClass;

public interface ITestClassMapper {
   @InsertProvider(type=TestClassProvider.class,method="addTestSql")
   public void addTestClass(TestClass tc);
   
   @SelectProvider(type=TestClassProvider.class,method="queryAllSql")
   @Results(value= {
           @Result(id = true, property = "id", column = "id"),  
           @Result(property = "name", column ="username"),
           @Result(property = "age" , column ="userage"),
           @Result(property = "address", column = "useraddress")})
   public List<TestClass> queryAll();
   
   @DeleteProvider(type=TestClassProvider.class,method="deleteAll")
   public void deleteAll();
   
   @SelectProvider(type=TestClassProvider.class,method="getByIdSql")
   @Results(value= {
           @Result(id = true, property = "id", column = "id"),  
           @Result(property = "name", column ="username"),
           @Result(property = "age" , column ="userage"),
           @Result(property = "address", column = "useraddress")})
   public TestClass getUser(int id);
}