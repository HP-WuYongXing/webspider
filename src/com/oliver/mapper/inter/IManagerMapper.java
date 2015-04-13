package com.oliver.mapper.inter;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectKey;

import com.oliver.db.sql.ManagerProvider;
import com.oliver.models.Manager;

public interface IManagerMapper {
	@InsertProvider(type=ManagerProvider.class,method="insertManagerSql")
	@SelectKey(keyProperty="id",keyColumn="id", before = false, resultType = int.class, statement = { "SELECT LAST_INSERT_ID() AS ID" })
	public void insertManager(Manager m);
}
