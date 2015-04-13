package com.oliver.service;

import java.util.List;

import com.oliver.models.Paragraph;

public interface IParagraphService {
	public void addParagraph(Paragraph paragraph);
	public List<Paragraph> getListByContentId(int contentId);
	public void addParagraphList(List<Paragraph> parList);
}
