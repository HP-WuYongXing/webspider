package com.oliver.dao.inter;

import java.util.List;

import com.oliver.models.Paragraph;

public interface IParagraphDao {

	public void addParagraph(Paragraph paragraph);
	public void addParagraphList(List<Paragraph> parList);
	public boolean DeleteParagraph(int id);
	public List<Paragraph> queryListByContentId(int contentId);
    public boolean updateParagraph(Paragraph paragraph);
}
