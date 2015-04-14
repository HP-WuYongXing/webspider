package com.oliver.mapper.inter;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectKey;

import com.oliver.db.sql.ParticipationProvider;
import com.oliver.models.Participation;

public interface IParticipationMapper {
	@InsertProvider(type=ParticipationProvider.class,method="insertParticipationSql")
	@SelectKey(keyProperty="id",keyColumn="id", before = false, resultType = int.class, statement = { "SELECT LAST_INSERT_ID() AS ID" })
	public void insertParticipation(Participation p);
}
