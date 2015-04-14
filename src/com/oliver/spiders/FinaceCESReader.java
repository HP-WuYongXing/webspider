package com.oliver.spiders;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.support.AbstractApplicationContext;

import com.oliver.models.NewsContent;
import com.oliver.models.NewsItem;
import com.oliver.models.Paragraph;
import com.oliver.models.Picture;
import com.oliver.service.impl.NewsContentService;
import com.oliver.service.impl.ParagraphService;
import com.oliver.service.impl.PictureService;
import com.oliver.context.AppContext;
import com.oliver.context.BeanLocator;
import com.oliver.dao.impl.NewsContentDao;
import com.oliver.dao.impl.NewsItemDao;
import com.oliver.dao.impl.ParagraphDao;
import com.oliver.dao.impl.PictureDao;

public class FinaceCESReader {
	//private static AbstractApplicationContext context= AppContext.getContext();
	
	public void excutedReadTask(){
		FinanceCESpider spider = new FinanceCESpider();
		List<NewsItem> titleList =null;
		try {
			titleList = getNewsItemList();
			System.out.println("title list size: "+titleList.size());
			int cnt=0;
			for(NewsItem item:titleList){
				int titleId = item.getId();
				NewsContent content = getNewsContent(titleId);
				if (content != null) {
					showNewsContent(content, cnt++);
				}
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	private List<NewsItem> getNewsItemList(){
		NewsItemDao dao = new NewsItemDao();
		return dao.selectAll();
	}

	private void showNewsContent(NewsContent content,int cnt) throws IOException {
		int maxLength = content.getMaxLength();
		System.out.println("maxLength:"+maxLength);
		File Output=  new File("E:/spider/result/output"+cnt+".txt");
		BufferedWriter bw = new BufferedWriter(new FileWriter(Output));
		List<Paragraph> parList = content.getParList();
		List<Picture> picList = content.getPicList();
	   if (parList != null && parList.size() != 0) {
		   System.out.println("parList size: "+parList.size());
	   }
	   if (picList != null && picList.size() != 0) {
		   System.out.println("picList size: "+picList.size());
	   }
		for(int i=0;i<maxLength;i++){
			Paragraph par = null;
			Picture pic = null;
			if (parList != null && parList.size() != 0) {
				for (int j = 0; j < parList.size(); j++) {
					//System.out.println("parList size: "+parList.size());
					Paragraph p = parList.get(j);
					if (i == p.getOrderNumber()) {
						par = p;
						break;
					}
				}
			}
			
			if (picList != null && picList.size() != 0) {
				//System.out.println("piclist size: "+picList.size());
				for (int j = 0; j < picList.size(); j++) {
					Picture p = picList.get(j);
					if (i == p.getOrderNumber()) {
						pic = p;
						break;
					}
				}
			}
			
			if(par!=null){
				System.out.println(par.getContent());
				bw.append(par.getContent());
				bw.newLine();
				bw.flush();
				continue;
			}
			
			if(pic!=null){
				System.out.println(pic.getImagePath());
				bw.append(pic.getImagePath());
				bw.newLine();
				bw.flush();
				continue;
			}
		}
		bw.close();
	}

	private NewsContent getNewsContent(int titleId) {
		NewsContentService contentService = (NewsContentService)BeanLocator.getBean("newsContentService");
		NewsContent content  = contentService.getByTitileId(titleId);
		if(content==null)return null;
		int contentId = content.getId();
		ParagraphService parService = (ParagraphService)BeanLocator.getBean("paragraphService");
		List<Paragraph> parList = parService.getListByContentId(contentId);
		
		PictureService picService =(PictureService)BeanLocator.getBean("pictureService");
		List<Picture> picList = picService.getListByContentId(contentId);
		
		content.setParList(parList);
		content.setPicList(picList);
		return content;
	}
	
	
	public JSONArray  getNewsItemInJson(){
		List<NewsItem> titleList = getNewsItemList();
		List<JSONObject>jsonList = new ArrayList<JSONObject>();
		try {
			for (NewsItem item : titleList) {
				JSONObject jo = new JSONObject();
				jo.put("id", item.getId());
				jo.put("link",item.getLink());
				jo.put("type",item.getNewsType());
				jo.put("time",item.getTime());
				jo.put("title", item.getTitle());
				jsonList.add(jo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new JSONArray(jsonList);
	}
	
	public JSONObject getNewsItemContentInJson(int titleId){
		NewsContent content = getNewsContent(titleId);
		JSONObject jo = new JSONObject();
		try {
			jo.put("id", content.getId());
			jo.put("maxLength", content.getMaxLength());
			jo.put("source", content.getSource());
			jo.put("subTitle", content.getSubTitle());
			jo.put("time", content.getTime());
			jo.put("titleId", content.getTitleId());
			List<JSONObject> JSONParList = new ArrayList<JSONObject>();
			List<Paragraph> paragraphList = content.getParList();
			for(Paragraph p:paragraphList){
				JSONObject o = new JSONObject();
				o.put("content", p.getContent());
				o.put("contentId", p.getContentId());
				o.put("id", p.getId());
				o.put("orderNumber", p.getOrderNumber());
				JSONParList.add(o);
			}
			jo.put("paragraphList", JSONParList);
			
			List<JSONObject> JSONPicList = new ArrayList<JSONObject>();
			List<Picture> pictureList = content.getPicList();
			for(Picture pic:pictureList){
				JSONObject o = new JSONObject();
				o.put("id", pic.getId());
				o.put("contentId", pic.getContentId());
				o.put("orderNumber", pic.getOrderNumber());
				JSONArray arr = new JSONArray(getByteArray(pic.getImagePath()));
				o.put("image", arr);
				JSONPicList.add(o);
			}
			jo.put("pictureList", JSONPicList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jo;
	}
	
    private byte[] getByteArray(String imagePath){
     	try {
    	String format = imagePath.substring(imagePath.length()-3);
    	BufferedImage image = ImageIO.read(new File(imagePath));
    	ByteArrayOutputStream out = new ByteArrayOutputStream();
	    ImageIO.write(image, format, out);
	    return out.toByteArray();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
    }
	
}
