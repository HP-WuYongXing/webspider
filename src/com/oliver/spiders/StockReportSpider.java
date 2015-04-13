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

public class StockReportSpider {

	private static String PAGE_URL ="http://stock1.sina.cn/dpool/stock_new/v2/report_list.php?vt=4&type=stock&code=stockCode";
	private static String ROOT_URL ="http://stock1.sina.cn/";
	private static String LINK_PATTERN_URL="http://stock1.sina.cn/dpool/stock_new/v2/report_list.php";
	private boolean refreshClear = true;
	private static final int URL_CODE=ConstantsForNewsItem.URL_SINA;
	private static final int NEWS_TYPE=ConstantsForNewsItem.NEWS_ITEM_KIND_STOCK_REPORT;
	//private static AbstractApplicationContext context = AppContext.getContext();
	
	public void excutedRefreshReport(Stock stock){
		if(this.refreshClear){
			NewsSpider.clearNewsItems(NEWS_TYPE,URL_CODE);
		}
		List<NewsItem> titleList = null;
		titleList =this.getStockReportList(stock);
		titleList = NewsSpider.getReverseList(titleList);
		if (titleList != null && titleList.size() != 0) {
			for(NewsItem title:titleList){
				title.setStockId(stock.getId());
			}
			NewsSpider.saveNewsTitle(titleList,NEWS_TYPE);
		}
		int cnt = 1;
		for (NewsItem item : titleList) {
			System.out.println("the " + (cnt++) + "th item...");
			NewsContent content = this.getStockReportContent(item);
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
		List<NewsItem>noContentList = NewsSpider.checkHasNewsContent(NEWS_TYPE,URL_CODE);
		if(noContentList.size()!=0){
			reloadNewsItemContent(noContentList);
		}
	}
	
	private void reloadNewsItemContent(List<NewsItem> noContentItemList) {
		int cnt=0;
		while(cnt<ConstantsForCommon.RELOAD_TIME){
			for(NewsItem i:noContentItemList){
				System.out.println(cnt+" times:reload News content with titleId:"+i.getId());
				NewsContent content = getStockReportContent(i);
				if (content == null)
					continue;
				List<Paragraph> parList = content.getParList();
				List<Picture> picList = content.getPicList();
				NewsSpider.saveNewsContent(i, content, parList,
						picList);
			}
			noContentItemList = NewsSpider.checkHasNewsContent(NEWS_TYPE,URL_CODE);
			
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
	
	public List<NewsItem> getStockReportList(Stock stock){
		List<NewsItem> itemList = new ArrayList<NewsItem>();
		String url = PAGE_URL.replace("stockCode", "sh"+stock.getCode());
		Document doc = DataUtils.doGet(url);
		Element el_msg_div = doc.getElementsByClass("msg").get(0);
		Elements el_a_list = el_msg_div.getElementsByTag("a");
		int size = el_a_list.size();
		System.out.println("el_a_list size: "+size);
		for(int i=0;i<size;i++){
			Element el_a = el_a_list.get(i);
			String linkStr = el_a.attr("href");
			if(!isReportLink(linkStr)){
				continue;
			}
			NewsItem item =  new NewsItem();
			item.setUrlCode(URL_CODE);
			System.out.println("link: "+linkStr);
			item.setLink(ROOT_URL+linkStr.substring(1));
			String titleStr = el_a.text();
			System.out.println("titleStr: "+titleStr);
			item.setTitle(titleStr);
			item.setNewsType(ConstantsForNewsItem.NEWS_ITEM_KIND_STOCK_REPORT);
			itemList.add(item);
		}
		return itemList;
	}
	
	private boolean isReportLink(String url){
		Pattern p = Pattern.compile("^/dpool/stock_new/v2/report_detail.php?.*");
		Matcher matcher = p.matcher(url);
		return matcher.matches();
	}
	
	public NewsContent getStockReportContent(NewsItem item){
		NewsContent content = new NewsContent();
		Document doc = DataUtils.doGet(item.getLink()+"&page=all");
		Element el_msg_div = doc.getElementsByClass("msg").get(0);
		String html =el_msg_div.toString();
		html = html.replace("&nbsp;","").replace(".", "");
		el_msg_div = Jsoup.parse(html).getElementsByClass("msg").get(0);
		Elements el_a_list =el_msg_div.getElementsByTag("a");
		int size = el_a_list.size();
	    for(int i=0;i<size;i++){
	    	Element el_a = el_a_list.get(i);
	    	el_a.remove();
	    }
	    String contentStr = el_msg_div.text();
	    String time = getTimeFromString(contentStr);
	    System.out.println("time: "+time);
	    content.setTime(time);
	    contentStr = "<p>"+contentStr+"</p>";
	    System.out.println("content: "+contentStr);
	    Document doc1 = Jsoup.parse(contentStr);
	    Elements el_p_list = doc1.getElementsByTag("p");
	    List<Paragraph> parList = new ArrayList<Paragraph>();
	    List<Picture> picList = new ArrayList<Picture>();
	    int maxLength = NewsSpider.handlePTagList(parList, picList, el_p_list);
	    content.setMaxLength(maxLength);
	    System.out.println("max length:"+maxLength);
	    content.setPicList(picList);
	    content.setParList(parList);
	    content.setTitleId(item.getId());
	    return content;
	}

	private String getTimeFromString(String contentStr) {
		Pattern p = Pattern.compile("\\[.*\\]");
		Matcher matcher = p.matcher(contentStr);
		String str = null;
		while(matcher.find()){
			str = matcher.group();
			break;
		}
		return str.substring(1,str.length()-1);
	}
}
