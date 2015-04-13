package com.oliver.db.sql;
import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;  
import static org.apache.ibatis.jdbc.SqlBuilder.FROM;  
import static org.apache.ibatis.jdbc.SqlBuilder.SELECT;  
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;  
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE; 
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;  
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;

public class ParticipationProvider {

	private static final String TABLE_NAME="participation";
	
	public String insertParticipationSql(){
		BEGIN();
		INSERT_INTO(TABLE_NAME);
		VALUES("company_name","#{companyName}");
		VALUES("money","#{money}");
		VALUES("proportion","#{proportion}");
		VALUES("business","#{business}");
		VALUES("company_id","#{companyId}");
		return SQL();
	}
}
