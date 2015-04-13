package com.oliver.spiders;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.oliver.models.NewsContent;
import com.oliver.models.NewsItem;
import com.oliver.models.Paragraph;
import com.oliver.models.Picture;
import com.oliver.constants.ConstantsForNewsItem;
import com.oliver.http.DataUtils;

public class FinanceSinaSpider extends NewsSpider{

	private static final String SINA_FOCUS_URL="http://finance.sina.com.cn/money/";
	private static final String STOCK_URL="http://top.finance.sina.com.cn/ws/GetTopDataList.php?top_not_url=/ustock/&top_type=day&top_cat=stock&top_time=20150405&top_show_num=20&top_order=ASC&js_var=stock_1_data";
	private static final String ROOT_URL ="http://finance.sina.com.cn/stock/";
	@Override
	public void executedRefreshFocus() {
		this.excutedRefreshFocus(this, SINA_FOCUS_URL);
	}
	
	public void executedRefreshStocks(){
		this.excutedRefreshStocksNews(this, ROOT_URL);
	}

	@Override
	public List<NewsItem> getFocusNewsList() {
		Document doc = DataUtils.doGet(SINA_FOCUS_URL);
		List<NewsItem> itemList = new ArrayList<NewsItem>();
		Elements el_news_item_list = doc.getElementsByClass("news-item");
		int len=el_news_item_list.size();
		System.out.println("news item list size: "+len);
		for(int i=0;i<len;i++){
			NewsItem  item = new NewsItem();
			Element el_news_item = el_news_item_list.get(i);
			Elements el_h2_list = el_news_item.getElementsByTag("h2");
			if(el_h2_list.size()==0)continue;
			Element el_h2 =el_h2_list.get(0);
			Element el_h2_a = el_h2.getElementsByTag("a").get(0);
			String str = el_h2_a.text();
			item.setTitle(str);
			str = el_h2_a.attr("href");
			if(!linkFilter(str))continue;
			item.setLink(str);
			Element el_time = el_news_item.getElementsByClass("time").get(0);
			str = el_time.text();
			item.setTime(str);
			item.setNewsType(ConstantsForNewsItem.NEWS_ITEM_KIND_FOCUS);
			itemList.add(item);
		}
		return itemList;
	}
	
	private boolean linkFilter(String url){
		Pattern p = Pattern.compile("^http://finance.sina.com.cn/money/lczx/.*");
		Matcher matcher = p.matcher(url);
		return matcher.matches();
	}

	@Override
	public NewsContent getFocusNewsContent(NewsItem title) {
		System.out.println("analysing link:"+title.getLink());
		String linkUrl = title.getLink();
		Document doc = DataUtils.doGet(title.getLink());
		NewsContent content = new NewsContent();
		Element el_title = doc.getElementById("artibodyTitle");
		String str = el_title.text();
		System.out.println("article title: "+str);
		content.setSubTitle(str);
		Element el_time = doc.getElementById("pub_date");
		str = el_time.text();
		System.out.println("article time: "+str);
		content.setTime(str);
		Element el_media_span = doc.getElementById("media_name");
		str = el_media_span.text();//.getElementsByTag("a");
		//str = el_media_span_a.text();
		str = str.replace("Î¢²©", "").trim();
		System.out.println("article source: "+str);
		content.setSource(str);
		
		Element el_article_body = doc.getElementById("artibody");
		
		Elements el_article_p_list = replaceDivWithP(el_article_body);// el_article_body.getElementsByTag("p");
		int len = el_article_p_list.size();
		int order=0;
		List<Paragraph> parList = new ArrayList<Paragraph>();
		List<Picture> picList = new ArrayList<Picture>();
		for(int i=0;i<len;i++){
			Element el_article_p = el_article_p_list.get(i);
			String str1 = el_article_p.text();
			if(str1!=null&&str1.length()>3){
				order = addParagraph(el_article_p, parList, order);
			}
			Elements el_actical_img_list = el_article_p.getElementsByTag("img");
			if(el_actical_img_list.size()!=0){
				order = savePictures(el_actical_img_list, picList, order,linkUrl);
			}
		}
		content.setMaxLength(order);
		System.out.println("content max length: "+order);
		System.out.println("content par length: "+parList.size());
		System.out.println("content pic length: "+picList.size());
		content.setParList(parList);
		content.setPicList(picList);
		return content;
	}
	
	private Elements replaceDivWithP(Element el_text_body){
		String bodyStr = el_text_body.toString();
		int firstLeft = bodyStr.indexOf(">");
		int lastRight = bodyStr.lastIndexOf("<");
		String mid = bodyStr.substring(firstLeft+1,lastRight);
		mid = mid.replace("div", "p");
		String newBody = bodyStr.substring(0,firstLeft+1)+mid+bodyStr.substring(lastRight,bodyStr.length());
		//System.out.println("new body: "+newBody);
		Document doc = Jsoup.parse(newBody);
		return doc.getElementsByTag("p");
	}

	@Override
	public List<NewsItem> getStocksNewsList() {
		List<NewsItem>itemList = new ArrayList<NewsItem>();
		String json = DataUtils.readJsonString(STOCK_URL);
		int start = json.indexOf("{");
		json = json.substring(start);
		System.out.println("json: "+json);
		try {
			JSONObject jsonObj = new JSONObject(json);
			JSONArray jsonArr = jsonObj.getJSONArray("data");
			int len = jsonArr.length();
			for(int i=0;i<len;i++){
				NewsItem item = new NewsItem();
				JSONObject subObject = jsonArr.getJSONObject(i);
				String str = subObject.getString("title");
			//	System.out.println("title: "+str);
				item.setTitle(str);
				
				str = subObject.getString("url");
				//System.out.println("url: "+str);
				
				item.setLink(str);
				itemList.add(item);
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//System.out.println(json);
		return itemList;
	}

	@Override
	public NewsContent getStocksNewsContent(NewsItem title) {
	   return this.getFocusNewsContent(title);
	}

}
