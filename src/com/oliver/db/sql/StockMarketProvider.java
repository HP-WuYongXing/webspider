package com.oliver.db.sql;
import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;  
import static org.apache.ibatis.jdbc.SqlBuilder.FROM;  
import static org.apache.ibatis.jdbc.SqlBuilder.SELECT;  
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;  
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE; 
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;  
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;
public class StockMarketProvider {

	private static final String TABLE_NAME="market";
	
	public String insertStockMarketSql(){
		BEGIN();
		INSERT_INTO(TABLE_NAME);
		VALUES("jkp","#{jkp}");
		VALUES("zsp","#{zsp}");
		VALUES("zgj","#{zgj}");
		VALUES("zdj","#{zdj}");
		VALUES("ztj","#{ztj}");
		VALUES("dtj","#{dtj}");
		VALUES("hsl","#{hsl}");
		VALUES("zf","#{zf}");
		VALUES("syl","#{syl}");
		VALUES("sjl","#{sjl}");
		VALUES("cjl","#{cjl}");
		VALUES("cje","#{cje}");
		VALUES("zsz","#{zsz}");
		VALUES("ltsz","#{ltsz}");
		VALUES("mr1","#{mr1}");
		VALUES("mr2","#{mr2}");
		VALUES("mr3","#{mr3}");
		VALUES("mr4","#{mr4}");
		VALUES("mr5","#{mr5}");
		VALUES("mc1","#{mc1}");
		VALUES("mc2","#{mc2}");
		VALUES("mc3","#{mc3}");
		VALUES("mc4","#{mc4}");
		VALUES("mc5","#{mc5}");
		
		VALUES("mr1_num","#{mr1Num}");
		VALUES("mr2_num","#{mr2Num}");
		VALUES("mr3_num","#{mr3Num}");
		VALUES("mr4_num","#{mr4Num}");
		VALUES("mr5_num","#{mr5Num}");
		
		VALUES("mc1_num","#{mc1Num}");
		VALUES("mc2_num","#{mc2Num}");
		VALUES("mc3_num","#{mc3Num}");
		VALUES("mc4_num","#{mc4Num}");
		VALUES("mc5_num","#{mc5Num}");
		return SQL();
	}
}
