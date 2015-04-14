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
import com.oliver.spiders.utils.StrUtils;

public class FinanceHeXunSpider extends NewsSpider{

	public static final String FOCUS_URL = "http://funds.hexun.com/";
	public static final String STOCK_URL ="http://stock.hexun.com/";
	private static final int URL_CODE=ConstantsForNewsItem.URL_HEXUN;
	
	@Override
	public void executedRefreshFocus() {
		this.excutedRefreshFocus(this, URL_CODE);
	}
	
	

	@Override
	public void executedRefreshStocks() {
		this.excutedRefreshStocksNews(this, URL_CODE);
	}



	@Override
	public List<NewsItem> getFocusNewsList() {
		Document doc = DataUtils.doGet(FOCUS_URL);
		List<NewsItem> newsItems = new ArrayList<NewsItem>();
		Element el_left_panel = doc.getElementById("gpmod01_con1");
		Elements el_h2_list = el_left_panel.getElementsByTag("h2");
		int len = el_h2_list.size();
		for(int i=0;i<len;i++){
			NewsItem item = new NewsItem();
			item.setUrlCode(URL_CODE);
			item.setNewsType(ConstantsForNewsItem.NEWS_ITEM_KIND_FOCUS);
			Element el_h2 = el_h2_list.get(i);
			Element el_h2_a = el_h2.getElementsByTag("a").get(0);
			String str = el_h2.text();
			item.setTitle(str);
			str = el_h2_a.attr("href");
			if(!filtLink(str))continue;
			item.setLink(str);
			newsItems.add(item);
		}
		Elements el_li_list = el_left_panel.getElementsByTag("li");
		len = el_li_list.size();
		for(int i=0;i<len;i++){
			NewsItem item = new NewsItem();
			item.setNewsType(ConstantsForNewsItem.NEWS_ITEM_KIND_FOCUS);
			Element el_li = el_li_list.get(i);
			Element el_li_a = el_li.getElementsByTag("a").get(0);
			String str = el_li_a.text();
			item.setTitle(str);
			str = el_li_a.attr("href");
			if(!filtLink(str))continue;
			item.setLink(str);
			newsItems.add(item);
		}
		return newsItems;
	}
	
	private boolean filtLink(String url){
		Pattern p = Pattern.compile("^http://funds.hexun.com/\\d{4}-\\d{2}-\\d{2}/.*");
		Matcher matcher = p.matcher(url);
		return matcher.matches();
	}

	@Override
	public NewsContent getFocusNewsContent(NewsItem title) {
		System.out.println("analysing link: "+title.getLink());
		NewsContent content = new NewsContent();
		content.setTitleId(title.getId());
		String url = StrUtils.getUrlWithoutHtmFileTail(title.getLink());
		Document doc = DataUtils.doGet(title.getLink());
		if(doc==null)return null;
		Element el_text_panel = doc.getElementsByClass("text_panel").get(0);
		Element el_title_div = el_text_panel.getElementById("artibodyTitle");
		Element el_title_h1  = el_title_div.getElementsByTag("h1").get(0);
		String str = el_title_h1.text();
		System.out.println("title:　"+str);
		content.setSubTitle(str);
		Element el_source_span = el_text_panel.getElementById("artibodyDesc");
		Element el_source_time = el_source_span.getElementById("pubtime_baidu");
		str = el_source_time.text();
		System.out.println("time:　"+str);
		content.setTime(str);
		Element el_source_baidu = el_source_span.getElementById("source_baidu");
		Element el_source_a = el_source_baidu.getElementsByTag("a").get(0);
		str = el_source_a.text();
		System.out.println("source:　"+str);
		content.setSource(str);
		Element el_arti_body = el_text_panel.getElementById("artibody");
		Elements el_arti_body_p_list = el_arti_body.getElementsByTag("p");
		int len = el_arti_body_p_list.size();
		List<Paragraph> parList = new ArrayList<Paragraph>();
		List<Picture> picList = new ArrayList<Picture>();
		int order= 0;
		for(int i=0;i<len;i++){
			Element el_arti_body_p = el_arti_body_p_list.get(i);
			str = el_arti_body_p.text();
			if(str.length()>2){
				order  = addParagraph(el_arti_body_p, parList, order);
			}
			Elements el_arti_body_p_img_list = el_arti_body_p.getElementsByTag("img");
		    if(el_arti_body_p_img_list.size()>0){
		    	order = savePictures(el_arti_body_p_img_list,
		    			picList, order, url);
		    }
		}
		System.out.println("parList size:　"+parList.size());
		System.out.println("picList size:　"+picList.size());
		System.out.println("max length:　"+order);
		content.setPicList(picList);
		content.setParList(parList);
		System.out.println("parList : "+parList);
		content.setMaxLength(order);
		return content;
	}

	@Override
	public List<NewsItem> getStocksNewsList() {
		List<NewsItem> itemList = new ArrayList<NewsItem>();
		Document doc = DataUtils.doGet(STOCK_URL);
		Element el_list_div = doc.getElementsByClass("c_screenL").get(0);
		Elements el_a_list = el_list_div.getElementsByTag("a");
		int size = el_a_list.size();
		for(int i=0;i<size;i++){
			NewsItem item = new NewsItem();
			item.setUrlCode(URL_CODE);
			Element el_a = el_a_list.get(i);
			String str = el_a.text();
			System.out.println("title: "+str);
			item.setTitle(str);
			str = el_a.attr("href");
			System.out.println("link: "+str);
			if(!filtStockLink(str))continue;
			item.setLink(str);
			itemList.add(item);
		}
		return itemList;
	}
	
	private boolean filtStockLink(String url){//http://stock.hexun.com/2015-04-03/
		Pattern p = Pattern.compile("^http://stock.hexun.com/\\d{4}-\\d{2}-\\d{2}/.*");
		Matcher matcher = p.matcher(url);
		return matcher.matches();
	}
	

	@Override
	public NewsContent getStocksNewsContent(NewsItem title) {
		return this.getFocusNewsContent(title);
	}

}
