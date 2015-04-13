package com.oliver.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import com.oliver.dao.inter.IParagraphDao;
import com.oliver.db.DBHelper;
import com.oliver.mapper.inter.IParagraphMapper;
import com.oliver.models.Paragraph;

@Repository("paragraphDao")
public class ParagraphDao implements IParagraphDao{

	@Resource(name="paragraphMapper")
	private IParagraphMapper paragraphMapper;
	
	@Override
	public void insertParagraph(Paragraph paragraph) {
		paragraphMapper.insertParagraph(paragraph);
	}

	@Override
	public List<Paragraph> selectListByContentId(int contentId) {
		return paragraphMapper.getListByContentId(contentId);
	}

	
	
}
