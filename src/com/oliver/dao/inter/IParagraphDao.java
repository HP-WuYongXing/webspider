package com.oliver.dao.inter;

import java.util.List;

import com.oliver.models.Paragraph;

public interface IParagraphDao {
	
	public void insertParagraph(Paragraph paragraph);
	public List<Paragraph> selectListByContentId(int contentId);
}
