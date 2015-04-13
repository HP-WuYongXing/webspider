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

public class FinanceSTCNSpider extends NewsSpider {

   private static final String FOCUS_URL = "http://news.stcn.com/secu/";
   private static final String STOCK_URL = "http://stock.stcn.com/yaowen/";
   private static final int URL_CODE= ConstantsForNewsItem.URL_STCN;
   
	@Override
	public void executedRefreshFocus() {
		this.excutedRefreshFocus(this,URL_CODE);
	}
	
	

	@Override
	public void executedRefreshStocks() {
		this.excutedRefreshStocksNews(this, URL_CODE);
	}



	@Override
	public List<NewsItem> getFocusNewsList() {
	    Document doc = DataUtils.doGet(FOCUS_URL);
	    List<NewsItem>itemList = new ArrayList<NewsItem>();
	    Element el_main_list = doc.getElementById("mainlist");
	    Elements el_li_list = el_main_list.getElementsByTag("li");
	    int size = el_li_list.size();
	    for(int i=0;i<size;i++){
	    	NewsItem item = new NewsItem();
	    	item.setUrlCode(URL_CODE);
	    	Element el_li = el_li_list.get(i);
	    	Element el_li_a = el_li.getElementsByTag("a").get(0);
	    	String str = el_li_a.text();
	    	System.out.println("title: "+str);
	    	item.setTitle(str);
	    	str = el_li_a.attr("href");
	    	System.out.println("link: "+str);
	    	if(!filtLink(str))continue;
	    	item.setLink(str);
	    	Element el_li_time = el_li.getElementsByTag("span").get(1);
	    	str = el_li_time.text();
	    	System.out.println("time: "+str);
	    	item.setTime(str);
	    	item.setNewsType(ConstantsForNewsItem.NEWS_ITEM_KIND_FOCUS);
	    	itemList.add(item);
	    }
		return itemList;
	}
	
	private boolean filtLink(String url){
			Pattern p = Pattern.compile("^http://news.stcn.com/\\d{4}/\\d{4}/.*");
			Matcher matcher = p.matcher(url);
			return matcher.matches();
	}

	@Override
	public NewsContent getFocusNewsContent(NewsItem title) {
		System.out.println("analysing link: "+title.getLink());
		NewsContent content = new NewsContent();
		content.setTitleId(title.getId());
		Document doc = DataUtils.doGet(title.getLink());
		if(doc==null)return null;
		Elements el_text_list =doc.getElementsByClass("txt");
		if(el_text_list.size()==0)return null;
		Element el_text = el_text_list.get(0);
		Elements el_text_hd_list =el_text.getElementsByClass("txt_hd");
		if(el_text_hd_list.size()==0)return null;
		Element el_text_hd = el_text_hd_list.get(0);
		Element el_title = el_text_hd.getElementsByTag("h1").get(0);
		String str = el_title.text();
		System.out.println("title: "+str);
	    content.setSubTitle(str);
	    Element el_text_hd_info = el_text_hd.getElementsByClass("info").get(0);
	    Element el_source = el_text_hd_info.getElementsByTag("span").get(0);
	    str = el_source.text();
	    System.out.println("source: "+str);
	    content.setSource(str);
	    str = el_text_hd_info.text();
	    str = getTimeFromInfo(str);
	    System.out.println("time: "+str);
	    content.setTime(str);
	    Element el_text_body = el_text.getElementById("ctrlfscont");
	    Elements el_text_p_list = el_text_body.getElementsByTag("p");
	    List<Paragraph>parList = new ArrayList<Paragraph>();
	    List<Picture> picList = new ArrayList<Picture>();
	    int maxLength = handlePTagList(parList, picList, el_text_p_list);
	    System.out.println("max length: "+maxLength);
	    content.setParList(parList);
	    content.setPicList(picList);
	    content.setMaxLength(maxLength);
	    System.out.println("parList length: "+parList.size());
	    System.out.println("picList length: "+picList.size());
		return content;
	}
	
	private String getTimeFromInfo(String str){
		String arr[] =str.split(" ");
//		for(int i=0;i<arr.length;i++){
//			System.out.println(i+": "+arr[i]);
//		}
		return arr[0]+" "+arr[1];
	}

	@Override
	public List<NewsItem> getStocksNewsList() {
		Document doc = DataUtils.doGet(STOCK_URL);
	    List<NewsItem>itemList = new ArrayList<NewsItem>();
	    Element el_main_list = doc.getElementById("mainlist");
	    Elements el_li_list = el_main_list.getElementsByTag("li");
	    int size = el_li_list.size();
	    for(int i=0;i<size;i++){
	    	NewsItem item = new NewsItem();
	    	item.setUrlCode(URL_CODE);
	    	Element el_li = el_li_list.get(i);
	    	Element el_li_a = el_li.getElementsByTag("a").get(0);
	    	String str = el_li_a.text();
	    	System.out.println("title: "+str);
	    	item.setTitle(str);
	    	str = el_li_a.attr("href");
	    	System.out.println("link: "+str);
	    	if(!filtStocksLink(str))continue;
	    	item.setLink(str);
	    	Element el_li_time = el_li.getElementsByTag("span").get(1);
	    	str = el_li_time.text();
	    	System.out.println("time: "+str);
	    	item.setTime(str);
	    	itemList.add(item);
	    }
		return itemList;
	}
	
	private boolean filtStocksLink(String url){//http://stock.stcn.com/2015/0403/
		Pattern p = Pattern.compile("^http://stock.stcn.com/\\d{4}/\\d{4}/.*");
		Matcher matcher = p.matcher(url);
		return matcher.matches();
	}

	@Override
	public NewsContent getStocksNewsContent(NewsItem title) {
		return this.getFocusNewsContent(title);
	}
	
	

}
