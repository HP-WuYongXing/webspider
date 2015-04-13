package com.oliver.db.sql;
import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;  
import static org.apache.ibatis.jdbc.SqlBuilder.FROM;  
import static org.apache.ibatis.jdbc.SqlBuilder.SELECT;  
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;  
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE; 
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;  
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;
public class ManagerProvider {

	private static final String TABLE_NAME="manager";
	
	public String insertManagerSql(){
		BEGIN();
		INSERT_INTO(TABLE_NAME);
		VALUES("name","#{name}");
		VALUES("job_name","#{jobName}");
		VALUES("term_day","#{termDay}");
		VALUES("dismiss_day","#{dismissDay}");
		VALUES("education","#{education}");
		VALUES("job_type","#{jobType}");
		VALUES("company_id","#{companyId}");
		return SQL();
	}
}
