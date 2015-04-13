package com.oliver.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.oliver.dao.inter.IParticipationDao;
import com.oliver.models.Participation;
import com.oliver.service.IParticipationService;

@Service("participationService")
public class ParticipationService implements IParticipationService {

	@Resource(name="participationDao")
	private IParticipationDao dao;
	
	@Override
	public void addParticipation(Participation p) {
		dao.insertParticipation(p);
	}

}
