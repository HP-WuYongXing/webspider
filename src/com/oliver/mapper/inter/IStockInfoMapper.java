package com.oliver.mapper.inter;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectKey;

import com.oliver.db.sql.StockInfoProvider;
import com.oliver.models.StockInfo;

public interface IStockInfoMapper {
	
	@InsertProvider(type=StockInfoProvider.class,method="insertStockInfoSql")
	@SelectKey(keyProperty="id",keyColumn="id", before = false, resultType = int.class, statement = { "SELECT LAST_INSERT_ID() AS ID" })
	public void insertStockInfo(StockInfo si);
}
