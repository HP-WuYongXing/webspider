package com.oliver.mapper.inter;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectKey;

import com.oliver.db.sql.StockMarketProvider;
import com.oliver.models.StockMarket;

public interface IStockMarketMapper {
	
	@InsertProvider(type=StockMarketProvider.class,method="insertStockMarketSql")
	@SelectKey(keyProperty="id",keyColumn="id", before = false, resultType = int.class, statement = { "SELECT LAST_INSERT_ID() AS ID" })
	public void insertStockMarket(StockMarket stockMarket);
}
