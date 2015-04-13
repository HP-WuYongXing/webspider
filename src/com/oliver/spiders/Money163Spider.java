package com.oliver.spiders;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.oliver.models.NewsContent;
import com.oliver.models.NewsItem;
import com.oliver.models.Paragraph;
import com.oliver.models.Picture;
import com.oliver.constants.ConstantsForNewsItem;
import com.oliver.http.DataUtils;

public class Money163Spider extends NewsSpider{
	private static final String MONEY_163_URL ="http://money.163.com/";
	private static final String STOCKS_URL="http://money.163.com/stock/";
	
	@Override
	public void executedRefreshFocus(){
		this.excutedRefreshFocus(this, MONEY_163_URL);
	}
	
	public void executedRefreshStocks(){
		this.excutedRefreshStocksNews(this, STOCKS_URL);
	}

	@Override
	public List<NewsItem> getFocusNewsList() {
		Document doc = DataUtils.doGet(MONEY_163_URL);
		List<NewsItem> itemList = new ArrayList<NewsItem>();
		Element el_focus_news_div = doc.getElementsByClass("fn_focus_news").get(0);/*第一部分 焦点新闻*/
		Elements el_three_cat_list = doc.getElementsByClass("fn_three_cat"); 
		Elements el_focus_news_list = el_focus_news_div.getElementsByTag("a");//.getElementsByTag();
		int focusLength = el_focus_news_list.size();
		for(int i=0;i<focusLength;i++){
			NewsItem item = new NewsItem();
			Element el_focus_item_a = el_focus_news_list.get(i);
			String str = el_focus_item_a.text();
			item.setTitle(str);
			str = el_focus_item_a.attr("href");
			if(!filterLink(str))continue;
			item.setLink(str);
			item.setNewsType(ConstantsForNewsItem.NEWS_ITEM_KIND_FOCUS);
			itemList.add(item);
		}
		
		int threeCatLength =el_three_cat_list.size();
		for(int i=0;i<threeCatLength;i++){
			Elements el_three_cat_a_list =el_three_cat_list.get(i).getElementsByTag("a");
			int sublen=el_three_cat_a_list.size();
			for(int j=0;j<sublen;j++){
				NewsItem item = new NewsItem();
				Element el_three_cat_a = el_three_cat_a_list.get(j);
				String str = el_three_cat_a.text();
				item.setTitle(str);
				str = el_three_cat_a.attr("href");
				if(!filterLink(str))continue;
				item.setLink(str);
				item.setNewsType(ConstantsForNewsItem.NEWS_ITEM_KIND_FOCUS);
				itemList.add(item);
			}
		}
		
		return itemList;
	}
	
	private boolean filterLink(String url){
		Pattern p = Pattern.compile("^http://money.163.com/[0-9]{2}/[0-9]{4}/[0-9]{2}/.*");
		Matcher matcher = p.matcher(url);
		return matcher.matches();
	}
	
	@Override
	public NewsContent getFocusNewsContent(NewsItem title) {
		NewsContent content = new NewsContent();
		Document doc = DataUtils.doGet(title.getLink());
		String linkUrl = title.getLink();
		if(doc==null)return null;
		Element el_text_panel = doc.getElementsByClass("ep-main-bg").get(0);
		Elements el_time_list = el_text_panel.getElementsByClass("ep-time-soure");
		if(el_time_list==null||el_time_list.size()==0)return null;
		System.out.println("analysing link: "+title.getLink());
		String str = el_time_list.get(0).text();
		str = getTime(str);
		content.setTime(str);
		Element el_title = el_text_panel.getElementById("h1title");
		content.setSubTitle(el_title.text());
		Element el_source = el_text_panel.getElementById("ne_article_source");
		str = el_source.text();
		content.setSource(str);
		Element el_content_body_div = el_text_panel.getElementById("endText");
		Elements el_content_p_list = el_content_body_div.getElementsByTag("p");
		int plen = el_content_p_list.size();
		List<Paragraph> parList = new ArrayList<Paragraph>();
		List<Picture> picList = new ArrayList<Picture>();
		int order=0;
		for(int i=0;i<plen;i++){
			Element el_content_p = el_content_p_list.get(i);
			String str2 = el_content_p.text();
			if(str2.length()<=2)continue;
			order = addParagraph(el_content_p, parList, order);
			Elements el_content_pic_list = el_content_p.getElementsByTag("img");
			if(el_content_pic_list.size()!=0){
				order = savePictures(el_content_pic_list,picList,order,linkUrl);
			}
		}
		content.setParList(parList);
		content.setPicList(picList);
		content.setTitleId(title.getId());
		content.setMaxLength(order);
		return content;
	}
	
	private String getTime(String str){
		String arr[] = str.split("\\s+");
		String res = arr[0]+" "+arr[1].substring(0, 8);
		return res;
	}

	@Override
	public List<NewsItem> getStocksNewsList(){
		List<NewsItem> itemList =  new ArrayList<NewsItem>();
		Document doc = DataUtils.doGet(STOCKS_URL);
		if(doc==null)return null;
		Element el_news_panel = doc.getElementsByClass("grid_news").get(0);
		Elements el_news_a_list = el_news_panel.getElementsByTag("a");
		int size = el_news_a_list.size();
		for(int i=0;i<size;i++){
			NewsItem item = new NewsItem();
			Element el_news_a = el_news_a_list.get(i);
			String str  = el_news_a.text();
			System.out.println("title: "+str);
			item.setTitle(str);
			str = el_news_a.attr("href");
			if(!filterLink(str))continue;
			System.out.println("link: "+str);
			item.setLink(str);
			itemList.add(item);
		}
		return itemList;
	}
	
	@Override
	public NewsContent getStocksNewsContent(NewsItem title){
		return this.getFocusNewsContent(title);
	}
	
	
}
