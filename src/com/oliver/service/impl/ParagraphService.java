package com.oliver.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.oliver.dao.impl.ParagraphDao;
import com.oliver.models.Paragraph;
import com.oliver.service.IParagraphService;

@Service("paragraphService")
public class ParagraphService implements IParagraphService {

	@Resource(name="paragraphDao")
	private ParagraphDao pdao;
	
	@Override
	public void addParagraph(Paragraph paragraph) {
		pdao.insertParagraph(paragraph);
	}

	@Override
	public List<Paragraph> getListByContentId(int contentId) {
		return pdao.selectListByContentId(contentId);
	}

	@Override
	public void addParagraphList(List<Paragraph> parList) {
		for(Paragraph p:parList){
			addParagraph(p);
		}
	}

	

}
