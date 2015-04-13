package com.oliver.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import com.oliver.models.Stock;
import com.oliver.dao.inter.IStockDao;
import com.oliver.db.DBHelper;
import com.oliver.mapper.inter.IStockMapper;

@Repository("stockDao")
public class StockDao implements IStockDao {

	@Resource(name="stockMapper")
	private IStockMapper stockMapper;
	
	@Override
	public void insertStock(Stock stock) {
		stockMapper.insertStock(stock);
	}

	@Override
	public List<Stock> selectAll() {
		return stockMapper.selectAll();
	}

	@Override
	public void deleteAll() {
		stockMapper.deleteAll();
	}

}
