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

import com.mysql.jdbc.Constants;
import com.oliver.constants.ConstantsForCommon;
import com.oliver.dao.impl.NewsContentDaoImpl;
import com.oliver.dao.impl.NewsItemDaoImpl;
import com.oliver.dao.impl.ParagraphDaoImpl;
import com.oliver.dao.impl.PictureDaoImpl;
import com.oliver.models.NewsContent;
import com.oliver.models.NewsItem;
import com.oliver.models.Paragraph;
import com.oliver.models.Picture;
import com.sun.corba.se.impl.orbutil.closure.Constant;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import sun.misc.BASE64Encoder;

public class FinanceCEReader {
	
	public void excutedReadTask(){
		List<NewsItem> titleList =null;
		try {
			titleList = getNewsItemList(0);
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
	
	private List<NewsItem> getNewsItemList(int page){
		NewsItemDaoImpl dao = new NewsItemDaoImpl();
		return dao.queryNewsItemsAtLimit(page*ConstantsForCommon.PAGE_LENGTH);
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
		NewsContentDaoImpl contentDao = new NewsContentDaoImpl();
		List<NewsContent> contentList  = contentDao.queryByTitileId(titleId);
		if(contentList==null||contentList.size()==0)return null;
		NewsContent content = contentList.get(0);
		System.out.println("titleId: "+titleId);
		int contentId = content.getId();
		ParagraphDaoImpl parDao = new ParagraphDaoImpl();
		List<Paragraph> parList = parDao.queryListByContentId(contentId);
		
		PictureDaoImpl picDao = new PictureDaoImpl();//325
		List<Picture> picList = picDao.queryListByContentId(contentId);
		
		content.setParList(parList);
		content.setPicList(picList);
		return content;
	}
	
	public JSONArray  getNewsItemInJson(int page){
		List<NewsItem> titleList = getNewsItemList(page);
		List<JSONObject>jsonList = new ArrayList<JSONObject>();
		try {
			int cnt=0;
			for (NewsItem item : titleList) {
				cnt++;
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
}
