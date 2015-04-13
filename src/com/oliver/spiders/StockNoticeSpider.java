package com.oliver.spiders;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.context.support.AbstractApplicationContext;

import com.oliver.models.NewsContent;
import com.oliver.models.NewsItem;
import com.oliver.models.Paragraph;
import com.oliver.models.Picture;
import com.oliver.models.Stock;
import com.oliver.service.impl.NewsItemService;
import com.oliver.constants.ConstantsForCommon;
import com.oliver.constants.ConstantsForNewsItem;
import com.oliver.context.AppContext;
import com.oliver.context.BeanLocator;
import com.oliver.http.DataUtils;

public class StockNoticeSpider {
	
	public static final String PAGE_URL="http://stock1.sina.cn/dpool/stock_new/v2/bulletin_info.php?vt=4&code=stockCode";
	public static final String ROOT_URL="http://stock1.sina.cn/dpool/stock_new/v2/";
	public static final String LINK_URL_PATTERN ="http://stock1.sina.cn/dpool/stock_new/v2/bulletin_detail.php";
	private boolean refreshClear=true;
	private static final int URL_CODE=ConstantsForNewsItem.URL_SINA;
	//private static AbstractApplicationContext context = AppContext.getContext();
	
	public void refreshStockNotice(Stock stock){
		if(this.refreshClear){
			NewsSpider.clearNewsItems(ConstantsForNewsItem.NEWS_ITEM_KIND_STOCK_NOTICE,URL_CODE);
		}
		List<NewsItem> titleList = null;
		titleList =this.getStockNoticeList(stock);
		titleList = NewsSpider.getReverseList(titleList);
		if (titleList != null && titleList.size() != 0) {
			for(NewsItem title:titleList){
				title.setStockId(stock.getId());
			}
			NewsSpider.saveNewsTitle(titleList,ConstantsForNewsItem.NEWS_ITEM_KIND_STOCK_NOTICE);
		}
		int cnt = 1;
		for (NewsItem item : titleList) {
			System.out.println("the " + (cnt++) + "th item...");
			NewsContent content = this.getStockNoticeContent(item);
			if (content == null)
				continue;
			List<Paragraph> parList = content.getParList();
			List<Picture> picList = content.getPicList();
			content.setSubTitle(item.getTitle());
			content.setTime(item.getTime());
			NewsSpider.saveNewsContent(item, content, parList,
					picList);
		}
		System.out.println("complited...");
		List<NewsItem>noContentList = NewsSpider.checkHasNewsContent(ConstantsForNewsItem.NEWS_ITEM_KIND_STOCK_NOTICE,URL_CODE);
		if(noContentList.size()!=0){
			reloadNewsItemContent(noContentList, ConstantsForNewsItem.NEWS_ITEM_KIND_STOCK_NOTICE);
		}
	}
	
	
	private void reloadNewsItemContent(List<NewsItem> noContentItemList,int newsType) {
		int cnt=0;
		while(cnt<ConstantsForCommon.RELOAD_TIME){
			for(NewsItem i:noContentItemList){
				System.out.println(cnt+" times:reload News content with titleId:"+i.getId());
				NewsContent content = getStockNoticeContent(i);
				if (content == null)
					continue;
				List<Paragraph> parList = content.getParList();
				List<Picture> picList = content.getPicList();
				NewsSpider.saveNewsContent(i, content, parList,
						picList);
			}
			noContentItemList = NewsSpider.checkHasNewsContent(newsType,URL_CODE);
			
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
	
	
	public List<NewsItem> getStockNoticeList(Stock stock){
		List<NewsItem> itemList = new ArrayList<NewsItem>();
		String url = PAGE_URL.replace("stockCode", "sh"+stock.getCode());
		System.out.println("url: "+url);
		Document doc = DataUtils.doGet(url);
		Element el_list_div = doc.getElementsByClass("msg").get(0);
		List<String>timeList = getTimeList(el_list_div);
		Elements el_list_a_list = el_list_div.getElementsByTag("a");
		int size = el_list_a_list.size();
		int cnt=0;
		for(int i=0;i<size;i++){
			NewsItem item = new NewsItem();
			item.setUrlCode(URL_CODE);
			Element el_list_a = el_list_a_list.get(i);
			String linkStr = el_list_a.attr("href");
			if(!isNoticeLink(linkStr))continue;
			item.setLink(ROOT_URL+linkStr);
			String titleStr = el_list_a.text();
			item.setTitle(titleStr);
			item.setNewsType(ConstantsForNewsItem.NEWS_ITEM_KIND_STOCK_NOTICE);
			item.setTime(timeList.get(cnt));
			System.out.println("time: "+timeList.get(cnt));
			cnt++;
			itemList.add(item);
		}
		return itemList;
	}
	
	private List<String> getTimeList(Element el_list_div) {
		String html = el_list_div.toString();
		Pattern p = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
		Matcher matcher = p.matcher(html);
		List<String> timeList = new ArrayList<String>();
		while(matcher.find()){
			String s = matcher.group();
		//	System.out.println("time finded: "+s);
			timeList.add(s);
		}
		return timeList;
	}

	private boolean isNoticeLink(String link){
		Pattern p = Pattern.compile("^bulletin_detail.php?.*");//bulletin_detail.php?id=1697103&code=sh601299&title=中国北车&vt=4
		Matcher matcher =p.matcher(link);
		return matcher.matches();
	}
	
	public NewsContent getStockNoticeContent(NewsItem newsTitle){
		NewsContent content = new NewsContent();
		Document doc = DataUtils.doGet(newsTitle.getLink()+"&page=all");
		if(doc==null)return null;
		Element el_content_div = doc.getElementsByClass("msg").get(0);
		String contentHtml = el_content_div.toString().replace("<br/>", "").replace("&nbsp;", "");
		el_content_div = Jsoup.parse(contentHtml).getElementsByClass("msg").get(0);
		Elements el_a_list =el_content_div.getElementsByTag("a");
		int size = el_a_list.size();
		int totalPage=1;
		for(int i=0;i<size;i++){
			Element el_a = el_a_list.get(i);
			String titleStr = el_a.text();
			if("末页".equals(titleStr)){
				String linkStr = el_a.attr("href");
				totalPage=getTotalPage(linkStr);
			}
			el_a.remove();
		}
		System.out.println("total page: "+totalPage);
		String contentStr = el_content_div.text();
	    if(totalPage>1){
	    	contentStr = getCompletedContent(contentStr,newsTitle,totalPage);
	    }
	    contentStr = contentStr.replace(".", "").trim();
	    System.out.println("content str: "+contentStr);
	    String html  ="<p>"+contentStr+"</p>";
	    Document pdoc = Jsoup.parse(html);
	    Elements el_p_list = pdoc.getElementsByTag("p");
	    List<Picture> picList=new ArrayList<Picture>();
	    List<Paragraph>parList = new ArrayList<Paragraph>();
	    int maxLength = NewsSpider.handlePTagList(parList, picList, el_p_list);
	    content.setMaxLength(maxLength);
	    content.setParList(parList);
	    content.setPicList(picList);
	    return content;
	}

	private String getCompletedContent(String contentStr,NewsItem title,int totalPage) {
		String url = title.getLink();
		StringBuilder sb = new StringBuilder(contentStr);
		for(int i=1;i<totalPage;i++){
			url = url.replace("page="+i,"page="+(i+1));
			System.out.println("page :"+(i+1)+" url: "+url);
			Document doc = DataUtils.doGet(url);
		    Element el_msg_div = doc.getElementsByClass("msg").get(0);
		    String contentHtml = el_msg_div.toString().replace("<br/>", "").replace("&nbsp;", "");
		    el_msg_div = Jsoup.parse(contentHtml).getElementsByClass("msg").get(0);
		    Elements el_msg_a_list = el_msg_div.getElementsByTag("a");
		    int size = el_msg_a_list.size();
		    for(int j=0;j<size;j++){
		    	Element el_msg_a = el_msg_a_list.get(j);
		    	el_msg_a.remove();
		    }
		   // String content  = el_msg_div.text().replace(".","").trim();
		  //  System.out.println("page content: "+content);
		    sb.append(el_msg_div.text());
		}
	  return sb.toString();
	}

	private int getTotalPage(String linkStr) {
		int idx = linkStr.indexOf("page=");
		String page = linkStr.substring(idx+5);
		return Integer.valueOf(page);
	}
	
	
}
