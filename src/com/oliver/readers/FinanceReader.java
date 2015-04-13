package com.oliver.readers;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.support.AbstractApplicationContext;

import com.mysql.jdbc.Constants;
import com.oliver.constants.ConstantsForCommon;
import com.oliver.constants.ConstantsForNewsItem;
import com.oliver.context.AppContext;
import com.oliver.context.BeanLocator;
import com.oliver.dao.impl.NewsContentDao;
import com.oliver.dao.impl.NewsItemDao;
import com.oliver.dao.impl.ParagraphDao;
import com.oliver.dao.impl.PictureDao;
import com.oliver.models.NewsContent;
import com.oliver.models.NewsItem;
import com.oliver.models.Paragraph;
import com.oliver.models.Picture;
import com.oliver.service.impl.NewsContentService;
import com.oliver.service.impl.NewsItemService;
import com.oliver.service.impl.ParagraphService;
import com.oliver.service.impl.PictureService;
import com.sun.corba.se.impl.orbutil.closure.Constant;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import sun.misc.BASE64Encoder;

public class FinanceReader {
	
	//private static AbstractApplicationContext context = AppContext.getContext();
	
	private List<NewsItem> getNewsItemList(int page,int newsType){
		NewsItemService itemService = (NewsItemService)BeanLocator.getBean("newsItemService");
		return itemService.getLIstByTypeAtOffset(page*ConstantsForCommon.PAGE_LENGTH,
				ConstantsForCommon.PAGE_LENGTH,
				newsType);
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
		NewsContentService contentService =(NewsContentService)BeanLocator.getBean("newsContentService");
		NewsContent content  = contentService.getByTitileId(titleId);
		if(content==null)return null;
		System.out.println("titleId: "+titleId);
		int contentId = content.getId();
		ParagraphService parService = (ParagraphService)BeanLocator.getBean("paragraphService");
		List<Paragraph> parList = parService.getListByContentId(contentId);
		
		PictureService picService =(PictureService)BeanLocator.getBean("pictureService");
		List<Picture> picList = picService.getListByContentId(contentId);
		
		content.setParList(parList);
		content.setPicList(picList);
		return content;
	}
	
	public JSONArray  getNewsItemInJson(int page,int newsType){
		List<NewsItem> titleList=null;
		if(page==0){
			titleList = getNewsItemsWithHeaderNews(newsType);
		}else{
			titleList = getNewsItemList(page,newsType);
		}
		List<JSONObject> jsonList = convertNewsItemListToJsonArray(titleList);
		return new JSONArray(jsonList);
	}
	
	public List<NewsItem> getNewsItemsWithHeaderNews(int newsType){
		List<NewsItem> titleList1 = getNewsItemList(0,newsType);
		List<NewsItem> titleList2 = getHeaderNews(100+newsType);
		titleList1.addAll(titleList2);
		return titleList1;
	}
	
	private List<NewsItem> getHeaderNews(int type){
		NewsItemService itemService = (NewsItemService)BeanLocator.getBean("newsItemService");
		return itemService.getListByTypeAtLimit(5, type);
	}
//	
//	public JSONArray getRandomNewsItemInJson(){
//		List<NewsItem> titleList = this.getNewsItemRandomList();
//		List<JSONObject> jsonList = convertNewsItemListToJsonArray(titleList);
//		return new JSONArray(jsonList);
//	}

	private List<JSONObject> convertNewsItemListToJsonArray(
			List<NewsItem> titleList) {
		List<JSONObject>jsonList = new ArrayList<JSONObject>();
		try {
		
			for (NewsItem item : titleList) {
				JSONObject jo = new JSONObject();
				jo.put("id", item.getId());
				jo.put("link",item.getLink());
				jo.put("type",item.getNewsType());
				jo.put("time",item.getTime());
				jo.put("title", item.getTitle());
				jo.put("hot", item.getHot());
				String imgPath = item.getThumbnail();
				if(imgPath!=null){
					String imgStr = getEncodedString(getByteArray(imgPath));
					jo.put("thumbnail", imgStr);
				}
				jsonList.add(jo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonList;
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
				String picStr =getEncodedString(getByteArray(pic.getImagePath()));
				o.put("image", picStr);
				JSONPicList.add(o);
			}
			jo.put("pictureList", JSONPicList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jo;
	}
	
    private String getEncodedString(byte[] bytes) {
    	BASE64Encoder encoder = new BASE64Encoder();
    	return encoder.encode(bytes);
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
	
	public void addHotPoint(int titleId){
		NewsItemService service = (NewsItemService)BeanLocator.getBean("newsItemService");
		NewsItem newsItem = service.getById(titleId);
		int hot = newsItem.getHot();
		newsItem.setHot(++hot);
		service.updateNewsItem(newsItem);
	}
}
