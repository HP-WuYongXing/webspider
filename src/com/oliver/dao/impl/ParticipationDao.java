package com.oliver.dao.impl;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import com.oliver.models.Participation;
import com.oliver.dao.inter.IParticipationDao;
import com.oliver.db.DBHelper;
import com.oliver.mapper.inter.IParticipationMapper;

@Repository("participationDao")
public class ParticipationDao implements IParticipationDao {

	@Resource(name="participationMapper")
	private IParticipationMapper mapper;
	
	@Override
	public void insertParticipation(Participation p) {
		mapper.insertParticipation(p);
	}

}
