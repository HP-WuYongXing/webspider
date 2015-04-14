package com.oliver.mapper.inter;

import java.util.List;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.oliver.db.sql.NewsItemProvider;
import com.oliver.models.NewsItem;

public interface INewsItemMapper {

	@InsertProvider(type=NewsItemProvider.class,method="insertNewsItemSql")
	@SelectKey(keyProperty="id",keyColumn="id", before = false, resultType = int.class, statement = { "SELECT LAST_INSERT_ID() AS ID" })
	public void insertNewsItem(NewsItem item);
	
	@SelectProvider(type=NewsItemProvider.class,method="selectByIdSql")
	@Results(value= {
	           @Result(id = true, property = "id", column = "id"),  
	           @Result(property = "title", column ="title"),
	           @Result(property = "time" , column ="time"),
	           @Result(property = "link", column = "link"),
	           @Result(property = "newsType", column = "type"),
	           @Result(property = "hot", column = "hot"),
	           @Result(property = "thumbnail", column ="thumbnail"),
	           @Result(property = "stockId",column="stock_id"),
	           @Result(property = "urlCode",column="url_code")})
	public NewsItem selectById(int id);
	
	@DeleteProvider(type=NewsItemProvider.class,method="deleteAllNewsItemsSql")
	public void deleteAllNewsItems();
	
	@SelectProvider(type=NewsItemProvider.class,method="selectAllSql")
	@Results(value= {
	           @Result(id = true, property = "id", column = "id"),  
	           @Result(property = "title", column ="title"),
	           @Result(property = "time" , column ="time"),
	           @Result(property = "link", column = "link"),
	           @Result(property = "newsType", column = "type"),
	           @Result(property = "hot", column = "hot"),
	           @Result(property = "thumbnail", column ="thumbnail"),
	           @Result(property = "stockId",column="stock_id"),
	           @Result(property = "urlCode",column="url_code")})
	public List<NewsItem> selectAll();
	
	@SelectProvider(type=NewsItemProvider.class,method="selectListByTypeAtOffsetSql")
	@Results(value= {
	           @Result(id = true, property = "id", column = "id"),  
	           @Result(property = "title", column ="title"),
	           @Result(property = "time" , column ="time"),
	           @Result(property = "link", column = "link"),
	           @Result(property = "newsType", column = "type"),
	           @Result(property = "hot", column = "hot"),
	           @Result(property = "thumbnail", column ="thumbnail"),
	           @Result(property = "stockId",column="stock_id"),
	           @Result(property = "urlCode",column="url_code")})
	public List<NewsItem> selectListByTypeAtOffset(int offset,int limit,int type);
	
	@DeleteProvider(type=NewsItemProvider.class,method="deleteByIdSql")
	public void deleteById(int id);
	
	@DeleteProvider(type=NewsItemProvider.class,method="deleteByTypeAndUrlCodeSql")
	public void deleteByTypeAndUrlCode(int type,int urlCode);

	@UpdateProvider(type=NewsItemProvider.class,method="updateNewsItemSql")
	public void updateNewsItem(NewsItem item);
	
	@SelectProvider(type=NewsItemProvider.class,method="selectListByTypeAtLimitSql")
	@Results(value= {
	           @Result(id = true, property = "id", column = "id"),  
	           @Result(property = "title", column ="title"),
	           @Result(property = "time" , column ="time"),
	           @Result(property = "link", column = "link"),
	           @Result(property = "newsType", column = "type"),
	           @Result(property = "hot", column = "hot"),
	           @Result(property = "thumbnail", column ="thumbnail"),
	           @Result(property = "stockId",column="stock_id"),
	           @Result(property = "urlCode",column="url_code")})
	public List<NewsItem> selectListByTypeAtLimit(int limit,int type);
	
	@SelectProvider(type=NewsItemProvider.class,method="selectListByTypeSql")
	@Results(value= {
	           @Result(id = true, property = "id", column = "id"),  
	           @Result(property = "title", column ="title"),
	           @Result(property = "time" , column ="time"),
	           @Result(property = "link", column = "link"),
	           @Result(property = "newsType", column = "type"),
	           @Result(property = "hot", column = "hot"),
	           @Result(property = "thumbnail", column ="thumbnail"),
	           @Result(property = "stockId",column="stock_id"),
	           @Result(property = "urlCode",column="url_code")})
	public List<NewsItem> selectListByType(int type);
	
	@SelectProvider(type=NewsItemProvider.class,method="selectByTypeAndUrlCode")
	@Results(value= {
	           @Result(id = true, property = "id", column = "id"),  
	           @Result(property = "title", column ="title"),
	           @Result(property = "time" , column ="time"),
	           @Result(property = "link", column = "link"),
	           @Result(property = "newsType", column = "type"),
	           @Result(property = "hot", column = "hot"),
	           @Result(property = "thumbnail", column ="thumbnail"),
	           @Result(property = "stockId",column="stock_id"),
	           @Result(property = "urlCode",column="url_code")})
	public List<NewsItem> selectByTypeAndUrlCode(int type,int urlCode);
	
}
