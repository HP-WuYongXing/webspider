package com.oliver.mapper.inter;

import java.util.List;

import com.oliver.models.Paragraph;

public interface IParagraphOperation {
	public void addParagraph(Paragraph paragraph);
	public List<Paragraph> queryListByContentId(int contentId);
}
