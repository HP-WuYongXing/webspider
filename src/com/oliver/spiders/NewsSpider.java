package com.oliver.spiders;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.context.support.AbstractApplicationContext;

import com.oliver.models.NewsContent;
import com.oliver.models.NewsItem;
import com.oliver.models.Paragraph;
import com.oliver.models.Picture;
import com.oliver.service.impl.NewsContentService;
import com.oliver.service.impl.NewsItemService;
import com.oliver.service.impl.ParagraphService;
import com.oliver.service.impl.PictureService;
import com.oliver.constants.ConstantsForCommon;
import com.oliver.constants.ConstantsForNewsItem;
import com.oliver.context.AppContext;
import com.oliver.context.BeanLocator;
import com.oliver.http.DataUtils;


public abstract class NewsSpider {
	public abstract void executedRefreshFocus();
	public abstract void executedRefreshStocks();
	public abstract List<NewsItem> getFocusNewsList();
	public abstract List<NewsItem> getStocksNewsList();
	public abstract NewsContent getFocusNewsContent(NewsItem title);
	public abstract NewsContent getStocksNewsContent(NewsItem title);
	public boolean refreshClear=true;
	
	public static void clearNewsItems(int newsType,int urlCode) {
		NewsItemService itemService = (NewsItemService)BeanLocator.getBean("newsItemService");
		itemService.deleteByTypeAndUrlCode(newsType, urlCode);
	}
	
	public static void saveNewsItemList(List<NewsItem> list){
		NewsItemService newsItemService = (NewsItemService)BeanLocator.getBean("newsItemService");
	    newsItemService.addNewsItemList(list);
	}
	
	public static  void saveParagraphs(List<Paragraph> list,int contentId){
		ParagraphService service = (ParagraphService)BeanLocator.getBean("paragraphService");
		for (Paragraph p:list) {
			p.setContentId(contentId);
		}
		service.addParagraphList(list);
	}
	
	public static void savePictures(List<Picture> list,int contentId){
		PictureService service = (PictureService)BeanLocator.getBean("pictureService");
		for (Picture p:list) {
			p.setContentId(contentId);
		}
		service.addPictureList(list);
	}
	
	public static List<NewsItem> checkHasNewsContent(int newsType,int urlCode){
		NewsItemService titleService = (NewsItemService) BeanLocator.getBean("newsItemService");
		NewsContentService contentService =(NewsContentService)BeanLocator.getBean("newsContentService");
		List<NewsItem> items = titleService.getListByTypeAndUrlCode(newsType, urlCode);
		List<NewsItem> list=new ArrayList<NewsItem>();
		for(NewsItem item:items){
			int titleId = item.getId();
			NewsContent content = contentService.getByTitileId(titleId);
			if(content==null){
				System.out.println("no content on NewsTitle: "+titleId);
				//titleService.deleteNewsItem(titleId);
				list.add(item);
			}
		}
		return list;
	}
	
	public static int addParagraph(Element el_text_p,List<Paragraph> parList,int order){
		Paragraph p = new Paragraph();
		p.setContent(el_text_p.text());
		p.setOrderNumber(order++);
		parList.add(p);
		return order;
	}

	public static  int savePictures(Elements el_img_list,List<Picture> picList,int order,String url){
		int len = el_img_list.size();
		for(int i=0;i<len;i++){
			Picture p = new Picture();
			Element el_img = el_img_list.get(i);
			String link = el_img.attr("src");
			if(link.startsWith("./")){
				link=url+link.substring(1);
			}
			String path=null;
			try {
				path = DataUtils.savePicture(link);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			p.setImagePath(path);
			p.setOrderNumber(order++);
			picList.add(p);
		}
		return order;
	}
	
	protected void excutedRefreshFocus(NewsSpider spider,int urlCode){
		if(this.refreshClear){
			clearNewsItems(ConstantsForNewsItem.NEWS_ITEM_KIND_FOCUS,urlCode);
		}
		List<NewsItem> titleList = null;
		titleList = spider.getFocusNewsList();
		titleList = getReverseList(titleList);
		if (titleList != null && titleList.size() != 0) {
			saveNewsTitle(titleList,ConstantsForNewsItem.NEWS_ITEM_KIND_FOCUS);
		}
		int cnt = 1;
		for (NewsItem item : titleList) {
			System.out.println("the " + (cnt++) + "th item...titleId:"+item.getId());
			NewsContent content = spider.getFocusNewsContent(item);
			if (content == null)
				continue;
			List<Paragraph> parList = content.getParList();
			List<Picture> picList = content.getPicList();
			saveNewsContent(item, content, parList,
					picList);
		}
		System.out.println("complited...");
		List<NewsItem> noContentItemList = checkHasNewsContent(ConstantsForNewsItem.NEWS_ITEM_KIND_FOCUS,urlCode);
		if(noContentItemList.size()!=0){
			reloadNewsItemContent(spider,noContentItemList);
		}
	}
	
	private static void reloadNewsItemContent(NewsSpider spider,List<NewsItem> noContentItemList) {
		int cnt=0;
		int newsType=noContentItemList.get(0).getNewsType();
		int urlCode=noContentItemList.get(0).getUrlCode();;
		while(cnt<ConstantsForCommon.RELOAD_TIME){
			for(NewsItem i:noContentItemList){
				System.out.println(cnt+" times:reload News content with titleId:"+i.getId());
				NewsContent content = spider.getFocusNewsContent(i);
				if (content == null)
					continue;
				List<Paragraph> parList = content.getParList();
				List<Picture> picList = content.getPicList();
				saveNewsContent(i, content, parList,
						picList);
			}
			noContentItemList = checkHasNewsContent(newsType,urlCode);
			if(noContentItemList.size()==0)break;
			cnt++;
		}
		
		if(noContentItemList.size()!=0){
			NewsItemService itemService = (NewsItemService)BeanLocator.getBean("newsItemService");
			for(NewsItem i:noContentItemList){
				System.out.println("delete news item with no content :"+i.getId());
				itemService.deleteNewsItem(i.getId());
			}
		}
	}
	
	protected void excutedRefreshStocksNews(NewsSpider spider,int urlCode){
		if(this.refreshClear){
			clearNewsItems(ConstantsForNewsItem.NEWS_ITEM_KIND_STOCKS,urlCode);
		}
		List<NewsItem> titleList = null;
		titleList = spider.getStocksNewsList();
		titleList = getReverseList(titleList);
		
		if (titleList != null && titleList.size() != 0) {
			saveNewsTitle(titleList,ConstantsForNewsItem.NEWS_ITEM_KIND_STOCKS);
		}
		
		int cnt = 1;
		for (NewsItem item : titleList) {
			System.out.println("the " + (cnt++) + "th item...");
			NewsContent content = spider.getStocksNewsContent(item);
			if (content == null)
				continue;
			List<Paragraph> parList = content.getParList();
			List<Picture> picList = content.getPicList();
			saveNewsContent(item, content, parList,
					picList);
		}
		System.out.println("complited...");
		List<NewsItem> noContentItemList = checkHasNewsContent(ConstantsForNewsItem.NEWS_ITEM_KIND_STOCKS,urlCode);
		if(noContentItemList.size()!=0){
			reloadNewsItemContent(spider,noContentItemList);
		}
	}
	

	public static List<NewsItem> getReverseList(List<NewsItem> list){
		List<NewsItem> resList = new ArrayList<NewsItem>();
		int len = list.size();
		for(int i=len-1;i>=0;i--){
			resList.add(list.get(i));
		}
		return resList;
	}
	
	
	public static void saveNewsTitle(List<NewsItem> titleList,int newsType) {
		for (NewsItem item : titleList) {
			item.setNewsType(newsType);//设置新闻类型值
		}
		saveNewsItemList(titleList);//保存到数据库获取id
		System.out.println("newsItem number: " + titleList.size());
	}
	
	public static void saveNewsContent(NewsItem item,
			NewsContent content, List<Paragraph> parList, List<Picture> picList) {
		if (item.getTime() == null || item.getTime() == "") {
			addTimeInfoToNewsItem(item.getId(), content.getTime());
		}
		content.setTitleId(item.getId());
	    NewsContentService service = (NewsContentService)BeanLocator.getBean("newsContentService");
	    System.out.println("add content: "+content);
		service.addNewsContent(content);

		if (parList != null && parList.size() != 0) {
			saveParagraphs(parList, content.getId());
		}
		if (picList != null && picList.size() != 0) {
			savePictures(picList, content.getId());
		}
	}
	
	
	public static void addTimeInfoToNewsItem(int id, String time) {
		NewsItemService itemService = (NewsItemService)BeanLocator.getBean("newsItemService");
		NewsItem item= itemService.getById(id);
		item.setTime(time);
		itemService.updateNewsItem(item);
	}
	
//	private void addSketch(NewsItem item, Paragraph paragraph) {
//		String str = paragraph.getContent();
//		int num = ConstantsForNewsItem.NEWS_ITEM_SKETCH_LENGTH;
//		if(str.length()>num){
//			str = str.substring(0,num);
//		}
//		System.out.println("sketch: "+str);
//		item.setSketch(str);
//		NewsItemDaoImpl newsItemDao = new NewsItemDaoImpl();
//		newsItemDao.addSketch(item);
//	}
	
	public static int handlePTagList(List<Paragraph> parList, List<Picture> picList,
			Elements el_text_p_list) {
		   String str;
		   int size = el_text_p_list.size();
		   int order=0;
		   for(int i=0;i<size;i++){
			   Element el_text_p = el_text_p_list.get(i);
			   str = el_text_p.text();
			   if(str.length()>2){
				  order = addParagraph(el_text_p, parList, order);
			   }
			   Elements el_text_p_img_list = el_text_p.getElementsByTag("img");
			   if(el_text_p_img_list.size()!=0){
				  order = savePictures(el_text_p_img_list, picList, order,null);
			   }
		   }
		   return order;
	}
	
	public static String getLinkFromUrl(String url, String link) {
		url = url.replace("http://", "").replace("//", "/");
		String pre="http://";
		if(link.startsWith("/")){
		     Pattern p = Pattern.compile("^[\\w|.]+/?");
		     Matcher matcher = p.matcher(url);
		     String rootUrl ="";
		     while(matcher.find()){
		    	 rootUrl = matcher.group();
		     }
		     return pre+rootUrl+link;
		}else if(link.startsWith("../")){
			int right = url.lastIndexOf("/");
			String tpurl= url.substring(0,right+1);
			String tplink = link;
			while(tplink.startsWith("../")){
				tplink=tplink.substring(3);
				int r=tpurl.substring(0,tpurl.length()-1).lastIndexOf("/");
				tpurl=tpurl.substring(0,r+1);
			}
		    return pre+tpurl+tplink;
		}
		int right = url.lastIndexOf("/");
		return pre+url.substring(0,right+1)+link.substring(2);
	}
}
