package com.oliver.dao.inter;

import java.util.List;

import com.oliver.models.Stock;

public interface IStockDao {
	public void insertStock(Stock stock);
	public List<Stock> selectAll();
	public void deleteAll();
}
