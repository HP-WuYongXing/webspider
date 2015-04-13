package com.oliver.service;

import java.util.List;

import com.oliver.models.Stock;

public interface IStockService {

	public void addStock(Stock stock);
	public List<Stock> getAll();
	public void deleteAll();
}
