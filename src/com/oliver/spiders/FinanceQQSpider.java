package com.oliver.spiders;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

public class FinanceQQSpider extends NewsSpider {
	
	public static final String FOCUS_URL= "http://finance.qq.com/";
	public static final String STOCKS_URL ="http://finance.qq.com/l/stock/gdzx/list2012121283658.htm";
	private static final int URL_CODE=ConstantsForNewsItem.URL_QQ;
	
	@Override
	public void executedRefreshFocus() {
		this.excutedRefreshFocus(this, URL_CODE);
	}
	
	public void executedRefreshStocks(){
		this.excutedRefreshStocksNews(this, URL_CODE);
	}

	@Override
	public List<NewsItem> getFocusNewsList() {
		String json = DataUtils.readJsonString("http://roll.finance.qq.com/interface/roll.php?0.7959656827557133&cata=&site=finance&date=&page=1&mode=1&of=json");
		
		List<NewsItem> items = new ArrayList<NewsItem>();
		try {
			System.out.println("json: "+json);
			System.out.println("size: "+json.length());
			//json.replace('\0', '\n');
			StringBuilder sb = new StringBuilder();
			for(int i=0;i<json.length();i++){
				if((int)(json.charAt(i))!=0&&json.charAt(i)!='\n'){
					sb.append(json.charAt(i));
				}
			}
			json = sb.toString();
			JSONObject obj = new JSONObject(json);
			JSONObject dataObj = obj.getJSONObject("data");
			String html = dataObj.getString("article_info");
			System.out.println("html: "+html);
			Document doc = Jsoup.parseBodyFragment(html);
			Elements el_li_list = doc.getElementsByTag("li");
			int len = el_li_list.size();
			for(int i=0;i<len;i++){
				NewsItem newsItem = new NewsItem();
				newsItem.setUrlCode(URL_CODE);
				Element el_li =el_li_list.get(i);
				System.out.println("li: "+el_li);
				Elements el_span_time_list = el_li.getElementsByClass("t-time");
				if(el_span_time_list.size()==0)continue;
				Element el_span_time = el_span_time_list.get(0);
				String str = el_span_time.text();
				newsItem.setTime(str);
				Elements el_span_a_list = el_li.getElementsByTag("a");
				if(el_span_a_list.size()==0)continue;
				Element el_span_a = el_li.getElementsByTag("a").get(0);
				str = el_span_a.text();
				newsItem.setTitle(str);
				str = el_span_a.attr("href");
				if(!filterLink(str))continue;
				newsItem.setLink(str);
				newsItem.setNewsType(ConstantsForNewsItem.NEWS_ITEM_KIND_FOCUS);
				items.add(newsItem);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return items;
	}
	
	private boolean filterLink(String url){
		Pattern p = Pattern.compile("^http://finance.qq.com/a/\\d{8}/.*");
	    Matcher matcher = p.matcher(url);
		return matcher.matches();
		}

	@Override
	public NewsContent getFocusNewsContent(NewsItem title) {
		System.out.println("analysing link:¡¡"+title.getLink());
		String linkUrl = title.getLink();
		NewsContent content = new NewsContent();
		Document doc = DataUtils.doGet(title.getLink());
		if(doc==null)return null;
		Element el_hd =doc.getElementsByClass("hd").get(0);
		Element el_hd_h1 = el_hd.getElementsByTag("h1").get(0);
		String str = el_hd_h1.text();
		System.out.println("sub title: "+str);
		content.setSubTitle(str);
		Element el_source = el_hd.getElementsByClass("where").get(0);
		str = el_source.text();
		System.out.println("source : "+str);
		content.setSource(str);
		Element el_pub_time = el_hd.getElementsByClass("pubTime").get(0);
		str = el_pub_time.text();
		System.out.println("pub time : "+str);
		content.setTime(str);

		Element el_bd = doc.getElementsByClass("bd").get(0);
		Element el_article_conent = el_bd.getElementById("Cnt-Main-Article-QQ");

		Elements el_article_p_list = el_article_conent.getElementsByTag("p");
		int len = el_article_p_list.size();
		List<Paragraph>parList = new ArrayList<Paragraph>();
		List<Picture> picList = new ArrayList<Picture>();
		int order=0;
		for(int i=0;i<len;i++){
			Element el_article_p = el_article_p_list.get(i);
			String str2 = el_article_p.text();
			if(str2.length()>2){
				order = addParagraph(el_article_p, parList, order);
			}
			Elements el_img_list = el_article_p.getElementsByTag("img");
			if(el_img_list.size()!=0){
				order = savePictures(el_img_list, picList, order,linkUrl);
			}
		}
		content.setMaxLength(order);
		System.out.println("max length : "+order);
		content.setParList(parList);
		System.out.println("parlist size : "+parList.size());
		content.setPicList(picList);
		System.out.println("piclist size : "+picList.size());
		content.setTitleId(title.getId());
		return content;
	}

	@Override
	public List<NewsItem> getStocksNewsList() {//
		List<NewsItem> itemList = new ArrayList<NewsItem>();
		Document doc = DataUtils.doGet(STOCKS_URL);
		Element el_list_div= doc.getElementsByClass("newslist").get(0);
		Elements el_list = el_list_div.getElementsByTag("li");
		int size = el_list.size();
		for(int i=0;i<size;i++){
			NewsItem item = new NewsItem();
			item.setUrlCode(URL_CODE);
			Element el_li = el_list.get(i);
			Element el_li_a = el_li.getElementsByTag("a").get(0);
			String str = el_li_a.text();
			System.out.println("title: "+str);
			item.setTitle(str);
			str = el_li_a.attr("href");
			System.out.println("link: "+str);
			item.setLink(str);
			itemList.add(item);
		}
		return itemList;
	}

	@Override
	public NewsContent getStocksNewsContent(NewsItem title) {
		return this.getFocusNewsContent(title);
	}

}
