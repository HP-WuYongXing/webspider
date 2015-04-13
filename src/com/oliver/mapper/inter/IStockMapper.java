package com.oliver.mapper.inter;

import java.util.List;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.SelectProvider;

import com.oliver.db.sql.StockProvider;
import com.oliver.models.Stock;

public interface IStockMapper {
	
	@InsertProvider(type=StockProvider.class,method="insertStockSql")
	@SelectKey(keyProperty="id",keyColumn="id", before = false, resultType = int.class, statement = { "SELECT LAST_INSERT_ID() AS ID" })
	public void insertStock(Stock stock);
	
	@SelectProvider(type=StockProvider.class,method="selectAllSql")
	@Results(value={
			@Result(id=true,property="id",column="id"),
			@Result(property="code",column="code"),
			@Result(property="name",column="name")
	})
	public List<Stock> selectAll();
	
	@DeleteProvider(type=StockProvider.class,method="deleteAllSql")
	public void deleteAll();
}
